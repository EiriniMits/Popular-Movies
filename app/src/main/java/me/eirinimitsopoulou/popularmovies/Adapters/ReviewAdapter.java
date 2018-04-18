package me.eirinimitsopoulou.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.eirinimitsopoulou.popularmovies.Models.Review;
import me.eirinimitsopoulou.popularmovies.R;

import java.util.ArrayList;

/**
 * Created by eirinimitsopoulou on 12/04/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MovieReviewViewHolder> {

    private ArrayList<Review> mReview;

    public ReviewAdapter(ArrayList<Review> mMovieReview, int rowLayout, Context context) {
        this.mReview = mMovieReview;
    }

    @Override
    public ReviewAdapter.MovieReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review, parent, false);
        return new MovieReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.MovieReviewViewHolder holder, int position) {

        Review movieReview = mReview.get(position);
        holder.author.setText(movieReview.getAuthor());
        holder.content.setText(movieReview.getContent());
    }

    @Override
    public int getItemCount() {
        return mReview.size();
    }

    public class MovieReviewViewHolder extends RecyclerView.ViewHolder {

        public TextView author;
        public TextView content;

        public MovieReviewViewHolder(View itemView) {
            super(itemView);
            this.author = (TextView) itemView.findViewById(R.id.m_author);
            this.content = (TextView) itemView.findViewById(R.id.m_content);
        }
    }

}
