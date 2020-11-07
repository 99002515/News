package com.example.news.movieFile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.news.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

public class ScoreActivity extends AppCompatActivity {
//    https://api.themoviedb.org/3/search/movie?api_key=1b8f73865fea28a5c09554004451630e&query=club
//    https://api.themoviedb.org/3/search/multi?api_key=1b8f73865fea28a5c09554004451630e&language=en-US&query=game&page=1&include_adult=true
    private Toolbar scoretoolbar;
    private EditText searchEt;
    private Button searchBtn;
    private CarouselView carouselView;

    final int[] sampleImages= {R.drawable.foggy, R.drawable.lightning, R.drawable.rain,
            R.drawable.cloud, R.drawable.drizzle};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        scoretoolbar = findViewById(R.id.scoreToolbar);
        searchEt = findViewById(R.id.searchEt);
        searchBtn = findViewById(R.id.searchBtn);
        setSupportActionBar(scoretoolbar);
        getSupportActionBar().setTitle("Movie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView();

        searchEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, SearchMoviesActivity.class);
                intent.putExtra("searchText", searchEt.getText()+"");
                startActivity(intent);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchEt.getText().toString().isEmpty()) {
                    Toast.makeText(ScoreActivity.this, "Please write some word to search movie", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(ScoreActivity.this, SearchMoviesActivity.class);
                    intent.putExtra("searchText", searchEt.getText()+"");
                    startActivity(intent);
                }
            }
        });
    }

    private void carouselView() {
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        });

        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(ScoreActivity.this, "Clicked Item =" + position + " will be applied soon.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
