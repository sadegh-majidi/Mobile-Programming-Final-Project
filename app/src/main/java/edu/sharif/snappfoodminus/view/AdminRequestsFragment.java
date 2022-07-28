package edu.sharif.snappfoodminus.view;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.adapter.RecyclerItemClickListener;
import edu.sharif.snappfoodminus.adapter.RequestsAdapter;
import edu.sharif.snappfoodminus.model.Category;
import edu.sharif.snappfoodminus.model.Food;
import edu.sharif.snappfoodminus.model.Request;
import edu.sharif.snappfoodminus.model.RequestStatus;

public class AdminRequestsFragment extends Fragment {

    private RequestsAdapter adapter;
    private ArrayList<Request> requests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.orders_rv);
        requests = Request.getAllRequests(getContext());
        adapter = new RequestsAdapter(requests, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener
                (getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        handleRequest(view, position);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                }));
    }

    private void handleRequest(View v, int position) {
        Request request = requests.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.layout_admin_request_view, null);
        TextView nameTextView = view.findViewById(R.id.item_name);
        TextView categoryTextView = view.findViewById(R.id.item_category);
        TextView descriptionTextView = view.findViewById(R.id.item_description);
        TextView priceTextView = view.findViewById(R.id.item_price);
        EditText feedbackEditText = view.findViewById(R.id.admin_feedback);

        Food food = new Gson().fromJson(request.data, new TypeToken<Food>(){}.getType());
        nameTextView.setText(food.name);
        categoryTextView.setText(food.category);
        descriptionTextView.setText(food.description);
        priceTextView.setText(String.valueOf(food.price));

        builder.setTitle(request.restaurant + "'s Request Info");
        builder.setView(view);
        builder.setPositiveButton("Accept", (dialog, which) -> {
            request.status = RequestStatus.ACCEPTED;
            request.description = feedbackEditText.getText().toString().trim();
            Request.updateRequest(getContext(), request, String.valueOf(request.id));
            adapter.notifyItemChanged(position);
            if (Category.getCategoryByName(getContext(), food.category) == null)
                Category.addCategory(getContext(), new Category(food.category));
            // Add food
            if (request.food == null) Food.addFood(getContext(), food);
            // Update food
            else Food.updateFood(getContext(), food, request.food, request.restaurant);
        });
        builder.setNegativeButton("Reject", (dialog, which) -> {
            request.status = RequestStatus.REJECTED;
            request.description = feedbackEditText.getText().toString().trim();
            Request.updateRequest(getContext(), request, String.valueOf(request.id));
            adapter.notifyItemChanged(position);
        });
        builder.setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        if (request.status != RequestStatus.PENDING) {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
            alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setEnabled(false);
            feedbackEditText.setText(request.description);
            feedbackEditText.setInputType(InputType.TYPE_NULL);
        }
    }
}
