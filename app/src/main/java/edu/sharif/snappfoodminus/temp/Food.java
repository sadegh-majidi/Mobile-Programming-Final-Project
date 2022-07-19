package edu.sharif.snappfoodminus.temp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Food {
    public String name;
    public String category;
    public String restaurant;
    public String description;
    public int price;

    public Food(String name, String category, String restaurant, String description, int price) {
        this.name = name;
        this.category = category;
        this.restaurant = restaurant;
        this.description = description;
        this.price = price;
    }

    public static void addFood(Context context, Food food) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FOOD_NAME, food.name);
        contentValues.put(DBHelper.FOOD_CATEGORY, food.category);
        contentValues.put(DBHelper.FOOD_RESTAURANT, food.restaurant);
        contentValues.put(DBHelper.FOOD_DESCRIPTION, food.description);
        contentValues.put(DBHelper.FOOD_PRICE, food.price);
        db.insert(DBHelper.FOOD_TABLE_NAME, null, contentValues);
        db.close();
    }

    public static void updateFood(Context context, Food food, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FOOD_NAME, food.name);
        contentValues.put(DBHelper.FOOD_CATEGORY, food.category);
        contentValues.put(DBHelper.FOOD_RESTAURANT, food.restaurant);
        contentValues.put(DBHelper.FOOD_DESCRIPTION, food.description);
        contentValues.put(DBHelper.FOOD_PRICE, food.price);
        db.update(DBHelper.FOOD_TABLE_NAME, contentValues,
                DBHelper.FOOD_NAME + " = ?", new String[]{key});
        db.close();
    }

    public static void deleteFood(Context context, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        db.delete(DBHelper.FOOD_TABLE_NAME, DBHelper.FOOD_NAME + " = ?", new String[]{key});
        db.close();
    }

    public static ArrayList<Food> getAllFoods(Context context) {
        ArrayList<Food> foods = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        String query = "SELECT * FROM " + DBHelper.FOOD_TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                foods.add(new Food(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foods;
    }

    public static Food getFoodByName(Context context, String name) {
        ArrayList<Food> foods = Food.getAllFoods(context);
        for (Food food : foods)
            if (food.name.equals(name))
                return food;
        return null;
    }

    public static ArrayList<Food> getFoodsByCategory(Context context, String categoryName) {
        ArrayList<Food> foods = Food.getAllFoods(context);
        ArrayList<Food> result = new ArrayList<>();
        for (Food food : foods)
            if (food.category.equals(categoryName))
                result.add(food);
        return result;
    }

    public static ArrayList<Food> getFoodsByRestaurant(Context context, String restaurantName) {
        ArrayList<Food> foods = Food.getAllFoods(context);
        ArrayList<Food> result = new ArrayList<>();
        for (Food food : foods)
            if (food.restaurant.equals(restaurantName))
                result.add(food);
        return result;
    }
}
