package edu.sharif.snappfoodminus.view;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.adapter.CategoriesAdapter;
import edu.sharif.snappfoodminus.adapter.FoodsAdapter;
import edu.sharif.snappfoodminus.adapter.RecyclerItemClickListener;
import edu.sharif.snappfoodminus.temp.CartRepository;
import edu.sharif.snappfoodminus.temp.Category;
import edu.sharif.snappfoodminus.temp.Food;
import edu.sharif.snappfoodminus.temp.Restaurant;
import edu.sharif.snappfoodminus.temp.RestaurantRepository;

public class RestaurantPageActivity extends AppCompatActivity {

    private static final String Shared_KEY = "edu.sharif.snappfoodminus";
    private SharedPreferences sharedPreferences;

    private RecyclerView categoriesRecyclerView;
    private RecyclerView foodsRecyclerView;
    private CategoriesAdapter categoriesAdapter;
    private FoodsAdapter foodsAdapter;
    private ArrayList<Category> categories;
    private ArrayList<Food> foods;

    private TextView restaurantNameTextView;
    private TextView shippingCostTextView;

    private String currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

        CartRepository.resetCart();

        sharedPreferences = getSharedPreferences(Shared_KEY, MODE_PRIVATE);

        if (sharedPreferences.getBoolean("DarkMode", false))
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);

        restaurantNameTextView = findViewById(R.id.restaurant_name);
        shippingCostTextView = findViewById(R.id.shipping_cost);

        Restaurant restaurant = Restaurant.getRestaurantByName(this, RestaurantRepository.name);
        restaurantNameTextView.setText(restaurant.name);
        shippingCostTextView.setText(getShippingCostToView(restaurant.shippingCost));

        categoriesRecyclerView = findViewById(R.id.categories_rv);
        categories = Category.getAllCategories(this);
        if (categories.size() > 0)
            currentCategory = categories.get(0).name;
        categoriesAdapter = new CategoriesAdapter(categories);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener
                (this,categoriesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onItemClick(View view, int position) {
                        handleCategorySelection(position);
                        foods.clear();
                        foods.addAll(Food.getFoodsByRestaurantAndCategory(view.getContext(), restaurant.name, currentCategory));
                        foodsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
        categoriesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                handleCategorySelection(-1);
            }
        });

        foodsRecyclerView = findViewById(R.id.foods_rv);
        foods = Food.getFoodsByRestaurantAndCategory(this, restaurant.name, currentCategory);
        foodsAdapter = new FoodsAdapter(foods);
        foodsRecyclerView.setAdapter(foodsAdapter);
        foodsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView cartImageView = findViewById(R.id.cart_image_view);
        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), CartActivity.class));
            }
        });
    }

    private void handleCategorySelection(int position) {
        if (position != -1)
            currentCategory = categories.get(position).name;
        for (int i = 0; i < categoriesRecyclerView.getChildCount(); i++) {
            final CategoriesAdapter.ViewHolder holder = (CategoriesAdapter.ViewHolder)
                    categoriesRecyclerView.getChildViewHolder(categoriesRecyclerView.getChildAt(i));
            TextView nameTextView = holder.itemView.findViewById(R.id.food_name_text_view);
            if (nameTextView.getText().toString().equals(currentCategory)) {
                holder.itemView.setBackgroundResource(R.drawable.bg_colored_border);
                nameTextView.setTextColor(ContextCompat.getColor(this, R.color.coloredBorder));
            } else {
                holder.itemView.setBackgroundResource(R.drawable.bg_plain_border);
                nameTextView.setTextColor(ContextCompat.getColor(this, R.color.plainBorder));
            }
        }
    }

    private String getShippingCostToView(int shippingCost) {
        return shippingCost == 0 ? "Free" : ("$" + shippingCost);
    }
}