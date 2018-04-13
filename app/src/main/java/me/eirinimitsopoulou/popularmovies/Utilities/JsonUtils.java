package me.eirinimitsopoulou.popularmovies.Utilities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import me.eirinimitsopoulou.popularmovies.Models.Movie;


/**
 * Created by eirinimitsopoulou on 07/04/2018.
 */

public final class JsonUtils {

    private static final String TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String RELEASE_DATE = "release_date";
    private static final String RESULTS = "results";
    private static final String FAILED_TO_RETRIEVE = "Failed to Retrieve";

    public JsonUtils() {
    }

    public static ArrayList<Movie> getMovieList(String json) throws JSONException {
        ArrayList<Movie> array = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject movieDetails = (JSONObject) jsonArray.get(i);
            String title = movieDetails.optString(TITLE, FAILED_TO_RETRIEVE);
            String imageURL = movieDetails.optString(POSTER_PATH, FAILED_TO_RETRIEVE);
            String plot = movieDetails.optString(OVERVIEW, FAILED_TO_RETRIEVE);
            double userRating = movieDetails.optDouble(VOTE_AVERAGE, 0.0);
            String releaseDate = movieDetails.optString(RELEASE_DATE, FAILED_TO_RETRIEVE);
            array.add(new Movie(title, imageURL, plot, userRating, releaseDate));
        }

        return array;
    }
}