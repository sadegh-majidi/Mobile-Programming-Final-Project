package edu.sharif.snappfoodminus.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.adapter.AdminCategoriesAdapter;
import edu.sharif.snappfoodminus.adapter.RestaurantsAdapter;
import edu.sharif.snappfoodminus.controller.AdminCategoriesController;
import edu.sharif.snappfoodminus.model.Filter;
import edu.sharif.snappfoodminus.temp.Category;

public class AdminCategoriesFragment extends Fragment {

    private AdminCategoriesController controller;

    private AdminCategoriesAdapter adapter;
    private ArrayList<Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        controller = new AdminCategoriesController(getContext());

        Button addButton = view.findViewById(R.id.add_category_button);
        addButton.setOnClickListener(v -> {
            getAddCategoryDialog().show();
        });

        RecyclerView recyclerView = view.findViewById(R.id.categories_rv);
        categories = Category.getAllCategories(getContext());
        adapter = new AdminCategoriesAdapter(categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private AlertDialog getAddCategoryDialog() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(layoutParams);
        layout.setPadding(50, 0, 50, 0);
        EditText nameEditText = new EditText(getContext());
        nameEditText.setLayoutParams(layoutParams);
        nameEditText.setHint("New category name");
        layout.addView(nameEditText);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Add New Category");
        alertDialog.setView(layout);
        alertDialog.setMessage("Enter a new category name");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Add",
                (dialog, which) -> {
                    String name = nameEditText.getText().toString().trim();
                    String nameError = controller.getNameError(name);
                    Category category = Category.getCategoryByName(getContext(), name);
                    if (nameError != null)
                        Toast.makeText(getContext(), nameError, Toast.LENGTH_LONG).show();
                    else if (category != null)
                        Toast.makeText(getContext(), "Category name exists", Toast.LENGTH_LONG).show();
                    else {
                        Category newCategory = new Category(name);
                        Category.addCategory(getContext(), newCategory);
                        categories.add(0, newCategory);
                        adapter.notifyItemInserted(0);
                    }
                    dialog.dismiss();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                (dialog, which) -> dialog.dismiss());
        return alertDialog;
    }
}
