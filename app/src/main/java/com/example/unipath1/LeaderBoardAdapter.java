package com.example.unipath1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.MyViewHolder> {
    private ArrayList<Professor> topProfs;
    int[] rank_images = {R.drawable.gold_medal, R.drawable.silver_medal, R.drawable.bronze_medal};

    Context context;

    public LeaderBoardAdapter(Context context,ArrayList<Professor> topProfs) {
        this.context=context ;
        this.topProfs = topProfs;
    }

    @NonNull
    @Override
    public LeaderBoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_recycler_view_row, parent, false);
        return new LeaderBoardAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardAdapter.MyViewHolder holder, int position) {
        if(position<3)  holder.rank.setImageResource(rank_images[position]);
        holder.prof_img.setImageResource(topProfs.get(position).getImage());
        holder.prof_name.setText(topProfs.get(position).getName());
        holder.ratingBar.setRating((float) topProfs.get(position).getRating());
        if (topProfs.get(position).getUrl_img() == null)
            holder.prof_img.setImageResource(R.drawable.default_profile_pic);
        else {
            Glide.with(context)
                    .asBitmap()
                    .load(topProfs.get(position).getUrl_img())
                    .into(holder.prof_img);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView rank, prof_img;
        TextView prof_name;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rankingimg);
            prof_img = itemView.findViewById(R.id.profimg);
            prof_name = itemView.findViewById(R.id.profname);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
