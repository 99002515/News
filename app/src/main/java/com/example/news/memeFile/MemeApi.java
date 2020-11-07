package com.example.news.memeFile;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MemeApi {

    String Base_Url = "https://meme-api.herokuapp.com/";

    @GET("gimme")
    Call<MemePojo> getData();
}
