package com.example.news.memeFile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.news.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartupActivity extends AppCompatActivity {

    Toolbar startuptoolbar;
    ImageView memeIv;
    Button shareBtn, nextBtn;
    ProgressDialog mProgressDialog;
    String memeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);


        startuptoolbar = findViewById(R.id.startupToolbar);
        setSupportActionBar(startuptoolbar);
        getSupportActionBar().setTitle("Memes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);

        memeIv = findViewById(R.id.memeIV);
        shareBtn = findViewById(R.id.shareBtn);
        nextBtn = findViewById(R.id.nextBtn);

        mProgressDialog.show();
        getMemeData();
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
//                Window window = mProgressDialog.getWindow();
//                window.setLayout(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
                getMemeData();
            }
        });
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Hey, checkout this meme \n" + memeImg);
                intent.putExtra(Intent.EXTRA_TEXT, memeImg);
                startActivity(Intent.createChooser(intent, "Share via."));
            }
        });
    }

    private void getMemeData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MemeApi.Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MemeApi api = retrofit.create(MemeApi.class);
        Call<MemePojo> call = api.getData();
        call.enqueue(new Callback<MemePojo>() {
            @Override
            public void onResponse(Call<MemePojo> call, Response<MemePojo> response) {
                MemePojo list = response.body();
                memeImg = list.getUrl();
                Picasso.get().load(memeImg).into(memeIv, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressDialog.dismiss();
                    }

                    @Override
                    public void onError(Exception e) {
                        mProgressDialog.dismiss();
                    }
                });
            }

            @Override
            public void onFailure(Call<MemePojo> call, Throwable t) {
                Toast.makeText(StartupActivity.this, "Check your internet connection...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
