package edu.sharif.snappfoodminus.temp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class User {
    public String username;
    public String password;
    public String name;
    public Role role;

    public User(String username, String password, String name, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public static void addUser(Context context, User user) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.USER_USERNAME, user.username);
        contentValues.put(DBHelper.USER_PASSWORD, user.password);
        contentValues.put(DBHelper.USER_NAME, user.name);
        contentValues.put(DBHelper.USER_ROLE, user.role.name());
        db.insert(DBHelper.USER_TABLE_NAME, null, contentValues);
        db.close();
    }

    public static void updateUser(Context context, User user, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.USER_USERNAME, user.username);
        contentValues.put(DBHelper.USER_PASSWORD, user.password);
        contentValues.put(DBHelper.USER_NAME, user.name);
        contentValues.put(DBHelper.USER_ROLE, user.role.name());
        db.update(DBHelper.USER_TABLE_NAME, contentValues,
                DBHelper.USER_USERNAME + "=?", new String[]{key});
        db.close();
    }

    public static void deleteUser(Context context, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        db.delete(DBHelper.USER_TABLE_NAME,
                DBHelper.USER_USERNAME + "=?", new String[]{key});
        db.close();
    }

    public static ArrayList<User> getAllUsers(Context context) {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        String query = "SELECT * FROM " + DBHelper.USER_TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                users.add(new User(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        Role.valueOf(cursor.getString(3))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }
}
