package edu.sharif.snappfoodminus.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.adapter.CategoriesAdapter;
import edu.sharif.snappfoodminus.adapter.OwnerFoodsAdapter;
import edu.sharif.snappfoodminus.adapter.RecyclerItemClickListener;
import edu.sharif.snappfoodminus.controller.OwnerRestaurantController;
import edu.sharif.snappfoodminus.model.Category;
import edu.sharif.snappfoodminus.model.Food;
import edu.sharif.snappfoodminus.model.LoginRepository;
import edu.sharif.snappfoodminus.model.Request;
import edu.sharif.snappfoodminus.model.RequestStatus;
import edu.sharif.snappfoodminus.model.Restaurant;
import edu.sharif.snappfoodminus.model.User;

public class OwnerRestaurantFragment extends Fragment {

    private OwnerRestaurantController controller;

    private RecyclerView categoriesRecyclerView;
    private RecyclerView foodsRecyclerView;
    private CategoriesAdapter categoriesAdapter;
    private OwnerFoodsAdapter foodsAdapter;
    private ArrayList<Category> categories;
    private ArrayList<Food> foods;

    private TextView restaurantNameTextView;
    private TextView shippingCostTextView;

    private String currentCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        controller = new OwnerRestaurantController(getContext());

        restaurantNameTextView = view.findViewById(R.id.restaurant_name);
        shippingCostTextView = view.findViewById(R.id.shipping_cost);
        ImageView editRestaurantNameImageView = view.findViewById(R.id.edit_restaurant_name);
        ImageView editShippingCostImageView = view.findViewById(R.id.edit_shipping_cost);

        User user = LoginRepository.getLoggedInUser(getContext());
        Restaurant restaurant = Restaurant.getRestaurantByOwner(getContext(), user.username);
        restaurantNameTextView.setText(restaurant.name);
        shippingCostTextView.setText(getShippingCostToView(restaurant.shippingCost));

        editRestaurantNameImageView.setOnClickListener(v -> {
            showEditRestaurantNameDialog(restaurant);
        });

        editShippingCostImageView.setOnClickListener(v -> {
            showEditShippingCostDialog(restaurant);
        });

        categoriesRecyclerView = view.findViewById(R.id.categories_rv);
        categories = Category.getAllCategories(getContext());
        if (categories.size() > 0)
            currentCategory = categories.get(0).name;
        categoriesAdapter = new CategoriesAdapter(categories);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener
                (getContext(),categoriesRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onItemClick(View view, int position) {
                handleCategorySelection(position);
                foods.clear();
                foods.addAll(Food.getFoodsByRestaurantAndCategory(getContext(), restaurant.name, currentCategory));
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

        foodsRecyclerView = view.findViewById(R.id.foods_rv);
        foods = Food.getFoodsByRestaurantAndCategory(getContext(), restaurant.name, currentCategory);
        foodsAdapter = new OwnerFoodsAdapter(foods);
        foodsRecyclerView.setAdapter(foodsAdapter);
        foodsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageView addItemImageView = view.findViewById(R.id.addItemImageView);
        addItemImageView.setOnClickListener(v -> {
            showAddItemDialog();
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
                nameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.coloredBorder));
            } else {
                holder.itemView.setBackgroundResource(R.drawable.bg_plain_border);
                nameTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.plainBorder));
            }
        }
    }

    private String getShippingCostToView(int shippingCost) {
        return shippingCost == 0 ? "Free" : ("$" + shippingCost);
    }

    private void showEditRestaurantNameDialog(Restaurant restaurant) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(layoutParams);
        layout.setPadding(50, 0, 50, 0);
        EditText nameEditText = new EditText(getContext());
        nameEditText.setLayoutParams(layoutParams);
        nameEditText.setHint("New restaurant name");
        layout.addView(nameEditText);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Edit Restaurant Name");
        alertDialog.setView(layout);
        alertDialog.setMessage("Change " + restaurant.name + " to a new name");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update",
                (dialog, which) -> {
                    String newName = nameEditText.getText().toString().trim();
                    String nameError = controller.getNameError(newName);
                    if (nameError != null)
                        Toast.makeText(getContext(), nameError, Toast.LENGTH_LONG).show();
                    else if (Restaurant.getRestaurantByName(getContext(), newName) != null)
                        Toast.makeText(getContext(), "Restaurant name exists", Toast.LENGTH_LONG).show();
                    else {
                        String oldName = restaurant.name;
                        restaurant.name = newName;
                        Restaurant.updateRestaurant(getContext(), restaurant, oldName);
                        restaurantNameTextView.setText(newName);
                    }
                    dialog.dismiss();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    private void showEditShippingCostDialog(Restaurant restaurant) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(layoutParams);
        layout.setPadding(50, 0, 50, 0);
        EditText nameEditText = new EditText(getContext());
        nameEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        nameEditText.setLayoutParams(layoutParams);
        nameEditText.setHint("New shipping cost");
        layout.addView(nameEditText);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Edit Shipping Cost");
        alertDialog.setView(layout);
        alertDialog.setMessage("Change $" + restaurant.shippingCost + " to a new cost");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update",
                (dialog, which) -> {
                    String newCostStr = nameEditText.getText().toString().trim();
                    try {
                        int newCost = Integer.parseInt(newCostStr);
                        restaurant.shippingCost = newCost;
                        Restaurant.updateRestaurant(getContext(), restaurant, restaurant.name);
                        shippingCostTextView.setText(getShippingCostToView(newCost));
                    } catch (Exception exception) {
                        Toast.makeText(getContext(), "Invalid cost", Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.layout_add_update_food, null);
        EditText nameEditText = view.findViewById(R.id.item_name);
        AutoCompleteTextView categoryTextView = view.findViewById(R.id.item_category);
        EditText descriptionEditText = view.findViewById(R.id.item_description);
        EditText priceEditText = view.findViewById(R.id.item_price);

        ArrayList<String> categories = Category.getAllCategoriesNames(getContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getContext(), android.R.layout.select_dialog_item, categories);
        categoryTextView.setAdapter(adapter);
        categoryTextView.setOnTouchListener((v, event) -> {
            categoryTextView.showDropDown();
            return false;
        });

        builder.setTitle("Request Add Item");
        builder.setView(view);
        builder.setPositiveButton("Request", (dialog, which) -> {
            String name = nameEditText.getText().toString().trim();
            String category = categoryTextView.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();
            int price = Integer.parseInt(priceEditText.getText().toString().trim());

            String nameError = controller.getNameError(name);
            String categoryError = controller.getNameError(category);
            if (nameError == null) {
                if (categoryError == null) {
                    String restaurant = Restaurant.getRestaurantByOwner(getContext(), LoginRepository.username).name;
                    Food food = new Food(name, category, restaurant, description, price);
                    String data = new Gson().toJson(food);
                    Request request = new Request(null, LoginRepository.username, restaurant, "Add".equals("Add") ? null : name, data, RequestStatus.PENDING, null);
                    Request.addRequest(getContext(), request);
                    Toast.makeText(getContext(), "Request sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), categoryError, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), nameError, Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
