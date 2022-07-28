package edu.sharif.snappfoodminus.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Review {
    public Integer id;
    public int rate;
    public String description;

    public Review(Integer id, int rate, String description) {
        this.id = id;
        this.rate = rate;
        this.description = description;
    }

    public static void addReview(Context context, Review review) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.REVIEW_RATE, review.rate);
        contentValues.put(DBHelper.REVIEW_DESCRIPTION, review.description);
        db.insert(DBHelper.REVIEW_TABLE_NAME, null, contentValues);
        db.close();
    }

    public static void deleteReview(Context context, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        db.delete(DBHelper.REVIEW_TABLE_NAME,
                DBHelper.REVIEW_ID + "=?", new String[]{key});
        db.close();
    }

    public static ArrayList<Review> getAllReviews(Context context) {
        ArrayList<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        String query = "SELECT * FROM " + DBHelper.REVIEW_TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                reviews.add(new Review(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return reviews;
    }

    public static Review getReviewByID(Context context, int id) {
        ArrayList<Review> reviews = getAllReviews(context);
        for (Review review: reviews)
            if (review.id == id)
                return review;
        return null;
    }

    public static ArrayList<Review> getReviewsByRestaurant(Context context, String restaurant) {
        ArrayList<Order> orders = Order.getOrdersByRestaurant(context, restaurant);
        ArrayList<Review> result = new ArrayList<>();
        for (Order order : orders)
            if (order.reviewID != null)
                result.add(getReviewByID(context, order.reviewID));
        return result;
    }
}
