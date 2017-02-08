package sai.com.mymovies;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sai.com.mymovies.adapters.GridviewAdapter;
import sai.com.mymovies.data.MovieFields;
import sai.com.mymovies.data.MoviesProvider;
import sai.com.mymovies.sync.SyncMoviesData;

/**
 * Created by krrish on 21/01/2017.
 */
public class HomepageFragment extends android.support.v4.app.Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = HomepageFragment.class.getSimpleName();
    private final static String BundleMovieKey = "movietype";
    private final static int LOADER_ID = 1001;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.adView)
    AdView adView;
    private GridviewAdapter mGridviewAdapter;
    private String mMovietype;

    public static android.support.v4.app.Fragment newInstance(int index, String movie_type) {
        HomepageFragment homepageFragment = new HomepageFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", index);
        args.putString(BundleMovieKey, movie_type);
        homepageFragment.setArguments(args);
        return homepageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            switch (bundle.getString(BundleMovieKey)) {
                case "Popular":
                    mMovietype = "popular";
                    break;
                case "TopRated":
                    mMovietype = "top_rated";
                    break;
                case "NowPlaying":
                    mMovietype = "now_playing";
                    break;
                case "Upcoming":
                    mMovietype = "upcoming";
                    break;
                default:
                    mMovietype = "popular";
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        SearchView searchView = null;
        SearchView.OnQueryTextListener queryTextListener;
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getActivity().getComponentName()));
        }
        queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("onQueryTextChange", newText);

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("onQueryTextSubmit", query);
                Intent searchResultIntent = new Intent(getActivity(), SearchActivity.class);
                searchResultIntent.putExtra(SearchActivity.EXTRA_QUERY, query);
                startActivity(searchResultIntent);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_gridview, container, false);
        ButterKnife.bind(this, view);
        if (contentExist().getCount() == 0) {
            SyncMoviesData syncMoviesData = new SyncMoviesData();
            syncMoviesData.getMoviesData(mMovietype, getActivity());
        }

        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
       /* mGridviewAdapter = new GridviewAdapter(getActivity());
        gridview.setAdapter(mGridviewAdapter);*/
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent DetailActivityIntent = new Intent(getActivity(), DetailActivity.class);
                DetailActivityIntent.putExtra(DetailActivity.EXTRA_MOVIEOBJECT, mGridviewAdapter.get(position));
                startActivity(DetailActivityIntent);
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    private Cursor contentExist() {
        String selection = MovieFields.Column_movieType + "=?";
        Log.d(LOG_TAG + "movietype in cursor", mMovietype);
        String[] selectionArgs = new String[]{mMovietype};
        return getActivity().getContentResolver().query(MoviesProvider.Movies.CONTENT_URI, null,
                selection, selectionArgs, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {
        String selection = MovieFields.Column_movieType + "=?";
        Log.d(LOG_TAG + "movietype in cursor", mMovietype);
        String[] selectionArgs = new String[]{mMovietype};
        return new android.support.v4.content.CursorLoader(getActivity(), MoviesProvider.Movies.CONTENT_URI, null,
                selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader loader, Cursor data) {
        Log.d(LOG_TAG + "count cursor", String.valueOf(data.getCount()));
        mGridviewAdapter = new GridviewAdapter(getContext(), data);
        gridview.setAdapter(mGridviewAdapter);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }


}
