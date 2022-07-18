package edu.sharif.snappfoodminus.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.model.Category;
import edu.sharif.snappfoodminus.repository.CategoryRepository;


public class CategoryViewModel extends AndroidViewModel {
    private final CategoryRepository categoryRepository;
    private final LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        this.categoryRepository = new CategoryRepository(application);
        this.allCategories = this.categoryRepository.getAllCategories();
    }

    public LiveData<Category> getCategoryById(int id) {
        return this.categoryRepository.getCategoryById(id);
    }

    public void insertCategory(Category category) {
        this.categoryRepository.insertCategory(category);
    }

    public void updateCategory(Category category) {
        this.categoryRepository.updateCategory(category);
    }

    public void deleteCategory(Category category) {
        this.categoryRepository.deleteCategory(category);
    }

    public void deleteAllCategories() {
        this.categoryRepository.deleteAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return this.allCategories;
    }
}
