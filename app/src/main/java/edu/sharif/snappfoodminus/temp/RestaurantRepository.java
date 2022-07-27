package edu.sharif.snappfoodminus.temp;

import android.content.Context;

public class RestaurantRepository {
    public static String name;

    public static Restaurant getSelectedRestaurant(Context context) {
        return Restaurant.getRestaurantByName(context, name);
    }
}
