package edu.sharif.snappfoodminus.temp;

import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;

import java.util.ArrayList;

public class CartRepository {
    public static ArrayList<Pair<Food, Integer>> items;

    public static void resetCart() {
        items = new ArrayList<>();
    }

    public static void updateCart(Food food, int count) {
        int idx = -1;
        for (int i = 0; i < items.size(); i++) {
            Pair<Food, Integer> item = items.get(i);
            if (item.first.name.equals(food.name))
                idx = i;
        }
        if (idx != -1)
            items.remove(idx);
        if (count > 0)
            items.add(new Pair<>(food, count));
        Log.d("miu", new Gson().toJson(items));
    }
}
