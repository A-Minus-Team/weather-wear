package com.example.parsagram;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostShirtAdapter extends RecyclerView.Adapter<PostShirtAdapter.ViewHolder> {

    private Context context;
    private List<PostShirt> shirts;

    public PostShirtAdapter(Context context, List<PostShirt> shirts) {
        this.context = context;
        this.shirts = shirts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostShirt shirt = shirts.get(position);
        holder.bind(shirt);
    }

    @Override
    public int getItemCount() {
        return shirts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // UPDATE THIS WITH CORRECT FIELDS
        private ImageView ivPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPost = itemView.findViewById(R.id.ivPost);
        }

        public void bind(PostShirt shirt) {
            ParseFile image = shirt.getImage();
            if(image != null)
                Glide.with(context).load(shirt.getImage().getUrl()).into(ivPost);
        }
    }
    public void clear() {
        shirts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<PostShirt> list) {
        shirts.addAll(list);
        notifyDataSetChanged();
    }
}
