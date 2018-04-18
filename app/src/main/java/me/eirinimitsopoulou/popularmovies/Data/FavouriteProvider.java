package me.eirinimitsopoulou.popularmovies.Data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by eirinimitsopoulou on 12/04/2018.
 */

public class FavouriteProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIE_WITH_ID = 101;
    private static final String AUTHORITY = "com.example.android.mypopularmoviesapp";
    private static final String PATH = "movie_favourite";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);
    private static final UriMatcher uriMatcher = buildUriMatcher();
    private FavouriteDBHelper mDBHelper;

    public static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, PATH, MOVIES);
        uriMatcher.addURI(AUTHORITY, PATH + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        mDBHelper = new FavouriteDBHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings,
                        @Nullable String s, @Nullable String[] strings1,
                        @Nullable String s1) {


        final SQLiteDatabase sqLiteDatabase = mDBHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor returnCursor;

        switch (match) {
            case MOVIES:
                returnCursor = sqLiteDatabase.query(
                        FavouriteContract.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1
                );
                break;

            case MOVIE_WITH_ID:
                returnCursor = sqLiteDatabase.query(
                        FavouriteContract.TABLE_NAME,
                        strings,
                        FavouriteContract.MOVIE_ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        s1);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        if (getContext() != null) {
            returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return returnCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase database = mDBHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES:
                long id = database.insert(FavouriteContract.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase database = mDBHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int movieDeleted;

        switch (match) {
            case MOVIES:
                movieDeleted = database.delete(FavouriteContract.TABLE_NAME, selection, selectionArgs);
                database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + FavouriteContract.TABLE_NAME + "'");
                break;
            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);
                selection = "_id=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                movieDeleted = database.delete(FavouriteContract.TABLE_NAME, selection, selectionArgs);
                database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + FavouriteContract.TABLE_NAME + "'");
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (movieDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return movieDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
