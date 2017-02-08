package sai.com.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sai.com.mymovies.adapters.SearchMoviesAdapter;
import sai.com.mymovies.endpoints.MovieEndpoints;
import sai.com.mymovies.model.Movie;
import sai.com.mymovies.utiities.NetworkUtilities;

/**
 * Created by krrish on 5/02/2017.
 */

public class SearchActivity extends AppCompatActivity {
    private static String LOG_TAG = SearchActivity.class.getSimpleName();
    public static String EXTRA_QUERY = "query";
    private ArrayList<Movie.results> mSearchMoviesList;
    private SearchMoviesAdapter mSearchAdapter;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        ButterKnife.bind(this);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent DetailActivityIntent = new Intent(SearchActivity.this, DetailActivity.class);
                DetailActivityIntent.putExtra(DetailActivity.EXTRA_MOVIEOBJECT, mSearchAdapter.getItem(position));
                startActivity(DetailActivityIntent);
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.searchactivity_titile);
        searchMovies();
    }


    public void searchMovies() {
        String query=getIntent().getStringExtra(EXTRA_QUERY);
        MovieEndpoints movieEndpointsService = NetworkUtilities.getClient();
        Call<Movie> call = movieEndpointsService.getSearchMovies(MainActivity.API_KEY, query);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movieResponse = response.body();
                Log.d("url", call.request().url().toString());
                mSearchMoviesList = movieResponse.getResults();
                if(mSearchMoviesList.size()>0) {
                    Log.d("hello", mSearchMoviesList.get(0).toString());
                    mSearchAdapter = new SearchMoviesAdapter(SearchActivity.this, mSearchMoviesList);
                    gridview.setAdapter(mSearchAdapter);
                }
                else
                    emptyView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("hello", "error");
            }
        });
    }

}
