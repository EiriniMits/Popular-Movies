package me.eirinimitsopoulou.popularmovies.Utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import me.eirinimitsopoulou.popularmovies.BuildConfig;

/**
 * Created by eirinimitsopoulou on 07/04/2018.
 */

public final class NetworkUtils {

    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final String POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String API = "api_key";
    private static final String LANGUAGE = "language";
    private static final String LAN_EN_US = "en-US";
    private static final String PAGE = "page";
    private static final String PAGE_NUMBER = "1";


    public static URL buildPopularMoviesUrl() {
        Uri builtUri = Uri.parse(BASE_URL + POPULAR).buildUpon()
                .appendQueryParameter(API, BuildConfig.API_KEY)
                .appendQueryParameter(LANGUAGE, LAN_EN_US)
                .appendQueryParameter(PAGE, PAGE_NUMBER)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildTopRatedMoviesUrl() {
        Uri builtUri = Uri.parse(BASE_URL + TOP_RATED).buildUpon()
                .appendQueryParameter(API, BuildConfig.API_KEY)
                .appendQueryParameter(LANGUAGE, LAN_EN_US)
                .appendQueryParameter(PAGE, PAGE_NUMBER)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
