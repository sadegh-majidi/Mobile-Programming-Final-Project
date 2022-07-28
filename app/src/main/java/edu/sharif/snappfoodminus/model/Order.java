package edu.sharif.snappfoodminus.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Order {
    public Integer id;
    public String customer;
    public String restaurant;
    public ArrayList<Pair<Food, Integer>> items;
    public int totalPrice;
    public int discount;
    public int shippingCost;
    public int toPay;
    public Integer reviewID;

    public Order(Integer id, String customer, String restaurant, ArrayList<Pair<Food, Integer>> items, int totalPrice, int discount, int shippingCost, int toPay, Integer reviewID) {
        this.id = id;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = items;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.shippingCost = shippingCost;
        this.toPay = toPay;
        this.reviewID = reviewID;
    }

    public static void addOrder(Context context, Order order) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.ORDER_CUSTOMER, order.customer);
        contentValues.put(DBHelper.ORDER_RESTAURANT, order.restaurant);
        contentValues.put(DBHelper.ORDER_ITEMS, new Gson().toJson(order.items));
        contentValues.put(DBHelper.ORDER_TOTAL_PRICE, order.totalPrice);
        contentValues.put(DBHelper.ORDER_DISCOUNT, order.discount);
        contentValues.put(DBHelper.ORDER_SHIPPING_COST, order.shippingCost);
        contentValues.put(DBHelper.ORDER_TO_PAY, order.toPay);
        contentValues.put(DBHelper.ORDER_REVIEW_ID, order.reviewID);
        db.insert(DBHelper.ORDER_TABLE_NAME, null, contentValues);
        db.close();
    }

    public static void updateOrder(Context context, Order order, String key) {
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.ORDER_CUSTOMER, order.customer);
        contentValues.put(DBHelper.ORDER_RESTAURANT, order.restaurant);
        contentValues.put(DBHelper.ORDER_ITEMS, new Gson().toJson(order.items));
        contentValues.put(DBHelper.ORDER_TOTAL_PRICE, order.totalPrice);
        contentValues.put(DBHelper.ORDER_DISCOUNT, order.discount);
        contentValues.put(DBHelper.ORDER_SHIPPING_COST, order.shippingCost);
        contentValues.put(DBHelper.ORDER_TO_PAY, order.toPay);
        contentValues.put(DBHelper.ORDER_REVIEW_ID, order.reviewID);
        db.update(DBHelper.ORDER_TABLE_NAME, contentValues,
                DBHelper.ORDER_ID + "=?", new String[]{key});
        db.close();
    }

    public static ArrayList<Order> getAllOrders(Context context) {
        ArrayList<Order> orders = new ArrayList<>();
        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        String query = "SELECT * FROM " + DBHelper.ORDER_TABLE_NAME;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                orders.add(new Order(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        new Gson().fromJson(cursor.getString(3), new TypeToken<ArrayList<Pair<Food, Integer>>>(){}.getType()),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getInt(7),
                        cursor.getInt(8)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orders;
    }

    public static ArrayList<Order> getOrdersByCustomer(Context context, String customer) {
        ArrayList<Order> orders = getAllOrders(context);
        ArrayList<Order> result = new ArrayList<>();
        for (Order order : orders)
            if (order.customer.equals(customer))
                result.add(order);
        return result;
    }

    public static ArrayList<Order> getOrdersByRestaurant(Context context, String restaurant) {
        ArrayList<Order> orders = getAllOrders(context);
        ArrayList<Order> result = new ArrayList<>();
        for (Order order : orders)
            if (order.restaurant.equals(restaurant))
                result.add(order);
        return result;
    }
}
