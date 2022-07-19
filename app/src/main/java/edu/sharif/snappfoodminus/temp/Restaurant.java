package edu.sharif.snappfoodminus.temp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Restaurant {

    public String name;
    public String owner;
    public int shippingCost;

    public Restaurant(String name, String owner, int shippingCost) {
        this.name = name;
        this.owner = owner;
        this.shippingCost = shippingCost;
    }

    public static void addRestaurant(Context context, Restaurant restaurant) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.RESTAURANT_NAME, restaurant.name);
        contentValues.put(DBHelper.RESTAURANT_OWNER, restaurant.owner);
        contentValues.put(DBHelper.RESTAURANT_SHIPPING_COST, restaurant.shippingCost);
        db.insert(DBHelper.RESTAURANT_TABLE_NAME, null, contentValues);
        db.close();
    }

    public static void updateRestaurant(Context context, Restaurant restaurant, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.RESTAURANT_NAME, restaurant.name);
        contentValues.put(DBHelper.RESTAURANT_OWNER, restaurant.owner);
        contentValues.put(DBHelper.RESTAURANT_SHIPPING_COST, restaurant.shippingCost);
        db.update(DBHelper.RESTAURANT_TABLE_NAME, contentValues,
                DBHelper.RESTAURANT_NAME + "=?", new String[]{key});
        db.close();
    }

    public static void deleteRestaurant(Context context, String key) {
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
                        cursor.getString(1),
                        cursor.getInt(2)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return restaurants;
    }

    public static Restaurant getRestaurantByName(Context context, String name) {
        ArrayList<Restaurant> restaurants = Restaurant.getAllRestaurants(context);
        for (Restaurant restaurant: restaurants)
            if (restaurant.name.equals(name))
                return restaurant;
        return null;
    }

    public static Restaurant getRestaurantByOwner(Context context, String name) {
        ArrayList<Restaurant> restaurants = Restaurant.getAllRestaurants(context);
        for (Restaurant restaurant: restaurants)
            if (restaurant.owner.equals(name))
                return restaurant;
        return null;
    }
}
