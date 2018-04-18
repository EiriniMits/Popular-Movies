package me.eirinimitsopoulou.popularmovies;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.eirinimitsopoulou.popularmovies.Adapters.MoviesListAdapter;
import me.eirinimitsopoulou.popularmovies.Data.FavouriteProvider;
import me.eirinimitsopoulou.popularmovies.Data.FavouriteContract;
import me.eirinimitsopoulou.popularmovies.Models.MovieResponse;
import me.eirinimitsopoulou.popularmovies.Api.ApiManager;
import me.eirinimitsopoulou.popularmovies.Models.Movie;
import me.eirinimitsopoulou.popularmovies.Api.ApiService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by eirinimitsopoulou on 07/04/2018.
 */

public class MainActivity extends AppCompatActivity implements MoviesListAdapter.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    @BindView((R.id.movie_list))
    RecyclerView recyclerview;

    private static final String FAVORITE = "state_favourite";
    private static final String POPULAR = "state_popular";
    private static Retrofit retrofit = null;
    private static boolean favourite;
    private static boolean popular = true;
    private ArrayList<Movie> movie;
    private ArrayList<Movie> movie2;
    private static final int LOADER = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));

        if (savedInstanceState != null) {

            favourite = savedInstanceState.getBoolean(FAVORITE);
            popular = savedInstanceState.getBoolean(POPULAR);

            if (favourite) {
                LoaderManager mLoaderManager = getLoaderManager();
                mLoaderManager.initLoader(LOADER, null, this);
            } else
                connectAndGetApiData(popular);

        } else {
            connectAndGetApiData(true);
        }
    }


    public void connectAndGetApiData(boolean isPopular) {

        favourite = false;
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiManager.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ApiService movieApiService = retrofit.create(ApiService.class);


        if (isPopular == true) {

            Call<MovieResponse> call = movieApiService.getPopularMovies(ApiManager.getApiKey());
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {


                    if (response.isSuccessful()) {

                        movie = response.body().getResults();
                        recyclerview.setAdapter(new MoviesListAdapter(movie, R.layout.movie_poster_fragment, getApplicationContext()));
                        MoviesListAdapter.setOnItemClickListener(MainActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                    if (t instanceof IOException)
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_loading_movies), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Call<MovieResponse> call = movieApiService.getTopRatedMovies(ApiManager.getApiKey());
            call.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                    if (response.isSuccessful()) {

                        movie = response.body().getResults();
                        recyclerview.setAdapter(new MoviesListAdapter(movie, R.layout.movie_poster_fragment, getApplicationContext()));
                        MoviesListAdapter.setOnItemClickListener(MainActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                    if (t instanceof IOException)
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_loading_movies), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.popular:
                popular = true;
                connectAndGetApiData(popular);
                break;
            case R.id.top_rated:
                popular = false;
                connectAndGetApiData(popular);
                break;
            case R.id.favourite:
                LoaderManager mLoaderManager = getLoaderManager();
                mLoaderManager.initLoader(LOADER, null, this);
                favourite = true;
                MoviesListAdapter.setOnItemClickListener(MainActivity.this);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {

        Intent detailIntent = new Intent(this, DetailActivity.class);
        Movie clickedItem;

        if (favourite) {
            clickedItem = movie2.get(position);
        } else {
            clickedItem = movie.get(position);
        }

        detailIntent.putExtra("id", clickedItem.getId());
        detailIntent.putExtra("title", clickedItem.getTitle());
        detailIntent.putExtra("releasedate", clickedItem.getRelease_date());
        detailIntent.putExtra("backdroppath", clickedItem.getBackdrop_path());
        detailIntent.putExtra("overview", clickedItem.getOverview());
        detailIntent.putExtra("voteavarage", clickedItem.getVote_average());
        detailIntent.putExtra("posterpath", clickedItem.getPoster_path());
        startActivity(detailIntent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        switch (i) {
            case LOADER:
                Uri favQueryUri = FavouriteProvider.CONTENT_URI;
                return new android.content.CursorLoader(this,
                        favQueryUri,
                        null,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader not implemented " + LOADER);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        movie2 = new ArrayList<>();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, getResources().getString(R.string.nofavourites), Toast.LENGTH_SHORT).show();
            return;
        }
        cursor.moveToFirst();

        do {
            int movieId = cursor.getInt(cursor.getColumnIndex(FavouriteContract.MOVIE_ID));
            String movieName = cursor.getString(cursor.getColumnIndex(FavouriteContract.MOVIE_TITLE));
            String moviePlot = cursor.getString(cursor.getColumnIndex(FavouriteContract.MOVIE_OVERVIEW));
            double movieRating = cursor.getDouble(cursor.getColumnIndex(FavouriteContract.MOVIE_AVERAGE_RATING));
            String moviePoster = cursor.getString(cursor.getColumnIndex(FavouriteContract.MOVIE_POSTER_IMAGE));
            String movieDate = cursor.getString(cursor.getColumnIndex(FavouriteContract.MOVIE_RELEASE_DATE));
            String movieBackdrop = cursor.getString(cursor.getColumnIndex(FavouriteContract.MOVIE_BACKDROP_IMAGE));
            movie2.add(new Movie(movieId, movieRating, movieName, moviePoster, movieBackdrop, moviePlot, movieDate));
        } while (cursor.moveToNext());

        recyclerview.setAdapter(new MoviesListAdapter(movie2, R.layout.movie_poster_fragment, getApplicationContext()));
        recyclerview.setHasFixedSize(true);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FAVORITE, favourite);
        outState.putBoolean(POPULAR, popular);
    }
}