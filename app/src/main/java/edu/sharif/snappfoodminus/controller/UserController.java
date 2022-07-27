package edu.sharif.snappfoodminus.controller;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

import edu.sharif.snappfoodminus.model.LoginRepository;
import edu.sharif.snappfoodminus.model.User;
import edu.sharif.snappfoodminus.view.MainActivity;

public class UserController {

    private Context context;

    public UserController(Context context) {
        this.context = context;
    }

    public String getUsernameError(String username) {
        if (username.isEmpty())
            return "Username field can not be blank";
        if (username.length() < 3)
            return "Username must be at least 3 characters long";
        if (!Pattern.matches("^[a-zA-Z0-9]+$", username))
            return "Only letters (a-z) and numbers (0-9) are allowed for username";
        return null;
    }

    public String getPasswordError(String password) {
        if (password.isEmpty())
            return "Password field can not be blank";
        if (password.length() < 4)
            return "Password must be at least 4 characters long";
        return null;
    }

    public String getNameError(String name) {
        if (name.isEmpty())
            return "Name field can not be blank";
        return null;
    }

    public User login(String username, String password) {
        User user = User.getUserByUsername(context, username);
        if (user != null && user.password.equals(password))
            return user;
        return null;
    }

    public String register(User toBeRegisteredUser) {
        ArrayList<User> users = User.getAllUsers(context);
        for (User user: users) {
            if (user.username.equals(toBeRegisteredUser.username))
                return "Choose another username";
        }
        return null;
    }

    public void logout() {
        LoginRepository.username = null;
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(context,"Successfully signed out" , Toast.LENGTH_SHORT).show();
        context.startActivity(intent);
    }
}