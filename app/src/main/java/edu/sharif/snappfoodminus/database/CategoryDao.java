package edu.sharif.snappfoodminus.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.sharif.snappfoodminus.model.Category;
import edu.sharif.snappfoodminus.model.User;

@Dao
public interface CategoryDao {

    @Insert
    void Insert(Category category);

    @Delete
    void delete(Category category);

    @Update
    public void updateCategories(Category... categories);


    @Query("SELECT * FROM category")
    List<Category> getAllCategories();
}
