package sai.com.mymovies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sai.com.mymovies.adapters.HorizontalCastAdapter;
import sai.com.mymovies.adapters.SimilarMoviesAdapter;
import sai.com.mymovies.endpoints.MovieEndpoints;
import sai.com.mymovies.model.CastAndCrew;
import sai.com.mymovies.model.Movie;
import sai.com.mymovies.model.Videos;
import sai.com.mymovies.utiities.NetworkUtilities;

/**
 * Created by krrish on 23/01/2017.
 */

public class DetailActivity extends AppCompatActivity
        implements YouTubePlayer.OnInitializedListener, SimilarMoviesAdapter.ListItemClickListener {
    public static final String EXTRA_MOVIEOBJECT = "movie";
    private static final String LOG_TAG = DetailActivity.class.getSimpleName();
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.description_tv)
    TextView description_tv;
    @BindView(R.id.movieposter_iv)
    ImageView movieposter_iv;
    @BindView(R.id.scrollView_recyclerview_related_movies)
    RecyclerView recyclerview_related_movies;
    @BindView(R.id.recyclerview_cast)
    RecyclerView recyclervie_cast;
    @BindView(R.id.ratingBar)
    RatingBar rating;
    private Target mTarget;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_layout)
    LinearLayout title_layout;
    private Bitmap movieBackgraoundBitmap;
    private YouTubePlayerFragment mfrag;
    private Movie.results mMovieObject;
    private List<CastAndCrew.cast> mCastList;
    private List<Movie.results> mSimilarMoviesList;
    private ArrayList<Videos.results> mVideoList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mfrag = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeplayer_fragment);
        if (getIntent() != null) {
            mMovieObject = getIntent().getParcelableExtra(EXTRA_MOVIEOBJECT);
            getSupportActionBar().setTitle(mMovieObject.getOriginal_title());
            getmovieVideos();
            RecyclerView.LayoutManager castLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclervie_cast.setLayoutManager(castLayoutManager);
            recyclervie_cast.addItemDecoration(new VerticalDividerItemDecoration
                    .Builder(this).color(Color.BLACK)
                    .sizeResId(R.dimen.divider)
                    //.marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                    .build());
            RecyclerView.LayoutManager moviesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerview_related_movies.setLayoutManager(moviesLayoutManager);
            recyclerview_related_movies.addItemDecoration(new VerticalDividerItemDecoration
                    .Builder(this).color(Color.BLACK)
                    .sizeResId(R.dimen.divider)
                    //.marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                    .build());
            getCastData();
            getSimilarMovies();
            UpdateUi();
            rating.setRating((float) (mMovieObject.getVote_average() / 2));
        }
    }

    private void getCastData() {
        MovieEndpoints movieEndpointsService = NetworkUtilities.getClient();
        Call<CastAndCrew> call = movieEndpointsService.getCastAndCrew(mMovieObject.getId(), MainActivity.API_KEY);
        call.enqueue(new Callback<CastAndCrew>() {
            @Override
            public void onResponse(Call<CastAndCrew> call, Response<CastAndCrew> response) {
                CastAndCrew castResponse = response.body();
                mCastList = castResponse.getCast();
                HorizontalCastAdapter adapter = new HorizontalCastAdapter(mCastList, DetailActivity.this);
                recyclervie_cast.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CastAndCrew> call, Throwable t) {

            }
        });
    }

   /* private void updateHorizontalList() {
        for(CastAndCrew.cast castObject:mCastList)
        {
            ImageView castImageView=new ImageView(this);
            castImageView.setLayoutParams(new LinearLayout.LayoutParams(150,150));
            castImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            castImageView.setAdjustViewBounds(true);
            Picasso.with(this)
                    .load(MainActivity.IMAGE_BASE_URL + "w185/" + castObject.getProfile_path())
                    .into(castImageView);
            cast_linearlayout.addView(castImageView);
        }
    }*/

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void UpdateUi() {
        title_tv.setText(mMovieObject.getOriginal_title());
        description_tv.setText(mMovieObject.getOverview());
        downloadImageBitmap();


    }

    private void downloadImageBitmap() {
        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                movieBackgraoundBitmap = bitmap;
                movieposter_iv.setImageBitmap(movieBackgraoundBitmap);
                Palette.from(movieBackgraoundBitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                        if (vibrantSwatch != null) {
                            title_layout.setBackgroundColor(vibrantSwatch.getRgb());
                            title_tv.setTextColor(vibrantSwatch.getTitleTextColor());
                            toolbar.setTitleTextColor(vibrantSwatch.getBodyTextColor());
                            final Drawable upArrow = ContextCompat.getDrawable(DetailActivity.this,
                                    android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
                            upArrow.setColorFilter(vibrantSwatch.getBodyTextColor(), PorterDuff.Mode.SRC_ATOP);
                        }
                    }
                });
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.with(this)
                .load(MainActivity.IMAGE_BASE_URL + "w500/" + mMovieObject.getBackdrop_path())
                .into(mTarget);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void getSimilarMovies() {
        MovieEndpoints movieEndpointsService = NetworkUtilities.getClient();
        Call<Movie> call = movieEndpointsService.getSimilarMovies(mMovieObject.getId(), MainActivity.API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movieResponse = response.body();
                mSimilarMoviesList = movieResponse.getResults();
                SimilarMoviesAdapter adapter = new SimilarMoviesAdapter(mSimilarMoviesList, DetailActivity.this);
                recyclerview_related_movies.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        if ((!wasRestored) && mVideoList.size() > 0)
            youTubePlayer.cueVideo(mVideoList.get(0).getKey());
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    public void getmovieVideos() {
        MovieEndpoints movieEndpointsService = NetworkUtilities.getClient();
        Call<Videos> call = movieEndpointsService.getMovieVideos(mMovieObject.getId(), MainActivity.API_KEY);
        call.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                Videos movieResponse = response.body();
                mVideoList = movieResponse.getResults();
                mfrag.initialize(MainActivity.YOUTUBE_API_KEY, DetailActivity.this);

            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {

            }
        });
    }

    @Override
    public void onListItemClicked(Movie.results movieObject) {
        Intent DetailactivitIntent = new Intent(this, DetailActivity.class);
        DetailactivitIntent.putExtra(EXTRA_MOVIEOBJECT, movieObject);
        DetailactivitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(DetailactivitIntent);
    }
}
