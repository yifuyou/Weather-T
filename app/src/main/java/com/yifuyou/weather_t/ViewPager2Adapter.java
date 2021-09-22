package com.yifuyou.weather_t;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.yifuyou.weather_t.base.BaseFragment;
import com.yifuyou.weather_t.ui.FirstFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class ViewPager2Adapter extends FragmentStateAdapter {
    private static final String TAG = "FragmentStateAdapter";
    private  ArrayList<BaseFragment> fragments;
    private  ArrayList<String> cities;

    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,@NonNull ArrayList<String> cities) {
        super(fragmentManager, lifecycle);
        this.cities=cities;
        fragments=new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            fragments.add(i,new FirstFragment(cities.get(i)));
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyCitiesChange(ArrayList<String> cities){
        this.cities=cities;
        Log.i(TAG, "notifyCitiesChange: "+this.cities.toString());

        int size=Math.min(fragments.size(),cities.size());

        for(int index=0;index<size;index++){
            fragments.get(index).setType(cities.get(index));
        }
        if(fragments.size()==size){
            for (int i = 0; i < cities.size()-size; i++) {
                fragments.add(new FirstFragment(cities.get(size+i)));
            }
        }else {
            for (int i = 0; i < fragments.size() - size; i++) {
                fragments.remove(size+i);
            }
        }
        notifyDataSetChanged();
    }

}
