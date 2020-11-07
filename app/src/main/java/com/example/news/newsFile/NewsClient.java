package com.example.news.newsFile;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClient {
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static Retrofit retrofit;
    private static NewsClient sNewsClient;
    private NewsClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized NewsClient getInstance(){
        if (sNewsClient == null){
            sNewsClient = new NewsClient();
        }
        return sNewsClient;
    }
    public NewsInterface getNewsInterface(){
        return retrofit.create(NewsInterface.class);
    }
}
