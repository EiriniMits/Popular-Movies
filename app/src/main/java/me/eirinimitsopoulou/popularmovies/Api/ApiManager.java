package me.eirinimitsopoulou.popularmovies.Api;

import me.eirinimitsopoulou.popularmovies.BuildConfig;

/**
 * Created by eirinimitsopoulou on 12/04/2018.
 */

public class ApiManager {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String BASE_URL_IMAGE_POSTER = "http://image.tmdb.org/t/p/w300";
    public static final String BASE_URL_IMAGE_BACKDROP = "http://image.tmdb.org/t/p/w342";
    private static final String API_KEY = BuildConfig.API_KEY;

    public static String getApiKey() {
        return API_KEY;
    }
}
