package edu.sharif.snappfoodminus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.controller.OwnerRestaurantController;
import edu.sharif.snappfoodminus.temp.Category;
import edu.sharif.snappfoodminus.temp.Food;
import edu.sharif.snappfoodminus.temp.LoginRepository;
import edu.sharif.snappfoodminus.temp.Request;
import edu.sharif.snappfoodminus.temp.RequestStatus;
import edu.sharif.snappfoodminus.temp.Restaurant;

public class OwnerFoodsAdapter extends RecyclerView.Adapter<OwnerFoodsAdapter.ViewHolder> {

    private ArrayList<Food> mFoods;

    public OwnerFoodsAdapter(ArrayList<Food> foods) {
        mFoods = foods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_owner_food_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = mFoods.get(position);
        holder.nameTextView.setText(food.name);
        holder.nameTextView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.layout_owner_view_food, null);
            TextView nameTextView = view.findViewById(R.id.item_name);
            TextView categoryTextView = view.findViewById(R.id.item_category);
            TextView descriptionTextView = view.findViewById(R.id.item_description);
            TextView priceTextView = view.findViewById(R.id.item_price);
            nameTextView.setText(food.name);
            categoryTextView.setText(food.category);
            descriptionTextView.setText(food.description);
            priceTextView.setText(String.valueOf(food.price));
            builder.setTitle("View Item");
            builder.setView(view);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        holder.editImageView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View view = inflater.inflate(R.layout.layout_add_update_food, null);
            EditText nameEditText = view.findViewById(R.id.item_name);
            AutoCompleteTextView categoryTextView = view.findViewById(R.id.item_category);
            EditText descriptionEditText = view.findViewById(R.id.item_description);
            EditText priceEditText = view.findViewById(R.id.item_price);
            nameEditText.setText(food.name);
            categoryTextView.setText(food.category);
            descriptionEditText.setText(food.description);
            priceEditText.setText(String.valueOf(food.price));

            ArrayList<String> categories = Category.getAllCategoriesNames(v.getContext());
            ArrayAdapter<String> adapter = new ArrayAdapter<>
                    (v.getContext(), android.R.layout.select_dialog_item, categories);
            categoryTextView.setAdapter(adapter);
            categoryTextView.setOnTouchListener((v1, event) -> {
                categoryTextView.showDropDown();
                return false;
            });

            builder.setTitle("Request Update Item");
            builder.setView(view);
            builder.setPositiveButton("Request", (dialog, which) -> {
                String name = nameEditText.getText().toString().trim();
                String category = categoryTextView.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                int price = Integer.parseInt(priceEditText.getText().toString().trim());
                OwnerRestaurantController controller = new OwnerRestaurantController(v.getContext());
                String nameError = controller.getNameError(name);
                String categoryError = controller.getNameError(category);
                if (nameError == null) {
                    if (categoryError == null) {
                        String restaurant = Restaurant.getRestaurantByOwner(v.getContext(), LoginRepository.username).name;
                        Food updatedFood = new Food(name, category, restaurant, description, price);
                        String data = new Gson().toJson(updatedFood);
                        Request request = new Request(null, LoginRepository.username, restaurant, food.name, data, RequestStatus.PENDING, null);
                        Request.addRequest(v.getContext(), request);
                        Toast.makeText(v.getContext(), "Request sent", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(v.getContext(), categoryError, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(v.getContext(), nameError, Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
        holder.deleteImageView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Delete Item");
            builder.setMessage("Are you sure to delete " + food.name + "?");
            builder.setPositiveButton("Delete", (dialog, which) -> {
                Food.deleteFood(v.getContext(), food.name, food.restaurant);
                mFoods.remove(position);
                notifyItemRemoved(position);
                dialog.dismiss();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView editImageView;
        public ImageView deleteImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.categoryNameTextView);
            editImageView = itemView.findViewById(R.id.editImageView);
            deleteImageView = itemView.findViewById(R.id.deleteImageView);
        }
    }
}
