package com.led.scroller.lite.view;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.led.scroller.lite.Content;
import com.led.scroller.lite.R;

public class CircleChoseLinearLayout extends LinearLayout implements View.OnClickListener {

    private ImageView iv_chose_1;
    private ImageView iv_chose_2;
    private ImageView iv_chose_3;
    private ImageView iv_chose_4;
    private ImageView iv_chose_5;
    private ImageView iv_chose_6;
    private int choseColor;
    private CallbackColor callbackColor;

    public CircleChoseLinearLayout(Context context) {
        this(context,null,0);
    }

    public CircleChoseLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleChoseLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.circle_chose_linear_layout,this,true);

        iv_chose_1 = findViewById(R.id.iv_chose_1);
        iv_chose_2 = findViewById(R.id.iv_chose_2);
        iv_chose_3 = findViewById(R.id.iv_chose_3);
        iv_chose_4 = findViewById(R.id.iv_chose_4);
        iv_chose_5 = findViewById(R.id.iv_chose_5);
        iv_chose_6 = findViewById(R.id.iv_chose_6);

        iv_chose_1.setOnClickListener(this);
        iv_chose_2.setOnClickListener(this);
        iv_chose_3.setOnClickListener(this);
        iv_chose_4.setOnClickListener(this);
        iv_chose_5.setOnClickListener(this);
        iv_chose_6.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {

        setColorBg(view.getId());

        /**颜色选择回掉*/
        if (callbackColor!=null){
            callbackColor.callbackColor(choseColor);
        }
    }

    public void setColorBg(int id){
        switch (id){
            case R.id.iv_chose_1:
                iv_chose_1.setImageResource(R.drawable.color_select_bg);
                iv_chose_2.setImageResource(0);
                iv_chose_3.setImageResource(0);
                iv_chose_4.setImageResource(0);
                iv_chose_5.setImageResource(0);
                iv_chose_6.setImageResource(0);
                choseColor = Content.fontColorMap[0];
                break;
            case R.id.iv_chose_2:
                iv_chose_1.setImageResource(0);
                iv_chose_2.setImageResource(R.drawable.color_select_bg);
                iv_chose_3.setImageResource(0);
                iv_chose_4.setImageResource(0);
                iv_chose_5.setImageResource(0);
                iv_chose_6.setImageResource(0);
                choseColor = Content.fontColorMap[1];
                break;
            case R.id.iv_chose_3:
                iv_chose_1.setImageResource(0);
                iv_chose_2.setImageResource(0);
                iv_chose_3.setImageResource(R.drawable.color_select_bg);
                iv_chose_4.setImageResource(0);
                iv_chose_5.setImageResource(0);
                iv_chose_6.setImageResource(0);
                choseColor = Content.fontColorMap[2];
                break;
            case R.id.iv_chose_4:
                iv_chose_1.setImageResource(0);
                iv_chose_2.setImageResource(0);
                iv_chose_3.setImageResource(0);
                iv_chose_4.setImageResource(R.drawable.color_select_bg);
                iv_chose_5.setImageResource(0);
                iv_chose_6.setImageResource(0);
                choseColor = Content.fontColorMap[3];
                break;
            case R.id.iv_chose_5:
                iv_chose_1.setImageResource(0);
                iv_chose_2.setImageResource(0);
                iv_chose_3.setImageResource(0);
                iv_chose_4.setImageResource(0);
                iv_chose_5.setImageResource(R.drawable.color_select_bg);
                iv_chose_6.setImageResource(0);
                choseColor = Content.fontColorMap[4];
                break;
            case R.id.iv_chose_6:
                iv_chose_1.setImageResource(0);
                iv_chose_2.setImageResource(0);
                iv_chose_3.setImageResource(0);
                iv_chose_4.setImageResource(0);
                iv_chose_5.setImageResource(0);
                iv_chose_6.setImageResource(R.drawable.color_select_bg);
                choseColor = Content.fontColorMap[5];
                break;
            default:
                iv_chose_1.setImageResource(0);
                iv_chose_2.setImageResource(0);
                iv_chose_3.setImageResource(0);
                iv_chose_4.setImageResource(0);
                iv_chose_5.setImageResource(0);
                iv_chose_6.setImageResource(0);
                choseColor = 0;
        }
    }

    public interface CallbackColor{
        public void callbackColor(int color);
    }

    public void setCallbackColor(CallbackColor callbackColor){

        this.callbackColor = callbackColor;
    }
}
