package sai.com.mymovies.utiities;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sai.com.mymovies.data.MovieFields;
import sai.com.mymovies.endpoints.MovieEndpoints;
import sai.com.mymovies.model.Movie;
import sai.com.mymovies.sync.MoviesDataJobService;

/**
 * Created by krrish on 24/01/2017.
 */

public class Utilities {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static final int END_TIME = 60 * 60 * 8;
    private static Retrofit retrofit = null;

    public static MovieEndpoints getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MovieEndpoints.class);
    }

    public static void ScheduleMoviesJobService(@NonNull Context context, String tag) {

        FirebaseJobDispatcher dispatcher =
                new FirebaseJobDispatcher(
                        new GooglePlayDriver(context)
                );
        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(MoviesDataJobService.class)
                // uniquely identifies the job
                .setTag(tag)
                // one-off job
                .setRecurring(false)
                // don't persist past a device reboot
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(0, END_TIME))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        // only run on an unmetered network
                        Constraint.ON_UNMETERED_NETWORK
                )
                .build();

        dispatcher.mustSchedule(myJob);
    }

    public static ContentValues getContentValues(Movie.results movieObject) {
        ContentValues values = new ContentValues();
        values.put(MovieFields.Column_movieId, movieObject.getId());
        values.put(MovieFields.Column_TITLE, movieObject.getOriginal_title());
        values.put(MovieFields.Column_voteCount, movieObject.getVote_count());
        values.put(MovieFields.Column_posterPath, movieObject.getPoster_path());
        values.put(MovieFields.Column_overview, movieObject.getOverview());
        values.put(MovieFields.Column_popularity, movieObject.getPopularity());
        values.put(MovieFields.Column_voteAverage, movieObject.getVote_average());
        values.put(MovieFields.Column_language, movieObject.getOriginal_language());
        values.put(MovieFields.Column_backdropPath, movieObject.getBackdrop_path());
        values.put(MovieFields.Column_releaseDate, movieObject.getRelease_date());

        return values;
    }
}

