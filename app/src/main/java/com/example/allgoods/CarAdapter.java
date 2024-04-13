package com.example.allgoods;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private static CarAdapter instance;
    private Context context;
    private List<CarListing> carListings;

    // Private constructor
    private CarAdapter(Context context) {
        this.context = context;
        this.carListings = new ArrayList<>(); // Initialize with an empty list
    }

    // Singleton getInstance method
    public static CarAdapter getInstance(Context context) {
        if (instance == null) {
            instance = new CarAdapter(context);
        }
        return instance;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_listing, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        CarListing carListing = carListings.get(position);
        holder.tvCarMakeModel.setText(carListing.getCar().getMake() + " " + carListing.getCar().getModel());
        holder.tvCarYear.setText("Year: " + carListing.getCar().getYear());
        holder.tvCarPrice.setText("$" + carListing.getPrice());
        holder.tvCarOdo.setText("Odo: " + carListing.getCar().getOdo() + " Km");
        // Set the car photo if you have one
        holder.ivCarPhoto.setImageURI(carListing.getFirstImage()); // Assuming getFirstImage() returns a String
    }

    @Override
    public int getItemCount() {
        return carListings.size();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCarPhoto;
        TextView tvCarMakeModel, tvCarYear, tvCarPrice, tvCarOdo;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCarPhoto = itemView.findViewById(R.id.ivCarPhoto);
            tvCarMakeModel = itemView.findViewById(R.id.tvCarMakeModel);
            tvCarYear = itemView.findViewById(R.id.tvCarYear);
            tvCarPrice = itemView.findViewById(R.id.tvCarPrice);
            tvCarOdo = itemView.findViewById(R.id.tvCarOdo);
        }
    }

    public void updateData(List<CarListing> newCarListings) {
        carListings.clear();
        carListings.addAll(newCarListings);
        notifyDataSetChanged();  // Notify the adapter that data has changed
    }
}
