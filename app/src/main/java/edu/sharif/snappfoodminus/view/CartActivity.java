package edu.sharif.snappfoodminus.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Pair;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.adapter.CartItemsAdapter;
import edu.sharif.snappfoodminus.model.CartRepository;
import edu.sharif.snappfoodminus.model.Discount;
import edu.sharif.snappfoodminus.model.Food;
import edu.sharif.snappfoodminus.model.Restaurant;
import edu.sharif.snappfoodminus.model.RestaurantRepository;

public class CartActivity extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        RecyclerView itemsRecyclerView = findViewById(R.id.discounts_rv);
        ArrayList<Pair<Food, Integer>> items = CartRepository.items;
        CartItemsAdapter adapter = new CartItemsAdapter(items);
        itemsRecyclerView.setAdapter(adapter);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Restaurant restaurant = Restaurant.getRestaurantByName(this, RestaurantRepository.name);

        TextView totalPriceTextView = findViewById(R.id.total_price_text_view);
        totalPriceTextView.setText("$" + CartRepository.totalPrice);

        TextView shippingCostTextView = findViewById(R.id.shipping_cost_text_view);
        shippingCostTextView.setText("$" + restaurant.shippingCost);

        TextView discountTextView = findViewById(R.id.discount_text_view);
        Discount discount = Discount.getDiscountByRestaurant(this, restaurant.name);
        int discountInt = (discount == null ? 0 : discount.percentage * CartRepository.totalPrice) / 100;
        discountTextView.setText("$" + discountInt);

        TextView toPayTextView = findViewById(R.id.to_pay_text_view);
        int to_pay = CartRepository.totalPrice + restaurant.shippingCost - discountInt;
        toPayTextView.setText("$" + to_pay);
    }
}
