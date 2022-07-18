package edu.sharif.snappfoodminus.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.sharif.snappfoodminus.model.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    public void updateUsers(User... users);

    @Delete
    void delete(User user);


    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT * FROM user WHERE username LIKE :username")
    public User findUserWithUsername(String username);

    @Query("SELECT * FROM user WHERE name LIKE :name")
    public List<User> findUserWithName(String name);




}
