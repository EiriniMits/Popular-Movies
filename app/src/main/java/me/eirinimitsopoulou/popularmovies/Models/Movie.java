package me.eirinimitsopoulou.popularmovies.Models;

/**
 * Created by eirinimitsopoulou on 07/04/2018.
 */

public class Movie {

    private Integer id;
    private String title;
    private String poster_path;
    private String backdrop_path;
    private double vote_average;
    private String overview;
    private String release_date;

    public Movie(Integer id, double vote_average, String title, String poster_path, String backdrop_path, String overview, String release_date) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

}

