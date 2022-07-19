package edu.sharif.snappfoodminus.view;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.repository.UserRepository;
import edu.sharif.snappfoodminus.temp.Role;
import edu.sharif.snappfoodminus.temp.User;

public class MainActivity extends AppCompatActivity {

    private static final String Shared_KEY = "edu.sharif.snappfoodminus";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(Shared_KEY, MODE_PRIVATE);

        initAdmin();

        if (sharedPreferences.getBoolean("DarkMode", false))
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new LoginFragment()).commit();
    }

    private void initAdmin() {
        User admin = User.getUserByUsername(this, "admin");
        if (admin == null) {
            admin = new User("admin", "admin", "admin", Role.ADMIN);
            User.addUser(this, admin);
        }
    }
}