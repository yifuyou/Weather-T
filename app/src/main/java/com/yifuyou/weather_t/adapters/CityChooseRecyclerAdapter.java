package com.yifuyou.weather_t.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yifuyou.weather_t.R;
import com.yifuyou.weather_t.commom.CityNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CityChooseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<CityNode> arrayList;
    static Map<String,String> chooseMap;
    ClickListener listener;
    CityChooseRecyclerAdapter adapter;
    int choosePosition=0;

    public CityChooseRecyclerAdapter(ArrayList<CityNode> nodeList) {
        this.arrayList = nodeList;
        System.out.println();
        chooseMap=new HashMap<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_choose_layout, parent, false);

        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Holder result=(Holder) holder;

        if(getItemViewType(position)!=2){
            result.textView.setText(arrayList.get(position).city);
            result.recyclerView.setLayoutManager(new LinearLayoutManager(((Holder) holder).textView.getContext()));
            adapter=new CityChooseRecyclerAdapter(arrayList.get(position).cityNodes);
            adapter.onItemClickListener(map -> {
                chooseMap.put(getItemViewType(position)==0?"city0":"city1",arrayList.get(position).city);
            });
            result.recyclerView.setAdapter(adapter);
            result.textView.setOnClickListener((v -> {
                System.out.println("click "+position+",  "+choosePosition);
                if (choosePosition!=position) {
                    result.show=!(result.show);
                    result.recyclerView.setVisibility(result.show?View.VISIBLE:View.GONE);
                }
            }));
        }else{
            result.textView.setText(arrayList.get(position).city);
            if(listener!=null){
                result.itemView.setOnClickListener(v -> {
                    chooseMap.put("city2",arrayList.get(position).city);
                    listener.onClick(chooseMap);
                });
            }else{
                result.textView.setOnClickListener(v -> {
                    System.out.println("click2 "+position+",  "+choosePosition);
                });

            }
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        Holder viewHolder=(Holder)holder;
        viewHolder.recyclerView.setVisibility(View.GONE);
        super.onViewRecycled(holder);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).cityNodes != null) {
            if(arrayList.get(position).cityNodes.get(0).cityNodes!=null){
                return 0;
            }
            return 1;
        }
        return 2;
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView recyclerView;
        boolean show;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_2);
            recyclerView = itemView.findViewById(R.id.recyc_2);
            show=true;
        }
    }

    public void onItemClickListener(ClickListener listener){
        this.listener=listener;
    }
    public interface ClickListener{
        void onClick(Map<String,String> map);
    }
}

