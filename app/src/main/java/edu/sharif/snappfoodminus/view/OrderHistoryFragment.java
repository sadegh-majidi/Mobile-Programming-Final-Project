package edu.sharif.snappfoodminus.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.adapter.CartItemsAdapter;
import edu.sharif.snappfoodminus.adapter.CustomerOrdersAdapter;
import edu.sharif.snappfoodminus.adapter.RecyclerItemClickListener;
import edu.sharif.snappfoodminus.adapter.RequestsAdapter;
import edu.sharif.snappfoodminus.model.CartRepository;
import edu.sharif.snappfoodminus.model.Discount;
import edu.sharif.snappfoodminus.model.Food;
import edu.sharif.snappfoodminus.model.LoginRepository;
import edu.sharif.snappfoodminus.model.Order;
import edu.sharif.snappfoodminus.model.Request;
import edu.sharif.snappfoodminus.model.Restaurant;
import edu.sharif.snappfoodminus.model.RestaurantRepository;
import edu.sharif.snappfoodminus.model.Review;

public class OrderHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Order> orders;
    private CustomerOrdersAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_history, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.orders_rv);
        orders = Order.getOrdersByCustomer(getContext(), LoginRepository.username);
        adapter = new CustomerOrdersAdapter(orders, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener
                (getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        showOrderDialog(position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                }));
    }

    @SuppressLint("SetTextI18n")
    private void showOrderDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.layout_order_view, null);
        Order order = orders.get(position);

        // Order info
        RecyclerView itemsRecyclerView = view.findViewById(R.id.orders_rv);
        ArrayList<Pair<Food, Integer>> items = order.items;
        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(items);
        itemsRecyclerView.setAdapter(cartItemsAdapter);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TextView totalPriceTextView = view.findViewById(R.id.total_price_text_view);
        TextView shippingCostTextView = view.findViewById(R.id.shipping_cost_text_view);
        TextView discountTextView = view.findViewById(R.id.discount_text_view);
        TextView toPayTextView = view.findViewById(R.id.to_pay_text_view);
        totalPriceTextView.setText("$" + order.totalPrice);
        shippingCostTextView.setText("$" + order.shippingCost);
        discountTextView.setText("$" + order.discount);
        toPayTextView.setText("$" + order.toPay);

        // Review info
        Spinner rateSpinner = view.findViewById(R.id.rate_spinner);
        String[] stars = new String[]{"★★★★★", "★★★★☆", "★★★☆☆", "★★☆☆☆", "★☆☆☆☆", "☆☆☆☆☆"};
        ArrayAdapter<String> starAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, stars);
        starAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rateSpinner.setAdapter(starAdapter);
        final int[] star = new int[1];
        rateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                star[0] = 5 - rateSpinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        EditText reviewEditText = view.findViewById(R.id.review_edit_text);
        if (order.reviewID == null || order.reviewID == 0) {
            builder.setPositiveButton("Rate", (dialog, which) -> {
                int reviewID = Review.getAllReviews(getContext()).size() + 1;
                String description = reviewEditText.getText().toString().trim();
                Review review = new Review(reviewID, star[0], description);
                Review.addReview(getContext(), review);
                orders.get(position).reviewID = reviewID;
                Order.updateOrder(getContext(), orders.get(position), String.valueOf(order.id));
                adapter.notifyItemChanged(position);
                dialog.dismiss();
            });
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        } else {
            Review review = Review.getReviewByID(getContext(), order.reviewID);
            rateSpinner.setSelection(5 - review.rate);
            rateSpinner.setEnabled(false);
            reviewEditText.setText(review.description);
            reviewEditText.setEnabled(false);
        }
        builder.setTitle("View Order");
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
