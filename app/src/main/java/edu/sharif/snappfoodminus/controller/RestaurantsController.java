package edu.sharif.snappfoodminus.controller;

import android.content.Context;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.sharif.snappfoodminus.model.Filter;
import edu.sharif.snappfoodminus.model.Food;
import edu.sharif.snappfoodminus.model.Order;
import edu.sharif.snappfoodminus.model.Restaurant;
import edu.sharif.snappfoodminus.model.Review;

public class RestaurantsController {

    private Context context;

    public RestaurantsController(Context context) {
        this.context = context;
    }

    public ArrayList<Restaurant> getFilteredRestaurants(ArrayList<Filter> filters) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        for (Restaurant restaurant: Restaurant.getAllRestaurants(context)) {
            boolean valid = false;
            for (Filter filter: filters)
                if (filter.state && !Food.getFoodsByRestaurantAndCategory(context, restaurant.name, filter.category).isEmpty())
                    valid = true;
            if (valid)
                restaurants.add(restaurant);
        }
        return restaurants;
    }

    public double getRestaurantRate(String restaurantName) {
        ArrayList<Order> orders = Order.getOrdersByRestaurant(context, restaurantName);
        double sumRate = 0;
        int count = 0;
        for (Order order: orders) {
            if (order.reviewID != null && order.reviewID != 0) {
                Review review = Review.getReviewByID(context, order.reviewID);
                sumRate += review.rate;
                count ++;
            }
        }
        if (count > 0)
            return sumRate / count;
        else
            return -1.0;
    }

    public String getRestaurantRateText(String restaurantName) {
        double rate = getRestaurantRate(restaurantName);
        return rate == -1 ? "NEW" : new DecimalFormat("0.0").format(rate);
    }
}
