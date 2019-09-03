package com.led.scroller.lite;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;

public class Utils {
    public static void saveBoolean(Context context,String key, boolean value){
        SharedPreferences led = context.getSharedPreferences("led", 0);
        SharedPreferences.Editor edit = led.edit();
        edit.putBoolean(key,value);
        edit.commit();
    }

    public static boolean getBoolean(Context context , String key,boolean defValue){
        SharedPreferences led = context.getSharedPreferences("led", 0);
        boolean aBoolean = led.getBoolean(key, defValue);
        return aBoolean;
    }

    public static void setState(Activity activity){
        Window window = activity.getWindow();
//这一步最好要做，因为如果这两个flag没有清除的话下面没有生效
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//设置布局能够延伸到状态栏(StatusBar)和导航栏(NavigationBar)里面
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//设置状态栏(StatusBar)颜色透明
        window.setStatusBarColor(activity.getResources().getColor(R.color.his_bg));
//设置导航栏(NavigationBar)颜色透明
//        window.setNavigationBarColor(Color.TRANSPARENT);
    }
}
