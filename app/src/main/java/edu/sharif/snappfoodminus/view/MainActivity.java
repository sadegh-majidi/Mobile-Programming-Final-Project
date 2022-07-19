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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new LoginFragment()).commit();
    }
}