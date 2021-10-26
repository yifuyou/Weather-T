package com.yifuyou.weather_t;

import android.app.Application;
import android.util.Log;

import com.yifuyou.weather_t.commom.CityResource;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CityResource.getInstance(getApplicationContext());
        Log.i("TAG", "onCreate: ");
    }

    @Override
    public void onLowMemory() {
        Log.i("TAG", "onLowMemory: ");
        super.onLowMemory();
    }
}
