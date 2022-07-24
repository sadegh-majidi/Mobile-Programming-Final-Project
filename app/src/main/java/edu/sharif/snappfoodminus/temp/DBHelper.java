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

    public static final String FOOD_TABLE_NAME = "food";
    public static final String FOOD_NAME = "name";
    public static final String FOOD_CATEGORY = "category";
    public static final String FOOD_RESTAURANT = "restaurant";
    public static final String FOOD_DESCRIPTION = "description";
    public static final String FOOD_PRICE = "price";
    private static final String FOOD_CREATE_TABLE_QUERY =
            "CREATE TABLE " + FOOD_TABLE_NAME + " (" +
                    FOOD_NAME + " TEXT, " +
                    FOOD_CATEGORY + " TEXT, " +
                    FOOD_RESTAURANT + " TEXT, " +
                    FOOD_DESCRIPTION + " TEXT, " +
                    FOOD_PRICE + "  INTEGER, " +
                    "FOREIGN KEY (" + FOOD_CATEGORY + ") " +
                    "REFERENCES " + CATEGORY_TABLE_NAME + "(" + CATEGORY_NAME + ") " +
                    "ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "FOREIGN KEY (" + FOOD_RESTAURANT + ") " +
                    "REFERENCES " + RESTAURANT_TABLE_NAME + "(" + RESTAURANT_NAME + ") " +
                    "ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "PRIMARY KEY (" + FOOD_RESTAURANT + ", " + FOOD_NAME + "));";
    private static final String FOOD_DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + FOOD_TABLE_NAME + ";";

    public static final String REQUEST_TABLE_NAME = "request";
    public static final String REQUEST_ID = "id";
    public static final String REQUEST_REQUESTER = "requester";
    public static final String REQUEST_RESTAURANT = "restaurant";
    public static final String REQUEST_FOOD = "food";
    public static final String REQUEST_DATA = "data";
    public static final String REQUEST_STATUS = "status";
    private static final String REQUEST_CREATE_TABLE_QUERY =
            "CREATE TABLE " + REQUEST_TABLE_NAME + " (" +
                    REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    REQUEST_REQUESTER + " TEXT, " +
                    REQUEST_RESTAURANT + " TEXT, " +
                    REQUEST_FOOD + " TEXT, " +
                    REQUEST_DATA + " TEXT, " +
                    REQUEST_STATUS + " TEXT, " +
                    "FOREIGN KEY (" + REQUEST_REQUESTER + ") " +
                    "REFERENCES " + USER_TABLE_NAME + "(" + USER_NAME + ") " +
                    "ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "FOREIGN KEY (" + REQUEST_RESTAURANT + ") " +
                    "REFERENCES " + RESTAURANT_TABLE_NAME + "(" + RESTAURANT_NAME + ") " +
                    "ON DELETE CASCADE ON UPDATE CASCADE, " +
                    "FOREIGN KEY (" + REQUEST_FOOD + ") " +
                    "REFERENCES " + FOOD_TABLE_NAME + "(" + FOOD_NAME + ") " +
                    "ON DELETE CASCADE ON UPDATE CASCADE);";
    private static final String REQUEST_DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + REQUEST_TABLE_NAME + ";";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CATEGORY_CREATE_TABLE_QUERY);
        db.execSQL(USER_CREATE_TABLE_QUERY);
        db.execSQL(RESTAURANT_CREATE_TABLE_QUERY);
        db.execSQL(FOOD_CREATE_TABLE_QUERY);
        db.execSQL(REQUEST_CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(REQUEST_DROP_TABLE_QUERY);
        db.execSQL(FOOD_DROP_TABLE_QUERY);
        db.execSQL(RESTAURANT_DROP_TABLE_QUERY);
        db.execSQL(USER_DROP_TABLE_QUERY);
        db.execSQL(CATEGORY_DROP_TABLE_QUERY);
        onCreate(db);
    }
}
