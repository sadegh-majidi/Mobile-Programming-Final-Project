package edu.sharif.snappfoodminus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.model.Order;
import edu.sharif.snappfoodminus.model.Review;

public class CustomerOrdersAdapter extends RecyclerView.Adapter<CustomerOrdersAdapter.ViewHolder> {

    private ArrayList<Order> mOrders;
    private Context mContext;

    public CustomerOrdersAdapter(ArrayList<Order> orders, Context context) {
        mOrders = orders;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_order_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = mOrders.get(position);
        Context context = holder.itemView.getContext();
        holder.restaurantTextView.setText((position + 1) + ". " + order.restaurant);
        Review review = Review.getReviewByID(context, order.reviewID);
        if (review != null) {
            if (review.rate == 0)
                holder.rateTextView.setText("☆☆☆☆☆");
            else if (review.rate == 1)
                holder.rateTextView.setText("★☆☆☆☆");
            else if (review.rate == 2)
                holder.rateTextView.setText("★★☆☆☆");
            else if (review.rate == 3)
                holder.rateTextView.setText("★★★☆☆");
            else if (review.rate == 4)
                holder.rateTextView.setText("★★★★☆");
            else if (review.rate == 5)
                holder.rateTextView.setText("★★★★★");
        } else {
            holder.rateTextView.setText("NOT RATED");
        }
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurantTextView;
        public TextView rateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            restaurantTextView = itemView.findViewById(R.id.customer_text_view);
            rateTextView = itemView.findViewById(R.id.rateTextView);
        }
    }
}
