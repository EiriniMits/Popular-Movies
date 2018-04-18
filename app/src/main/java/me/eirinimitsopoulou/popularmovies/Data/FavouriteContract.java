package me.eirinimitsopoulou.popularmovies.Data;

import android.provider.BaseColumns;

/**
 * Created by eirinimitsopoulou on 12/04/2018.
 */

public class FavouriteContract implements BaseColumns {

    public static final String TABLE_NAME = "movie_favourite";
    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_TITLE = "movie_title";
    public static final String MOVIE_POSTER_IMAGE = "movie_poster_image";
    public static final String MOVIE_BACKDROP_IMAGE = "movie_backdrop_image";
    public static final String MOVIE_AVERAGE_RATING = "movie_average_rating";
    public static final String MOVIE_OVERVIEW = "movie_overview";
    public static final String MOVIE_RELEASE_DATE = "movie_release_date";
}
