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
import edu.sharif.snappfoodminus.model.Rating;

@Dao
public interface RatingDao {
    @Query("SELECT * FROM " + Constants.RATING_TABLE_NAME + " ORDER BY id ASC")
    LiveData<List<Rating>> getAllRatings();

    @Query("SELECT * FROM " + Constants.RATING_TABLE_NAME + " WHERE food_id=:foodId")
    LiveData<List<Rating>> getRatingsByFood(int foodId);

    @Query("SELECT * FROM " + Constants.RATING_TABLE_NAME + " WHERE id=:id")
    LiveData<Rating> getRatingById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRating(Rating rating);

    @Update
    void updateRating(Rating rating);

    @Delete
    void deleteRating(Rating rating);

    @Query("DELETE FROM " + Constants.RATING_TABLE_NAME)
    void deleteAllRatings();
}
