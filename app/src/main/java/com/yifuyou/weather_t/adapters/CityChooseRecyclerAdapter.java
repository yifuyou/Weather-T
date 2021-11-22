package com.yifuyou.weather_t.adapters;

import android.annotation.SuppressLint;
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
    static int choosePosition=0;

    public CityChooseRecyclerAdapter(ArrayList<CityNode> nodeList,ClickListener listener) {
        this.listener=listener;
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

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Holder result=(Holder) holder;
        result.textView.setText(arrayList.get(position).city);
        switch (getItemViewType(position)){
            case 0:
                result.recyclerView.setLayoutManager(new LinearLayoutManager( holder.itemView.getContext()));
                adapter=new CityChooseRecyclerAdapter(arrayList.get(position).cityNodes,map -> {
                    chooseMap.put("city2",map.get("city2"));
                    chooseMap.put("city1",map.get("city1"));
                    chooseMap.put("city0",arrayList.get(position).city);
                    listener.onClick(chooseMap);
                });
                result.recyclerView.setAdapter(adapter);
                result.recyclerView.setVisibility(result.show?View.VISIBLE:View.GONE);

                result.textView.setOnClickListener(v -> {
                    System.out.println("click 0 "+",  "+choosePosition);

                        result.show=!(result.show);
                        result.recyclerView.setVisibility(result.show?View.VISIBLE:View.GONE);
                        choosePosition=position;

                });
                break;
            case 1:
                result.recyclerView.setLayoutManager(new LinearLayoutManager( holder.itemView.getContext()));
                adapter=new CityChooseRecyclerAdapter(arrayList.get(position).cityNodes, map -> {
                    chooseMap.put("city2",map.get("city2"));
                    chooseMap.put("city1",arrayList.get(position).city);
                    listener.onClick(chooseMap);
                });
                result.recyclerView.setAdapter(adapter);
                result.recyclerView.setVisibility(result.show?View.VISIBLE:View.GONE);
                result.textView.setOnClickListener(v -> {
                    System.out.println("click 1 "+",  "+choosePosition);

                        result.show=!(result.show);
                        result.recyclerView.setVisibility(result.show?View.VISIBLE:View.GONE);
                        choosePosition=position;

                });
                break;
            case 2:
                if(listener!=null){
                    result.textView.setOnClickListener(v -> {
                        chooseMap.put("city2",arrayList.get(position).city);
                        System.out.println("click2 no item"+position+",  "+choosePosition);
                        listener.onClick(chooseMap);
                    });
                }else{
                    result.textView.setOnClickListener(v -> {
                        System.out.println("click2 "+position+",  "+choosePosition);
                    });

                }

            default:break;

        }

        System.out.println("onBindViewHolder " +position);
/*        if(getItemViewType(position)!=2){
            result.recyclerView.setLayoutManager(new LinearLayoutManager( holder.itemView.getContext()));
            adapter=new CityChooseRecyclerAdapter(arrayList.get(position).cityNodes);
            adapter.onItemClickListener(map -> {
                chooseMap.put(getItemViewType(position)==0?"city0":"city1",arrayList.get(position).city);
            });
            result.recyclerView.setAdapter(adapter);

            result.recyclerView.setVisibility(result.show?View.VISIBLE:View.GONE);
            result.textView.setOnClickListener((v -> {
                System.out.println("click "+position+",  "+choosePosition);
                if (choosePosition!=position) {
                    result.show=!(result.show);
                    result.recyclerView.setVisibility(result.show?View.VISIBLE:View.GONE);
                    choosePosition=position;
                }
            }));
        }else{
            if(listener!=null){
                result.textView.setOnClickListener(v -> {
                    chooseMap.put("city2",arrayList.get(position).city);
                    System.out.println("click2 no item"+position+",  "+choosePosition);
                    listener.onClick(chooseMap);
                });
            }else{
                result.textView.setOnClickListener(v -> {
                    System.out.println("click2 "+position+",  "+choosePosition);
                });

            }
        }*/
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
            show=false;
            recyclerView.setVisibility(View.GONE);
        }
    }

}

