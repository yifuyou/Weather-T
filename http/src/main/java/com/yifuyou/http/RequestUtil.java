package com.yifuyou.http;

import androidx.lifecycle.MutableLiveData;

import com.yifuyou.http.pojo.ResponseWeather;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class RequestUtil {

    public static MutableLiveData<ResponseWeather> getWeatherMsgByCity(String city){
        RequestWeather.RequestCall call = RequestWeather.getInstance().request(city);
        return call.execute();
    }

}
