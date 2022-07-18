package edu.sharif.snappfoodminus.controller;

public class ProfileController {



    public void changeUsername(String newUsername) throws Exception {
        if(!isUsernameValid(newUsername)) {
            throw new Exception("Username is unavailable.");
        } // Todo kiram to sadegh

    }

    public void changeName(String newName) throws Exception {
        // Todo kiram to sadegh
    }






    private boolean isUsernameValid(String username) {
        return false;
    }




}
