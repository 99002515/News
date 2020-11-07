package com.example.news.movieFile;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private Context context;
    private List<Result> movieList;

    public ListAdapter(Context context, List<Result> movieList, SearchMoviesActivity searchMoviesActivity) {
        this.context = context;
        this.movieList = movieList;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_movie, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Result movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        holder.releaseDate.setText(movie.getReleaseDate());
        holder.vote_average.setText(movie.getVoteAverage().toString());
        String poster_url = "http://image.tmdb.org/t/p/w185"+movie.getPosterPath();
        Log.i("poster", poster_url);
        if (!poster_url.equals(null)) {
            Glide.with(context).load(poster_url).into(holder.posterIV);
        } else {
            Glide.with(context).load(R.drawable.sunny).into(holder.posterIV);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView posterIV;
        TextView title, releaseDate, vote_average;

        ListViewHolder(View view) {
            super(view);
            posterIV = view.findViewById(R.id.posterIv);
            title = view.findViewById(R.id.title);
            releaseDate = view.findViewById(R.id.releaseDate);
            vote_average = view.findViewById(R.id.vote_average);
        }
    }
}
