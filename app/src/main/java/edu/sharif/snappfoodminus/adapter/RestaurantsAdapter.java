package edu.sharif.snappfoodminus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.controller.RestaurantsController;
import edu.sharif.snappfoodminus.model.Category;
import edu.sharif.snappfoodminus.model.Discount;
import edu.sharif.snappfoodminus.model.Food;
import edu.sharif.snappfoodminus.model.Restaurant;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = mRestaurants.get(position);
        Context context = holder.itemView.getContext();
        holder.nameTextView.setText(restaurant.name);
        ArrayList<String> categoriesNames = new ArrayList<>();
        for (Category category: Category.getAllCategories(context))
            if (!Food.getFoodsByRestaurantAndCategory(context, restaurant.name, category.name).isEmpty())
                if (!categoriesNames.contains(category.name))
                    categoriesNames.add(category.name);
        holder.categoriesTextView.setText(String.join(", ", categoriesNames));
        int shippingCost = restaurant.shippingCost;
        holder.shippingCostTextView.setText(shippingCost == 0 ? "Free" : ("$" + shippingCost));
        Discount discount = Discount.getDiscountByRestaurant(context, restaurant.name);
        if (discount == null)
            holder.percentageTextView.setText("");
        else
            holder.percentageTextView.setText(discount.percentage + "%");

        String rate = new RestaurantsController(context).getRestaurantRateText(restaurant.name);
        holder.rateTextView.setText(rate);
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
        public TextView percentageTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name_textview);
            categoriesTextView = itemView.findViewById(R.id.categories_textview);
            shippingCostTextView = itemView.findViewById(R.id.shipping_cost);
            rateTextView = itemView.findViewById(R.id.rate);
            percentageTextView = itemView.findViewById(R.id.discount_percentage_text_view);
        }
    }
}
