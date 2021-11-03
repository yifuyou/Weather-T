package com.yifuyou.weather_t;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yifuyou.weather_t.commom.CityResource;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CityResource.getInstance(getApplicationContext());
        registerActivityLifecycleCallbacks(new LifeCycleCallback());
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
    static class LifeCycleCallback implements ActivityLifecycleCallbacks{
        private final String TAG="MyApplication - LifeCycleCallback";
        private int _count=0;
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            Log.i(TAG, "onActivityCreated: "+(_count++)+activity.getLocalClassName());
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            Log.i(TAG, "onActivityStarted: ");
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            Log.i(TAG, "onActivityResumed: ");
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            Log.i(TAG, "onActivityPaused: ");
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
            Log.i(TAG, "onActivityStopped: ");
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
            Log.i(TAG, "onActivitySaveInstanceState: ");
        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            _count--;
            Log.i(TAG, "onActivityDestroyed: "+_count);
            if(activity.getLocalClassName().equals("ui.MainActivity")){
                try {
                    activity.getApplication().onTerminate();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }

}
