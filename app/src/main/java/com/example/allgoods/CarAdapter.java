package com.example.allgoods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private Context context;
    private List<CarListing> carListings;

    public CarAdapter(Context context, List<CarListing> carListings) {
        this.context = context;
        this.carListings = carListings;
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
        // Assuming you have methods to get the car's details
        holder.tvCarMakeModel.setText(carListing.getCar().getMake() + " " + carListing.getCar().getModel());
        holder.tvCarYear.setText("Year: " + carListing.getCar().getYear());
        holder.tvCarPrice.setText("$" + carListing.getPrice());
        holder.tvCarOdo.setText("Odo: " + carListing.getCar().getOdo() + " Km");
        // Set the car photo if you have one
        // holder.ivCarPhoto.setImageResource(...);
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
}

