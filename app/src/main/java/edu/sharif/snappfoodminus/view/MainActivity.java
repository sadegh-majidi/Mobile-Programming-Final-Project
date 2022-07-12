package edu.sharif.snappfoodminus.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import edu.sharif.snappfoodminus.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new RegisterFragment()).commit();
    }
}