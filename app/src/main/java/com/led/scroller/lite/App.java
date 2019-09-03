package com.led.scroller.lite;

import android.app.Application;

import com.led.scroller.lite.db.DBOperation;

public class App extends Application {

    public static final String ISFIRST = "isfirst";
    private static App app;

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        //初始化数据库
        DBOperation.getInstance();

//        Stetho.initializeWithDefaults(this);
    }
}
