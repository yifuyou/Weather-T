package com.yifuyou.weather_t.adapters;

import android.app.ActionBar;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yifuyou.weather_t.R;
import com.yifuyou.weather_t.commom.CityNode;

import java.util.ArrayList;
import java.util.HashMap;


public class CityPickerBuilder {
    private RecyclerView[] recyclerViews;
    private CityPickerAdapter[] adapters;
    private int[] positions;
    private LinearLayout layout;
    private ArrayList<CityNode> date;
    private HashMap<String,String> cityMap;
    private ClickListener itemClickListener;

    public CityPickerBuilder(Context context){
        cityMap=new HashMap<>();
        layout= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.city_picker_layout,null,false);
        adapters=new CityPickerAdapter[3];
/*        layout.getLayoutParams().width= LinearLayout.LayoutParams.MATCH_PARENT;
        layout.getLayoutParams().height= LinearLayout.LayoutParams.MATCH_PARENT;*/

        positions=new int[3];

        recyclerViews=new RecyclerView[3];
        for (int i = 0; i < recyclerViews.length; i++) {
            recyclerViews[i]=new RecyclerView(context);
            recyclerViews[i].setLayoutManager(new LinearLayoutManager(context));
            recyclerViews[i].addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
        }
    }

    public CityPickerBuilder setDate(ArrayList<CityNode> arrayList){
        if(arrayList!=null){
            date=arrayList;
        }else {
            date=new ArrayList<>();
        }
        initAdapter();
        return this;
    }

    public void setOnItemClickListen(ClickListener clickListen){
        this.itemClickListener=clickListen;
    }

    private void initAdapter(){
        adapters[0]=new CityPickerAdapter(date,(position,view)-> {
            positions[0]=position;
            positions[1]=0;
            positions[2]=0;
            adapters[1].setDate(date.get(position).cityNodes);
            adapters[2].setDate(date.get(position).cityNodes.get(0).cityNodes);
            itemClick();
        });
        adapters[1]=new CityPickerAdapter((date.get(0).cityNodes),(position,view)->{
            positions[1]=position;
            positions[2]=0;
            adapters[2].setDate(date.get(positions[0]).cityNodes.get(position).cityNodes);
            itemClick();
        });
        adapters[2]=new CityPickerAdapter((date.get(0).cityNodes.get(0).cityNodes),(position,view)->{
            positions[2]=position;
            itemClick();
        });

    }

    private void itemClick(){
        if(itemClickListener!=null){
            cityMap.put("city0", date.get(positions[0]).city);
            cityMap.put("city1",date.get(positions[0]).cityNodes.get(positions[1]).city);
            cityMap.put("city2",date.get(positions[0]).cityNodes.get(positions[1]).cityNodes.get(positions[2]).city);
            itemClickListener.onClick(cityMap);
        }
    }

    public LinearLayout getView(){
        for (int i = 0; i < recyclerViews.length; i++) {
            recyclerViews[i].setAdapter(adapters[i]);

            layout.addView(recyclerViews[i]);
/*            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)layout.getLayoutParams();
//            layoutParams.width= LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.height= LinearLayout.LayoutParams.MATCH_PARENT;
            layoutParams.weight=1;
            recyclerViews[i].setLayoutParams(layoutParams);*/
        }

        return layout;
    }

    public interface OnItemClick{
        void click(int position, View view);
    }

}
