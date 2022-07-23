package edu.sharif.snappfoodminus.temp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Request {
    public String requester;
    public String foodName;
    public String foodJSON;
    public boolean deleteMode;

    public Request(String requester, String foodName, String foodJSON, boolean deleteMode) {
        this.requester = requester;
        this.foodName = foodName;
        this.foodJSON = foodJSON;
        this.deleteMode = deleteMode;
    }

    public static void addRequest(Context context, Request request) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.REQUEST_REQUESTER, request.requester);
        contentValues.put(DBHelper.REQUEST_FOOD_NAME, request.foodName);
        contentValues.put(DBHelper.REQUEST_FOOD_JSON, request.foodJSON);
        contentValues.put(DBHelper.REQUEST_DELETE_MODE, request.deleteMode);
        db.insert(DBHelper.REQUEST_TABLE_NAME, null, contentValues);
        db.close();
    }

    public static void updateRequest(Context context, Request request, int id) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.REQUEST_REQUESTER, request.requester);
        contentValues.put(DBHelper.REQUEST_FOOD_NAME, request.foodName);
        contentValues.put(DBHelper.REQUEST_FOOD_JSON, request.foodJSON);
        contentValues.put(DBHelper.REQUEST_DELETE_MODE, request.deleteMode);
        db.update(DBHelper.REQUEST_TABLE_NAME,
                contentValues,
                DBHelper.REQUEST_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public static void deleteRequest(Context context, int id) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        db.delete(DBHelper.REQUEST_TABLE_NAME,
                DBHelper.REQUEST_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public static ArrayList<Request> getAllRequests(Context context) {
        ArrayList<Request> requests = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        String query = "SELECT * FROM " + DBHelper.REQUEST_TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                requests.add(new Request(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) > 0
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return requests;
    }

    public static ArrayList<Request> getRequestsByRequester(Context context, String requester) {
        ArrayList<Request> requests = Request.getAllRequests(context);
        ArrayList<Request> result = new ArrayList<>();
        for (Request request : requests)
            if (request.requester.equals(requester))
                result.add(request);
        return result;
    }

}
