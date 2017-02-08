package sai.com.mymovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import sai.com.mymovies.MainActivity;
import sai.com.mymovies.R;
import sai.com.mymovies.data.MovieFields;
import sai.com.mymovies.model.Movie;

/**
 * Created by krrish on 22/01/2017.
 */

public class GridviewAdapter extends CursorAdapter {
    ImageView imageView;
    private Context mContext;
    private Cursor mCursor;

    public GridviewAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mContext = context;
        mCursor = cursor;

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.gridview_listitem, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        imageView = (ImageView) view.findViewById(R.id.imageView);

        Picasso.with(context)
                .load(MainActivity.IMAGE_BASE_URL + "w185/" + cursor.getString(cursor.getColumnIndex(MovieFields.Column_posterPath)))
                .placeholder(R.drawable.ic_movie_black_48dp)
                .error(R.drawable.ic_movie_black_48dp)
                .into(imageView);
        Log.d(cursor.getString(cursor.getColumnIndex(MovieFields.Column_movieType)), cursor.getString(cursor.getColumnIndex(MovieFields.Column_TITLE)));
    }

    public Movie.results get(int position) {
        Cursor cursor = getCursor();
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
