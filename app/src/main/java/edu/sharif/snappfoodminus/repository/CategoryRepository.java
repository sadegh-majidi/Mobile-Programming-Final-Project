package edu.sharif.snappfoodminus.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.database.CategoryDao;
import edu.sharif.snappfoodminus.database.DataBase;
import edu.sharif.snappfoodminus.model.Category;

public class CategoryRepository {
    private final CategoryDao categoryDao;
    private final LiveData<List<Category>> allCategories;

    public CategoryRepository(Application application) {
        DataBase database = DataBase.getInstance(application);
        this.categoryDao = database.categoryDao();
        this.allCategories = categoryDao.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return this.allCategories;
    }

    public LiveData<Category> getCategoryById(int id) {
        return this.categoryDao.getCategoryById(id);
    }

    public void insertCategory(Category category) {
        DataBase.databaseWriteExecutor.execute(() -> this.categoryDao.insertCategory(category));
    }

    public void updateCategory(Category category) {
        DataBase.databaseWriteExecutor.execute(() -> this.categoryDao.updateCategory(category));
    }

    public void deleteCategory(Category category) {
        DataBase.databaseWriteExecutor.execute(() -> this.categoryDao.deleteCategory(category));
    }

    public void deleteAllCategories() {
        DataBase.databaseWriteExecutor.execute(categoryDao::deleteAllCategories);
    }
}
