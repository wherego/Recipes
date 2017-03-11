package com.i7gk.recipes.app;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by 64886 on 2017/3/9 0009.
 */

public class App extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        LitePal.initialize(context);
    }
    public static Context getContext(){
        return context;
    }
}

