package com.example.unipath1;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class PickProfScreen extends AppCompatActivity {

    ArrayList<Professor> professors = new ArrayList<>();

    DataBaseHelper dataBaseHelper;
    public static Intent intent ;
    String receiver_subject;
    Button returnButton;
    int student_id;

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_prof_screen);
        intent = getIntent();
        RecyclerView rec_view =findViewById(R.id.my_rec_view);

        dataBaseHelper = new DataBaseHelper(this);
        Intent intent = getIntent();
        receiver_subject = intent.getStringExtra("clicked_subject");
        student_id =intent.getIntExtra("student_id",1);

        professors = dataBaseHelper.getProfessors(receiver_subject);

        PickProfAdapter adapter = new PickProfAdapter(this, professors, receiver_subject);
        adapter.setStudent_id(student_id);
        rec_view.setAdapter(adapter);
        rec_view.setLayoutManager(new LinearLayoutManager(this));
        returnButton =findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        startActivity(HomeFragment.intent);
    }
}