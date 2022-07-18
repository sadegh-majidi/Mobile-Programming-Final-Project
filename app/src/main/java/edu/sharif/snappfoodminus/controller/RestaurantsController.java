package edu.sharif.snappfoodminus.controller;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.snappfoodminus.model.Category;
import edu.sharif.snappfoodminus.model.Filter;
import edu.sharif.snappfoodminus.model.Restaurant;
import edu.sharif.snappfoodminus.repository.CategoryRepository;

public class RestaurantsController {

    private static RestaurantsController instance;
    private static CategoryRepository categoryRepository;


    public static RestaurantsController getInstance() {
        if (instance == null)
            instance = new RestaurantsController();
        return instance;
    }

    public LiveData<List<Category>> getAllCategories(Application application) {
        categoryRepository = new CategoryRepository(application);
        return categoryRepository.getAllCategories();
        // For now:
//        return new String[]{"kebab", "fastfood", "desert", "drink", "appetizer", "sauce"};
    }

    public ArrayList<Restaurant> getFilteredRestaurants(ArrayList<Filter> filters) {
        // TODO: get all filtered restaurants
        // For now:
        return new ArrayList<>();
    }
}
