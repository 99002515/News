package com.example.news.newsFile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.news.MainActivity;
import com.example.news.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    private static final String API_KEY = "07af1771d8764b0a8f8c576b20cb6ee0";
    RecyclerView mRecyclerView;
    Adapter mAdapter;
    List<Article> mArticles = new ArrayList<>();
    Toolbar newstoolbar;
    Geocoder geoc;
    List<Address> addresses;
    double lat=51.50735;
    double lon=-0.1277567;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        if (!isLocationEnabled(NewsActivity.this)) {
            Toast.makeText(this, "Please enable gps", Toast.LENGTH_SHORT).show();
            finish();
        }

        newstoolbar = findViewById(R.id.newsToolbar);
        setSupportActionBar(newstoolbar);
        getSupportActionBar().setTitle("News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        geoc = new Geocoder(this);

        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country = getCountr();
        fetchJSON(country, API_KEY);
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        }else{
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.news_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.refreshTab:
                finish();
                startActivity(getIntent());
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void fetchJSON(String country, String apiKey) {
        Call<Headlines> call = NewsClient.getInstance().getNewsInterface().getHeadlines(country, apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){
                    mArticles.clear();
                    mAdapter = new Adapter(NewsActivity.this, response.body().getArticles());
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(NewsActivity.this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getCountr() {
        String country = "in";
        lat = Double.parseDouble(MainActivity.latitude);
        lon = Double.parseDouble(MainActivity.longitude);
        Log.d("Country", lat+" @@@@@@@@@@ "+lon);
        try {
            addresses = geoc.getFromLocation(lat, lon, 1);
            Address address = addresses.get(0);
            country = address.getCountryCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return country.toLowerCase();
    }
}
