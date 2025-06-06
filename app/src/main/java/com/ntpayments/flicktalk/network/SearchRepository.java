package com.ntpayments.flicktalk.network;

import static com.ntpayments.flicktalk.network.MovieService.API_KEY;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.ntpayments.flicktalk.models.Movie;
import com.ntpayments.flicktalk.models.MovieApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class SearchRepository {

    private List<Movie> searchedList = new ArrayList<>();
    private final MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Movie>> getMutableLiveData(String query) {
        RetrofitClient.getInstance()
                .getMovieService().searchForMovies(query, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        Log.v("onResponse", response.body() + " Movies");

                        if (response.body() != null) {
                            searchedList = response.body().getMovies();
                            mutableLiveData.setValue(searchedList);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                        Log.v("onFailure", " Failed to get movies");
                    }
                });

        return mutableLiveData;
    }

}

