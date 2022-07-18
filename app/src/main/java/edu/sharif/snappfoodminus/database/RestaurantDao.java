package edu.sharif.snappfoodminus.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.sharif.snappfoodminus.Constants;
import edu.sharif.snappfoodminus.model.Restaurant;

@Dao
public interface RestaurantDao {
    @Query("SELECT * FROM " + Constants.RESTAURANT_TABLE_NAME + " ORDER BY id ASC")
    LiveData<List<Restaurant>> getAllRestaurants();

    @Query("SELECT * FROM " + Constants.RESTAURANT_TABLE_NAME + " WHERE owner_id=:ownerId")
    LiveData<List<Restaurant>> getRestaurantsByOwner(int ownerId);

    @Query("SELECT * FROM " + Constants.RESTAURANT_TABLE_NAME + " WHERE id=:id")
    LiveData<Restaurant> getRestaurantById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRestaurant(Restaurant restaurant);

    @Update
    void updateRestaurant(Restaurant restaurant);

    @Delete
    void deleteRestaurant(Restaurant restaurant);

    @Query("DELETE FROM " + Constants.RESTAURANT_TABLE_NAME)
    void deleteAllRestaurants();
}
