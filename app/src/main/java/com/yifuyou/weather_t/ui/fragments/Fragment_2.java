package com.yifuyou.weather_t.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yifuyou.http.pojo.DayWeather;
import com.yifuyou.http.pojo.HoursWeather;
import com.yifuyou.weather_t.base.BaseFragment;
import com.yifuyou.weather_t.commom.EventMainUI;
import com.yifuyou.weather_t.databinding.Fragment2LayoutBinding;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fragment_2 extends BaseFragment {
    public Fragment_2(String type) {
        super(type);
    }
    Fragment2LayoutBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding=Fragment2LayoutBinding.inflate(inflater,container,false);

        int[] ys={27, 27, 28, 28, 28, 27, 25, 23, 21, 21, 21, 20, 20, 20, 19, 19, 19, 20, 20};
        int[] xs={13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 0, 1, 2, 3, 4, 5, 6, 7};

        binding.paintView.setXYSource(xs,ys);
        System.out.println("set paint source");
        return binding.getRoot();


    }

    @Subscribe
    public void flashWeather(EventMainUI e){
        if(!getType().endsWith(e.getResponseWeather().getCity())){
            return;
        }
        ArrayList<HoursWeather> weathers = e.getResponseWeather().getData().get(0).getHours();
        int count=weathers.size();
        int[] xs=new int[count];
        int[] ys=new int[count];

        for (int i=0;i< count;i++) {
            String[] strings = weathers.get(i).hours.split("时");
            xs[i]=(Integer.parseInt(strings[0]));
            ys[i]=(Integer.parseInt(weathers.get(i).tem));
        }
        binding.paintView.setXYSource(xs,ys);

    }








}
