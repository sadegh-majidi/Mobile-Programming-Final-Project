package edu.sharif.snappfoodminus.temp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Category {
    public String name;

    public Category(String name) {
        this.name = name;
    }

    public static void addCategory(Context context, Category category) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.CATEGORY_NAME, category.name);
        db.insert(DBHelper.CATEGORY_TABLE_NAME, null, contentValues);
        db.close();
    }

    public static void updateCategory(Context context, Category category, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.CATEGORY_NAME, category.name);
        db.update(DBHelper.CATEGORY_TABLE_NAME, contentValues,
                DBHelper.CATEGORY_NAME + "=?", new String[]{key});
        db.close();
    }

    public static void deleteCategory(Context context, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        db.delete(DBHelper.CATEGORY_TABLE_NAME,
                DBHelper.CATEGORY_NAME + "=?", new String[]{key});
        db.close();
    }

    public static ArrayList<Category> getAllCategories(Context context) {
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
}
