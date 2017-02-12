package sai.com.mymovies.widget;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sai.com.mymovies.DetailActivity;
import sai.com.mymovies.R;
import sai.com.mymovies.adapters.GridviewAdapter;
import sai.com.mymovies.data.MoviesProvider;

/**
 * Created by krrish on 11/02/2017.
 */

public class FavouriteMovies extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1001;
    @BindView(R.id.gridview_search)
    GridView gridview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Cursor mcursor;
    private GridviewAdapter mGridviewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        actionbar.setTitle(getClass().getSimpleName());
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent DetailActivityIntent = new Intent(FavouriteMovies.this, DetailActivity.class);
                DetailActivityIntent.putExtra(DetailActivity.EXTRA_STARTEDINTENT, FavouriteMovies.class.getSimpleName());
                DetailActivityIntent.putExtra(DetailActivity.EXTRA_MOVIEOBJECT, mGridviewAdapter.get(position));
                startActivity(DetailActivityIntent);
            }
        });

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MoviesProvider.FavouritesContract.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("cursor size", String.valueOf(data.getCount()));
        mGridviewAdapter = new GridviewAdapter(this, data);
        gridview.setAdapter(mGridviewAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mGridviewAdapter.swapCursor(null);
    }
}
