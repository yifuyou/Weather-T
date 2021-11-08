package com.yifuyou.http;

import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.yifuyou.http.pojo.ResponseWeather;

import org.json.JSONException;

import java.io.IOException;
import java.net.NetPermission;

import kotlin.text.Charsets;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class RequestUtil {

public static void enqueue(String city,Callback callback){

    RequestWeather.getInstance().request(city).enqueue(new Callback() {
        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {

            System.out.println("call.enqueue fail");
            e.printStackTrace();
            callback.onFailure(call,e);
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
/*            try {
                System.out.println(response.headers().toString());
      *//*          result.postValue(
                        ResponseWeather.wrapperJson(
                                response.body().source().readString(Charsets.UTF_8)
                        )
                );*//*
                onResponse(call,response);
            } catch (IOException | JSONException e) {
                e.printStackTrace();

            }*/
        }
    });
}

    public static MutableLiveData<ResponseWeather> getWeatherMsgByCity(String city){

        RequestWeather.RequestCall call = RequestWeather.getInstance().request(city);
        return call.execute();
    }

}
