package com.mateoj.hacku4;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.parse.Parse;

/**
 * Created by jose on 2/4/16.
 */
public class HackUApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
