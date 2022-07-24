package edu.sharif.snappfoodminus.temp;

import android.content.Context;

public class LoginRepository {
    public static String username;

    public static User getLoggedInUser(Context context) {
        return User.getUserByUsername(context, username);
    }

    public static Restaurant getLoggedInUserRestaurant(Context context) {
        User user = getLoggedInUser(context);
        if (user.role == Role.OWNER)
            return Restaurant.getRestaurantByOwner(context, username);
        return null;
    }
}
