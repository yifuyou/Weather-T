package com.yifuyou.weather_t.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yifuyou.common.SettingObject;
import com.yifuyou.common.SettingWidgetFactory;
import com.yifuyou.common.ToastUtil;
import com.yifuyou.weather_t.databinding.ActivityTempBinding;


public class SettingActivity extends AppCompatActivity {

    public static final SettingObject[] map = {
            new SettingObject("更多", (v) -> {
                ToastUtil.Factory().setText("点击- 更多 ", Toast.LENGTH_SHORT).show();
            }),
            new SettingObject("通知", false, (v) -> {
            }),
            new SettingObject("广告", true, (v) -> {
                ToastUtil.Factory().setText("点击- 广告 ", Toast.LENGTH_SHORT).show();
            }),
            new SettingObject("状态开关", false, (v) -> {
            }),
            new SettingObject("状态开关", true, (v) -> {
            }),
            new SettingObject("个人", (v) -> {
            }),
            new SettingObject("高级", (v) -> {
            }),
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTempBinding binding = ActivityTempBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        SettingWidgetFactory factory = SettingWidgetFactory.of(this);
        for (SettingObject object : map) {
            binding.settingList.addView(factory.getWidget(object), params);
        }

    }
}
