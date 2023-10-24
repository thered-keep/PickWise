package com.example.unipath1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(@Nullable Context context) {
        super(context, "mainDataBase.db", null, 1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public ArrayList<Subject> getSubjects(int semesterId) {
        ArrayList<Subject> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM Subject WHERE semester_id = " + semesterId + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                String subjectName = cursor.getString(1);
                Subject subject = new Subject(subjectName, semesterId);
                returnList.add(subject);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public ArrayList<Professor> getProfessors(String subject) {
        ArrayList<Professor> returnList = new ArrayList<>();

        String queryString = "SELECT Professor.prof_id, prof_name, email, phone, image, rating FROM  Professor JOIN teaches ON Professor.prof_id = teaches.prof_id JOIN Subject ON teaches.subject_id = Subject.subject_id WHERE Subject.subject_name = " + "'"+subject+"'" + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String email = cursor.getString(2);
                String phone = cursor.getString(3);
                String url_img = cursor.getString(4);
                double rating = cursor.getDouble(5);
                Professor prof = new Professor(id, name, email, phone, url_img, rating);
                returnList.add(prof);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public int checkUserData(String student_id, String password) {
        String queryString = "SELECT * FROM Student WHERE student_id = " + "'"+ student_id + "'"+ " AND password = " + "'"+password+"'" + " ;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst())
            return cursor.getInt(3);
        return -1;
    }

    public String getStudentName(String student_id) {
        String queryString = "SELECT * FROM Student WHERE student_id = " + student_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        String st_name = cursor.getString(2);
        db.close();
        cursor.close();
        return st_name;
    }

    public int getSubjectId(String subject_name) {
        String queryString = "SELECT * FROM Subject WHERE subject_name = " + "'" + subject_name + "'" + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        int sub_id=cursor.getInt(0);
        db.close();
        cursor.close();
        return sub_id ;
    }

    public ArrayList<Feedback> getFeedbacks(int prof_id, int subject_id) {
        ArrayList<Feedback> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM Feedback WHERE prof_id = " + prof_id + " AND subject_id = " + subject_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                double lecture_rating = cursor.getDouble(3);
                double lab_rating = cursor.getDouble(4);
                double exam_rating = cursor.getDouble(5);
                double help_rating = cursor.getDouble(6);
                String opinion = cursor.getString(7);
                Feedback feedback = new Feedback(opinion, lecture_rating, lab_rating, exam_rating, help_rating);
                returnList.add(feedback);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public double[] getProfAvgRatingInSubject(int prof_id, int subject_id) {
        String queryString = "SELECT AVG(lecture_rating), AVG(lab_rating), AVG(exam_rating), AVG(helpfulness_rating) FROM Feedback WHERE prof_id = "+ prof_id +" AND subject_id = " + subject_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        double lecture_rating = cursor.getDouble(0);
        double lab_rating = cursor.getDouble(1);
        double exam_rating = cursor.getDouble(2);
        double help_rating = cursor.getDouble(3);

        cursor.close();
        db.close();
        return new double[]{lecture_rating, lab_rating, exam_rating, help_rating, (lecture_rating+lab_rating+exam_rating+help_rating)/4};
    }
    public double getProfAvgRatingGeneral(int prof_id) {
        String queryString = "SELECT AVG(lecture_rating), AVG(lab_rating), AVG(exam_rating), AVG(helpfulness_rating) FROM Feedback WHERE prof_id = "+ prof_id  +  ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        double lecture_rating = cursor.getDouble(0);
        double lab_rating = cursor.getDouble(1);
        double exam_rating = cursor.getDouble(2);
        double help_rating = cursor.getDouble(3);
        double result = (lecture_rating + lab_rating+exam_rating+help_rating) / 4;
        cursor.close();
        db.close();
        return result;
    }
    public void update_avg_rating_in_general(int prof_id){
        double result = this.getProfAvgRatingGeneral(prof_id);
        String queryString = "update Professor SET rating = " + result +" WHERE prof_id = "+prof_id + " ;";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(queryString);

        db.close();
    }
    public void update_or_add_feedback(Feedback feedback){
        String opinion =feedback.getOpinion();
        double lecture_rating =feedback.getLecture_rating();
        double lab_rating =feedback.getLab_rating();
        double exam_rating =feedback.getExam_rating();
        double helpfulness_rating=feedback.getHelpfulness_rating();
        int student_id =feedback.getStudent_id();
        int prof_id =feedback.getProf_id();
        int subject_id=feedback.getSubject_id();

        String queryString = "REPLACE INTO Feedback values ( " + student_id + " , "+ subject_id + " , "+ prof_id + " , "+ lecture_rating + " , "+lab_rating + " , "+ exam_rating + " , "+ helpfulness_rating + " , ' "+ opinion + " ' )" + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        cursor.moveToFirst();
        cursor.close();
        db.close();
        update_avg_rating_in_general(prof_id);
    }

    public Student getStudent(int student_id){
        String queryString = "SELECT * FROM Student WHERE student_id = " + student_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        Student student = new Student();
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(2);
                String nationality = cursor.getString(5);
                String address = cursor.getString(6);
                int age = cursor.getInt(7);
                String uni = cursor.getString(8);
                String url_img = cursor.getString(4);
                String deg = cursor.getString(9);

                student = new Student(name,uni,deg,nationality,age,url_img,student_id,address);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }
    public int getNumberOfCompletedSubjects(int semester_id , int student_id){
        String queryString = "SELECT count(*) FROM studies JOIN Subject on Subject.subject_id = studies.subject_id where semester_id != " +semester_id + " and student_id = " +student_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        int subjects_number =0;
        if (cursor.moveToFirst()) {subjects_number = cursor.getInt(0); }
        db.close();
        cursor.close();
        return subjects_number;
    }
    public int getNumberInProgressSubjects(int semester_id , int student_id){
        String queryString = "SELECT count(*) FROM studies JOIN Subject on Subject.subject_id = studies.subject_id where semester_id = " +semester_id + " and student_id = " +student_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        int subjects_number =0;
        if (cursor.moveToFirst()) {subjects_number = cursor.getInt(0); }
        db.close();
        cursor.close();
        return subjects_number;
    }
    public ArrayList<Professor> getTopProfs(){
        int n=0;
        ArrayList<Professor> profs =new ArrayList<>();
        String queryString = "select prof_name , image , rating ,ranking   from Professor  order  BY rating  desc  ; " ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                n++;
                String name = cursor.getString(0);
                String image = cursor.getString(1);
                double rating = cursor.getDouble(2);
                int ranking = cursor.getInt(3);

                Professor prof  = new Professor(name,image,rating,ranking);
                profs.add(prof);

            }
            while (cursor.moveToNext() && n<10);
        }
        cursor.close();
        db.close();
        return  profs;
    }
    public boolean allowToRate(int stud_id, int prof_id, int subject_id){
        String queryString = "SELECT * FROM studies where subject_id  = " +subject_id + " and prof_id = " +prof_id + " and student_id = " + stud_id + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        boolean is_allowed = cursor.moveToFirst();
        cursor.close();
        db.close();
        return is_allowed;
    }

}
