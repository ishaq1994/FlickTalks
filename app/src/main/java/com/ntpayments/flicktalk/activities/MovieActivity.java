package com.ntpayments.flicktalk.activities;


import static com.ntpayments.flicktalk.utils.Constant.IMAGE_URL;
import static com.ntpayments.flicktalk.utils.Constant.MOVIE;
import static com.ntpayments.flicktalk.utils.Utility.formatDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.ntpayments.flicktalk.R;
import com.ntpayments.flicktalk.databinding.ActivityMovieBinding;
import com.ntpayments.flicktalk.models.Movie;


public class MovieActivity extends AppCompatActivity {

    private ActivityMovieBinding binding;

    public static String idOfMovie;
    private String title;
    private String formattedDate;
    private String vote;
    private String description;
    private String language;
    private String poster;
    private String backDrop;


    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

       receiveMovieDetails();

    }

    private void receiveMovieDetails() {
        // Receive the movie object
        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra(MOVIE);

        idOfMovie = movie.getMovieId();
        title = movie.getMovieTitle();
        vote = movie.getMovieVote();
        description = movie.getMovieDescription();
        language = movie.getMovieLanguage();
        backDrop = movie.getMovieBackdrop();
        poster = movie.getMoviePoster();

        binding.titleOfMovie.setText(title);
        binding.ratingOfMovie.setText(vote);
        binding.descriptionOfMovie.setText(description);

        formattedDate = getString(R.string.date, formatDate(movie.getMovieReleaseDate()));
        binding.releaseDateOfMovie.setText(formattedDate);

        binding.languageOfMovie.setText(language);

        Glide.with(this)
                .load(IMAGE_URL + backDrop)
                .into(binding.backdropImage);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
