package ru.ifmo.md.exam1.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import ru.ifmo.md.exam1.MusicApplication;
import ru.ifmo.md.exam1.R;
import ru.ifmo.md.exam1.models.Playlist;
import ru.ifmo.md.exam1.models.Song;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by creed on 23.01.15.
 */
public class MusicProvider extends ContentProvider {
    private CupboardSQLiteOpenHelper mDatabaseHelper;
    private static UriMatcher sMatcher;

    private static String mContentProviderAuth;

    private static final int SONG = 0;
    private static final int SONGS = 1;
    private static final int PLAYLIST = 2;
    private static final int PLAYLISTS = 3;

    private static final String BASE_PLAYLISTS = "playlists";
    private static final String BASE_PLAYLIST = "playlist";
    private static final String BASE_SONGS = "songs";
    private static final String BASE_SONG = "song";

    @Override
    public boolean onCreate() {
        if (sMatcher == null) {
            sMatcher = new UriMatcher(UriMatcher.NO_MATCH);
            mContentProviderAuth = getContext().getString(R.string.authority);
            sMatcher.addURI(mContentProviderAuth, BASE_PLAYLISTS, PLAYLISTS);
            sMatcher.addURI(mContentProviderAuth, BASE_PLAYLIST + "/#", PLAYLIST);
            sMatcher.addURI(mContentProviderAuth, BASE_SONGS, SONGS);
            sMatcher.addURI(mContentProviderAuth, BASE_SONG + "/#", SONG);
        }
        mDatabaseHelper = new CupboardSQLiteOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Class clz;
        switch (sMatcher.match(uri)) {
            case PLAYLIST:
            case PLAYLISTS:
                clz = Playlist.class;
                return cupboard().withDatabase(db).query(clz).
                        withProjection(projection).
                        withSelection(selection, selectionArgs).
                        orderBy(sortOrder).
                        getCursor();
            case SONG:
            case SONGS:
                clz = Song.class;
                Cursor cursor = cupboard().withDatabase(db).query(clz).
                        withProjection(projection).
                        withSelection(selection, selectionArgs).
                        orderBy(sortOrder).
                        getCursor();
                Log.d(MusicApplication.TAG, "Provider.query for songs " + cursor.getCount() + " entries");
                return cursor;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Class clz;
        long id = Long.getLong(uri.getLastPathSegment(), 0);
        switch (sMatcher.match(uri)) {
            case PLAYLIST:
            case PLAYLISTS:
                clz = Playlist.class;
                if (id == 0) {
                    id = cupboard().withDatabase(db).put(clz, values);
                } else {
                    id = cupboard().withDatabase(db).update(clz, values);
                }
                return Uri.parse(mContentProviderAuth + "/" + BASE_PLAYLIST + "/" + id);
            case SONG:
            case SONGS:
                clz = Song.class;
                if (id == 0) {
                    id = cupboard().withDatabase(db).put(clz, values);
                } else {
                    id = cupboard().withDatabase(db).update(clz, values);
                }
                return Uri.parse(mContentProviderAuth + "/" + BASE_SONG + "/" + id);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Class clz;
        switch (sMatcher.match(uri)) {
            case PLAYLIST:
            case PLAYLISTS:
                clz = Playlist.class;
                return cupboard().withDatabase(db).update(clz, values, selection, selectionArgs);
            case SONG:
            case SONGS:
                clz = Song.class;
                return cupboard().withDatabase(db).update(clz, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}