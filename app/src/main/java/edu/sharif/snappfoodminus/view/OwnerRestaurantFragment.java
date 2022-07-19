package edu.sharif.snappfoodminus.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.adapter.AdminCategoriesAdapter;
import edu.sharif.snappfoodminus.controller.AdminCategoriesController;
import edu.sharif.snappfoodminus.controller.OwnerRestaurantController;
import edu.sharif.snappfoodminus.temp.Category;
import edu.sharif.snappfoodminus.temp.LoginRepository;
import edu.sharif.snappfoodminus.temp.Restaurant;
import edu.sharif.snappfoodminus.temp.User;

public class OwnerRestaurantFragment extends Fragment {

    private OwnerRestaurantController controller;
    
    private TextView restaurantNameTextView;
    private TextView shippingCostTextView;

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

        TextView textView = view.findViewById(R.id.rate);
        textView.setOnClickListener(v -> {
            showItemDialog("Add");
        });
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

    private void showItemDialog(String mode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.layout_add_update_food, null);
        builder.setTitle("Request " + mode + " Item");
        builder.setView(view);
        builder.setPositiveButton("Request", (dialog, which) -> {
            dialog.dismiss();
            // TODO: Handle request
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
