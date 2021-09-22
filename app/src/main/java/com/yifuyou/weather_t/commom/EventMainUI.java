package com.yifuyou.weather_t.commom;

import com.yifuyou.http.RequestWeather;
import com.yifuyou.http.pojo.ResponseWeather;

public class EventMainUI {
    private int code;

    private String formType;

    private ResponseWeather responseWeather;
    public EventMainUI(int code, ResponseWeather responseWeather) {
        this(code,"",responseWeather);
    }

    public EventMainUI(int code,String type,ResponseWeather responseWeather) {
        this.code = code;
        this.formType=type;
        this.responseWeather = responseWeather;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public ResponseWeather getResponseWeather() {
        return responseWeather;
    }

    public void setResponseWeather(ResponseWeather responseWeather) {
        this.responseWeather = responseWeather;
    }

    @Override
    public String toString() {
        return "EventMainUI{" +
                "code=" + code +
                ", responseWeather=" + responseWeather +
                '}';
    }
}
