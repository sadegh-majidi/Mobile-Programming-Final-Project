package edu.sharif.snappfoodminus.controller;

import android.util.Pair;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.model.Filter;
import edu.sharif.snappfoodminus.model.Restaurant;

public class RestaurantsController {

    private static RestaurantsController instance;

    public static RestaurantsController getInstance() {
        if (instance == null)
            instance = new RestaurantsController();
        return instance;
    }

    public String[] getAllCategories() {
        // TODO: get all categories names from database
        // For now:
        return new String[]{"kebab", "fastfood", "desert", "drink", "appetizer", "sauce"};
    }

    public ArrayList<Restaurant> getFilteredRestaurants(ArrayList<Filter> filters) {
        // TODO: get all filtered restaurants
        // For now:
        return new ArrayList<>();
    }
}
