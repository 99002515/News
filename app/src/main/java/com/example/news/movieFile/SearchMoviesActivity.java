package com.example.news.movieFile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.example.news.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchMoviesActivity extends AppCompatActivity {

    private SearchView moviesSearchEt;
    private RecyclerView moviesRv;
    private Toolbar searchMovietoolbar;
    String searchText;
    private List<Result> movieList = new ArrayList<>();
    ListAdapter adaptor;
    String API_KEY = "1b8f73865fea28a5c09554004451630e";
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);

        searchMovietoolbar = findViewById(R.id.searchMovieToolbar);
        setSupportActionBar(searchMovietoolbar);
        getSupportActionBar().setTitle("Search Movies");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moviesSearchEt = findViewById(R.id.moviesSearchET);
        moviesRv = findViewById(R.id.moviesRV);

        searchText = getIntent().getStringExtra("searchText");
        moviesSearchEt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                moviesSearchEt.clearFocus();
                fetchJSON(query, API_KEY);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.d("huaa huaa", newText+" abc");
                movieList.clear();
                fetchJSON(newText, API_KEY);
//                Log.d("huaa huaa", newText+" 2abc");
                return false;
            }
        });
    }


    private void fetchJSON(CharSequence s, String api_key) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MovieInterface.Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieInterface api = retrofit.create(MovieInterface.class);

        Call<MoviePojo> movieListCall = api.getSearchResult(api_key, s.toString());
        movieListCall.enqueue(new Callback<MoviePojo>() {
            @Override
            public void onResponse(Call<MoviePojo> call, Response<MoviePojo> response) {
                if (response.code()==200){
                    movieList.addAll(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MoviePojo> call, Throwable t) {

            }
        });
        layoutManager = new GridLayoutManager(SearchMoviesActivity.this, 2);
        adaptor = new ListAdapter(SearchMoviesActivity.this, movieList, this);
        moviesRv.setLayoutManager(layoutManager);
        moviesRv.setAdapter(adaptor);
    }
}
