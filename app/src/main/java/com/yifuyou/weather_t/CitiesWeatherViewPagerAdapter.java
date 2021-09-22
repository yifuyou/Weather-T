package com.yifuyou.weather_t;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.yifuyou.weather_t.base.BaseFragment;
import com.yifuyou.weather_t.ui.FirstFragment;

import java.util.ArrayList;

public class CitiesWeatherViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> cities;
    private ArrayList<FirstFragment> fragments;



    public CitiesWeatherViewPagerAdapter(@NonNull FragmentManager fragmentManager, ArrayList<String> cities) {
        super(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        if(cities==null||cities.isEmpty()){
            this.cities=new ArrayList<>();
        }else {
            this.cities=cities;
        }
        fragments=new ArrayList<>();
        for(String c:this.cities){
            fragments.add(new FirstFragment(c));
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    public static final int TYPE_ADD=1;
    public static final int TYPE_DEL=2;
    @SuppressLint("NotifyDataSetChanged")
    public void notifyCitiesChange(String city, int type){
        if(type==TYPE_ADD){
            if (cities.contains(city)) {
                return;
            }
            fragments.add(new FirstFragment(city));
            cities.add(city);
        }else if(type==TYPE_DEL){
            if (!cities.contains(city)) {
                return;
            }
            fragments.remove(cities.indexOf(city));
            cities.remove(city);

        }
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return cities.size();
    }


}
