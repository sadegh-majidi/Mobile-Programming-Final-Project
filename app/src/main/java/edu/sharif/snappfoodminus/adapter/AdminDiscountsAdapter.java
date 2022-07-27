package edu.sharif.snappfoodminus.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.model.Discount;

public class AdminDiscountsAdapter extends RecyclerView.Adapter<AdminDiscountsAdapter.ViewHolder> {

    private ArrayList<Discount> mDiscounts;

    public AdminDiscountsAdapter(ArrayList<Discount> discounts) {
        mDiscounts = discounts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_admin_discount_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Discount discount = mDiscounts.get(position);
        holder.restaurantNameTextView.setText(discount.restaurant);
        holder.percentageTextView.setText(discount.percentage + "%");
        holder.deleteImageView.setOnClickListener(v -> {
            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
            alertDialog.setTitle("Delete Discount");
            alertDialog.setMessage("Are you sure to delete?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete",
                    (dialog, which) -> {
                        Discount.deleteDiscount(v.getContext(), discount.restaurant);
                        mDiscounts.remove(position);
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
        return mDiscounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurantNameTextView;
        public TextView percentageTextView;
        public ImageView deleteImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.restaurantTextView);
            percentageTextView = itemView.findViewById(R.id.percentageTextView);
            deleteImageView = itemView.findViewById(R.id.discountDeleteImageView);
        }
    }
}
