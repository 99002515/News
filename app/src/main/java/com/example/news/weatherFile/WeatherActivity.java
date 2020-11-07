package com.example.news.weatherFile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.Common;
import com.example.news.MainActivity;
import com.example.news.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    Toolbar weathertoolbar;
    TextView mCity, mHumidity, mSunrise, mSunset, mPressure, mTemp, mDescription, mWind, mGeoCoord, mMain;
    ImageView mCondition;
    String key = "280e7bae6c1e2a7b018275684c81bd9d";
    LinearLayout mLayout;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (!isLocationEnabled(WeatherActivity.this)) {
            Toast.makeText(this, "Please enable gps", Toast.LENGTH_SHORT).show();
            finish();
        }

        this.mHandler = new Handler();
        m_Runnable.run();
//        this.mHandler.postDelayed(m_Runnable,5000);

        weathertoolbar = findViewById(R.id.weatherToolbar);
        setSupportActionBar(weathertoolbar);
        getSupportActionBar().setTitle("Weather");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLayout = findViewById(R.id.layoutLinear);

        mTemp = findViewById(R.id.txt_temp);
        mCity = findViewById(R.id.txt_city);
        mHumidity = findViewById(R.id.humidityTv);
        mSunrise = findViewById(R.id.sunriseTv);
        mSunset = findViewById(R.id.sunsetTv);
        mPressure = findViewById(R.id.pressureTv);
        mDescription = findViewById(R.id.txt_description);
        mWind = findViewById(R.id.windTv);
        mGeoCoord = findViewById(R.id.geoCoordTv);
        mMain = findViewById(R.id.txt_main);
        mCondition = findViewById(R.id.img_weather);
        getWeatherData();
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

    protected void getWeatherData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApi.Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi api = retrofit.create(WeatherApi.class);

        Call<WeatherPojo> call = api.getData(MainActivity.latitude, MainActivity.longitude, key, WeatherApi.Temp_Unit);
        call.enqueue(new Callback<WeatherPojo>() {
            @Override
            public void onResponse(Call<WeatherPojo> call, Response<WeatherPojo> response) {
                WeatherPojo list = response.body();

                Log.d("Location", MainActivity.latitude+ "=="+MainActivity.longitude);
                double temp = list.getMain().getTemp();
                mTemp.setText(String.valueOf(temp) + " °C");

                String city = list.getName();
                mCity.setText(city);

                double pressure = list.getMain().getPressure();
                mPressure.setText(String.valueOf(pressure));

                int deg = (int) list.getWind().getDeg();
                double speed = list.getWind().getSpeed();
                mWind.setText(String.valueOf(speed) + " Deg: " + String.valueOf(deg));

                int humidity = list.getMain().getHumidity();
                mHumidity.setText(String.valueOf(humidity));


                int sunrise = list.getSys().getSunrise();
                mSunrise.setText(Common.convertUnixToHour(sunrise));

                int sunset = list.getSys().getSunset();
                mSunset.setText(Common.convertUnixToHour(sunset));

                double lati = list.getCoord().getLat();
                double longi = list.getCoord().getLon();
                mGeoCoord.setText("[" + String.valueOf(lati) + ", " + String.valueOf(longi) + "]");

                String imgurl = "http://openweathermap.org/img/wn/" + list.getWeather().get(0).getIcon() + ".png";
                Picasso.get().load(imgurl).into(mCondition);

                String desc = list.getWeather().get(0).getDescription();
                mDescription.setText(desc);

                String main = list.getWeather().get(0).getMain();
                mMain.setText(main);

                switch (main) {
                    case "Thunderstorm":
                        mLayout.setBackgroundResource(R.drawable.lightning);
                        break;

                    case "Drizzle":
                        mLayout.setBackgroundResource(R.drawable.drizzle);
                        break;

                    case "Rain":
                        mLayout.setBackgroundResource(R.drawable.rain);
                        break;

                    case "Snow":
                        mLayout.setBackgroundResource(R.drawable.snow);
                        break;

                    case "Clear":
                        mLayout.setBackgroundResource(R.drawable.sunny);
                        break;

                    case "Clouds":
                        mLayout.setBackgroundResource(R.drawable.cloud);
                        break;

                    case "Fog":
                        mLayout.setBackgroundResource(R.drawable.foggy);
                        break;

                    case "Tornado":
                        mLayout.setBackgroundResource(R.drawable.wind);
                        break;

                    default:
                        mLayout.setBackgroundResource(R.drawable.sunny);

                }


            }

            @Override
            public void onFailure(Call<WeatherPojo> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                Log.d("==============", t.getMessage());
            }
        });

    }

    private final Runnable m_Runnable = new Runnable() {
        @Override
        public void run() {
//            Toast.makeText(WeatherActivity.this, "in runnable", Toast.LENGTH_SHORT).show();
            getWeatherData();
            WeatherActivity.this.mHandler.postDelayed(m_Runnable, 10000);
        }
    };

//    @Override
//    protected void onPause() {
//        super.onPause();
//        mHandler.removeCallbacks(m_Runnable);
//        finish();
//    }
}
