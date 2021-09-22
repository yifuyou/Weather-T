package com.yifuyou.weather_t.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseFragment extends  Fragment {
    public void log(String s){
        Log.i( "-----log----",s);
    }

    public BaseFragment(String type){
        this.type=type;
    }

    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    protected int getResourceId(String sName){
       return getResources().getIdentifier(sName, "drawable", getActivity().getPackageName());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        log("------------log------------"+getType());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("------------create----------"+getType());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        log("-----------viewCreated-----------"+getType());
    }

    @Override
    public void onStart() {
        super.onStart();
        log("---------start------------"+getType());
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        log("-----------resume----------"+getType());
    }

    @Override
    public void onPause() {
        super.onPause();
        log("-----------pause-----------"+getType());
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        log("---------stop-------------"+getType());
        super.onStop();
    }
}
