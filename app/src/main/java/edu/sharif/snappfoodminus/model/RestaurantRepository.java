package edu.sharif.snappfoodminus.model;

import android.content.Context;

public class RestaurantRepository {
    public static String name;

    public static Restaurant getSelectedRestaurant(Context context) {
        return Restaurant.getRestaurantByName(context, name);
    }
}
