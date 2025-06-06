package com.ntpayments.flicktalk.network;



import com.ntpayments.flicktalk.models.MovieApiResponse;
import com.ntpayments.flicktalk.utils.Constant;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieService {


    String API_KEY= Constant.API_KEY;

    @GET("movie/{sort}")
    Call<MovieApiResponse> getMovies(@Path("sort") String sortBy, @Query("page") int page, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieApiResponse> searchForMovies(@Query("query") String query, @Query("api_key") String apiKey);


}


