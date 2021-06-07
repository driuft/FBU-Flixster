package com.example.fbu_flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import com.bumptech.glide.Glide;
import com.example.fbu_flixster.MovieDetailsActivity;
import com.example.fbu_flixster.R;
import com.example.fbu_flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);

            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;

            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // then imageUrl = backdropPath
                imageUrl = movie.getBackdropPath();

                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.flicks_backdrop_placeholder)
                        .into(ivPoster);
            } else {
                // else imageUrl = posterPath
                imageUrl = movie.getPosterPath();

                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .into(ivPoster);
            }
        }

        @Override
        public void onClick(View v) {
            // Get item position
            int position = getAdapterPosition();

            // Make sure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                // Get the movie at the position
                Movie movie = movies.get(position);
                // Create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // Serialize the movie using Parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // Show the activity
                context.startActivity(intent);
            }
        }
    }
}
