package com.yifuyou.weather_t.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yifuyou.weather_t.R;
import com.yifuyou.weather_t.commom.CityNode;

import java.util.ArrayList;

public class CityPickerAdapter extends RecyclerView.Adapter<CityPickerAdapter.Holder> {


    ArrayList<CityNode> arrayList;
    CityPickerBuilder.OnItemClick listener;
    int selectedIndex=0;

    public CityPickerAdapter(ArrayList<CityNode> nodeList, CityPickerBuilder.OnItemClick listener) {
        this.listener=listener;
        this.arrayList = nodeList;
    }

    public void setDate(ArrayList<CityNode> arrayList){
        this.arrayList=arrayList;
        notifyDataSetChanged();
        selectedIndex=0;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.city_choose_layout,parent,false);
        return new Holder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if(position==0){
            holder.textView.setText(getTile(position));
            holder.itemView.setSelected(true);
            return;
        }
        holder.textView.setText(arrayList.get(position-1).city);
        if(position==selectedIndex+1){
            holder.itemView.setActivated(true);
        }else if(holder.itemView.isActivated()){
            holder.itemView.setActivated(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.click(position-1,v);
                selectedIndex=position-1;
                holder.itemView.setActivated(true);
                notifyDataSetChanged();
            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        int type=0;
        if(position==0){
            return type;
        }
        if (arrayList.get(position-1).cityNodes!=null) {
            if(arrayList.get(position-1).cityNodes.get(0).cityNodes!=null){
                type=1;
            }else {
                type=2;
            }
        }else {
            type=3;
        }
        return type;
    }

    @Override
    public void onViewRecycled(@NonNull Holder holder) {
        super.onViewRecycled(holder);
    }


    public String getTile(int choosePosition){
        String result="";
        switch (getItemViewType(choosePosition+1)) {
            case 1:result="国家";break;
            case 2:result="省份";break;
            case 3:result="城市/城镇";break;
        }
        return result;
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.text_2);
        }
    }
}
