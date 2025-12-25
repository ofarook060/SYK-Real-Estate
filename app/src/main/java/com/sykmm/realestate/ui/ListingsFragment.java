package com.sykmm.realestate.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sykmm.realestate.R;
import com.sykmm.realestate.api.ApiClient;
import com.sykmm.realestate.api.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListingsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListingsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listings, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchListings();

        return view;
    }

    private void fetchListings() {
        ApiService apiService = ApiClient.getApi();
        Call<List<Listing>> call = apiService.getListings();

        call.enqueue(new Callback<List<Listing>>() {
            @Override
            public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                if (response.isSuccessful()) {
                    List<Listing> listings = response.body();
                    adapter = new ListingsAdapter(listings);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to load listings", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Listing>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Updated Listing model class based on typical WDK API response
    public static class Listing {
        public int id;
        public String title;
        public String price;
        public String content; // or description
        public String featured_image; // URL for image
        // Add other fields as needed, e.g., location, etc.
    }

    // Updated ListingsAdapter class
    public static class ListingsAdapter extends RecyclerView.Adapter<ListingsAdapter.ViewHolder> {
        private List<Listing> listings;

        public ListingsAdapter(List<Listing> listings) {
            this.listings = listings;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listing, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Listing listing = listings.get(position);
            holder.titleTextView.setText(listing.title != null ? listing.title : "No Title");
            holder.priceTextView.setText(listing.price != null ? listing.price : "Price not available");
            holder.descriptionTextView.setText(listing.content != null ? listing.content : "No description");

            // Load image using Glide
            if (listing.featured_image != null && !listing.featured_image.isEmpty()) {
                Glide.with(holder.itemView.getContext())
                        .load(listing.featured_image)
                        .placeholder(R.drawable.placeholder) // Add a placeholder drawable
                        .into(holder.imageView);
            } else {
                holder.imageView.setImageResource(R.drawable.placeholder);
            }
        }

        @Override
        public int getItemCount() {
            return listings.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView titleTextView;
            TextView priceTextView;
            TextView descriptionTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                titleTextView = itemView.findViewById(R.id.titleTextView);
                priceTextView = itemView.findViewById(R.id.priceTextView);
                descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            }
        }
    }
}