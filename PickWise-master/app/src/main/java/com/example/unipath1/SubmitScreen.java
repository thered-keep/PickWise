package com.example.unipath1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class SubmitScreen extends AppCompatActivity {

    TextView prof_name_view;
    ImageView prof_img_view;

    int prof_id, subject_id;
    String prof_name, prof_img;
    Button returnButton, submitButton;

    double  prof_lecture_rating, prof_lab_rating, prof_exam_rating, prof_helpfulness_rating;
    String student_opinion;
    RatingBar prof_lecture_rating_bar, prof_lab_rating_bar, prof_exam_rating_bar, prof_helpfulness_rating_bar;
    EditText opinion;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_screen);

        prof_name_view = findViewById(R.id.nameTxt);
        prof_img_view = findViewById(R.id.prof_img);
        opinion = findViewById(R.id.opinion);

        submitButton = findViewById(R.id.rateBtn);
        Intent intent = getIntent();
        prof_name = intent.getStringExtra("prof_name");
        prof_img = intent.getStringExtra("prof_img");
        int student_id = intent.getIntExtra("student_id", 1);
        subject_id = intent.getIntExtra("subject_id", 1);
        prof_id = intent.getIntExtra("prof_id", 1);


        prof_name_view.setText("Prof.Dr " + prof_name);
        if (prof_img == null)
            prof_img_view.setImageResource(R.drawable.default_profile_pic);
        else {
            Glide.with(this).asBitmap().load(prof_img).into(prof_img_view);
        }
        returnButton = findViewById(R.id.returnButton);

        prof_lecture_rating_bar = findViewById(R.id.lecture_bar);
        prof_lab_rating_bar = findViewById(R.id.lab_bar);
        prof_exam_rating_bar = findViewById(R.id.exam_bar);
        prof_helpfulness_rating_bar = findViewById(R.id.help_bar);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prof_lecture_rating = prof_lecture_rating_bar.getRating();
                prof_exam_rating = prof_exam_rating_bar.getRating();
                prof_helpfulness_rating = prof_helpfulness_rating_bar.getRating();
                prof_lab_rating = prof_lab_rating_bar.getRating();
                student_opinion = String.valueOf(opinion.getEditableText());
                Feedback feedback = new Feedback(student_opinion, student_id, prof_id, subject_id, prof_lecture_rating, prof_lab_rating, prof_exam_rating, prof_helpfulness_rating);
                DataBaseHelper db = new DataBaseHelper(getBaseContext());
                db.update_or_add_feedback(feedback);
                Toast toast = Toast.makeText(getBaseContext(), "Submitted", Toast.LENGTH_SHORT);
                toast.show();
                onBackPressed();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(RatingScreen.intent);
    }
}