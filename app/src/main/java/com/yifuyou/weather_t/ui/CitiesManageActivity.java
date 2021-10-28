package com.yifuyou.weather_t.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yifuyou.weather_t.adapters.CityChooseRecyclerAdapter;
import com.yifuyou.weather_t.commom.CityNode;
import com.yifuyou.weather_t.commom.CityResource;
import com.yifuyou.weather_t.databinding.CitiesManageBinding;

import java.util.ArrayList;

public class CitiesManageActivity extends AppCompatActivity {
    private CitiesManageBinding binding;
    private CityResource citiesRes;
    private CityChooseRecyclerAdapter adapter;

    private String city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CityResource.initFinish()) {
            System.out.println("============unready==========");
        }
        citiesRes = CityResource.getInstance(getApplicationContext());

        binding = CitiesManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyc1.setLayoutManager(new LinearLayoutManager(this));
        binding.recyc1.post(()->{

            adapterInit();
            binding.recyc1.setAdapter(adapter);
        });


        binding.buttonChoose.setOnClickListener(
                (view) -> {
                    Intent data=new Intent();
                    data.putExtra("newCity",city);
                    Log.i("TAG", "onCreate: "+city);
                    setResult(0x011,data);
                    finish();
                }
        );


    }

    @SuppressLint("SetTextI18n")
    void adapterInit(){
        if(!CityResource.initFinish()){
            ArrayList<CityNode> arrayList=new ArrayList<>();
            ArrayList<CityNode> list=new ArrayList<>();
            ArrayList<CityNode> itemList=new ArrayList<>();
            itemList.add(new CityNode("天河",null));
            itemList.add(new CityNode("白云",null));
            list.add(new CityNode("广州",itemList));
            arrayList.add(new CityNode("广东",list));
            adapter=new CityChooseRecyclerAdapter(arrayList,map -> {
                binding.chooseResult.setText(map.get("city0")+" - "+map.get("city1")+" - "+map.get("city2"));
                city=map.get("city2");
            });
        }else{
            adapter=new CityChooseRecyclerAdapter(citiesRes.parseNodes(),map -> {
            binding.chooseResult.setText(map.get("city0")+" - "+map.get("city1")+" - "+map.get("city2"));
            city=map.get("city2");
        });
    }
    }

}
