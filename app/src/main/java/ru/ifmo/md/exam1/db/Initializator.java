package ru.ifmo.md.exam1.db;


import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import nl.qbusict.cupboard.DatabaseCompartment;
import ru.ifmo.md.exam1.models.Song;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by creed on 23.01.15.
 */
public class Initializator {
    public static void store(SQLiteDatabase db, List<Song> songs) {
        DatabaseCompartment dbc = cupboard().withDatabase(db);
        for(int i = 0; i < Math.min(songs.size(), 10); i++) {
            dbc.put(songs.get(i));
        }
    }
}
