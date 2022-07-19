package edu.sharif.snappfoodminus.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.repository.UserRepository;
import edu.sharif.snappfoodminus.temp.Role;
import edu.sharif.snappfoodminus.temp.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAdmin();

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