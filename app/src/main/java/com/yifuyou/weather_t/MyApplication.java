package com.yifuyou.weather_t;

import android.app.Application;
import android.util.Log;

import com.yifuyou.weather_t.commom.CityResource;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CityResource.getInstance(getApplicationContext());
        Log.i("MyApplication", "onCreate: ");
    }

    @Override
    public void onLowMemory() {
        Log.i("MyApplication", "onLowMemory: ");
        super.onLowMemory();
    }

    @Override
    protected void finalize() throws Throwable {
        Log.i("MyApplication", "finalize: ");
        super.finalize();
    }

    @Override
    public void onTerminate() {
        Log.i("MyApplication", "onTerminate: ");
        super.onTerminate();
    }
}
