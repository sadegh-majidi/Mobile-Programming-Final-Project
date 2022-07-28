package edu.sharif.snappfoodminus.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.model.Review;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private ArrayList<Review> mReviews;

    public ReviewsAdapter(ArrayList<Review> reviews) {
        mReviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_review_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = mReviews.get(position);
        Context context = holder.itemView.getContext();
        holder.customerTextView.setText(Review.getCustomerByReviewID(context, review.id));
        holder.rateTextView.setText(String.valueOf(review.rate));
        holder.descriptionTextView.setText(review.description);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView customerTextView;
        public TextView rateTextView;
        public TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            customerTextView = itemView.findViewById(R.id.customer_text_view);
            rateTextView = itemView.findViewById(R.id.rate_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
        }
    }
}
