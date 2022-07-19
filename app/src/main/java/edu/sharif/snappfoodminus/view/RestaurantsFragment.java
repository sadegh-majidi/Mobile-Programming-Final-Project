package edu.sharif.snappfoodminus.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.adapter.RestaurantsAdapter;
import edu.sharif.snappfoodminus.controller.RestaurantsController;
import edu.sharif.snappfoodminus.model.Filter;
import edu.sharif.snappfoodminus.temp.Category;
import edu.sharif.snappfoodminus.temp.Restaurant;

public class RestaurantsFragment extends Fragment {

    private RestaurantsController controller;

    private static final String Shared_KEY = "edu.sharif.snappfoodminus";
    private SharedPreferences sharedPreferences;

    private ArrayList<Filter> filters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurants, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        controller = new RestaurantsController(getContext());
        sharedPreferences = getActivity().getSharedPreferences(Shared_KEY, Context.MODE_PRIVATE);
        filters = initFilters();

        Button filterButton = view.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(v -> {
            filters = getCurrentFilters();
            getFiltersDialog().show();
        });

        RecyclerView recyclerView = view.findViewById(R.id.restaurants_rv);
        ArrayList<Restaurant> restaurants = controller.getFilteredRestaurants(filters);
        RestaurantsAdapter adapter = new RestaurantsAdapter(restaurants);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private ArrayList<Filter> initFilters() {
        ArrayList<Category> categories = Category.getAllCategories(getContext());
        ArrayList<Filter> filters = new ArrayList<>();
        for (Category category: categories)
            filters.add(new Filter(category.name, true));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Filters", new Gson().toJson(filters));
        editor.apply();
        return filters;
    }

    private ArrayList<Filter> getCurrentFilters() {
        String objectAsString = sharedPreferences.getString("Filters", "[]");
        return new Gson().fromJson(objectAsString, new TypeToken<ArrayList<Filter>>(){}.getType());
    }

    private void updateCurrentFilters() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Filters", new Gson().toJson(filters));
        editor.apply();
    }

    private View getFiltersView(Context context) {
        GridLayout layout = new GridLayout(context);
        layout.setColumnCount(3);
        layout.setPadding(50, 0, 50, 0);
        ArrayList<Filter> filters = getCurrentFilters();
        for (int i = 0; i < filters.size(); i++) {
            Filter filter = filters.get(i);
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(filter.category);
            checkBox.setChecked(filter.state);
            layout.addView(checkBox, i);
            final int index = i;
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                this.filters.get(index).state = isChecked;
            });
        }
        return layout;
    }

    private AlertDialog getFiltersDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Filter Restaurants");
        alertDialog.setView(getFiltersView(getActivity()));
        alertDialog.setMessage("Select your desired categories");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Filter",
                (dialog, which) -> {
                    // TODO: Filter restaurants
                    // controller.getFilteredRestaurants(filters);
                    updateCurrentFilters();
                    dialog.dismiss();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                (dialog, which) -> dialog.dismiss());
        return alertDialog;
    }
}
