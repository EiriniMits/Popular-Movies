package me.eirinimitsopoulou.popularmovies.Models;

import java.util.ArrayList;

/**
 * Created by eirinimitsopoulou on 12/04/2018.
 */

public class ReviewResponse {

    private int id;
    private ArrayList<Review> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

}
