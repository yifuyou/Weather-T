package com.yifuyou.weather_t.ui;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yifuyou.weather_t.R;
import com.yifuyou.weather_t.adapters.CityChooseRecyclerAdapter;
import com.yifuyou.weather_t.commom.CityNode;
import com.yifuyou.weather_t.commom.CityResource;
import com.yifuyou.weather_t.commom.SharedPUtil;
import com.yifuyou.weather_t.databinding.CitiesManageBinding;

import java.util.ArrayList;

public class CitiesManageActivity extends AppCompatActivity {
    private CitiesManageBinding binding;
    private CityResource citiesRes;
    private CityChooseRecyclerAdapter adapter;
    private static final String tagPreString="cities-";
    private ArrayList<String> cities;
    private String city;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CityResource.initFinish()) {
            System.out.println("============unready==========");
        }

        binding = CitiesManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cities= SharedPUtil.getCities(this);
        binding.citiesClickLayout.removeAllViews();
        for (String c : cities) {
            TextView view = new TextView(this);
            view.setText(c.trim());
            view.setGravity(Gravity.CENTER);

            view.setBackgroundColor(R.color.teal_700);
            view.setTag(tagPreString+c);
            view.setOnClickListener(new ClickListener());
            view.setPadding(0,10,0,10);
            System.out.println("--textview"+c);
            binding.citiesClickLayout.addView(view);
            GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) view.getLayoutParams();
            layoutParams.columnSpec=GridLayout.spec(GridLayout.UNDEFINED,1f);
            layoutParams.topMargin=20;
            layoutParams.leftMargin=10;
            layoutParams.rightMargin=10;
        }
        citiesRes = CityResource.getInstance(getApplicationContext());


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

    class ClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String city="";
            try{
                Object tag = v.getTag();
                assert tag instanceof String;
                city=((String) tag).substring(tagPreString.length());
            }catch (Exception e){
                e.printStackTrace();
            }
            AlertDialog alertDialog = new AlertDialog.Builder(CitiesManageActivity.this)
                    .setMessage("确定不再关注 "+city+"天气 ？")
                    .setCancelable(true)
                    .setNegativeButton("取消" ,(dialog,w)->{})
                    .setPositiveButton("不再关注",(dialog,w)->{ })
                    .create();
            alertDialog.show();
        }
    }


}
