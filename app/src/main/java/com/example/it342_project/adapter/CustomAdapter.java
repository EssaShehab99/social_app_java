package com.example.it342_project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.it342_project.models.Post;

import com.example.it342_project.R;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    List<Post> postList;
    private static ClickListener clickListener;
    private Context context;

    public CustomAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView text;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            text = itemView.findViewById(R.id.text_post);
        }


        @Override
        public void onClick(View v) {
            try {
                clickListener.onItemClick(getAdapterPosition(), v);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        void setPost(String value) {
            text.setText(value);
        }

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View listViewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, null);
        return new MyViewHolder(listViewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.setPost(post.text);
    }
    @Override
    public int getItemCount() {
        return postList.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View v) throws Exception;
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        CustomAdapter.clickListener = clickListener;
    }
}
