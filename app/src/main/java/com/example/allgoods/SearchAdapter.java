package com.example.allgoods;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
    private Context context;
    private List<CarListing> carListings;
    private List<CarListing> filteredCarListings;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAddWatchClick(int position);
    }

    public SearchAdapter(Context context, List<CarListing> carListings, OnItemClickListener listener) {
        this.context = context;
        this.carListings = carListings;
        this.filteredCarListings = new ArrayList<>(carListings);
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_listing, parent, false);
        return new SearchViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        CarListing carListing = filteredCarListings.get(position);
        holder.tvCarMakeModel.setText(carListing.getCar().getMake() + " " + carListing.getCar().getModel());
        holder.tvCarYear.setText("Year: " + carListing.getCar().getYear());
        holder.tvCarPrice.setText("$" + carListing.getPrice());
        holder.tvCarOdo.setText("Odo: " + carListing.getCar().getOdo() + " Km");
        if (carListing.getFirstImage() != null) {
            holder.ivCarPhoto.setImageURI(carListing.getFirstImage());
        }
    }

    @Override
    public int getItemCount() {
        return filteredCarListings.size();
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
        this.filteredCarListings = new ArrayList<>(carListings);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchQuery = constraint.toString().toLowerCase();
                if (searchQuery.isEmpty()) {
                    filteredCarListings = new ArrayList<>(carListings);
                } else {
                    List<CarListing> tempList = new ArrayList<>();
                    for (CarListing listing : carListings) {
                        if (listing.getCar().getMake().toLowerCase().contains(searchQuery) ||
                                listing.getCar().getModel().toLowerCase().contains(searchQuery) ||
                                listing.getCar().getType().toLowerCase().contains(searchQuery)){
                            tempList.add(listing);
                        }
                    }
                    filteredCarListings = tempList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCarListings;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredCarListings = (ArrayList<CarListing>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCarPhoto;
        TextView tvCarMakeModel, tvCarYear, tvCarPrice, tvCarOdo;

        SearchViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
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
