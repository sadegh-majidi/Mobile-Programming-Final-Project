package edu.sharif.snappfoodminus.controller;

import android.content.Context;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.model.Filter;
import edu.sharif.snappfoodminus.model.Food;
import edu.sharif.snappfoodminus.model.Restaurant;

public class RestaurantsController {

    private Context context;

    public RestaurantsController(Context context) {
        this.context = context;
    }

    public ArrayList<Restaurant> getFilteredRestaurants(ArrayList<Filter> filters) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (Restaurant restaurant: Restaurant.getAllRestaurants(context)) {
            boolean valid = false;
            for (Filter filter: filters)
                if (filter.state && !Food.getFoodsByRestaurantAndCategory(context, restaurant.name, filter.category).isEmpty())
                    valid = true;
            if (valid)
                restaurants.add(restaurant);
        }
        return restaurants;
    }
}
