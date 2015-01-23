package ru.ifmo.md.exam1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import nl.qbusict.cupboard.DatabaseCompartment;
import ru.ifmo.md.exam1.models.Playlist;
import ru.ifmo.md.exam1.models.Song;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by creed on 23.01.15.
 */

public class CupboardSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "music.db";
    private static final int DATABASE_VERSION = 1;

    static {
        // register our models
        cupboard().register(Song.class);
        cupboard().register(Playlist.class);
    }

    public CupboardSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MUSIC", db.toString());
        DatabaseCompartment  dbc = cupboard().withDatabase(db);
        Log.d("MUSIC", cupboard().getRegisteredEntities().toString());
        // this will ensure that all tables are created
        dbc.createTables();
        // add indexes and other database tweaks
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this will upgrade tables, adding columns and new tables.
        // Note that existing columns will not be converted
        cupboard().withDatabase(db).upgradeTables();
        // do migration work
    }
}