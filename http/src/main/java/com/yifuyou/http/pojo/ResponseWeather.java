package com.yifuyou.http.pojo;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ResponseWeather {
    public static final String TAG = "ResponseWeather";
    private String cityid;

    private String city;
    private String cityEn;
    private String country;
    private String countryEn;
    private String update_time;
    private List<DayWeather> data;

    public static ResponseWeather wrapperJson(String source) throws JSONException {
        ResponseWeather responseWeather = new ResponseWeather();

        JSONObject jsonObject=new JSONObject(source);
        Log.i(TAG, "wrapperJson: source  :"+source);
        try {
            String code = jsonObject.getString("errcode");
            System.out.println(code);
            if(code!=null){
                return responseWeather;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        responseWeather.cityid=jsonObject.getString("cityid");
        responseWeather.city=jsonObject.getString("city");
        responseWeather.cityEn= jsonObject.getString("cityEn");
        responseWeather.country= jsonObject.getString("country");
        responseWeather.countryEn= jsonObject.getString("countryEn");
        responseWeather.update_time= jsonObject.getString("update_time");
        responseWeather.data= DayWeather.wrapperDayWeather(jsonObject.getJSONArray("data"));
        return responseWeather;
    }



    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<DayWeather> getData() {
        return data;
    }

    public void setData(List<DayWeather> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseWeather{" +
                "cityid='" + cityid + '\'' +
                ", city='" + city + '\'' +
                ", cityEn='" + cityEn + '\'' +
                ", country='" + country + '\'' +
                ", countryEn='" + countryEn + '\'' +
                ", update_time='" + update_time + '\'' +
                ", data=" + data +
                '}';
    }
}
