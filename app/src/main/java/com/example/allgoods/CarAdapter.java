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
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAddWatchClick(int position);
    }

    private CarAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.carListings = new ArrayList<>();
    }

    public static CarAdapter getInstance(Context context, OnItemClickListener listener) {
        if (instance == null) {
            instance = new CarAdapter(context, listener);
        }
        return instance;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_listing, parent, false);
        return new CarViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        CarListing carListing = carListings.get(position);
        holder.tvCarMakeModel.setText(carListing.getCar().getMake() + " " + carListing.getCar().getModel());
        holder.tvCarYear.setText("Year: " + carListing.getCar().getYear());
        holder.tvCarPrice.setText("$" + carListing.getPrice());
        holder.tvCarOdo.setText("Odo: " + carListing.getCar().getOdo() + " Km");
        holder.ivCarPhoto.setImageURI(carListing.getFirstImage());
    }

    @Override
    public int getItemCount() {
        return carListings.size();
    }


    public CarListing getItem(int position) {
        if (position >= 0 && position < carListings.size()) {
            return carListings.get(position);
        }
        return null;
    }


    public void updateData(List<CarListing> newCarListings) {
        carListings.clear();
        carListings.addAll(newCarListings);
        notifyDataSetChanged();
    }

    static class CarViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCarPhoto;
        TextView tvCarMakeModel, tvCarYear, tvCarPrice, tvCarOdo;

        CarViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            ivCarPhoto = itemView.findViewById(R.id.ivCarPhoto);
            tvCarMakeModel = itemView.findViewById(R.id.tvCarMakeModel);
            tvCarYear = itemView.findViewById(R.id.tvCarYear);
            tvCarPrice = itemView.findViewById(R.id.tvCarPrice);
            tvCarOdo = itemView.findViewById(R.id.tvCarOdo);

            itemView.findViewById(R.id.AddWatchList).setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onAddWatchClick(getAdapterPosition());

                }
            });
        }
    }
}
