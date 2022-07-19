package edu.sharif.snappfoodminus.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.adapter.AdminCategoriesAdapter;
import edu.sharif.snappfoodminus.controller.AdminCategoriesController;
import edu.sharif.snappfoodminus.controller.OwnerRestaurantController;
import edu.sharif.snappfoodminus.temp.Category;

public class OwnerRestaurantFragment extends Fragment {

    private OwnerRestaurantController controller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_owner_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        controller = new OwnerRestaurantController(getContext());
    }
}
