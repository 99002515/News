package com.example.news.movieFile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface MovieInterface {
    String Base_Url = "https://api.themoviedb.org/3/";

    @GET("search/movie")
    Call<MoviePojo> getSearchResult(
            @Query("api_key") String api_key,
            @Query("query") String query
    );
}
