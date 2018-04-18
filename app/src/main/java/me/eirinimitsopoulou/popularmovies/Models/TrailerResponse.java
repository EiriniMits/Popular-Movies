package me.eirinimitsopoulou.popularmovies.Models;


import java.util.ArrayList;

/**
 * Created by eirinimitsopoulou on 12/04/2018.
 */

public class TrailerResponse {

    private int id;
    private ArrayList<Trailer> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Trailer> getResults() {
        return results;
    }
}
