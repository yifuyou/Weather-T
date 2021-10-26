package com.yifuyou.weather_t.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.yifuyou.weather_t.CitiesWeatherViewPagerAdapter;
import com.yifuyou.weather_t.R;
import com.yifuyou.weather_t.ViewPager2Adapter;
import com.yifuyou.weather_t.commom.EventMainUI;
import com.yifuyou.weather_t.commom.SharedPUtil;
import com.yifuyou.weather_t.databinding.ActivityMainBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<String> cities;
    private String city;
    private ViewPager.OnPageChangeListener pageChangeListener;
    private CitiesWeatherViewPagerAdapter adapter;
    private ViewPager2Adapter adapter2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate");
        city=getIntent().getStringExtra("city");
        cities=getIntent().getStringArrayListExtra("cities");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        setSupportActionBar(binding.toolbar);

        //title 位置需要自定义，所以必须隐藏默认title
        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);


//        adapter = new CitiesWeatherViewPagerAdapter(getSupportFragmentManager(),  cities);
//        binding.weatherViewPager.setAdapter( adapter);
        adapter2 = new ViewPager2Adapter(getSupportFragmentManager(),getLifecycle(),cities);
        binding.weatherViewPager.setAdapter(adapter2);
        binding.weatherViewPager.setCurrentItem(cities.indexOf(city));


        pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.mainLayoutCity.setText(cities.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
//        binding.weatherViewPager.addOnPageChangeListener(pageChangeListener);
        binding.weatherViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.mainLayoutCity.setText(cities.get(position));

            }
        });
        binding.weatherViewPager.setOffscreenPageLimit(1);
        binding.mainLayoutCity.setText(city);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        SharedPUtil.setCity(this,city);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        log("onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, CitiesManageActivity.class));
            return true;
        }else if (id == R.id.action_addPlace) {
            /*int index=cities.indexOf(city);
            city="广州";
            binding.mainLayoutCity.setText(city);
//            adapter.notifyCitiesChange(city,CitiesWeatherViewPagerAdapter.TYPE_ADD);

            if(cities.contains(city)){
                cities=new ArrayList<>();
                cities.add(0,"上海");
                cities.add(1,"西安");
                cities.add(2,"南昌");

            }else{
                cities=new ArrayList<>();
                cities.add(0,city);
                cities.add(1,"深圳");
            }
            log(cities.toString());
            adapter2.notifyCitiesChange(cities);
            binding.weatherViewPager.setCurrentItem(cities.indexOf(city));*/
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Subscribe
    public void eventListener(EventMainUI e) throws JSONException {
        System.out.println(e);
//        EventBus.getDefault().post(e);
    }

    public String getCity(){
        return this.city;
    }
    public ArrayList<String> getCities(){
        return this.cities;
    }


    @Override
    public int checkPermission(String permission, int pid, int uid) {
        if(permission==null){
            permission= Manifest.permission.INTERNET;
        }
        return super.checkPermission(permission, pid, uid);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void log(String s){
        Log.i(s, "-----log----");
    }
}