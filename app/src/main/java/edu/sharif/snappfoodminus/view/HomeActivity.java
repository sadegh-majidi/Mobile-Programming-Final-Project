package edu.sharif.snappfoodminus.view;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.model.Category;
import edu.sharif.snappfoodminus.model.Role;
import edu.sharif.snappfoodminus.model.User;
import edu.sharif.snappfoodminus.repository.CategoryRepository;
import edu.sharif.snappfoodminus.repository.UserRepository;

public class HomeActivity extends AppCompatActivity {

    private static final String Shared_KEY = "edu.sharif.snappfoodminus";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        sharedPreferences = getSharedPreferences(Shared_KEY, MODE_PRIVATE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RestaurantsFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = new RestaurantsFragment();
            if (item.getItemId() == R.id.nav_order_history)
                selectedFragment = new OrderHistoryFragment();
            if (item.getItemId() == R.id.nav_user_panel)
                selectedFragment = new UserPanelFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        });


        if (sharedPreferences.getBoolean("DarkMode", false))
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);

        if (sharedPreferences.getBoolean("DarkModeJustChanged", false)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new UserPanelFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_user_panel);
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RestaurantsFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
        sharedPreferences.edit().putBoolean("DarkModeJustChanged", false).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shopping_cart) {

        }
        return super.onOptionsItemSelected(item);
    }
}
