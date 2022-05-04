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
import com.example.parsagram.models.PostPants;
import com.parse.ParseFile;

import java.util.List;

public class ViewPantsAdapter extends RecyclerView.Adapter<ViewPantsAdapter.ViewHolder> {

    private Context context;
    private List<PostPants> pants;

    public ViewPantsAdapter(Context context, List<PostPants> pants) {
        this.context = context;
        this.pants = pants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wardrobe_item, parent, false);
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

        public void bind(PostPants pant) {
            ParseFile image = pant.getImage();
            if(image != null)
                Glide.with(context).load(pant.getImage().getUrl()).into(ivPost);
            tvColor.setText(pant.getColor());
            tvLength.setText(pant.getLength());
            tvThickness.setText(pant.getThickness());
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
