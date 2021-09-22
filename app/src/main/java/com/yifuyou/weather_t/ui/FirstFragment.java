package com.yifuyou.weather_t.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.yifuyou.http.RequestUtil;
import com.yifuyou.http.pojo.ResponseWeather;
import com.yifuyou.weather_t.FragmentsManager;
import com.yifuyou.weather_t.R;
import com.yifuyou.weather_t.base.BaseFragment;
import com.yifuyou.weather_t.commom.EventMainUI;
import com.yifuyou.weather_t.databinding.FragmentFirstBinding;
import com.yifuyou.weather_t.ui.fragments.Fragment0;
import com.yifuyou.weather_t.ui.fragments.Fragment_1;
import com.yifuyou.weather_t.ui.fragments.Fragment_2;
import com.yifuyou.weather_t.ui.fragments.Fragment_3;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

public class FirstFragment extends BaseFragment {

    private FragmentFirstBinding binding;
    private FragmentsManager fm;

    public FirstFragment(String type) {
        super(type);
        log("type: "+type);
    }

    public FirstFragment(){
        this("parent_main");
    }



    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setWeather(getType());
    }

    private void setWeather(String city){
        MutableLiveData<ResponseWeather> stringMutableLiveData = RequestUtil.getWeatherMsgByCity(city);
        stringMutableLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseWeather>() {
            @Override
            public void onChanged(ResponseWeather s) {
                fm=new FragmentsManager(getChildFragmentManager(),R.id.first_layout_list);
                fm.addFragment(new Fragment0("frag0".concat(getType())))
                        .addFragment(new Fragment_1("frag1".concat(getType())))
                        .addFragment(new Fragment_2("flag2".concat(getType())))
                        .addFragment(new Fragment_3("flag3".concat(getType())))
                        .flash();
                EventBus.getDefault().post(new EventMainUI(200,getType(),s));
            }
        });
    }

    @Subscribe
    public void changeCity(String city){
        if(getType().equals(city)){
            setWeather(city);
        }
    }

    @Subscribe
    public void flashWeather(EventMainUI e) {
        if(getType().equals(e.getFormType())){
            log(e.toString());
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}