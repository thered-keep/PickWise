package com.example.unipath1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PickProfAdapter extends RecyclerView.Adapter<PickProfAdapter.MyViewHolder> {
    Context context;

    ArrayList<Professor> professors;

    String current_subject;
    int student_id;
    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public PickProfAdapter(Context context, ArrayList<Professor> professors, String current_subject) {
        this.context = context;
        this.professors = professors;
        this.current_subject = current_subject;
    }

    @NonNull
    @Override
    public PickProfAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.prof_recycler_view_row,parent,false);
        return new PickProfAdapter.MyViewHolder(view);
    }

    //assigning values back to the views when restored based on position
    @Override
    public void onBindViewHolder(@NonNull PickProfAdapter.MyViewHolder holder, int position) {
        holder.name.setText("Prof.Dr " + professors.get(position).getName());
        holder.email.setText((professors.get(position).getEmail()));
        holder.phone.setText((professors.get(position).getPhone()));
        DataBaseHelper dataBaseHelper =new DataBaseHelper(context.getApplicationContext());
        int subject_id=dataBaseHelper.getSubjectId(current_subject);
        double prof_rating =dataBaseHelper.getProfAvgRatingInSubject(professors.get(position).getId(), subject_id)[4];

        holder.rating.setText(String.valueOf(new DecimalFormat("##.#").format(prof_rating)   ));

        if (professors.get(position).getUrl_img() == null)
            holder.prof_img.setImageResource(R.drawable.default_profile_pic);
        else {
            Glide.with(context)
                    .asBitmap()
                    .load(professors.get(position).getUrl_img())
                    .into(holder.prof_img);
        }

        holder.profCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.profCardView.getContext(), RatingScreen.class);
                intent.putExtra("current_subject", current_subject);
                intent.putExtra("prof_id", professors.get(holder.getAdapterPosition()).getId());
                intent.putExtra("prof_name", professors.get(holder.getAdapterPosition()).getName());
                intent.putExtra("prof_img", professors.get(holder.getAdapterPosition()).getUrl_img());
                intent.putExtra("prof_rating", professors.get(holder.getAdapterPosition()).getRating());
                intent.putExtra("student_id",student_id);
                holder.profCardView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return professors.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView prof_img;
        TextView name , email, phone , rating;
        CardView profCardView;
        public MyViewHolder(@NonNull View view) {
            super(view);
            prof_img = view.findViewById(R.id.photo_1);
            name = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
            phone = view.findViewById(R.id.phone);
            rating = view.findViewById(R.id.score);
            profCardView = view.findViewById(R.id.profCardView);


        }
    }
}
