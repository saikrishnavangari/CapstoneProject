package sai.com.mymovies.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by krrish on 29/11/2016.
 */
@ContentProvider(authority = MoviesProvider.AUTHORITY, database = MoviesDatabase.class)
public final class MoviesProvider {
    public static final String AUTHORITY =
            "sai.com.mymovies.data.MoviesProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    interface Path {
        String Movies = "movies";
    }

    @TableEndpoint(table = MoviesDatabase.Movies)
    public static class Movies {
        @ContentUri(
                path = Path.Movies,
                type = "vnd.android.cursor.dir/movies",
                defaultSort = MovieFields.Column_movieId + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.Movies);

        @InexactContentUri(
                name = "MOVIE_ID",
                path = Path.Movies + "/#",
                type = "vnd.android.cursor.item/planet",
                whereColumn = MovieFields.Column_movieId,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.Movies, String.valueOf(id));
        }
    }
}
