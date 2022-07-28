package edu.sharif.snappfoodminus.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.adapter.AdminDiscountsAdapter;
import edu.sharif.snappfoodminus.model.Discount;
import edu.sharif.snappfoodminus.model.Restaurant;

public class AdminDiscountsFragment extends Fragment {

    private AdminDiscountsAdapter discountsAdapter;
    private ArrayList<Discount> discounts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_discounts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.orders_rv);
        discounts = Discount.getAllDiscounts(getContext());
        discountsAdapter = new AdminDiscountsAdapter(discounts);
        recyclerView.setAdapter(discountsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button addDiscountButton = view.findViewById(R.id.order_button);
        addDiscountButton.setOnClickListener(v -> showAddDiscountDialog());
    }

    private void showAddDiscountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.layout_admin_add_discount, null);
        Spinner restaurantSpinner = view.findViewById(R.id.restaurant_spinner);
        EditText percentageEditText = view.findViewById(R.id.percentage_edit_text);

        ArrayList<Restaurant> restaurants = Restaurant.getAllRestaurants(getContext());
        ArrayList<String> restaurantsNames = new ArrayList<>();
        for (Restaurant restaurant: restaurants)
            if (Discount.getDiscountByRestaurant(getContext(), restaurant.name) == null)
                restaurantsNames.add(restaurant.name);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, restaurantsNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurantSpinner.setAdapter(adapter);
        final String[] selectedRestaurantName = new String[1];
        restaurantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRestaurantName[0] = restaurantsNames.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        builder.setTitle("Add Discount");
        builder.setView(view);
        builder.setPositiveButton("Add", (dialog, which) -> {
            int percentage = Integer.parseInt(percentageEditText.getText().toString().trim());
            if (percentage <= 0 || percentage > 100) {
                Toast.makeText(getContext(), "Percentage must be in (0, 100]", Toast.LENGTH_LONG).show();
            } else if (selectedRestaurantName[0] == null) {
                Toast.makeText(getContext(), "No selected restaurant", Toast.LENGTH_LONG).show();
            } else {
                Discount discount = new Discount(selectedRestaurantName[0], percentage);
                Discount.addDiscount(getContext(), discount);
                discounts.add(discount);
                discountsAdapter.notifyItemInserted(discounts.size());
            }
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
