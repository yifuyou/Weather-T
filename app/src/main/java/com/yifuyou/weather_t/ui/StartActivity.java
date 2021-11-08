package com.yifuyou.weather_t.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yifuyou.weather_t.R;
import com.yifuyou.weather_t.commom.SharedPUtil;
import com.yifuyou.weather_t.databinding.StartLayoutBinding;

import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class StartActivity extends AppCompatActivity {


    long wholeTime=0L;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.start_layout);
        wholeTime=System.currentTimeMillis();
        ImageView imageView=findViewById(R.id.start_sun);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(imageView,"rotation",-15f,15f,-15f)
                .setDuration(3000L);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(System.currentTimeMillis()-wholeTime >5000L){
                    goToMain();
                }
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                valueAnimator.reverse();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animation.cancel();
                animation.pause();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.start();

    }

    private void  goToMain(){
        ArrayList<String> cities = SharedPUtil.getCities(getApplicationContext());
        String city = SharedPUtil.getCity(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        intent.putExtra("city",city);
        intent.putExtra("cities",cities);
        startActivity(intent);
        finish();
    }


}
