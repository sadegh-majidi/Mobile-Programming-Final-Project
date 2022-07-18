package edu.sharif.snappfoodminus.controller;

import android.content.Context;

import java.util.NoSuchElementException;

import edu.sharif.snappfoodminus.database.DataBase;
import edu.sharif.snappfoodminus.database.UserDao;
import edu.sharif.snappfoodminus.model.User;


public class RegisterController {

//    public User registerStudent(String firstName, String lastName, String username, String password, String studentNumber) {
//        if (User.getUser(context, username) == null)
//            return Student.createStudent(context, username, password, firstName, lastName, studentNumber);
//        return null;
//    }
//
//    public User registerProfessor(String firstName, String lastName, String username, String password, String universityName) {
//        if (User.getUser(context, username) == null)
//            return Professor.createProfessor(context, username, password, firstName, lastName, universityName);
//        return null;
//    }

    public void test(Context context) {
        DataBase db = DataBase.getInstance(context);
        System.out.println(db.userDao().getAllUsers());

    }

}
