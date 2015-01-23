package ru.ifmo.md.exam1.ui;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import ru.ifmo.md.exam1.MusicApplication;
import ru.ifmo.md.exam1.R;
import ru.ifmo.md.exam1.SongListCursorAdapter;
import ru.ifmo.md.exam1.db.CupboardSQLiteOpenHelper;
import ru.ifmo.md.exam1.db.Initializator;
import ru.ifmo.md.exam1.models.Song;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class MainActivity extends ActionBarActivity {
    public static final String musicParsed = "music_parsed";
    private ListView mListView;
    private SongListCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (true || prefs.getBoolean(musicParsed, false) == false) {

            Toast.makeText(this, "Starting to parse...", Toast.LENGTH_SHORT).show();

            InputStream inputStream = getResources().openRawResource(R.raw.music);
            Reader reader = new InputStreamReader(inputStream);
            Type listType = new TypeToken<List<Song>>(){}.getType();

            Gson gson = new Gson();
            List<Song> response = gson.fromJson(reader, listType);
            Log.d(MusicApplication.TAG, response.toString());
            CupboardSQLiteOpenHelper helper = new CupboardSQLiteOpenHelper(MusicApplication.getContext());
            SQLiteDatabase db = helper.getWritableDatabase();
            Initializator.store(db, response);

            Toast.makeText(this, "Music parsed", Toast.LENGTH_SHORT).show();

            //save progress
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(musicParsed, true);
            editor.commit();
        }

        String authority = getResources().getString(R.string.authority);
        Uri SONGS_URI = Uri.parse("content://"+authority+"/songs");

        Cursor songs = cupboard().withContext(MusicApplication.getContext()).query(SONGS_URI, Song.class).getCursor();
        mListView = (ListView) findViewById(R.id.listView);
        mAdapter = new SongListCursorAdapter(MusicApplication.getContext(), songs, 0);
        mListView.setAdapter(mAdapter);

        /*QueryResultIterable<Song> itr = cupboard().withContext(MusicApplication.getContext()).query(SONGS_URI, Song.class).query();
        List<Song> cont = new ArrayList<>();
        for (Song song : itr) {
            cont.add(song);
        }
        Log.d(MusicApplication.TAG, cont.toString());
        itr.close();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
