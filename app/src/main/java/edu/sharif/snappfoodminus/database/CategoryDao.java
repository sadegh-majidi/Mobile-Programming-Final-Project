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
import edu.sharif.snappfoodminus.model.Category;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM " + Constants.CATEGORY_TABLE_NAME + " ORDER BY id ASC")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM " + Constants.CATEGORY_TABLE_NAME + " WHERE id=:id")
    LiveData<Category> getCategoryById(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("DELETE FROM " + Constants.CATEGORY_TABLE_NAME)
    void deleteAllCategories();
}
