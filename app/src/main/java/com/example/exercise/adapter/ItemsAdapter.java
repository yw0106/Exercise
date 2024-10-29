package com.example.exercise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercise.model.Item;
import com.example.exercise.R;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private static final String TAG = "ItemsAdapter";
    private final List<Item> items;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView itemIdTV, nameTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemIdTV = itemView.findViewById(R.id.itemIdTV);
            nameTV = itemView.findViewById(R.id.itemNameTV);
        }

        public void bind(@NonNull Item item) {
            itemIdTV.setText(String.valueOf(item.getId()));
            nameTV.setText(item.getName());
        }
    }

    public ItemsAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
