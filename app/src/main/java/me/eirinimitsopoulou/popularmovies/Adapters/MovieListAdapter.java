package me.eirinimitsopoulou.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.eirinimitsopoulou.popularmovies.BuildConfig;
import me.eirinimitsopoulou.popularmovies.MainActivity;
import me.eirinimitsopoulou.popularmovies.MovieDetailActivity;
import me.eirinimitsopoulou.popularmovies.Models.Movie;
import me.eirinimitsopoulou.popularmovies.Fragments.MovieDetailFragment;
import me.eirinimitsopoulou.popularmovies.R;

/**
 * Created by eirinimitsopoulou on 07/04/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private final Context mContext;
    private List<Movie> movieList;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Movie item = (Movie) view.getTag();
            Context context = view.getContext();
            Intent intent = new Intent(context, MovieDetailActivity.class);
            intent.putExtra(MovieDetailFragment.ITEM, item);
            context.startActivity(intent);
        }
    };

    public MovieListAdapter(Context context, MainActivity parent) {
        mContext = context;
        movieList = new ArrayList<>();
    }

    public void setData(List<Movie> movies) {
        movieList.clear();
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster_fragment, parent, false);
        return new MovieListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieListAdapter.ViewHolder holder, int position) {

        Movie movie = movieList.get(position);

        Picasso.with(mContext)
                .load(BuildConfig.BASE_IMAGE_URL + "/" + movie.getPoster())
                .into(holder.poster);

        holder.itemView.setTag(movie);
        holder.name.setText(movie.getTitle());
        holder.rating.setText(Double.toString(movie.getVoteAverage()) + "⭐");
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView poster;
        final TextView name, rating;

        ViewHolder(View view) {
            super(view);
            poster = view.findViewById(R.id.poster);
            name = view.findViewById(R.id.title);
            rating = view.findViewById(R.id.rating);
        }
    }
}
