package com.yifuyou.http.pojo;

import com.yifuyou.http.BeanConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DayWeather {

    public String day;
    public String date;
    public String week;
    public String wea;
    public String wea_img;
    public String wea_day;
    public String wea_day_img;
    public String wea_night;
    public String wea_night_img;
    public String tem;
    public String tem1;
    public String tem2;
    public String humidity;
    public String visibility;
    public String pressure;
    public ArrayList<String> win;
    public String win_speed;
    public String win_meter;
    public String sunrise;
    public String sunset;
    public String air;
    public String air_level;
    public String air_tips;

    public Alarm alarm;//todo change
    public ArrayList<Exponent> index;
    public ArrayList<HoursWeather> hours;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWea_img() {
        return wea_img;
    }

    public void setWea_img(String wea_img) {
        this.wea_img = wea_img;
    }

    public String getWea_day() {
        return wea_day;
    }

    public void setWea_day(String wea_day) {
        this.wea_day = wea_day;
    }

    public String getWea_day_img() {
        return wea_day_img;
    }

    public void setWea_day_img(String wea_day_img) {
        this.wea_day_img = wea_day_img;
    }

    public String getWea_night() {
        return wea_night;
    }

    public void setWea_night(String wea_night) {
        this.wea_night = wea_night;
    }

    public String getWea_night_img() {
        return wea_night_img;
    }

    public void setWea_night_img(String wea_night_img) {
        this.wea_night_img = wea_night_img;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public ArrayList<String> getWin() {
        return win;
    }

    public void setWin(ArrayList<String> win) {
        this.win = win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    public String getWin_meter() {
        return win_meter;
    }

    public void setWin_meter(String win_meter) {
        this.win_meter = win_meter;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
    }

    public String getAir_level() {
        return air_level;
    }

    public void setAir_level(String air_level) {
        this.air_level = air_level;
    }

    public String getAir_tips() {
        return air_tips;
    }

    public void setAir_tips(String air_tips) {
        this.air_tips = air_tips;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public ArrayList<Exponent> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<Exponent> index) {
        this.index = index;
    }

    public ArrayList<HoursWeather> getHours() {
        return hours;
    }

    public void setHours(ArrayList<HoursWeather> hours) {
        this.hours = hours;
    }

    public DayWeather(){}


    public static List<DayWeather> wrapperDayWeather(JSONArray source) throws JSONException {
        List<DayWeather> dayWeathers=new ArrayList<>();
        BeanConverter converter = new BeanConverter.BuilderJson().build();
        for(int i=0;i<source.length();i++){
            JSONObject obj = source.getJSONObject(i);
        /*    DayWeather dayWeather=new DayWeather();
            dayWeather.date=obj.getString("date");
            dayWeather.week=obj.getString("week");
            dayWeather.wea=obj.getString("wea");
            dayWeather.wea_img= obj.getString("wea_img");
            dayWeather.wea_day = obj.getString("wea_day");
            dayWeather.wea_day_img = obj.getString("wea_day_img");
            dayWeather.tem = obj.getString("tem");
            dayWeather.tem1 = obj.getString("tem1");
            dayWeather.tem2 = obj.getString("tem2");
            dayWeather.humidity = obj.getString("humidity");
            dayWeather.visibility = obj.getString("visibility");
            dayWeather.pressure = obj.getString("pressure");
            dayWeather.visibility = obj.getString("visibility");//todo
            dayWeather.win_speed = obj.getString("win_speed");
            dayWeather.win_meter = obj.getString("win_meter");
            dayWeather.sunrise = obj.getString("sunrise");
            dayWeather.sunset = obj.getString("sunset");
            dayWeather.air = obj.getString("air");
            dayWeather.air_level = obj.getString("air_level");
            dayWeather.air_tips = obj.getString("air_tips");*/
            DayWeather dayWeather = null;
            try {
                dayWeather = converter.convertJsonDayWeather(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            dayWeathers.add(dayWeather);
        }

        return dayWeathers;
    }

    @Override
    public String toString() {
        return "DayWeather{" +
                "day='" + day + '\'' +
                ", date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", wea='" + wea + '\'' +
                ", wea_img='" + wea_img + '\'' +
                ", wea_day='" + wea_day + '\'' +
                ", wea_day_img='" + wea_day_img + '\'' +
                ", wea_night='" + wea_night + '\'' +
                ", wea_night_img='" + wea_night_img + '\'' +
                ", tem='" + tem + '\'' +
                ", tem1='" + tem1 + '\'' +
                ", tem2='" + tem2 + '\'' +
                ", humidity='" + humidity + '\'' +
                ", visibility='" + visibility + '\'' +
                ", pressure='" + pressure + '\'' +
                ", win=" + win +
                ", win_speed='" + win_speed + '\'' +
                ", win_meter='" + win_meter + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", air='" + air + '\'' +
                ", air_level='" + air_level + '\'' +
                ", air_tips='" + air_tips + '\'' +
                ", alarm=" + alarm +
                ", index=" + index +
                ", hours=" + hours +
                '}';
    }
/*
"day": "15日（星期三）",
            "date": "2021-09-15",
            "week": "星期三",
            "wea": "多云转雷阵雨",
            "wea_img": "yun",
            "wea_day": "多云",
            "wea_day_img": "yun",
            "wea_night": "雷阵雨",
            "wea_night_img": "yu",
            "tem": "29℃",
            "tem1": "34℃",
            "tem2": "25℃",
            "humidity": "77%",
            "visibility": "20km",
            "pressure": "1006",
            "win": [
                "西南风",
                "无持续风向"
            ],
            "win_speed": "<3级",
            "win_meter": "0km/h",
            "sunrise": "06:13",
            "sunset": "18:30",
            "air": "107",
            "air_level": "轻度污染",
            "air_tips"

            alarm
            hours
            index

 */

}
