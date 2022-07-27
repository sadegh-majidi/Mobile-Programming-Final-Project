package edu.sharif.snappfoodminus.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Discount {
    public String restaurant;
    public int percentage;

    public Discount(String restaurant, int percentage) {
        this.restaurant = restaurant;
        this.percentage = percentage;
    }

    public static void addDiscount(Context context, Discount discount) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.DISCOUNT_RESTAURANT, discount.restaurant);
        contentValues.put(DBHelper.DISCOUNT_PERCENTAGE, discount.percentage);
        db.insert(DBHelper.DISCOUNT_TABLE_NAME, null, contentValues);
        db.close();
    }

    public static void deleteDiscount(Context context, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        db.delete(DBHelper.DISCOUNT_TABLE_NAME,
                DBHelper.DISCOUNT_RESTAURANT + "=?", new String[]{key});
        db.close();
    }

    public static ArrayList<Discount> getAllDiscounts(Context context) {
        ArrayList<Discount> discounts = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        String query = "SELECT * FROM " + DBHelper.DISCOUNT_TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                discounts.add(new Discount(
                        cursor.getString(0),
                        cursor.getInt(1)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return discounts;
    }

    public static Discount getDiscountByRestaurant(Context context, String restaurant) {
        ArrayList<Discount> discounts = getAllDiscounts(context);
        for (Discount discount: discounts)
            if (discount.restaurant.equals(restaurant))
                return discount;
        return null;
    }
}
