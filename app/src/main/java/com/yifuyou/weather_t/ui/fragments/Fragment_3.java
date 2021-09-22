package com.yifuyou.weather_t.ui.fragments;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yifuyou.http.pojo.DayWeather;
import com.yifuyou.weather_t.DaysWeatherListAdapter;
import com.yifuyou.weather_t.base.BaseFragment;
import com.yifuyou.weather_t.commom.EventMainUI;
import com.yifuyou.weather_t.databinding.Fragment3LayoutBinding;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class Fragment_3 extends BaseFragment {
    Fragment3LayoutBinding binding;

    public Fragment_3(String type) {
        super(type);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=Fragment3LayoutBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Subscribe
    public void flashWeather(EventMainUI e) {
        if(!getType().endsWith(e.getResponseWeather().getCity())){
            return;
        }
        List<DayWeather> data = e.getResponseWeather().getData();
        DaysWeatherListAdapter daysWeatherListAdapter = new DaysWeatherListAdapter(data);
        binding.f3ListLayout.removeAllViews();
        for (int i = 0; i < data.size(); i++) {
            binding.f3ListLayout.addView(daysWeatherListAdapter.getView(i,null, binding.f3ListLayout));
        }
    }


}
