package com.yifuyou.weather_t.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yifuyou.weather_t.adapters.CityChooseRecyclerAdapter;
import com.yifuyou.weather_t.commom.CityResource;
import com.yifuyou.weather_t.databinding.CitiesManageBinding;

import java.util.ArrayList;

public class CitiesManageActivity extends AppCompatActivity {
    private CitiesManageBinding binding;
    private CityResource citiesRes;
    private CityChooseRecyclerAdapter adapter;


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

                }
        );


    }

    @SuppressLint("SetTextI18n")
    void adapterInit(){
        adapter=new CityChooseRecyclerAdapter(citiesRes.parseNodes());
        adapter.onItemClickListener(map -> {
            binding.chooseResult.setText(map.get("city0")+" "+map.get("city1")+" "+map.get("city2"));
        });

    }

}
