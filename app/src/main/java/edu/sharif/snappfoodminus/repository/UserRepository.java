package edu.sharif.snappfoodminus.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.database.DataBase;
import edu.sharif.snappfoodminus.database.UserDao;
import edu.sharif.snappfoodminus.model.User;

public class UserRepository {
    private final UserDao userDao;
    private final LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        DataBase database = DataBase.getInstance(application);
        this.userDao = database.userDao();
        this.allUsers = userDao.getAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return this.allUsers;
    }

    public LiveData<User> getUserById(int id) {
        return this.userDao.getUserById(id);
    }

    public LiveData<User> getUserByUsername(String username) {
        return this.userDao.getUserByUsername(username);
    }

    public void insertUser(User user) {
        DataBase.databaseWriteExecutor.execute(() -> this.userDao.insertUser(user));
    }

    public void updateUser(User user) {
        DataBase.databaseWriteExecutor.execute(() -> this.userDao.updateUser(user));
    }

    public void deleteUser(User user) {
        DataBase.databaseWriteExecutor.execute(() -> this.userDao.deleteUser(user));
    }

    public void deleteAllUsers() {
        DataBase.databaseWriteExecutor.execute(userDao::deleteAllUsers);
    }
}