package edu.sharif.snappfoodminus.temp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database";
    private static final int DATABASE_VERSION = 1;

    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_NAME = "name";
    private static final String CATEGORY_CREATE_TABLE_QUERY =
            "CREATE TABLE " + CATEGORY_TABLE_NAME + " (" +
                    CATEGORY_NAME + " TEXT PRIMARY KEY);";
    private static final String CATEGORY_DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME + ";";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_ROLE = "role";
    private static final String USER_CREATE_TABLE_QUERY =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_USERNAME + " TEXT PRIMARY KEY, " +
                    USER_PASSWORD + " TEXT, " +
                    USER_NAME + " TEXT, " +
                    USER_ROLE + " TEXT);";
    private static final String USER_DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    public static final String RESTAURANT_TABLE_NAME = "restaurant";
    public static final String RESTAURANT_NAME = "name";
    public static final String RESTAURANT_OWNER = "owner";
    public static final String RESTAURANT_SHIPPING_COST = "shipping_cost";
    private static final String RESTAURANT_CREATE_TABLE_QUERY =
            "CREATE TABLE " + RESTAURANT_TABLE_NAME + " (" +
                    RESTAURANT_NAME + " TEXT PRIMARY KEY, " +
                    RESTAURANT_OWNER + " TEXT, " +
                    RESTAURANT_SHIPPING_COST + "  INTEGER, " +
                    "FOREIGN KEY (" + RESTAURANT_OWNER + ") " +
                    "REFERENCES " + USER_TABLE_NAME + "(" + USER_USERNAME + ") " +
                    "ON DELETE CASCADE ON UPDATE CASCADE);";
    private static final String RESTAURANT_DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + RESTAURANT_TABLE_NAME + ";";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CATEGORY_CREATE_TABLE_QUERY);
        db.execSQL(USER_CREATE_TABLE_QUERY);
        db.execSQL(RESTAURANT_CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CATEGORY_DROP_TABLE_QUERY);
        db.execSQL(USER_DROP_TABLE_QUERY);
        db.execSQL(RESTAURANT_DROP_TABLE_QUERY);
        onCreate(db);
    }
}
