package com.yifuyou.weather_t.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yifuyou.http.pojo.DayWeather;
import com.yifuyou.weather_t.base.BaseFragment;
import com.yifuyou.weather_t.commom.EventMainUI;
import com.yifuyou.weather_t.databinding.Fragment1LayoutBinding;

import org.greenrobot.eventbus.Subscribe;

public class Fragment_1 extends BaseFragment {
    public Fragment1LayoutBinding binding;
    public Fragment_1(String type) {
        super(type);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding=Fragment1LayoutBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }


    @Subscribe
    @SuppressLint("SetTextI18n")
    public void flashWeather(EventMainUI e){
        if(!getType().endsWith(e.getResponseWeather().getCity())){
            return;
        }
        DayWeather today = e.getResponseWeather().getData().get(0);
        DayWeather tomorrow = e.getResponseWeather().getData().get(1);
        binding.imageView.setImageResource(getResourceId(today.wea_img));
        binding.imageView2.setImageResource(getResourceId(tomorrow.wea_img));

        binding.weatherTodayText.setText("Today" +
                "\n" +today.wea+
                "\n"+today.tem);
        binding.weatherTomorrowText.setText("Tomorrow" +
                "\n" +tomorrow.wea+
                "\n"+tomorrow.tem);


    }




}
