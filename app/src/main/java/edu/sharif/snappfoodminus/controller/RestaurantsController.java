package edu.sharif.snappfoodminus.controller;

import android.content.Context;
import android.util.Pair;

import androidx.room.RoomDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.sharif.snappfoodminus.database.DataBase;
import edu.sharif.snappfoodminus.model.Category;
import edu.sharif.snappfoodminus.model.Filter;
import edu.sharif.snappfoodminus.model.Restaurant;

public class RestaurantsController {

    private static RestaurantsController instance;

    public static RestaurantsController getInstance() {
        if (instance == null)
            instance = new RestaurantsController();
        return instance;
    }

    public String[] getAllCategories(Context context) {
        // TODO: get all categories names from database
        // For now:
//         DataBase db = DataBase.getInstance(context);
//         List<Category> categories = db.categoryDao().getAllCategories();
//        return categories.stream().map(Category::getName).collect(Collectors.toList()).toArray(new String[0]);
        return new String[]{"kebab", "fastfood", "desert", "drink", "appetizer", "sauce"};
    }

    public ArrayList<Restaurant> getFilteredRestaurants(ArrayList<Filter> filters) {
        // TODO: get all filtered restaurants
        // For now:
        return new ArrayList<>();
    }
}
