package edu.sharif.snappfoodminus.controller;

import android.content.Context;

public class OwnerRestaurantController {

    private Context context;

    public OwnerRestaurantController(Context context) {
        this.context = context;
    }

    public String getNameError(String name) {
        if (name.isEmpty())
            return "Name field can not be blank";
        return null;
    }
}
