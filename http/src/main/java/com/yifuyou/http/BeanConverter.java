package com.yifuyou.http;

import com.yifuyou.http.pojo.DayWeather;
import com.yifuyou.http.pojo.Exponent;
import com.yifuyou.http.pojo.HoursWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BeanConverter {

    public static class BuilderJson{
        public  BeanConverter build(){
            return new BeanConverter();
        }
    }
    public DayWeather convertJsonDayWeather(JSONObject o) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> aClass = Class.forName("com.yifuyou.http.pojo.DayWeather");
        DayWeather instance = (DayWeather)aClass.newInstance();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            try{
                Object o1 = o.get(field.getName());
                if(field.getType().equals(String.class)){

                    field.set(instance,String.valueOf(o1));
                }else {
                    field.set(instance,converted(field.getType().newInstance(),o1,field.getName()));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return instance;
    }
    public Object converted(Object ins,Object obj,String type) throws JSONException, IllegalAccessException, InstantiationException {
        Class<?> aClass = ins.getClass();
        if (obj.getClass().equals(JSONObject.class)) {

            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field field : declaredFields) {
                Object o = ((JSONObject) obj).get(field.getName());
                if (field.getType().equals(String.class)) {
                    field.set(ins, String.valueOf(o));
                } else {
                    field.set(ins, converted(field.getType().newInstance(), o, field.getName()));
                }
            }
        } else if (obj.getClass().equals(JSONArray.class)) {
            if (!(ins instanceof ArrayList)) {
                throw new IllegalAccessException("request List<> but got " + ins.getClass());
            } else {
                JSONArray jsonArray = (JSONArray) obj;
                if(!(jsonArray.get(0) instanceof JSONObject)){
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Object o = jsonArray.get(i);
                        ((List) ins).add(o);
                    }
                    return ins;
                }

                Class<?> cls = null;
                if ("hours".equals(type)) {//todo remove constant
                    cls = HoursWeather.class;
                } else if ("index".equals(type)) {
                    cls = Exponent.class;
                } else {
                    System.out.println(ins.getClass());
                    throw new IllegalAccessException("cls could not convert");
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    Object newIns = cls.newInstance();
                    Object o = jsonArray.get(i);
                    newIns = converted(newIns, o, "");
                    ((List) ins).add(newIns);
                }
                return ins;

            }

        }else {
            throw new IllegalAccessException("converted fail ");
        }
        return ins;
    }





}
