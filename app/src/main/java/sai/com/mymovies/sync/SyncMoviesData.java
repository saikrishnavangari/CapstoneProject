package sai.com.mymovies.sync;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sai.com.mymovies.MainActivity;
import sai.com.mymovies.data.MovieFields;
import sai.com.mymovies.data.MoviesProvider;
import sai.com.mymovies.endpoints.MovieEndpoints;
import sai.com.mymovies.model.Movie;
import sai.com.mymovies.utiities.NetworkUtilities;

/**
 * Created by krrish on 25/01/2017.
 */

public class SyncMoviesData {
    private static final String LOG_TAG = SyncMoviesData.class.getSimpleName();
    private Context mContext;
    private String movie_type;

    public void getMoviesData(String movie_type, Context context) {
        mContext = context;
        this.movie_type = movie_type;
        MovieEndpoints movieEndpointsService = NetworkUtilities.getClient();

        Call<Movie> call = movieEndpointsService.getMovies(movie_type, MainActivity.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie moviesResponse = response.body();
                saveData(moviesResponse.getResults());
              /*  List<Movie.results>moviesList=moviesResponse.getResults();
                for (Movie.results movie :moviesList) {
                    Log.d(LOG_TAG, movie.toString());
                }*/
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d(LOG_TAG, t.getMessage());
            }
        });

    }


    void saveData(List<Movie.results> movies) {
        ContentValues values = new ContentValues();
        for (Movie.results movie : movies) {
            values.put(MovieFields.Column_movieId, movie.getId());
            values.put(MovieFields.Column_TITLE, movie.getTitle());
            values.put(MovieFields.Column_voteCount, movie.getVote_count());
            values.put(MovieFields.Column_posterPath, movie.getPoster_path());
            values.put(MovieFields.Column_overview, movie.getOverview());
            values.put(MovieFields.Column_popularity, movie.getPopularity());
            values.put(MovieFields.Column_voteAverage, movie.getVote_average());
            values.put(MovieFields.Column_language, movie.getOriginal_language());
            values.put(MovieFields.Column_backdropPath, movie.getBackdrop_path());
            values.put(MovieFields.Column_releaseDate, movie.getRelease_date());
            values.put(MovieFields.Column_movieType, movie_type);
            mContext.getContentResolver().insert(MoviesProvider.Movies.CONTENT_URI,values);
        }


    }

}
