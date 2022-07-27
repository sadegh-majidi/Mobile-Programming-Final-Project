package edu.sharif.snappfoodminus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import edu.sharif.snappfoodminus.R;
import edu.sharif.snappfoodminus.temp.Food;
import edu.sharif.snappfoodminus.temp.Request;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    private ArrayList<Request> mRequests;
    private Context mContext;

    public RequestsAdapter(ArrayList<Request> requests, Context context) {
        mRequests = requests;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.layout_request_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request request = mRequests.get(position);

        Food food = new Gson().fromJson(request.data, new TypeToken<Food>(){}.getType());
        String requestTitle;
        if (request.food == null)
            requestTitle = "New: " + food.name;
        else
            requestTitle = "Update: " + request.food;
        holder.nameTextView.setText(requestTitle);

        holder.statusTextView.setText(request.status.name());
    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView statusTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.food_name_text_view);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}
