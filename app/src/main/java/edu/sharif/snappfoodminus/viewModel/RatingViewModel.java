package edu.sharif.snappfoodminus.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.model.Rating;
import edu.sharif.snappfoodminus.repository.RatingRepository;

public class RatingViewModel extends AndroidViewModel {
    private final RatingRepository ratingRepository;
    private final LiveData<List<Rating>> allRatings;

    public RatingViewModel(@NonNull Application application) {
        super(application);
        this.ratingRepository = new RatingRepository(application);
        this.allRatings = this.ratingRepository.getAllRatings();
    }

    public LiveData<List<Rating>> getRatingsByFood(int foodId) {
        return this.ratingRepository.getRatingsByFood(foodId);
    }

    public LiveData<Rating> getRatingById(long id) {
        return this.ratingRepository.getRatingById(id);
    }

    public void insertRating(Rating rating) {
        this.ratingRepository.insertRating(rating);
    }

    public void updateRating(Rating rating) {
        this.ratingRepository.updateRating(rating);
    }

    public void deleteRating(Rating rating) {
        this.ratingRepository.deleteRating(rating);
    }

    public void deleteAllRatings() {
        this.ratingRepository.deleteAllRatings();
    }

    public LiveData<List<Rating>> getAllRatings() {
        return this.allRatings;
    }
}
