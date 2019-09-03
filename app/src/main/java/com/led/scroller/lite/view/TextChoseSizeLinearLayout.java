package com.led.scroller.lite.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.led.scroller.lite.Content;
import com.led.scroller.lite.R;

public class TextChoseSizeLinearLayout extends LinearLayout implements View.OnClickListener {

    private ImageView text_size_small;
    private ImageView text_size_medium;
    private ImageView text_size_large;
    private ImageView text_size_extra;
    private ImageView text_size_very;
    private TextSizeCallback textSizeCallback;
    private float size;

    public TextChoseSizeLinearLayout(Context context) {
        this(context,null,0);
    }

    public TextChoseSizeLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextChoseSizeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.text_chose_size_layout,this,true);

        initView();
    }

    private void initView() {
        text_size_small = findViewById(R.id.text_size_small);
        text_size_medium = findViewById(R.id.text_size_medium);
        text_size_large = findViewById(R.id.text_size_large);
        text_size_extra = findViewById(R.id.text_size_extra);
        text_size_very = findViewById(R.id.text_size_very);

        text_size_small.setOnClickListener(this);
        text_size_medium.setOnClickListener(this);
        text_size_large.setOnClickListener(this);
        text_size_extra.setOnClickListener(this);
        text_size_very.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        setTextSizeBg(view.getId());

        if (textSizeCallback!=null){
            textSizeCallback.textSizeCallback(size);
        }
    }

    public void setTextSizeBg(int id){
        switch (id){
            case R.id.text_size_small:
                text_size_small.setBackgroundResource(R.drawable.text_size_select_bg);
                text_size_medium.setBackgroundResource(0);
                text_size_large.setBackgroundResource(0);
                text_size_extra.setBackgroundResource(0);
                text_size_very.setBackgroundResource(0);
                size = Content.fontSizeMap[0];
                break;
            case R.id.text_size_medium:
                text_size_small.setBackgroundResource(0);
                text_size_medium.setBackgroundResource(R.drawable.text_size_select_bg);
                text_size_large.setBackgroundResource(0);
                text_size_extra.setBackgroundResource(0);
                text_size_very.setBackgroundResource(0);
                size = Content.fontSizeMap[1];
                break;
            case R.id.text_size_large:
                text_size_small.setBackgroundResource(0);
                text_size_medium.setBackgroundResource(0);
                text_size_large.setBackgroundResource(R.drawable.text_size_select_bg);
                text_size_extra.setBackgroundResource(0);
                text_size_very.setBackgroundResource(0);
                size = Content.fontSizeMap[2];
                break;
            case R.id.text_size_extra:
                text_size_small.setBackgroundResource(0);
                text_size_medium.setBackgroundResource(0);
                text_size_large.setBackgroundResource(0);
                text_size_extra.setBackgroundResource(R.drawable.text_size_select_bg);
                text_size_very.setBackgroundResource(0);
                size = Content.fontSizeMap[3];
                break;
            case R.id.text_size_very:
                text_size_small.setBackgroundResource(0);
                text_size_medium.setBackgroundResource(0);
                text_size_large.setBackgroundResource(0);
                text_size_extra.setBackgroundResource(0);
                text_size_very.setBackgroundResource(R.drawable.text_size_select_bg);
                size = Content.fontSizeMap[4];
                break;
        }
    }

    public interface TextSizeCallback{
        public void textSizeCallback(float size);
    }

    public void setTextSizeCallback(TextSizeCallback textSizeCallback){

        this.textSizeCallback = textSizeCallback;
    }
}
