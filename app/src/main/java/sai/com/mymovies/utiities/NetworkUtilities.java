package sai.com.mymovies.utiities;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sai.com.mymovies.endpoints.MovieEndpoints;

/**
 * Created by krrish on 24/01/2017.
 */

public class NetworkUtilities {

   public static final String BASE_URL = "http://api.themoviedb.org/3/";
   private static Retrofit retrofit = null;

      public static MovieEndpoints getClient() {
         if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
         }
         return retrofit.create(MovieEndpoints.class);
      }
   }

