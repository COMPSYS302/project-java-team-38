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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private static CarAdapter instance;
    private Context context;
    private List<CarListing> carListings;
    private List<CarListing> weightedList = new ArrayList<>();
    private OnItemClickListener listener;
    private boolean showAll;

    private CarAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.carListings = new ArrayList<>();
        setHasStableIds(true);
    }

    public static CarAdapter getInstance(Context context, OnItemClickListener listener) {
        if (instance == null) {
            instance = new CarAdapter(context, listener);
        }
        return instance;
    }

    @Override
    public long getItemId(int position) {
        // Assuming each CarListing has a unique identifier
        return showAll ? carListings.get(position).hashCode() : weightedList.get(position).hashCode();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_listing, parent, false);
        return new CarViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        CarListing carListing = showAll ? carListings.get(position) : weightedList.get(position);
        holder.tvCarMakeModel.setText(carListing.getCar().getMake() + " " + carListing.getCar().getModel());
        holder.tvCarYear.setText("Year: " + carListing.getCar().getYear());
        holder.tvCarPrice.setText("$" + carListing.getPrice());
        holder.tvCarOdo.setText("Odo: " + carListing.getCar().getOdo() + " Km");
        holder.ivCarPhoto.setImageURI(carListing.getFirstImage());
    }

    @Override
    public int getItemCount() {
        return showAll ? carListings.size() : Math.min(weightedList.size(), 3);
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
        notifyDataSetChanged();
    }

    public void updateData(List<CarListing> newCarListings) {
        carListings.clear();
        carListings.addAll(newCarListings);
        updateWeightedList();
    }

    public void updateRecentlyViewed(int position) {
        CarListing clickedListing = getItem(position);
        User user = UserSession.getInstance(context).getUser();
        user.addViewedCarListing(clickedListing);
    }

    public void updateWeightedList() {
        HashMap<CarListing, Integer> weightMap = new HashMap<>();
        List<CarListing> newList = new ArrayList<>(carListings);
        List<CarListing> recentlyViewed = UserSession.getInstance(this.context).getUser().getRecentViewedCarListings();

        if (!recentlyViewed.isEmpty()) {
            for (CarListing viewed : recentlyViewed) {
                for (CarListing listing : carListings) {
                    int weight = calculateWeight(viewed, listing);
                    weightMap.put(listing, weightMap.getOrDefault(listing, 0) + weight);
                }
            }
            Collections.sort(newList, (a, b) -> weightMap.getOrDefault(b, 0) - weightMap.getOrDefault(a, 0));
        }

        weightedList.clear();
        weightedList.addAll(newList);
        notifyDataSetChanged();
    }

    private int calculateWeight(CarListing viewed, CarListing listing) {
        int weight = 0;
        if (viewed.getCar().getMake().equals(listing.getCar().getMake())) weight++;
        if (viewed.getCar().getModel().equals(listing.getCar().getModel())) weight++;
        if (viewed.getCar().getType().equals(listing.getCar().getType())) weight++;
        return weight;
    }

    public CarListing getItem(int position) {
        return showAll ? carListings.get(position) : weightedList.get(position);
    }

    public interface OnItemClickListener {
        void onAddWatchClick(int position);
        void onImageClick(int position);
        void onPriceClick(int position);
        void onMakeModelClick(int position);
        void onYearClick(int position);
        void onOdoClick(int position);
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

            ivCarPhoto.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onImageClick(getAdapterPosition());
                }
            });

            tvCarPrice.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onPriceClick(getAdapterPosition());
                }
            });

            tvCarMakeModel.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onMakeModelClick(getAdapterPosition());
                }
            });

            tvCarYear.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onYearClick(getAdapterPosition());
                }
            });

            tvCarOdo.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onOdoClick(getAdapterPosition());
                }
            });
        }
    }
}
