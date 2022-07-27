package edu.sharif.snappfoodminus.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import edu.sharif.snappfoodminus.model.Food;
import edu.sharif.snappfoodminus.model.LoginRepository;
import edu.sharif.snappfoodminus.model.Request;

public class OwnerRequestsFragment extends Fragment {

    private RequestsAdapter adapter;
    private ArrayList<Request> requests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.discounts_rv);
        requests = Request.getRequestsByRequester(getContext(), LoginRepository.username);
        adapter = new RequestsAdapter(requests, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener
                (getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showRequestInfo(requests.get(position));
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }

    private void showRequestInfo(Request request) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.layout_owner_request_view_info, null);
        TextView nameTextView = view.findViewById(R.id.item_name);
        TextView categoryTextView = view.findViewById(R.id.item_category);
        TextView descriptionTextView = view.findViewById(R.id.item_description);
        TextView priceTextView = view.findViewById(R.id.item_price);
        TextView feedbackTextView = view.findViewById(R.id.admin_feedback);

        Food food = new Gson().fromJson(request.data, new TypeToken<Food>(){}.getType());
        nameTextView.setText(food.name);
        categoryTextView.setText(food.category);
        descriptionTextView.setText(food.description);
        priceTextView.setText(String.valueOf(food.price));
        if (request.description != null && !request.description.isEmpty())
            feedbackTextView.setText(request.description);

        builder.setTitle("Request Info");
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
