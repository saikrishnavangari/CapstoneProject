package sai.com.mymovies.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by krrish on 29/11/2016.
 */

@Database(version = MoviesDatabase.VERSION)
public final class MoviesDatabase {

    public static final int VERSION = 1;

    @Table(MovieFields.class)
    public static final String Movies = "movies";
    @Table(FavouriteMovieFields.class)
    public static final String Favourites = "favourites";
}
