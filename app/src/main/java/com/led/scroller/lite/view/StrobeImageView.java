package com.led.scroller.lite.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.led.scroller.lite.Content;

import java.util.Random;

public class StrobeImageView extends ImageView {

    private ValueAnimator valueAnimator;
    private boolean tag;
    private int background;

    public StrobeImageView(Context context) {
        this(context,null,0);
    }

    public StrobeImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StrobeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

     private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Random random = new Random();
            int i = random.nextInt(6);
//            Log.e("tag","i:"+i);
            setBackgroundResource(Content.colors[i]);

            if (!tag) {
                this.sendEmptyMessageDelayed(0, 50);
            }else{
                setBackgroundColor(background);
            }
        }
    };

    public void startStrobe(){

//        handler.removeCallbacks(null);
        tag = false;
        handler.sendEmptyMessageDelayed(0,0);
    }

    public void stopStrobe(int background){
        this.background = background;

        tag = true;
        handler.removeCallbacks(null);
        setBackgroundColor(background);
    }

    public void stopStrobe(){
        tag = true;
        handler.removeCallbacks(null);

    }


}
