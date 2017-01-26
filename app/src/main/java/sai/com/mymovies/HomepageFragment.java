package sai.com.mymovies;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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
    @BindView(R.id.gridview)
    GridView gridview;
    private final static String BundleMovieKey = "movietype";
    private final static int LOADER_ID=1001;
    private GridviewAdapter mGridviewAdapter;
    private String mMovietype;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_gridview, container, false);
        ButterKnife.bind(this, view);
        if(contentExist().getCount()==0) {
            SyncMoviesData syncMoviesData = new SyncMoviesData();
            syncMoviesData.getMoviesData(mMovietype, getActivity());
        }
       /* mGridviewAdapter = new GridviewAdapter(getActivity());
        gridview.setAdapter(mGridviewAdapter);*/
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent DetailActivity = new Intent(getActivity(), sai.com.mymovies.DetailActivity.class);
                startActivity(DetailActivity);
            }
        });
        return view;
    }

    private Cursor contentExist() {
        String selection= MovieFields.Column_movieType+"=?";
        Log.d(LOG_TAG +"movietype in cursor",mMovietype);
        String[] selectionArgs=new String[]{mMovietype};
       return getActivity().getContentResolver().query( MoviesProvider.Movies.CONTENT_URI, null,
                selection, selectionArgs, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(LOADER_ID,null,this);
    }

    public static android.support.v4.app.Fragment newInstance(int index, String movie_type) {
        HomepageFragment homepageFragment = new HomepageFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", index);
        args.putString(BundleMovieKey, movie_type);
        homepageFragment.setArguments(args);
        return homepageFragment;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public android.support.v4.content.Loader onCreateLoader(int id, Bundle args) {
        String selection= MovieFields.Column_movieType+"=?";
        Log.d(LOG_TAG +"movietype in cursor",mMovietype);
        String[] selectionArgs=new String[]{mMovietype};
        return new android.support.v4.content.CursorLoader(getActivity(), MoviesProvider.Movies.CONTENT_URI, null,
                selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader loader, Cursor data) {
        Log.d(LOG_TAG+"count cursor", String.valueOf(data.getCount()));
        GridviewAdapter adapter=new GridviewAdapter(getContext(),data);
        gridview.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }


}
