package edu.sharif.snappfoodminus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.temp.Category;

public class AdminCategoriesAdapter extends RecyclerView.Adapter<AdminCategoriesAdapter.ViewHolder> {

    private ArrayList<Category> mCategories;

    public AdminCategoriesAdapter(ArrayList<Category> categories) {
        mCategories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = mCategories.get(position);
        holder.nameTextView.setText(category.name);

        holder.editImageView.setOnClickListener(v -> {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout layout = new LinearLayout(v.getContext());
            layout.setLayoutParams(layoutParams);
            layout.setPadding(50, 0, 50, 0);
            EditText nameEditText = new EditText(v.getContext());
            nameEditText.setLayoutParams(layoutParams);
            nameEditText.setHint("New category name");
            layout.addView(nameEditText);
            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
            alertDialog.setTitle("Edit Category");
            alertDialog.setView(layout);
            alertDialog.setMessage("Change " + category.name + " to a new name");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Update",
                    (dialog, which) -> {
                        String newName = nameEditText.getText().toString().trim();
                        Category newCategory = Category.getCategoryByName(v.getContext(), newName);
                        if (newName.isEmpty())
                            Toast.makeText(v.getContext(), "Name field can not be blank", Toast.LENGTH_LONG).show();
                        else if (newCategory != null)
                            Toast.makeText(v.getContext(), "Category name exists", Toast.LENGTH_LONG).show();
                        else {
                            Category.updateCategory(v.getContext(), new Category(newName), category.name);
                            mCategories.get(position).name = newName;
                            notifyItemChanged(position);
                        }
                        dialog.dismiss();
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        });

        holder.deleteImageView.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
            alertDialog.setTitle("Delete Category");
            alertDialog.setMessage("Are you sure to delete " + category.name + "?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete",
                    (dialog, which) -> {
                        Category.deleteCategory(v.getContext(), category.name);
                        mCategories.remove(position);
                        notifyItemRemoved(position);
                        dialog.dismiss();
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                    (dialog, which) -> dialog.dismiss());
            alertDialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public ImageView editImageView;
        public ImageView deleteImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.categoryName);
            editImageView = itemView.findViewById(R.id.categoryEditImageView);
            deleteImageView = itemView.findViewById(R.id.categoryDeleteImageView);
        }
    }
}
