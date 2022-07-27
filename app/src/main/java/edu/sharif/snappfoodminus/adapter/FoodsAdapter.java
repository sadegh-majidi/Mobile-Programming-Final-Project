package edu.sharif.snappfoodminus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.controller.OwnerRestaurantController;
import edu.sharif.snappfoodminus.temp.CartRepository;
import edu.sharif.snappfoodminus.temp.Category;
import edu.sharif.snappfoodminus.temp.Food;
import edu.sharif.snappfoodminus.temp.LoginRepository;
import edu.sharif.snappfoodminus.temp.Request;
import edu.sharif.snappfoodminus.temp.RequestStatus;
import edu.sharif.snappfoodminus.temp.Restaurant;

public class FoodsAdapter extends RecyclerView.Adapter<FoodsAdapter.ViewHolder> {

    private ArrayList<Food> mFoods;

    public FoodsAdapter(ArrayList<Food> foods) {
        mFoods = foods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_food_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = mFoods.get(position);
        holder.nameTextView.setText(food.name);
        holder.priceTextView.setText(food.price == 0 ? "Free" : ("$" + food.price));
        holder.descriptionTextView.setText(food.description);

        holder.zeroCount = true;
        holder.itemCountTextView.setText("0");
        holder.itemCountTextView.setVisibility(View.INVISIBLE);
        holder.removeItemTextView.setVisibility(View.INVISIBLE);
        for (Pair<Food, Integer> item: CartRepository.items) {
            if (item.first.name.equals(food.name)) {
                holder.zeroCount = false;
                holder.itemCountTextView.setText(String.valueOf(item.second));
                holder.itemCountTextView.setVisibility(View.VISIBLE);
                holder.removeItemTextView.setVisibility(View.VISIBLE);
            }
        }

        holder.addItemTextView.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.itemCountTextView.getText().toString());
            if (holder.zeroCount) {
                holder.itemCountTextView.setText("1");
                holder.itemCountTextView.setVisibility(View.VISIBLE);
                holder.removeItemTextView.setVisibility(View.VISIBLE);
                holder.zeroCount = false;
            } else {
                holder.itemCountTextView.setText(String.valueOf(count + 1));
            }
            CartRepository.updateCart(food, count + 1);
        });
        holder.removeItemTextView.setOnClickListener(v -> {
            int count = Integer.parseInt(holder.itemCountTextView.getText().toString());
            if (count == 1) {
                holder.itemCountTextView.setText("0");
                holder.itemCountTextView.setVisibility(View.INVISIBLE);
                holder.removeItemTextView.setVisibility(View.INVISIBLE);
                holder.zeroCount = true;
            } else {
                holder.itemCountTextView.setText(String.valueOf(count - 1));
            }
            CartRepository.updateCart(food, count - 1);
        });
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView priceTextView;
        public TextView descriptionTextView;
        public TextView itemCountTextView;
        public ImageView addItemTextView;
        public ImageView removeItemTextView;
        public boolean zeroCount;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.food_name_text_view);
            priceTextView = itemView.findViewById(R.id.price_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
            itemCountTextView = itemView.findViewById(R.id.item_count_text_view);
            addItemTextView = itemView.findViewById(R.id.add_item_image_view);
            removeItemTextView = itemView.findViewById(R.id.remove_item_image_view);
            zeroCount = true;
        }
    }
}
