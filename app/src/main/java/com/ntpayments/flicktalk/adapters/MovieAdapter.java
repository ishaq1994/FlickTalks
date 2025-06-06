package com.ntpayments.flicktalk.adapters;
import static com.ntpayments.flicktalk.utils.Constant.IMAGE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ntpayments.flicktalk.R;
import com.ntpayments.flicktalk.models.Movie;


public class MovieAdapter extends PagedListAdapter<Movie,MovieAdapter.MovieViewHolder> {

    // It determine if two list objects are the same or not
    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            return oldMovie.movieId.equals(newMovie.movieId);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            return oldMovie.equals(newMovie);
        }
    };
    private Context mContext;
    private Movie movie;
    // Create a final private MovieAdapterOnClickHandler called mClickHandler
    private MovieAdapterOnClickHandler clickHandler;
    public MovieAdapter(Context mContext, MovieAdapterOnClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
        this.clickHandler = clickHandler;
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        movie = getItem(position);

        if (movie != null) {
            holder.movieTitle.setText(movie.getMovieTitle());
            holder.movieRating.setText(movie.getMovieVote());

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.gary)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();

            // Load the Movie poster into ImageView
            Glide.with(mContext)
                    .load(IMAGE_URL + movie.getMoviePoster())
                    //.apply(options)
                    .into(holder.moviePoster);
        } else {
            Toast.makeText(mContext, "Movie is null", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public PagedList<Movie> getCurrentList() {
        return super.getCurrentList();
    }
    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        TextView movieTitle;
        TextView movieRating;
        ImageView moviePoster;

        private MovieViewHolder(View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieRating = itemView.findViewById(R.id.movie_rating);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // Get position of the movie
            movie = getItem(position);
            // Send movie through click
            clickHandler.onClick(movie);
        }
    }
}
