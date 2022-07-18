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
import edu.sharif.snappfoodminus.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM " + Constants.USER_TABLE_NAME + " ORDER BY id ASC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM " + Constants.USER_TABLE_NAME + " WHERE id=:id")
    LiveData<User> getUserById(int id);

    @Query("SELECT * FROM " + Constants.USER_TABLE_NAME + " WHERE username=:username")
    LiveData<User> getUserByUsername(String username);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM " + Constants.USER_TABLE_NAME)
    void deleteAllUsers();
}
