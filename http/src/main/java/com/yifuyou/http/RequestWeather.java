package com.yifuyou.http;

import android.content.pm.PackageManager;

import androidx.lifecycle.MutableLiveData;

import com.yifuyou.http.pojo.ResponseWeather;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import kotlin.text.Charsets;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestWeather {
    private OkHttpClient client;
    private Request req;
    private static RequestWeather instance;
    private final String cityString="city";

    private RequestWeather(){
        client=new OkHttpClient();

    }
    public static RequestWeather getInstance(){
        if(instance==null){
            instance=new RequestWeather();
        }
        return instance;
    }

    private final String[] args={
            "version",
            "appid",
            "appsecret"
    };

    private  String getWeatherUrl(String city){
        HashMap<String,String> map=new HashMap<>();
        map.put(args[0],CommomData.VERSION);
        map.put(args[1],CommomData.APPID);
        map.put(args[2],CommomData.APPSECRET);
        String url=CommomData.BASEURL.concat("?");

        for (String s : args) {
                url=url.concat(s)
                    .concat("=")
                    .concat(map.getOrDefault(s,"Illegal-"+s))
                    .concat("&");
        }
        String s = url.concat(cityString).concat("=").concat(city);
        System.out.println("=======" +
                s +
                "=========");
        return s;
    }

    public RequestCall request(String city){
        System.out.println("000000000========================00000000"+city);
         req=new Request.Builder()
                .url(getWeatherUrl(city))
                .get()
                .build();
        return new RequestCall(client.newCall(req));
    }


    public  class RequestCall{
        private Call call;
        RequestCall(Call call){
            this.call=call;
        }
        public void enqueue(Callback callback){
            client.newCall(req).enqueue(callback);
        }

        public MutableLiveData<ResponseWeather> execute(){
            MutableLiveData<ResponseWeather> result=new MutableLiveData<>();

            new Thread(()->{
                try {
                    Response response = call.execute();
                    System.out.println(response.headers().toString());
                    System.out.println("response message"+response.message());

                    result.postValue(
                            ResponseWeather.wrapperJson(
                                    response.body().source().readString(Charsets.UTF_8)
                            )
                    );
                } catch (IOException | JSONException e) {
                    e.printStackTrace();

                }

            }).start();
            return result;
        }

    }



}
