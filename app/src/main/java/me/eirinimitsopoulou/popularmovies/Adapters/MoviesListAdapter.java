package me.eirinimitsopoulou.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.eirinimitsopoulou.popularmovies.Models.Movie;
import me.eirinimitsopoulou.popularmovies.R;
import me.eirinimitsopoulou.popularmovies.Api.ApiManager;

import java.util.List;

/**
 * Created by eirinimitsopoulou on 07/04/2018.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder> {

    private static OnItemClickListener mListener;
    private List<Movie> movieList;
    private Context mContext;


    public MoviesListAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movieList = movies;
        this.mContext = context;
    }

    public static void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_poster_fragment, parent, false);
        return new MovieViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Picasso.with(mContext)
                .load(ApiManager.BASE_URL_IMAGE_POSTER + movieList.get(position).getPoster_path())
                .into(holder.poster);
        holder.name.setText(movie.getTitle());
        holder.rating.setText(Double.toString(movie.getVote_average()) + "⭐");
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public ImageView poster;
        final TextView name, rating;

        public MovieViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            name = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}

