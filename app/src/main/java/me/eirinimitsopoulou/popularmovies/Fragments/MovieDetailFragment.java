package me.eirinimitsopoulou.popularmovies.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.eirinimitsopoulou.popularmovies.BuildConfig;
import me.eirinimitsopoulou.popularmovies.Models.Movie;
import me.eirinimitsopoulou.popularmovies.R;


/**
 * Created by eirinimitsopoulou on 07/04/2018.
 */


public class MovieDetailFragment extends Fragment {

    public static final String ITEM = "item";

    private Movie movie;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments().containsKey(ITEM)) {
            movie = getArguments().getParcelable(ITEM);

            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(movie.getTitle());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movie_detail_fragment, container, false);

        if (movie != null) {
            ((TextView) rootView.findViewById(R.id.m_title)).setText(movie.getTitle());
            ((TextView) rootView.findViewById(R.id.m_overview)).setText(movie.getOverview());
            ((TextView) rootView.findViewById(R.id.m_vote_average)).setText(String.valueOf
                    (movie.getVoteAverage()) + "⭐");
            ((TextView) rootView.findViewById(R.id.m_release_date)).setText(movie
                    .getReleaseDate());

            ImageView posterImageView = rootView.findViewById(R.id.m_poster);
            Picasso.with(getContext())
                    .load(BuildConfig.BASE_IMAGE_URL + "/" + movie.getPoster())
                    .into(posterImageView);

            ImageView posterImageView2 = rootView.findViewById(R.id.m_poster2);
            Picasso.with(getContext())
                    .load(BuildConfig.BASE_IMAGE_URL + "/" + movie.getPoster())
                    .into(posterImageView2);
        }

        return rootView;
    }
}
