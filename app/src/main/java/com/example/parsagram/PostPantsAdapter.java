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

public class PostPantsAdapter extends RecyclerView.Adapter<PostPantsAdapter.ViewHolder> {

    private Context context;
    private List<PostPants> pants;

    public PostPantsAdapter(Context context, List<PostPants> pants) {
        this.context = context;
        this.pants = pants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostPants pant = pants.get(position);
        holder.bind(pant);
    }

    @Override
    public int getItemCount() {
        return pants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPost = itemView.findViewById(R.id.ivPost);
        }

        public void bind(PostPants pant) {
            ParseFile image = pant.getImage();
            if(image != null)
                Glide.with(context).load(pant.getImage().getUrl()).into(ivPost);
        }
    }
    public void clear() {
        pants.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<PostPants> list) {
        pants.addAll(list);
        notifyDataSetChanged();
    }
}
