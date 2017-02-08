package sai.com.mymovies.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import sai.com.mymovies.R;
import sai.com.mymovies.data.MovieFields;
import sai.com.mymovies.data.MoviesProvider;
import sai.com.mymovies.model.Movie;

/**
 * Created by krrish on 5/02/2017.
 */

public class MoviesWidgetService extends RemoteViewsService {
    public static final String EXTRA_MOVIEOBJECT = "movie";
    private static final String[] Movie_COLUMNS = {
            MovieFields.Column_movieId,
            MovieFields.Column_posterPath,
            MovieFields.Column_movieType
    };
    private static final int INDEX_MOVIE_ID = 0;
    private static final int INDEX_MOVIE_POSTERPATH = 3;
    private static final int INDEX_MOVIE_MOVIETYPE = 10;
    private int mappWidgetId;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        mappWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        return new MoviesRemoteViewsFactory();
    }


    class MoviesRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private Cursor data = null;
        public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            if (data != null) {
                data.close();
            }
            String movie_type = "upcoming";
            String selection = MovieFields.Column_movieType + "=?";
            String[] selectionArgs = new String[]{movie_type};
            // This method is called by the app hosting the widget (e.g., the launcher)
            // However, our ContentProvider is not exported so it doesn't have access to the
            // data. Therefore we need to clear (and finally restore) the calling identity so
            // that calls use our process and permission
            final long identityToken = Binder.clearCallingIdentity();
            data = getContentResolver().query(MoviesProvider.Movies.CONTENT_URI, null,
                    selection, selectionArgs, null);
            Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public void onDestroy() {
            if (data != null) {
                data.close();
                data = null;
            }
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (position == AdapterView.INVALID_POSITION ||
                    data == null || !data.moveToPosition(position)) {
                return null;
            }
            RemoteViews views = new RemoteViews(getPackageName(),
                    R.layout.movie_widget_listitem);
            String movie_id = data.getString(INDEX_MOVIE_ID);
            String poster_path = data.getString(data.getColumnIndex(MovieFields.Column_posterPath));
            Log.d("widget movie posterpath", poster_path);
/*
            views.setImageViewResource(R.id.imageView_widget,R.mipmap.ic_launcher);
*/
            try {
                Bitmap b = Picasso.with(MoviesWidgetService.this)
                        .load(IMAGE_BASE_URL + "w185/" + poster_path)
                        .get();
                views.setImageViewBitmap(R.id.imageView_widget, b);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Movie.results movieObject=get(position);
            final Intent fillInIntent = new Intent();
            fillInIntent.putExtra(EXTRA_MOVIEOBJECT,movieObject);
            views.setOnClickFillInIntent(R.id.imageView_widget, fillInIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return new RemoteViews(getPackageName(), R.layout.movie_widget_listitem);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            if (data.moveToPosition(position))
                return data.getLong(0);
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


        public Movie.results get(int position) {
            Cursor cursor = data;
            Movie.results movieObject = new Movie.results();
            if (cursor.moveToPosition(position)) {
                movieObject.setId(cursor.getInt(cursor.getColumnIndex(MovieFields.Column_movieId)));
                movieObject.setOriginal_title(cursor.getString(cursor.getColumnIndex(MovieFields.Column_TITLE)));
                movieObject.setVote_count(cursor.getInt(cursor.getColumnIndex(MovieFields.Column_voteCount)));
                movieObject.setVote_average(cursor.getInt(cursor.getColumnIndex(MovieFields.Column_voteAverage)));
                movieObject.setBackdrop_path(cursor.getString(cursor.getColumnIndex(MovieFields.Column_backdropPath)));
                movieObject.setOriginal_language(cursor.getString(cursor.getColumnIndex(MovieFields.Column_language)));
                movieObject.setPopularity(cursor.getInt(cursor.getColumnIndex(MovieFields.Column_popularity)));
                movieObject.setOverview(cursor.getString(cursor.getColumnIndex(MovieFields.Column_overview)));
                movieObject.setRelease_date(cursor.getString(cursor.getColumnIndex(MovieFields.Column_releaseDate)));
                movieObject.setPoster_path(cursor.getString(cursor.getColumnIndex(MovieFields.Column_posterPath)));
            }

            return movieObject;
        }
    }
}
