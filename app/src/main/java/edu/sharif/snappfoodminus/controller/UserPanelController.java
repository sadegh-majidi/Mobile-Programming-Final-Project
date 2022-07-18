package edu.sharif.snappfoodminus.controller;

import android.content.Context;

public class UserPanelController {

    private Context context;

    public UserPanelController(Context context) {
        this.context = context;
    }

    public String getError(String newName, String oldPassword, String oldPasswordConfirmation, String newPassword) {
        String errorMsg = null;

        if (oldPassword.isEmpty() || oldPasswordConfirmation.isEmpty() || newPassword.isEmpty()
                || newName.isEmpty()) {
            return "Please fill out all the fields.";
        }else if (!oldPassword.equals(oldPasswordConfirmation)) {
            return "Passwords don't match.";
        }else if (!isPasswordCorrect(oldPassword)) {
            return "Your old password is incorrect.";
        }else {
            return null;
        }
    }


    private boolean isPasswordCorrect(String password) {
        return true;
    }
}
