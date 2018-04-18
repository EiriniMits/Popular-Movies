package me.eirinimitsopoulou.popularmovies.Models;


/**
 * Created by eirinimitsopoulou on 12/04/2018.
 */

public class Trailer {

    private String id;
    private String name;
    private String key;


    public Trailer(String id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

}


