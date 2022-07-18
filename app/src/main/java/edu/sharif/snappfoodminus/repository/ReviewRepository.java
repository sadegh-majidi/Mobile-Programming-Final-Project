package edu.sharif.snappfoodminus.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.database.DataBase;
import edu.sharif.snappfoodminus.database.ReviewDao;
import edu.sharif.snappfoodminus.model.Review;

public class ReviewRepository {
    private final ReviewDao reviewDao;
    private final LiveData<List<Review>> allReviews;

    public ReviewRepository(Application application) {
        DataBase database = DataBase.getInstance(application);
        this.reviewDao = database.reviewDao();
        this.allReviews = reviewDao.getAllReviews();
    }

    public LiveData<List<Review>> getAllReviews() {
        return this.allReviews;
    }

    public LiveData<List<Review>> getReviewsByFood(int foodId) {
        return this.reviewDao.getReviewsByFood(foodId);
    }

    public LiveData<Review> getReviewById(long id) {
        return this.reviewDao.getReviewById(id);
    }

    public void insertReview(Review review) {
        DataBase.databaseWriteExecutor.execute(() -> this.reviewDao.insertReview(review));
    }

    public void updateReview(Review review) {
        DataBase.databaseWriteExecutor.execute(() -> this.reviewDao.updateReview(review));
    }

    public void deleteReview(Review review) {
        DataBase.databaseWriteExecutor.execute(() -> this.reviewDao.deleteReview(review));
    }

    public void deleteAllReviews() {
        DataBase.databaseWriteExecutor.execute(reviewDao::deleteAllReviews);
    }
}
