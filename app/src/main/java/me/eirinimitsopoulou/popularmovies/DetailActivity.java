package me.eirinimitsopoulou.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import me.eirinimitsopoulou.popularmovies.Adapters.ReviewAdapter;
import me.eirinimitsopoulou.popularmovies.Adapters.TrailerAdapter;
import me.eirinimitsopoulou.popularmovies.Data.FavouriteProvider;
import me.eirinimitsopoulou.popularmovies.Data.FavouriteContract;
import me.eirinimitsopoulou.popularmovies.Data.FavouriteDBHelper;
import me.eirinimitsopoulou.popularmovies.Models.Review;
import me.eirinimitsopoulou.popularmovies.Models.ReviewResponse;
import me.eirinimitsopoulou.popularmovies.Models.Trailer;
import me.eirinimitsopoulou.popularmovies.Models.TrailerResponse;
import me.eirinimitsopoulou.popularmovies.Api.ApiManager;
import me.eirinimitsopoulou.popularmovies.Api.ApiService;

import com.squareup.picasso.Picasso;

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

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.OnItemClickListener {

    @BindView((R.id.m_overview))
    TextView mOverview;
    @BindView((R.id.m_releasedate))
    TextView mReleaseDate;
    @BindView((R.id.m_voteaverage))
    TextView mVoteAverage;
    @BindView((R.id.m_backdrop))
    ImageView mBackDropImage;
    @BindView((R.id.m_posterimage))
    ImageView mPosterImage;
    @BindView((R.id.collapsing))
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView((R.id.toolbar))
    Toolbar toolbar;
    @BindView((R.id.m_trailer))
    RecyclerView recyclerview;
    @BindView((R.id.m_review))
    RecyclerView recyclerviewReview;
    @BindView((R.id.fab_fav))
    FloatingActionButton fab;

    private static Retrofit retrofit = null;
    public ArrayList<Trailer> trailers;
    public ArrayList<Review> reviews;
    public boolean favourite;
    String id;
    String title;
    String releasedate;
    String backdropPath;
    String overview;
    String voteAverage;
    String posterPath;
    private SQLiteDatabase movieDb;

    public static void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        FavouriteDBHelper dbHelper = new FavouriteDBHelper(this);
        movieDb = dbHelper.getWritableDatabase();


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        if (bundle != null) {

            id = String.valueOf(bundle.getInt("id"));
            title = bundle.getString("title");
            releasedate = bundle.getString("releasedate");
            backdropPath = bundle.getString("backdroppath");
            overview = bundle.getString("overview");
            voteAverage = String.valueOf(bundle.getDouble("voteavarage"));
            posterPath = bundle.getString("posterpath");

            collapsingToolbarLayout.setTitle(title);

            mOverview.setText(overview);
            mReleaseDate.setText(releasedate);
            mVoteAverage.setText(voteAverage + "⭐");

            Picasso.with(this)
                    .load(ApiManager.BASE_URL_IMAGE_POSTER + backdropPath)
                    .into(mBackDropImage);

            Picasso.with(this)
                    .load(ApiManager.BASE_URL_IMAGE_BACKDROP + posterPath)
                    .into(mPosterImage);


            favourite = favExists(id);
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerview.setLayoutManager(layoutManager);

        recyclerviewReview.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewReview.setNestedScrollingEnabled(false);

        connectAndGetApiData();

        if (favourite) {
            favourite = true;
            fab.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star, null));
        } else {
            favourite = false;
            fab.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_border, null));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favourite) {
                    fab.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_border, null));
                    deleteFromFavorite();
                } else {
                    fab.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star, null));
                    addToFavourite();
                }
            }
        });
    }

    public void connectAndGetApiData() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiManager.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ApiService movieApiService = retrofit.create(ApiService.class);


        Call<TrailerResponse> call = movieApiService.getTrailer(Integer.parseInt(id), ApiManager.getApiKey());
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {

                if (response.isSuccessful()) {

                    trailers = response.body().getResults();
                    recyclerview.setAdapter(new TrailerAdapter(trailers, R.layout.trailer, getApplicationContext()));
                    TrailerAdapter.setOnItemClickListener(DetailActivity.this);
                }

            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {

            }
        });

        Call<ReviewResponse> call2 = movieApiService.getReview(Integer.parseInt(id), ApiManager.getApiKey());
        call2.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call2, Response<ReviewResponse> response2) {

                if (response2.isSuccessful()) {
                    reviews = response2.body().getResults();
                    recyclerviewReview.setAdapter(new ReviewAdapter(reviews, R.layout.review, getApplicationContext()));
                }

            }

            @Override
            public void onFailure(Call<ReviewResponse> call2, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(int position) {
        Trailer clickedItem = trailers.get(position);
        String mVideoKey = clickedItem.getKey();
        watchYoutubeVideo(this, mVideoKey);
    }

    public boolean favExists(String id) {

        String selectString = "SELECT * FROM " + FavouriteContract.TABLE_NAME +
                " WHERE " + FavouriteContract.MOVIE_ID +
                " =?";
        Cursor cursor = movieDb.rawQuery(selectString, new String[]{id});
        boolean hasObject = false;
        if (cursor.moveToFirst()) {
            hasObject = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
        }
        cursor.close();
        return hasObject;
    }

    public void addToFavourite() {

        favourite = true;
        ContentResolver contentResolver = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(FavouriteContract.MOVIE_ID, id);
        values.put(FavouriteContract.MOVIE_TITLE, title);
        values.put(FavouriteContract.MOVIE_AVERAGE_RATING, voteAverage);
        values.put(FavouriteContract.MOVIE_POSTER_IMAGE, posterPath);
        values.put(FavouriteContract.MOVIE_BACKDROP_IMAGE, backdropPath);
        values.put(FavouriteContract.MOVIE_OVERVIEW, overview);
        values.put(FavouriteContract.MOVIE_RELEASE_DATE, releasedate);

        contentResolver.insert(FavouriteProvider.CONTENT_URI, values);
        Toast.makeText(getApplicationContext(), R.string.addedtofavourites, Toast.LENGTH_SHORT).show();
    }

    public void deleteFromFavorite() {

        favourite = false;
        ContentResolver contentResolver = getContentResolver();

        String selection = FavouriteContract.MOVIE_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        contentResolver.delete(FavouriteProvider.CONTENT_URI, selection, selectionArgs);
        Toast.makeText(getApplicationContext(), R.string.removedfromfavourites, Toast.LENGTH_SHORT).show();
    }
}
