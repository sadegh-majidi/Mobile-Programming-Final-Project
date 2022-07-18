package edu.sharif.snappfoodminus.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.model.Restaurant;
import edu.sharif.snappfoodminus.repository.RestaurantRepository;

public class RestaurantViewModel extends AndroidViewModel {
    private final RestaurantRepository restaurantRepository;
    private final LiveData<List<Restaurant>> allRestaurants;

    public RestaurantViewModel(@NonNull Application application) {
        super(application);
        this.restaurantRepository = new RestaurantRepository(application);
        this.allRestaurants = this.restaurantRepository.getAllRestaurants();
    }

    public LiveData<List<Restaurant>> getRestaurantsByOwner(int ownerId) {
        return this.restaurantRepository.getRestaurantsByOwner(ownerId);
    }

    public LiveData<Restaurant> getRestaurantById(int id) {
        return this.restaurantRepository.getRestaurantById(id);
    }

    public void insertRestaurant(Restaurant restaurant) {
        this.restaurantRepository.insertRestaurant(restaurant);
    }

    public void updateRestaurant(Restaurant restaurant) {
        this.restaurantRepository.updateRestaurant(restaurant);
    }

    public void deleteRestaurant(Restaurant restaurant) {
        this.restaurantRepository.deleteRestaurant(restaurant);
    }

    public void deleteAllRestaurants() {
        this.restaurantRepository.deleteAllRestaurants();
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        return this.allRestaurants;
    }
}
