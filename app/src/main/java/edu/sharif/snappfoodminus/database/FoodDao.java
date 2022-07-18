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
import edu.sharif.snappfoodminus.model.Food;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM " + Constants.FOOD_TABLE_NAME + " ORDER BY id ASC")
    LiveData<List<Food>> getAllFoods();

    @Query("SELECT * FROM " + Constants.FOOD_TABLE_NAME + " WHERE restaurant_id=:restaurantId")
    LiveData<List<Food>> getFoodsByRestaurant(int restaurantId);

    @Query("SELECT * FROM " + Constants.FOOD_TABLE_NAME + " WHERE category_id=:categoryId")
    LiveData<List<Food>> getFoodsByCategory(int categoryId);

    @Query("SELECT * FROM " + Constants.FOOD_TABLE_NAME + " WHERE id=:id")
    LiveData<Food> getFoodById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFood(Food food);

    @Update
    void updateFood(Food food);

    @Delete
    void deleteFood(Food food);

    @Query("DELETE FROM " + Constants.FOOD_TABLE_NAME)
    void deleteAllFoods();
}
