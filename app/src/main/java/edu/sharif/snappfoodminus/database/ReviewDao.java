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
import edu.sharif.snappfoodminus.model.Review;

@Dao
public interface ReviewDao {
    @Query("SELECT * FROM " + Constants.REVIEW_TABLE_NAME + " ORDER BY id ASC")
    LiveData<List<Review>> getAllReviews();

    @Query("SELECT * FROM " + Constants.REVIEW_TABLE_NAME + " WHERE food_id=:foodId")
    LiveData<List<Review>> getReviewsByFood(int foodId);

    @Query("SELECT * FROM " + Constants.REVIEW_TABLE_NAME + " WHERE id=:id")
    LiveData<Review> getReviewById(long id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReview(Review review);

    @Update
    void updateReview(Review review);

    @Delete
    void deleteReview(Review review);

    @Query("DELETE FROM " + Constants.REVIEW_TABLE_NAME)
    void deleteAllReviews();
}
