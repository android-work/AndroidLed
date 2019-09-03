package com.led.scroller.lite.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.led.scroller.lite.Content;
import com.led.scroller.lite.R;
import com.led.scroller.lite.bean.ScrollContentBean;
import com.led.scroller.lite.db.DBOperation;
import com.led.scroller.lite.view.ChoseColorSpeedPopupWindow;
import com.led.scroller.lite.view.ScrollTextView;
import com.led.scroller.lite.view.StrobeImageView;

import java.io.Serializable;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private StrobeImageView main_iv_bg;
    private ScrollTextView main_scroll_text;
    private ChoseColorSpeedPopupWindow choseColorSpeedPopupWindow;
    private int bg;
    private int color;
    private String content;
    private String createTime;
    private float size;
    private float speed;
    private int screenBrightness;
    private int screenMode;
    private ScrollContentBean scrollContentBean;
    private boolean b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏状态栏
        Window window = getWindow();
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag=WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);


        //设置屏幕亮度最大
//        b = checkWriteSettingPermission(this, 0);

        Intent intent = getIntent();
        if (intent==null){
            return;
        }
        Bundle bundle = intent.getBundleExtra("bundle");
        scrollContentBean = (ScrollContentBean) bundle.getSerializable("obj");

        setContentView(R.layout.activity_main);

        main_iv_bg = findViewById(R.id.main_iv_bg);
        main_scroll_text = findViewById(R.id.main_scroll_text);


        bg = scrollContentBean.getBg();
        color = scrollContentBean.getColor();
        content = scrollContentBean.getContent();
        createTime = scrollContentBean.getCreateTime();
        size = scrollContentBean.getSize();
        speed = scrollContentBean.getSpeed();

        Log.e("tag","bg:"+bg+":color:"+color);

        //初始化led
        startScrollContent(bg,color,content,size,speed);

        main_iv_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choseColorSpeedPopupWindow = new ChoseColorSpeedPopupWindow(MainActivity.this,scrollContentBean);
                choseColorSpeedPopupWindow.show(main_iv_bg);

                setListener();
            }
        });

    }

    /**滚动文本的一些初始化*/
    private void startScrollContent(int bg, int color, String content,float size, float speed) {
        main_scroll_text.setColor(color);
        main_scroll_text.setContent(content);
        main_scroll_text.setSize(size);
        main_scroll_text.setTextSpeed(speed);
        isStrobeBg(bg);
        main_scroll_text.startScroll();
    }

    /**滚动背景为爆闪还是纯色*/
    private void isStrobeBg(int bg) {

        if (bg == 0 || bg == color){
            main_iv_bg.startStrobe();
        }else{
            main_iv_bg.setBackgroundColor(bg);
        }
    }

    private void setListener() {
        if (choseColorSpeedPopupWindow==null){
            return;
        }

        /**滚动速度毁掉*/
        choseColorSpeedPopupWindow.setSpeedUiCallback(new ChoseColorSpeedPopupWindow.SpeedUiCallback() {
            @Override
            public void speedUiCallback(float speed) {
                Log.e("tag","滚动速度毁掉："+speed);
                main_scroll_text.setTextSpeed(speed / 2);
                scrollContentBean.setSpeed(speed / 2);
            }
        });

        /**文本字体大小毁掉*/
        choseColorSpeedPopupWindow.setSizeUiCallback(new ChoseColorSpeedPopupWindow.SizeUiCallback() {
            @Override
            public void sizeUiCallback(float size) {
                Log.e("tag","文本字体大小毁掉："+size);
                main_scroll_text.setSize(size);
                scrollContentBean.setSize(size);
            }
        });

        /**文本颜色毁掉*/
        choseColorSpeedPopupWindow.setColorUiCallback(new ChoseColorSpeedPopupWindow.ColorUiCallback() {
            @Override
            public void colorUiCallback(int color) {
                Log.e("tag",MainActivity.this.bg+":文本颜色毁掉："+color);
                main_scroll_text.setColor(color);
                scrollContentBean.setColor(color);
                MainActivity.this.color = color;

                //如果当背景色与字体色一致，置为爆闪模式
                if (bg == color){
                    isStrobeBg(0);
                    main_iv_bg.startStrobe();
                }
            }
        });

        /**背景毁掉*/
        choseColorSpeedPopupWindow.setBackgroundUiCallback(new ChoseColorSpeedPopupWindow.BackgroundUiCallback() {

            @Override
            public void backgroundUiCallback(int background) {
                Log.e("tag",MainActivity.this.color+":背景毁掉："+background);
                MainActivity.this.bg = background;
                //如果当背景色与字体色一致，置为爆闪模式
                if (bg == color){
//                    isStrobeBg(0);
                    background = 0;
                    main_iv_bg.startStrobe();
                }else{
                    main_iv_bg.stopStrobe(background);
                }
                scrollContentBean.setBg(background);

            }
        });

        choseColorSpeedPopupWindow.setDisMissPopupwindowListener(new ChoseColorSpeedPopupWindow.DismissPopupwindowListener() {
            @Override
            public void dissmissPopupwindow() {
                Log.e("tag","更新数据库");
                //更新数据库的值
                DBOperation.getInstance().updataDB(scrollContentBean);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        //恢复屏幕亮度
//        if (b)
//            setOriginalBrightness(screenBrightness,screenMode);
    }

    @Override
    protected void onDestroy() {
        main_iv_bg.stopStrobe();
        main_scroll_text.stopScroll();
        choseColorSpeedPopupWindow = null;
        super.onDestroy();

    }

    /**回复原来屏幕亮度*/
    public void setOriginalBrightness(int screenBrightness,int screenMode){
        // 设置屏幕亮度值为原来的
        setScreenBrightness(screenBrightness);
        // 设置当前屏幕亮度的模式 为原来的
        setScreenMode(screenMode);

    }

    /**调节手机亮度最大*/
    public void SetBrightest(){
        try {
            /*
             * 获得当前屏幕亮度的模式
             * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
             * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
             */
            screenMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);

            // 获得当前屏幕亮度值 0--255
            screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);

            // 如果当前的屏幕亮度调节调节模式为自动调节，则改为手动调节屏幕亮度
            if (screenMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                setScreenMode(Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }

            // 设置屏幕亮度值为最大值255
            setScreenBrightness(255.0F);

        } catch (Settings.SettingNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 设置当前屏幕亮度的模式
     * SCREEN_BRIGHTNESS_MODE_AUTOMATIC=1 为自动调节屏幕亮度
     * SCREEN_BRIGHTNESS_MODE_MANUAL=0 为手动调节屏幕亮度
     */
    private void setScreenMode(int value) {
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, value);
    }

    /**
     * 设置当前屏幕亮度值 0--255，并使之生效
     */
    private void setScreenBrightness(float value) {
        Window mWindow = getWindow();
        WindowManager.LayoutParams mParams = mWindow.getAttributes();
        float f = value / 255.0F;
        mParams.screenBrightness = f;
        mWindow.setAttributes(mParams);

        // 保存设置的屏幕亮度值
        Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (int) value);
    }

    /*public static boolean checkWriteSettingPermission(Activity context, int requestCode) {
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context);
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (!permission) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivityForResult(intent, requestCode);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_SETTINGS}, requestCode);
            }
        }
        return permission;
    }*/


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(this)) {
                    SetBrightest();
                }
            }
        }

    }*/
}
