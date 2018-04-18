package me.eirinimitsopoulou.popularmovies.Data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eirinimitsopoulou on 12/04/2018.
 */

public class FavouriteDBHelper extends SQLiteOpenHelper {

    public static final String SQL_CREATE_MOVIE_FAVOURITE_TABLE = " CREATE TABLE IF NOT EXISTS " +
            FavouriteContract.TABLE_NAME + " (" +
            FavouriteContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FavouriteContract.MOVIE_ID + " INTEGER UNIQUE, " +
            FavouriteContract.MOVIE_TITLE + " TEXT NOT NULL, " +
            FavouriteContract.MOVIE_POSTER_IMAGE + " TEXT NOT NULL, " +
            FavouriteContract.MOVIE_BACKDROP_IMAGE + " TEXT NOT NULL, " +
            FavouriteContract.MOVIE_AVERAGE_RATING + " TEXT NOT NULL, " +
            FavouriteContract.MOVIE_OVERVIEW + " TEXT NOT NULL, " +
            FavouriteContract.MOVIE_RELEASE_DATE + " TEXT NOT NULL" +
            " );";
    static final String DATABASE_NAME = "favourite";
    private static final int VERSION = 1;


    public FavouriteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_FAVOURITE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteContract.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
