package edu.sharif.snappfoodminus.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.database.DataBase;
import edu.sharif.snappfoodminus.database.FoodDao;
import edu.sharif.snappfoodminus.model.Food;

public class FoodRepository {
    private final FoodDao foodDao;
    private final LiveData<List<Food>> allFoods;

    public FoodRepository(Application application) {
        DataBase database = DataBase.getInstance(application);
        this.foodDao = database.foodDao();
        this.allFoods = foodDao.getAllFoods();
    }

    public LiveData<List<Food>> getAllFoods() {
        return this.allFoods;
    }

    public LiveData<List<Food>> getFoodsByRestaurant(int restaurantId) {
        return this.foodDao.getFoodsByRestaurant(restaurantId);
    }

    public LiveData<List<Food>> getFoodsByCategory(int categoryId) {
        return this.foodDao.getFoodsByCategory(categoryId);
    }

    public LiveData<Food> getFoodById(int id) {
        return this.foodDao.getFoodById(id);
    }

    public void insertFood(Food food) {
        DataBase.databaseWriteExecutor.execute(() -> this.foodDao.insertFood(food));
    }

    public void updateFood(Food food) {
        DataBase.databaseWriteExecutor.execute(() -> this.foodDao.updateFood(food));
    }

    public void deleteFood(Food food) {
        DataBase.databaseWriteExecutor.execute(() -> this.foodDao.deleteFood(food));
    }

    public void deleteAllFoods() {
        DataBase.databaseWriteExecutor.execute(foodDao::deleteAllFoods);
    }
}
