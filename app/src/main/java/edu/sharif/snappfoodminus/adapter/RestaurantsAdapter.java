package edu.sharif.snappfoodminus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.temp.Restaurant;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {

    private ArrayList<Restaurant> mRestaurants;

    public RestaurantsAdapter(ArrayList<Restaurant> restaurants) {
        mRestaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_restaurant_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = mRestaurants.get(position);
        holder.nameTextView.setText(restaurant.name);
        // TODO: methods for getting restaurant categories, shipping cost, and average rate
//        holder.categoriesTextView.setText("");
//        holder.shippingCostTextView.setText("");
//        holder.rateTextView.setText("");
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView categoriesTextView;
        public TextView shippingCostTextView;
        public TextView rateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_textview);
            categoriesTextView = itemView.findViewById(R.id.categories_textview);
            shippingCostTextView = itemView.findViewById(R.id.shipping_cost_textview);
            rateTextView = itemView.findViewById(R.id.rate_textview);
        }
    }
}
