package edu.sharif.snappfoodminus.temp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Restaurant {

    private String name;
    private int shippingCost;
    private byte[] image;

    public Restaurant(String name, int shippingCost, byte[] image) {
        this.name = name;
        this.shippingCost = shippingCost;
        this.image = image;
    }


    public static void addRestaurant(Context context, Restaurant restaurant) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.RESTAURANT_NAME, restaurant.name);
        contentValues.put(DBHelper.RESTAURANT_SHIPPING_COST, restaurant.shippingCost);
        db.insert(DBHelper.RESTAURANT_TABLE_NAME, null, contentValues);
        db.close();
    }

    public static void updateUser(Context context, Restaurant restaurant, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.RESTAURANT_NAME, restaurant.name);
        contentValues.put(DBHelper.RESTAURANT_SHIPPING_COST, restaurant.shippingCost);
        db.update(DBHelper.RESTAURANT_TABLE_NAME, contentValues,
                DBHelper.RESTAURANT_NAME + "=?", new String[]{key});
        db.close();
    }

    public static void deleteUser(Context context, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        db.delete(DBHelper.RESTAURANT_TABLE_NAME,
                DBHelper.RESTAURANT_NAME + "=?", new String[]{key});
        db.close();
    }

    public static ArrayList<Restaurant> getAllRestaurants(Context context) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        String query = "SELECT * FROM " + DBHelper.RESTAURANT_TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                restaurants.add(new Restaurant(
                        cursor.getString(0),
                        cursor.getInt(1),
                        null
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return restaurants;
    }

    public static Restaurant getRestaurantByName(Context context, String name) {
        ArrayList<Restaurant> users = Restaurant.getAllRestaurants(context);
        for (Restaurant restaurant: users)
            if (restaurant.name.equals(name))
                return restaurant;
        return null;
    }
}
