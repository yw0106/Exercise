package com.example.exercise.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercise.model.GroupedItem;
import com.example.exercise.R;

import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.GroupViewHolder> {

    private final List<GroupedItem> list;
    private final Context context;

    public static class GroupViewHolder extends RecyclerView.ViewHolder{
        TextView listIdTV;
        RecyclerView childRV;
        ImageView arrowImg;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            listIdTV = itemView.findViewById(R.id.listIdTV);
            childRV = itemView.findViewById(R.id.childRV);
            arrowImg = itemView.findViewById(R.id.arrowImg);
        }

        private void bind(@NonNull GroupedItem item) {
            listIdTV.setText("ID: " + item.getId());
        }
    }

    public ParentAdapter(Context context, List<GroupedItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ParentAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_row, parent, false);
        return new GroupViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentAdapter.GroupViewHolder holder, int position) {
        GroupedItem group = list.get(position);
        holder.bind(group);

        // Toggle visibility based on the expanded state
        if (group.isExpanded()) {
            holder.childRV.setVisibility(View.VISIBLE);
        } else {
            holder.childRV.setVisibility(View.GONE);
        }

        // Set up the child RecyclerView with the items of this group
        ItemsAdapter childAdapter = new ItemsAdapter(group.getItems());
        holder.childRV.setLayoutManager(new LinearLayoutManager(context));
        holder.childRV.setAdapter(childAdapter);

        holder.arrowImg.setRotation(group.isExpanded() ? 90f : 0f);

        // Click listener for expanding and collapsing the group
        holder.itemView.setOnClickListener(v -> {
            group.setExpanded(!group.isExpanded()); // Toggle expanded state
            notifyItemChanged(position); // Notify the adapter to refresh this item
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
