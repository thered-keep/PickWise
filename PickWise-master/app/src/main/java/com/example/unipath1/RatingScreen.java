package com.example.unipath1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RatingScreen extends AppCompatActivity {

    Button rateBtn , returnButton ;
    TextView prof_name_view;
    ImageView prof_img_view;
    public static Intent intent ;
    RatingBar rating_Bar, prof_lecture_rating_bar, prof_lab_rating_bar, prof_exam_rating_bar, prof_helpfulness_rating_bar;

    ArrayList<Feedback> feedbacks = new ArrayList<>();
    int prof_id, subject_id , student_id;
    String prof_name, prof_img;
    double prof_rating, prof_lecture_rating, prof_lab_rating, prof_exam_rating, prof_helpfulness_rating;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_screen);
        intent = getIntent();
        rateBtn = findViewById(R.id.rateBtn);
        prof_name_view = findViewById(R.id.nameTxt);
        prof_img_view = findViewById(R.id.prof_img);
        rating_Bar = findViewById(R.id.ratingBar);
        prof_lecture_rating_bar = findViewById(R.id.lecture_bar);
        prof_lab_rating_bar = findViewById(R.id.lab_bar);
        prof_exam_rating_bar = findViewById(R.id.exam_bar);
        prof_helpfulness_rating_bar = findViewById(R.id.help_bar);

        //fillStudentFeedback();
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        subject_id = dataBaseHelper.getSubjectId(intent.getStringExtra("current_subject"));
        prof_id = intent.getIntExtra("prof_id", 20);
        prof_name = intent.getStringExtra("prof_name");
        prof_img = intent.getStringExtra("prof_img");
        student_id = intent.getIntExtra("student_id",1);


        prof_name_view.setText("Prof.Dr " + prof_name);
        if (prof_img == null)
            prof_img_view.setImageResource(R.drawable.default_profile_pic);
        else {
            Glide.with(this).asBitmap().load(prof_img).into(prof_img_view);
        }

        double[] ratings = dataBaseHelper.getProfAvgRatingInSubject(prof_id, subject_id);
        prof_lecture_rating = ratings[0];
        prof_lab_rating = ratings[1];
        prof_exam_rating = ratings[2];
        prof_helpfulness_rating = ratings[3];
        prof_rating = ratings[4];


        rating_Bar.setRating((float) prof_rating);
        prof_lecture_rating_bar.setRating((float) prof_lecture_rating);
        prof_lab_rating_bar.setRating((float) prof_lab_rating);
        prof_exam_rating_bar.setRating((float) prof_exam_rating);
        prof_helpfulness_rating_bar.setRating((float) prof_helpfulness_rating);

        feedbacks = dataBaseHelper.getFeedbacks(prof_id, subject_id);



        RecyclerView feedbackRecycleView = findViewById(R.id.feedbackRecyclerView);
        FeedbackAdapter feedback_adapter = new FeedbackAdapter(this, feedbacks);
        feedbackRecycleView.setAdapter(feedback_adapter);
        feedbackRecycleView.setLayoutManager(new LinearLayoutManager(this));





        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dataBaseHelper.allowToRate(student_id,prof_id,subject_id)){
                    Toast.makeText(getBaseContext(), "You musst have first studied under this Prof !", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(v.getContext(), SubmitScreen.class);
                intent.putExtra("prof_id", prof_id);
                intent.putExtra("prof_name", prof_name);
                intent.putExtra("prof_img", prof_img);
                intent.putExtra("subject_id", subject_id);
                intent.putExtra("student_id",student_id);
                // student_id
                startActivity(intent);
            }
        });


        returnButton=findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(PickProfScreen.intent);
    }
}