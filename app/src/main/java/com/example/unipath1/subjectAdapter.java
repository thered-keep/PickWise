package com.example.unipath1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class subjectAdapter extends RecyclerView.Adapter<subjectAdapter.MyViewHolder> {
    private ArrayList<Subject> subjects;
    private int student_id ;

    public subjectAdapter(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    @NonNull
    @Override
    public subjectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_recycler_view_row, parent, false);
        return new subjectAdapter.MyViewHolder(view);
    }


    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public void onBindViewHolder(@NonNull subjectAdapter.MyViewHolder holder, int position) {
        holder.subBtn.setText(subjects.get(position).getName());
        holder.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.clicked_subject = holder.subBtn.getText().toString();

                Intent intent = new Intent(holder.subBtn.getContext(), PickProfScreen.class);
                intent.putExtra("clicked_subject", holder.clicked_subject);
                intent.putExtra("student_id",student_id);
                holder.subBtn.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        Button subBtn;
        String clicked_subject;
        public MyViewHolder(@NonNull View view) {
            super(view);

            subBtn = view.findViewById(R.id.subBtn);

        }
    }

}
