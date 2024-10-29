package com.example.exercise;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exercise.adapter.ParentAdapter;
import com.example.exercise.model.GroupedItem;
import com.example.exercise.model.Item;
import com.example.exercise.network.ExerciseApiService;
import com.example.exercise.network.RetrofitManager;
import com.example.exercise.util.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ParentAdapter adapter;

    private List<GroupedItem> items;
    private ItemProcessor itemProcessor;

    private HandlerThread handlerThread;
    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_main);

        items = new ArrayList<>();
        itemProcessor = new ItemProcessor();

        handlerThread = new HandlerThread("BackgroundThread");
        handlerThread.start();
        // Create a handler for the background thread to handle sorting
        backgroundHandler = new Handler(handlerThread.getLooper());

        RecyclerView recyclerView = findViewById(R.id.parentRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button collapseBtn = findViewById(R.id.collapseBtn);
        collapseBtn.setOnClickListener(v -> collapseAll());

        fetchAndProcessData(recyclerView);
    }

    private void fetchAndProcessData(RecyclerView recyclerView) {
        RetrofitManager.getRetrofitInstance()
                       .create(ExerciseApiService.class)
                       .fetchSamples()
                       .enqueue(new Callback<List<Item>>()
        {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> responseList = response.body();
                    // Post the sorting task to the background handler
                    backgroundHandler.post(() -> {
                        List<GroupedItem> groupedItems = itemProcessor.groupAndSortItems(responseList);
                        // Post UI updates back to the main thread
                        runOnUiThread(() -> {
                            items = groupedItems;
                            adapter = new ParentAdapter(MainActivity.this, items);
                            recyclerView.setAdapter(adapter);
                        });
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable throwable) {
                Toast.makeText(
                        MainActivity.this,
                        "Failed to fetch items",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void collapseAll() {
        for (GroupedItem group : items) {
            group.setExpanded(false);
        }
        // refresh recycler view
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up the handler thread when the activity is destroyed
        handlerThread.quitSafely();
    }

}