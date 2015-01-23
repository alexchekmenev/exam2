package ru.ifmo.md.exam1;

/**
 * Created by creed on 23.01.15.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ru.ifmo.md.exam1.models.Song;
import ru.ifmo.md.exam1.ui.SongActivity;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class SongListCursorAdapter  extends CursorAdapter {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;

    public SongListCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public SongListCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final Song song = cupboard().withCursor(cursor).get(Song.class);
        TextView title = (TextView)view.findViewById(R.id.name);
        title.setText(song.getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SongActivity.class);
                //intent.putExtra(song.getId(), item._id);
                context.startActivity(intent);
            }
        });
    }
}
