package com.example.parsagram.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parsagram.R;
import com.example.parsagram.models.PostShirt;
import com.parse.ParseFile;

import java.util.List;

public class ViewShirtAdapter extends RecyclerView.Adapter<ViewShirtAdapter.ViewHolder> {

    private Context context;
    private List<PostShirt> shirts;

    public ViewShirtAdapter(Context context, List<PostShirt> shirts) {
        this.context = context;
        this.shirts = shirts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wardrobe_item, parent, false);
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

        private ImageView ivPost;
        private TextView tvColor;
        private TextView tvLength;
        private TextView tvThickness;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPost = itemView.findViewById(R.id.ivPost);
            tvColor = itemView.findViewById(R.id.tvColor);
            tvLength = itemView.findViewById(R.id.tvLength);
            tvThickness = itemView.findViewById(R.id.tvThickness);
        }

        public void bind(PostShirt shirt) {
            ParseFile image = shirt.getImage();
            if(image != null)
                Glide.with(context).load(shirt.getImage().getUrl()).into(ivPost);
            tvColor.setText(shirt.getColor());
            tvLength.setText(shirt.getLength());
            tvThickness.setText(shirt.getThickness());
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
