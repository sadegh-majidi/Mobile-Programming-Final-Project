package edu.sharif.snappfoodminus.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.model.Food;
import edu.sharif.snappfoodminus.repository.FoodRepository;


public class FoodViewModel extends AndroidViewModel {
    private final FoodRepository foodRepository;
    private final LiveData<List<Food>> allFoods;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        this.foodRepository = new FoodRepository(application);
        this.allFoods = this.foodRepository.getAllFoods();
    }

    public LiveData<List<Food>> getFoodsByCategory(int categoryId) {
        return this.foodRepository.getFoodsByCategory(categoryId);
    }

    public LiveData<List<Food>> getFoodsByRestaurant(int restaurantId) {
        return this.foodRepository.getFoodsByRestaurant(restaurantId);
    }

    public LiveData<Food> getFoodById(int id) {
        return this.foodRepository.getFoodById(id);
    }

    public void insertFood(Food food) {
        this.foodRepository.insertFood(food);
    }

    public void updateFood(Food food) {
        this.foodRepository.updateFood(food);
    }

    public void deleteFood(Food food) {
        this.foodRepository.deleteFood(food);
    }

    public void deleteAllFoods() {
        this.foodRepository.deleteAllFoods();
    }

    public LiveData<List<Food>> getAllFoods() {
        return this.allFoods;
    }
}
