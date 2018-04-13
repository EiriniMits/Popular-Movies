package me.eirinimitsopoulou.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eirinimitsopoulou on 07/04/2018.
 */

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String poster;
    private double voteAverage;
    private String overview;
    private String releaseDate;

    public Movie(String title, String imageUrl, String overview, double userRating, String releaseDate) {
        this.title = title;
        this.poster = imageUrl;
        this.overview = overview;
        this.voteAverage = userRating;
        this.releaseDate = releaseDate;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster = in.readString();
        voteAverage = in.readDouble();
        overview = in.readString();
        releaseDate = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() { return poster; }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeDouble(voteAverage);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}