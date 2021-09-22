package com.yifuyou.weather_t;

import android.annotation.SuppressLint;
import android.database.DataSetObserver;
import android.util.Half;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.yifuyou.http.pojo.DayWeather;
import com.yifuyou.weather_t.commom.EventMainUI;

import java.util.List;

public class DaysWeatherListAdapter implements ListAdapter {
    private List<DayWeather> days;

    public DaysWeatherListAdapter(List<DayWeather> days) {
        this.days = days;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.f3_list_item_layout,parent,false);
            holder= new ViewHolder();
            holder.day=convertView.findViewById(R.id.f3_item_1);
            holder.date=convertView.findViewById(R.id.f3_item_2);
            holder.wea_img=convertView.findViewById(R.id.f3_item_3);
            holder.tem=convertView.findViewById(R.id.f3_item_4);
            holder.win=convertView.findViewById(R.id.f3_item_5);
            holder.air=convertView.findViewById(R.id.f3_item_6);
            holder.info1=convertView.findViewById(R.id.f3_item_info1);
            holder.info2=convertView.findViewById(R.id.f3_item_info2);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        DayWeather weather = (DayWeather) getItem(position);
        holder.day.setText(weather.day);
        holder.date.setText(weather.date);
        int resId=parent.getContext().getResources().getIdentifier(weather.wea_img,"drawable",parent.getContext().getPackageName());
        holder.wea_img.setImageResource(resId);
        holder.tem.setText(weather.tem1+"\n" +weather.tem2);
        String info1=" ";
        String info2=" ";
        if(position==0){
             info1="风力风向";
             info2="空气质量";
        }
        holder.info1.setText(info1);
        holder.info2.setText(info2);
        holder.win.setText(weather.win_speed+"\n"+weather.win.get(0));
        holder.air.setText(weather.air_level+"\n"+weather.air);
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public boolean isEmpty() {
        return days.isEmpty();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    static class ViewHolder{
        TextView day;
        TextView date;
        ImageView wea_img;
        TextView tem;
        TextView info1;
        TextView win;
        TextView info2;
        TextView air;
    }

}
