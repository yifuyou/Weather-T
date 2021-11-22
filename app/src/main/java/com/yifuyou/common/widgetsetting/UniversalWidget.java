package com.yifuyou.common.widgetsetting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class UniversalWidget extends WidgetInterface{
    public UniversalWidget(Context context) {
        this(context,null);
    }

    public UniversalWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UniversalWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public UniversalWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public View addExtend() {
        return null;
    }
}
