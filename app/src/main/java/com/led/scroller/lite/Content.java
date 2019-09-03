package com.led.scroller.lite;

import android.graphics.Color;

public class Content {

    public static Float[] fontSizeMap = new Float[]{50f, 90f, 120f, 150f, 180f};

    public static int matchSizeView(float size){
        int id = 0;
        if (size == 50f){
            id = R.id.text_size_small;
        }else if (size == 90f){
            id = R.id.text_size_medium;
        }else if (size == 120f){
            id = R.id.text_size_large;
        }else if (size == 150f){
            id = R.id.text_size_extra;
        }else if (size == 180f){
            id = R.id.text_size_very;
        }
        return id;
    }

    public static double getScrollSpeed(int speedProgress) {
        return ((double) speedProgress) / 60;
    }

    public static int[] fontColorMap =new int[]{Color.WHITE,Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE,Color.BLACK};

    public static int matchColorView(int color){
        int id = 0;
        switch (color){
            case Color.WHITE:
                id = R.id.iv_chose_1;
                break;
            case Color.RED:
                id = R.id.iv_chose_2;
                break;
            case Color.YELLOW:
                id = R.id.iv_chose_3;
                break;
            case Color.GREEN:
                id = R.id.iv_chose_4;
                break;
            case Color.BLUE:
                id = R.id.iv_chose_5;
                break;
            case Color.BLACK:
                id = R.id.iv_chose_6;
                break;
        }
        return id;
    }

    public static int[] colors = {R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5,R.color.color6,R.color.color7};
}
