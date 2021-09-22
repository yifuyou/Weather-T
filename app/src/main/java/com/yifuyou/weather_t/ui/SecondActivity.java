package com.yifuyou.weather_t.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yifuyou.weather_t.databinding.ActivityTempBinding;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTempBinding binding=ActivityTempBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.gotoFirst.setOnClickListener((view)->{
            startActivity(new Intent(SecondActivity.this, MainActivity.class));


        });
    }
}
