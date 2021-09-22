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
import com.yifuyou.weather_t.databinding.Fragment0LayoutBinding;

import org.greenrobot.eventbus.Subscribe;

public class Fragment0 extends BaseFragment {
    Fragment0LayoutBinding binding;
    public Fragment0(String type) {
        super(type);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=Fragment0LayoutBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void flashWeather(EventMainUI e) {
        if(!getType().endsWith(e.getResponseWeather().getCity())){
            return;
        }
        DayWeather dayWeather = e.getResponseWeather().getData().get(0);
        binding.nowWeatherText.setText(dayWeather.wea.trim());
        binding.nowWeatherImg.setImageResource(getResourceId(dayWeather.wea_img));
        binding.nowWeatherTem.setText(dayWeather.tem1+"/"+dayWeather.tem2);
        binding.nowWeatherMore.setText("湿度："+dayWeather.humidity+", 风力: "+dayWeather.win_speed);
        binding.nowWeatherAir.setText("空气质量: "+dayWeather.air+"("+dayWeather.air_level+")");
    }
}