package com.example.allgoods;

import android.content.Context;
import android.content.Intent;
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

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private static final int ITEM_VIEW_TYPE_RESULT = 0;
    private static final int ITEM_VIEW_TYPE_EMPTY = 1;

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

    @Override
    public int getItemViewType(int position) {
        return filteredCarListings.isEmpty() ? ITEM_VIEW_TYPE_EMPTY : ITEM_VIEW_TYPE_RESULT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_EMPTY) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_no_result, parent, false);
            return new SearchViewHolder.EmptyViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_car_listing, parent, false);
            return new SearchViewHolder(view, listener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_RESULT) {
            CarListing carListing = filteredCarListings.get(position);
            SearchViewHolder viewHolder = (SearchViewHolder) holder;
            viewHolder.bind(carListing);
        }
    }

    @Override
    public int getItemCount() {
        return filteredCarListings.isEmpty() ? 1 : filteredCarListings.size();
    }

    public CarListing getItem(int position) {
        if (!filteredCarListings.isEmpty() && position >= 0 && position < filteredCarListings.size()) {
            return filteredCarListings.get(position);
        }
        return null;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchQuery = constraint.toString().toLowerCase();
                List<CarListing> tempList = new ArrayList<>();
                if (!searchQuery.isEmpty()) {
                    for (CarListing listing : carListings) {
                        if (listing.getCar().getMake().toLowerCase().contains(searchQuery) ||
                                listing.getCar().getModel().toLowerCase().contains(searchQuery) ||
                                listing.getCar().getType().toLowerCase().contains(searchQuery)) {
                            tempList.add(listing);
                        }
                    }
                }
                filteredCarListings = tempList;
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

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onAddWatchClick(getAdapterPosition());
                }
            });
        }

        void bind(CarListing carListing) {
            tvCarMakeModel.setText(carListing.getCar().getMake() + " " + carListing.getCar().getModel());
            tvCarYear.setText("Year: " + carListing.getCar().getYear());
            tvCarPrice.setText("$" + carListing.getPrice());
            tvCarOdo.setText("Odo: " + carListing.getCar().getOdo() + " Km");
            if (carListing.getFirstImage() != null) {
                ivCarPhoto.setImageURI(carListing.getFirstImage());
            }

            // Register click event to navigate to detailed car view and add to recently viewed
            itemView.setOnClickListener(v -> {
                User user = UserSession.getInstance(itemView.getContext()).getUser();
                user.addViewedCarListing(carListing);
                navigateToCarDetails(carListing);
            });
        }

        private void navigateToCarDetails(CarListing carListing) {
            Context context = itemView.getContext(); // Get the context from itemView
            Intent intent = new Intent(context, IndepthListingActivity.class);
            intent.putExtra("CarListing", carListing);  // Ensure CarListing is Serializable or Parcelable
            context.startActivity(intent);
        }


        static class EmptyViewHolder extends RecyclerView.ViewHolder {
            ImageView noResultsImageView;

            EmptyViewHolder(View itemView) {
                super(itemView);
                noResultsImageView = itemView.findViewById(R.id.noSearchResults);
            }
        }
    }
}
