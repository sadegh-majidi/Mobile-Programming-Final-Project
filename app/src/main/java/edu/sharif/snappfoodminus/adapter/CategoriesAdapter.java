package edu.sharif.snappfoodminus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.model.Category;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private ArrayList<Category> mCategories;

    public CategoriesAdapter(ArrayList<Category> categories) {
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
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.customer_text_view);
        }
    }
}
