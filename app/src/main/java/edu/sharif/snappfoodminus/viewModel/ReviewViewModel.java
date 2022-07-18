package edu.sharif.snappfoodminus.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.model.Review;
import edu.sharif.snappfoodminus.repository.ReviewRepository;

public class ReviewViewModel extends AndroidViewModel {
    private final ReviewRepository reviewRepository;
    private final LiveData<List<Review>> allReviews;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        this.reviewRepository = new ReviewRepository(application);
        this.allReviews = this.reviewRepository.getAllReviews();
    }

    public LiveData<List<Review>> getReviewsByFood(int foodId) {
        return this.reviewRepository.getReviewsByFood(foodId);
    }

    public LiveData<Review> getReviewById(long id) {
        return this.reviewRepository.getReviewById(id);
    }

    public void insertReview(Review review) {
        this.reviewRepository.insertReview(review);
    }

    public void updateReview(Review review) {
        this.reviewRepository.updateReview(review);
    }

    public void deleteReview(Review review) {
        this.reviewRepository.deleteReview(review);
    }

    public void deleteAllReviews() {
        this.reviewRepository.deleteAllReviews();
    }

    public LiveData<List<Review>> getAllReviews() {
        return this.allReviews;
    }
}
