package edu.sharif.snappfoodminus.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.database.DataBase;
import edu.sharif.snappfoodminus.database.RestaurantDao;
import edu.sharif.snappfoodminus.model.Restaurant;

public class RestaurantRepository {
    private final RestaurantDao restaurantDao;
    private final LiveData<List<Restaurant>> allRestaurants;

    public RestaurantRepository(Application application) {
        DataBase database = DataBase.getInstance(application);
        this.restaurantDao = database.restaurantDao();
        this.allRestaurants = restaurantDao.getAllRestaurants();
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        return this.allRestaurants;
    }

    public LiveData<List<Restaurant>> getRestaurantsByOwner(int ownerId) {
        return this.restaurantDao.getRestaurantsByOwner(ownerId);
    }

    public LiveData<Restaurant> getRestaurantById(int id) {
        return this.restaurantDao.getRestaurantById(id);
    }

    public void insertRestaurant(Restaurant restaurant) {
        DataBase.databaseWriteExecutor.execute(() -> this.restaurantDao.insertRestaurant(restaurant));
    }

    public void updateRestaurant(Restaurant restaurant) {
        DataBase.databaseWriteExecutor.execute(() -> this.restaurantDao.updateRestaurant(restaurant));
    }

    public void deleteRestaurant(Restaurant restaurant) {
        DataBase.databaseWriteExecutor.execute(() -> this.restaurantDao.deleteRestaurant(restaurant));
    }

    public void deleteAllRestaurants() {
        DataBase.databaseWriteExecutor.execute(restaurantDao::deleteAllRestaurants);
    }
}
