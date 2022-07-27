package edu.sharif.snappfoodminus.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Request {
    public Integer id;
    public String requester;
    public String restaurant;
    public String food;
    public String data;
    public RequestStatus status;
    public String description;

    public Request(Integer id, String requester, String restaurant, String food, String data, RequestStatus status, String description) {
        this.id = id;
        this.requester = requester;
        this.restaurant = restaurant;
        this.food = food;
        this.data = data;
        this.status = status;
        this.description = description;
    }

    public static void addRequest(Context context, Request request) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.REQUEST_REQUESTER, request.requester);
        contentValues.put(DBHelper.REQUEST_RESTAURANT, request.restaurant);
        contentValues.put(DBHelper.REQUEST_FOOD, request.food);
        contentValues.put(DBHelper.REQUEST_DATA, request.data);
        contentValues.put(DBHelper.REQUEST_STATUS, request.status.name());
        contentValues.put(DBHelper.REQUEST_DESCRIPTION, request.description);
        db.insert(DBHelper.REQUEST_TABLE_NAME, null, contentValues);
        db.close();
    }

    public static void updateRequest(Context context, Request request, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.REQUEST_REQUESTER, request.requester);
        contentValues.put(DBHelper.REQUEST_RESTAURANT, request.restaurant);
        contentValues.put(DBHelper.REQUEST_FOOD, request.food);
        contentValues.put(DBHelper.REQUEST_DATA, request.data);
        contentValues.put(DBHelper.REQUEST_STATUS, request.status.name());
        contentValues.put(DBHelper.REQUEST_DESCRIPTION, request.description);
        db.update(DBHelper.REQUEST_TABLE_NAME, contentValues,
                DBHelper.REQUEST_ID + "=?", new String[]{key});
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
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        RequestStatus.valueOf(cursor.getString(5)),
                        cursor.getString(6)
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
