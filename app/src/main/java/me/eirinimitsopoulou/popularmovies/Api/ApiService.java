package me.eirinimitsopoulou.popularmovies.Api;

import me.eirinimitsopoulou.popularmovies.Models.MovieResponse;
import me.eirinimitsopoulou.popularmovies.Models.ReviewResponse;
import me.eirinimitsopoulou.popularmovies.Models.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by eirinimitsopoulou on 12/04/2018.
 */

public interface ApiService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getTrailer(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getReview(@Path("id") int id, @Query("api_key") String apiKey);
}
