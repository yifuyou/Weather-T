package com.yifuyou.http.pojo;

public class HoursWeather {
    public String hours;

    public String wea;
    public String wea_img;
    public String tem;
    public String win;

    public String win_speed;


    @Override
    public String toString() {
        return "HoursWeather{" +
                "hours='" + hours + '\'' +
                ", wea='" + wea + '\'' +
                ", wea_img='" + wea_img + '\'' +
                ", tem='" + tem + '\'' +
                ", win='" + win + '\'' +
                ", win_speed='" + win_speed + '\'' +
                '}';
    }
    /*
    {
                    "hours": "19时",
                    "wea": "阴",
                    "wea_img": "yin",
                    "tem": "29",
                    "win": "东南风",
                    "win_speed": "<3级"
                }

     */
}
