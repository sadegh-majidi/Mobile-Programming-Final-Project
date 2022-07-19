package edu.sharif.snappfoodminus.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.temp.Category;
import edu.sharif.snappfoodminus.model.Filter;
import edu.sharif.snappfoodminus.model.Restaurant;
import edu.sharif.snappfoodminus.temp.DBHelper;

public class RestaurantsController {

    private static RestaurantsController instance;

    public static RestaurantsController getInstance() {
        if (instance == null)
            instance = new RestaurantsController();
        return instance;
    }

    public ArrayList<Restaurant> getFilteredRestaurants(ArrayList<Filter> filters) {
        // TODO: get all filtered restaurants
        // For now:
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            restaurants.add(new Restaurant("Restaurant" + String.valueOf(i), 1, 1L, new byte[2]));
        }
        return restaurants;
    }
}
