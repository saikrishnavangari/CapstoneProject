package sai.com.mymovies.endpoints;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sai.com.mymovies.model.CastAndCrew;
import sai.com.mymovies.model.Movie;
import sai.com.mymovies.model.Reviews;
import sai.com.mymovies.model.Videos;

/**
 * Created by krrish on 24/01/2017.
 */

public interface MovieEndpoints {

    @GET("movie/{movie_type}")
    Call<Movie> getMovies(
            @Path("movie_type") String movie_type,
            @Query("api_key") String api_key);


    @GET("movie/{movie_id}/reviews")
    Call<Reviews> getMovieReviews(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key);

    @GET("movie/{movie_id}/videos")
    Call<Videos> getMovieVideos(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key);

    @GET("movie/{movie_id}/credits")
    Call<CastAndCrew> getCastAndCrew(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key);

    @GET("movie/{movie_id}/similar")
    Call<Movie> getSimilarMovies(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key);

    @GET("search/movie")
    Call<Movie> getSearchMovies(
            @Query("api_key") String api_key,
            @Query("query") String query);

}
