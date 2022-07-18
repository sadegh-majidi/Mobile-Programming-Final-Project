package edu.sharif.snappfoodminus.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.controller.RestaurantsController;
import edu.sharif.snappfoodminus.model.Filter;

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
        controller = RestaurantsController.getInstance();
        sharedPreferences = getActivity().getSharedPreferences(Shared_KEY, Context.MODE_PRIVATE);
        filters = initFilters();

        Button filterButton = view.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(v -> {
            filters = getCurrentFilters();
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
            alertDialog.show();
        });

    }

    private ArrayList<Filter> initFilters() {
        String[] categories = controller.getAllCategories(getActivity());
        ArrayList<Filter> filters = new ArrayList<>();
        for (String category: categories)
            filters.add(new Filter(category, true));
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
        GridLayout layout = new GridLayout(getContext());
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
}
