package edu.sharif.snappfoodminus.controller;

import android.content.Context;

public class AdminCategoriesController {

    private Context context;

    public AdminCategoriesController(Context context) {
        this.context = context;
    }

    public String getNameError(String name) {
        if (name.isEmpty())
            return "Name field can not be blank";
        return null;
    }
}
