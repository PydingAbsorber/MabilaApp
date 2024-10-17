package com.example.myapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>{

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row,parent,false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class NewsHolder extends RecyclerView.ViewHolder{

        ImageView cover,avatar;
        TextView title,category,publisher;
        int likes, comments;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
