package com.yifuyou.weather_t.commom;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SharedPUtil {

    private static String cityString="city";
    private static String citiesString="cities";
    private static String sp_fileName="ct_sp";


    public static ArrayList<String> getCities(Context context){
        SharedPreferences sp = context.getSharedPreferences(sp_fileName, Context.MODE_PRIVATE);
        Set<String> stringSet = sp.getStringSet(citiesString, new HashSet<>());
        ArrayList<String> list = new ArrayList<>(stringSet);
        if(stringSet.size()<=1) {
            list.add(0,"广州");
        }
        return list;
    }
    public static int addCity(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(sp_fileName, Context.MODE_PRIVATE);
        HashSet<String> strings = new HashSet<>(sp.getStringSet(citiesString, new HashSet<>()));
        strings.add(city);
        SharedPreferences.Editor edit = sp.edit();
        edit.putStringSet(citiesString,strings);
        edit.apply();
        return strings.size();
    }
    public static void removeCity(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(sp_fileName, Context.MODE_PRIVATE);
        HashSet<String> strings = new HashSet<>(sp.getStringSet(citiesString, null));
        SharedPreferences.Editor edit = sp.edit();
        strings.remove(city);
        edit.putStringSet(citiesString,strings);
        edit.apply();
    }
    public static String getCity(Context context){
        SharedPreferences sp = context.getSharedPreferences(sp_fileName, Context.MODE_PRIVATE);
        return sp.getString(cityString,"广州");
    }
    public static void setCity(Context context,String city){
        SharedPreferences sp = context.getSharedPreferences(sp_fileName, Context.MODE_PRIVATE);
        sp.edit().putString(cityString,city).apply();
    }
    public static void setCities(Context context,ArrayList<String> cities){
        SharedPreferences sp = context.getSharedPreferences(sp_fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        HashSet<String> hashSet=new HashSet<>(cities);
        edit.putStringSet(citiesString,hashSet);
        edit.apply();
    }
}
