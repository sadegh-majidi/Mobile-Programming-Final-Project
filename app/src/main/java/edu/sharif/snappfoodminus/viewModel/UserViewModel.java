package edu.sharif.snappfoodminus.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.sharif.snappfoodminus.model.User;
import edu.sharif.snappfoodminus.repository.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;
    private final LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.userRepository = new UserRepository(application);
        this.allUsers = this.userRepository.getAllUsers();
    }

    public LiveData<User> getUserById(int id) {
        return this.userRepository.getUserById(id);
    }

    public LiveData<User> getUserByUsername(String username) {
        return this.userRepository.getUserByUsername(username);
    }

    public void insertUser(User user) {
        this.userRepository.insertUser(user);
    }

    public void updateUser(User user) {
        this.userRepository.updateUser(user);
    }

    public void deleteUser(User user) {
        this.userRepository.deleteUser(user);
    }

    public void deleteAllUsers() {
        this.userRepository.deleteAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return this.allUsers;
    }
}
