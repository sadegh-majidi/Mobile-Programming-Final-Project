package edu.sharif.snappfoodminus.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.temp.Category;
import edu.sharif.snappfoodminus.model.Filter;
import edu.sharif.snappfoodminus.temp.DBHelper;
import edu.sharif.snappfoodminus.temp.Restaurant;

public class RestaurantsController {

    private Context context;

    public RestaurantsController(Context context) {
        this.context = context;
    }

    public ArrayList<Restaurant> getFilteredRestaurants(ArrayList<Filter> filters) {
        // TODO: get all filtered restaurants
        // For now:
        return Restaurant.getAllRestaurants(context);
    }
}
