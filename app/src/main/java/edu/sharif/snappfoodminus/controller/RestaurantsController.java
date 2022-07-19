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

    public void addCategory(Context context, Category category) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.CATEGORY_NAME, category.name);
        db.insert(DBHelper.CATEGORY_TABLE_NAME, null, contentValues);
        db.close();
    }

    public void updateCategory(Context context, Category category, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.CATEGORY_NAME, category.name);
        db.update(DBHelper.CATEGORY_TABLE_NAME, contentValues,
                DBHelper.CATEGORY_NAME + "=?", new String[]{key});
        db.close();
    }

    public void deleteCategory(Context context, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        db.delete(DBHelper.CATEGORY_TABLE_NAME,
                DBHelper.CATEGORY_NAME + "=?", new String[]{key});
        db.close();
    }

    public ArrayList<Category> getAllCategories(Context context) {
        ArrayList<Category> categories = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        String query = "SELECT * FROM " + DBHelper.CATEGORY_TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                categories.add(new Category(
                        cursor.getString(0)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
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
