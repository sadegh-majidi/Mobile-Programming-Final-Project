package edu.sharif.snappfoodminus.temp;

import android.content.Context;

public class LoginRepository {
    public static String username;

    public static User getLoggedInUser(Context context) {
        return User.getUserByUsername(context, username);
    }
}
