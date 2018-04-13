package me.eirinimitsopoulou.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.eirinimitsopoulou.popularmovies.Adapters.MovieListAdapter;
import me.eirinimitsopoulou.popularmovies.Models.Movie;
import me.eirinimitsopoulou.popularmovies.Utilities.JsonUtils;
import me.eirinimitsopoulou.popularmovies.Utilities.NetworkUtils;


/**
 * Created by eirinimitsopoulou on 07/04/2018.
 */

public class MainActivity extends AppCompatActivity {

    private static final int DEFAULT = GetMoviesTask.POPULAR_MOVIES;

    private RecyclerView recyclerview;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        loading = findViewById(R.id.loading);
        recyclerview = findViewById(R.id.movie_list);
        assert recyclerview != null;
        setupRecyclerView();

        loadData(DEFAULT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                loadData(GetMoviesTask.POPULAR_MOVIES);
                break;
            default:
                loadData(GetMoviesTask.TOP_RATED_MOVIES);

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView() {
        recyclerview.setAdapter(new MovieListAdapter(this, this));
    }

    private void loadData(int sortType) {
        new GetMoviesTask().execute(sortType);
    }

    private void showMovies(List<Movie> movies) {
        ((MovieListAdapter) recyclerview.getAdapter()).setData(movies);
    }

    private void error() {
        Toast.makeText(this, R.string.error_loading_movies, Toast.LENGTH_SHORT).show();
    }

    class GetMoviesTask extends AsyncTask<Integer, Void, List<Movie>> {

        static final int POPULAR_MOVIES = 0;
        static final int TOP_RATED_MOVIES = 1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(Integer... params) {
            URL url;
            switch (params[0]) {
                case POPULAR_MOVIES:
                    url = NetworkUtils.buildPopularMoviesUrl();
                    break;
                case TOP_RATED_MOVIES:
                    url = NetworkUtils.buildTopRatedMoviesUrl();
                    break;
                default:
                    return null;
            }

            ArrayList<Movie> array = null;
            try {
                array = JsonUtils.getMovieList(NetworkUtils.getResponseFromHttpUrl(url));
            } catch (IOException e) {
                System.out.println("FAILED TO EXTRACT JSON FROM URL: " + e);
            } catch (JSONException e) {
                System.out.println("JSON EXCEPTION: " + e);
            }
            return array;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            loading.setVisibility(View.INVISIBLE);
            if (!movies.isEmpty()) {
                showMovies(movies);
            } else {
                error();
            }
        }
    }
}
