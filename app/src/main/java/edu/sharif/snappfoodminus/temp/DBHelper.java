package edu.sharif.snappfoodminus.temp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CATEGORY_CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CATEGORY_DROP_TABLE_QUERY);
        onCreate(db);
    }
}
