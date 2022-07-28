package edu.sharif.snappfoodminus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.model.Food;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {

    private ArrayList<Pair<Food, Integer>> mItems;

    public CartItemsAdapter(ArrayList<Pair<Food, Integer>> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pair<Food, Integer> item = mItems.get(position);
        Food food = item.first;
        holder.foodNameTextView.setText(food.name);
        holder.countTextView.setText(String.valueOf(item.second));
        holder.priceTextView.setText("$" + food.price);
        int totalPrice = item.second * food.price;
        holder.totalPriceTextView.setText("$" + totalPrice);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView foodNameTextView;
        public TextView countTextView;
        public TextView priceTextView;
        public TextView totalPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            foodNameTextView = itemView.findViewById(R.id.restaurant_text_view);
            countTextView = itemView.findViewById(R.id.count_text_view);
            priceTextView = itemView.findViewById(R.id.price_text_view);
            totalPriceTextView = itemView.findViewById(R.id.sum_price_text_view);
        }
    }
}
