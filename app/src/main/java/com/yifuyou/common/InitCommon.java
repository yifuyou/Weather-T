package com.yifuyou.common;

import android.content.Context;

public class InitCommon {
    public static void init(Context context){
        ToastUtil.init(context);
        Logcat.debugging=true;
    }


}
