package ru.ifmo.md.exam1;

import android.app.Application;
import android.content.Context;

/**
 * Created by creed on 23.01.15.
 */

public class MusicApplication extends Application {
    public static final String TAG = "[MUSIC]";

    private static Context mContext;

    static {
        //Log.d(TAG, "Register classes for DB");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Hold a reference to ourself; this is safe because the Application class is a singleton
        mContext = this.getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
