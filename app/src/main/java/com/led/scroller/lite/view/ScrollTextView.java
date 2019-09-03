package com.led.scroller.lite.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class ScrollTextView extends View {

    private Paint textPaint;
    private int color = Color.WHITE;
    private String content = "奔跑吧！野驴";
    private float size = 80;
    private float textX = 0;
    private float textLenth;
    private float textSpeed = 10;
    private int widthPixels;
    private boolean isStop;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //改变文本的x值，让其滚动
            if (textX <= -textLenth){
                //当文本的x坐标小于-自身长度，说明文本已经完全消失，此时需要将文本x坐标置为屏幕右端
                textX = widthPixels;
            }
            textX -= textSpeed;
            invalidate();

            if (!isStop) {
                this.sendEmptyMessageDelayed(0, 10);
            }
        }
    };

    public ScrollTextView(Context context) {
        this(context,null,0);
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        widthPixels = context.getResources().getDisplayMetrics().widthPixels;

        initView();
    }

    private void initView() {

        //文字笔
        textPaint = new Paint();
    }

    public void startScroll(){
        handler.sendEmptyMessageDelayed(0,500);
    }

    public void stopScroll(){
        isStop = true;
        handler.removeCallbacks(null);
    }

    public void setSize(float size){
        this.size = size;
    }

    public void setColor(int color){
        this.color = color;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setTextSpeed(float speed){
        this.textSpeed = speed;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (TextUtils.isEmpty(content)){
            return;
        }

        //设置文本颜色
        textPaint.setColor(color);
        //设置文本大小
        textPaint.setTextSize(size);
        //获取文本长度
        textLenth = textPaint.measureText(content);

        canvas.drawText(content,textX,getHeight()/2,textPaint);
    }
}
