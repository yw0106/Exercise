package com.example.exercise.network;

import com.example.exercise.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExerciseApiService {

    @GET("/hiring.json")
    Call<List<Item>> fetchSamples();

}
