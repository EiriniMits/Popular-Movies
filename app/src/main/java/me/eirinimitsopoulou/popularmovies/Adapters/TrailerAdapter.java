package me.eirinimitsopoulou.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.eirinimitsopoulou.popularmovies.Models.Trailer;
import me.eirinimitsopoulou.popularmovies.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by eirinimitsopoulou on 12/04/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MovieTrailerViewHolder> {

    private static final String YOUTUBE_IMAGE_URL_PREFIX = "http://img.youtube.com/vi/";
    private static final String YOUTUBE_IMAGE_URL_SUFFIX = "/0.jpg";
    private static OnItemClickListener mListener;
    private ArrayList<Trailer> mTrailer;
    private Context mContext;

    public TrailerAdapter(ArrayList<Trailer> mMovieTrailer, int rowLayout, Context context) {
        this.mTrailer = mMovieTrailer;
        this.mContext = context;
    }

    public static void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public TrailerAdapter.MovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer, parent, false);
        return new MovieTrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.MovieTrailerViewHolder holder, int position) {

        Trailer movieTrailer = mTrailer.get(position);
        Picasso.with(mContext)
                .load(YOUTUBE_IMAGE_URL_PREFIX + movieTrailer.getKey() + YOUTUBE_IMAGE_URL_SUFFIX)
                .into(holder.trailer);
    }

    @Override
    public int getItemCount() {
        return mTrailer.size();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class MovieTrailerViewHolder extends RecyclerView.ViewHolder {

        protected ImageView trailer;

        public MovieTrailerViewHolder(View itemView) {
            super(itemView);
            trailer = (ImageView) itemView.findViewById(R.id.m_trailer);
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
