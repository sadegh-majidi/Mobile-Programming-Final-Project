package edu.sharif.snappfoodminus.model;

import android.util.Pair;

import java.util.ArrayList;

public class CartRepository {
    public static ArrayList<Pair<Food, Integer>> items;
    public static int totalPrice;

    public static void resetCart() {
        items = new ArrayList<>();
        totalPrice = 0;
    }

    public static void updateCart(Food food, int count) {
        int idx = -1;
        for (int i = 0; i < items.size(); i++) {
            Pair<Food, Integer> item = items.get(i);
            if (item.first.name.equals(food.name))
                idx = i;
        }
        if (idx != -1) {
            totalPrice -= items.get(idx).second * items.get(idx).first.price;
            items.remove(idx);
        }
        if (count > 0) {
            items.add(new Pair<>(food, count));
            totalPrice += count * food.price;
        }
    }
}
