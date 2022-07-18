package edu.sharif.snappfoodminus.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.database.DataBase;
import edu.sharif.snappfoodminus.database.RatingDao;
import edu.sharif.snappfoodminus.model.Rating;

public class RatingRepository {
    private final RatingDao ratingDao;
    private final LiveData<List<Rating>> allRatings;

    public RatingRepository(Application application) {
        DataBase database = DataBase.getInstance(application);
        this.ratingDao = database.ratingDao();
        this.allRatings = ratingDao.getAllRatings();
    }

    public LiveData<List<Rating>> getAllRatings() {
        return this.allRatings;
    }

    public LiveData<List<Rating>> getRatingsByFood(int foodId) {
        return this.ratingDao.getRatingsByFood(foodId);
    }

    public LiveData<Rating> getRatingById(long id) {
        return this.ratingDao.getRatingById(id);
    }

    public void insertRating(Rating rating) {
        DataBase.databaseWriteExecutor.execute(() -> this.ratingDao.insertRating(rating));
    }

    public void updateRating(Rating rating) {
        DataBase.databaseWriteExecutor.execute(() -> this.ratingDao.updateRating(rating));
    }

    public void deleteRating(Rating rating) {
        DataBase.databaseWriteExecutor.execute(() -> this.ratingDao.deleteRating(rating));
    }

    public void deleteAllRatings() {
        DataBase.databaseWriteExecutor.execute(ratingDao::deleteAllRatings);
    }
}
