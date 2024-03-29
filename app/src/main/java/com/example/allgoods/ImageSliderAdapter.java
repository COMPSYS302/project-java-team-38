package com.example.allgoods;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder> {

    private List<Uri> imageUris;
    private Context context;

    public ImageSliderAdapter(Context context) {
        this.context = context;
        this.imageUris = new ArrayList<>();
    }

    public void setImageUris(List<Uri> imageUris) {
        this.imageUris = imageUris;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Glide.with(context)
                .load(imageUris.get(position)) // Load the image from the URI at the current position
                .error(R.drawable.rohan_image_circle) // Specify your custom error placeholder drawable
                .into(holder.imageView); // Set the loaded image into the ImageView of the current holder
    }


    @Override
    public int getItemCount() {
        return imageUris.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public List<Uri> getImageUris() {
        return new ArrayList<>(imageUris); // Return a copy of the current list of image URIs
    }

}