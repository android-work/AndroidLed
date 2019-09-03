package com.led.scroller.lite.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import com.led.scroller.lite.Content;
import com.led.scroller.lite.R;
import com.led.scroller.lite.bean.ScrollContentBean;

public class ChoseColorSpeedPopupWindow extends PopupWindow {

    private Context context;
    private final View inflate;
    private final SeekBar speed_seekbar;
    private final TextChoseSizeLinearLayout text_size_chose;
    private final CircleChoseLinearLayout tv_color_chose;
    private final CircleChoseLinearLayout bg_color_chose;
    private SpeedUiCallback speedUiCallback;
    private SizeUiCallback sizeUiCallback;
    private ColorUiCallback colorUiCallback;
    private BackgroundUiCallback backgroundUiCallback;
    private DismissPopupwindowListener disMissPopupwindowListener;

    public ChoseColorSpeedPopupWindow(Context context, ScrollContentBean scrollContentBean){

        this.context = context;

        //加载布局
        inflate = LayoutInflater.from(context).inflate(R.layout.popupwidow_layout, null);

        speed_seekbar = inflate.findViewById(R.id.speed_seekbar);
        text_size_chose = inflate.findViewById(R.id.text_size_chose);
        tv_color_chose = inflate.findViewById(R.id.tv_color_chose);
        bg_color_chose = inflate.findViewById(R.id.bg_color_chose);

        initView();

        setListener();

        //对popupwindow进行初始化
        initScroll(scrollContentBean);

    }

    /**初始化弹窗控件*/
    private void initScroll( ScrollContentBean scrollContentBean) {
        speed_seekbar.setProgress((int) (scrollContentBean.getSpeed()*2));
        text_size_chose.setTextSizeBg(Content.matchSizeView(scrollContentBean.getSize()));
        tv_color_chose.setColorBg(Content.matchColorView(scrollContentBean.getColor()));
        bg_color_chose.setColorBg(Content.matchColorView(scrollContentBean.getBg()));

    }

    private void setListener() {

        /**seekbar改变毁掉*/
        speed_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                Log.e("tag","i:::"+i);
                if (speedUiCallback!=null){
                    Log.e("tag","i:::"+i);
                    speedUiCallback.speedUiCallback(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /**大小毁掉*/
        text_size_chose.setTextSizeCallback(new TextChoseSizeLinearLayout.TextSizeCallback() {
            @Override
            public void textSizeCallback(float size) {
                if (sizeUiCallback!=null){
                    Log.e("tag","1111111111111:"+size);
                    sizeUiCallback.sizeUiCallback(size);
                }
            }
        });

        /**字体颜色毁掉*/
        tv_color_chose.setCallbackColor(new CircleChoseLinearLayout.CallbackColor() {
            @Override
            public void callbackColor(int color) {
                if (colorUiCallback!=null){
                    Log.e("tag","2222222222222:"+color);
                    colorUiCallback.colorUiCallback(color);
                }
            }
        });

        /**背景毁掉*/
        bg_color_chose.setCallbackColor(new CircleChoseLinearLayout.CallbackColor() {
            @Override
            public void callbackColor(int color) {
                if (backgroundUiCallback!=null){
                    Log.e("tag","33333333333333:"+color);
                    backgroundUiCallback.backgroundUiCallback(color);
                }
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (disMissPopupwindowListener!=null){
                    disMissPopupwindowListener.dissmissPopupwindow();
                }
            }
        });
    }

    private void initView() {
        this.setContentView(inflate);
        this.setAnimationStyle(R.style.PopupWindowStyle);
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.popop_bg)));
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public void show(View view){
        this.showAtLocation(view, Gravity.BOTTOM,0,0);
    }

    /**速度回掉接口*/
    public interface SpeedUiCallback{
        public void speedUiCallback(float speed);
    }

    public void setSpeedUiCallback(SpeedUiCallback speedUiCallback){
        this.speedUiCallback = speedUiCallback;
    }

    /**字体大小毁掉接口*/
    public interface SizeUiCallback{
        public void sizeUiCallback(float size);
    }

    public void setSizeUiCallback(SizeUiCallback sizeUiCallback){
        this.sizeUiCallback = sizeUiCallback;
    }

    /**文本颜色毁掉接口*/
    public interface ColorUiCallback{
        public void colorUiCallback(int color);
    }

    public void setColorUiCallback(ColorUiCallback colorUiCallback){
        this.colorUiCallback = colorUiCallback;
    }

    /**背景毁掉接口*/
    public interface BackgroundUiCallback{
        public void backgroundUiCallback(int background);
    }

    public void setBackgroundUiCallback(BackgroundUiCallback backgroundUiCallback){
        this.backgroundUiCallback = backgroundUiCallback;
    }

    /**弹窗消失毁掉接口*/
    public interface DismissPopupwindowListener{
        public void dissmissPopupwindow();
    }

    public void setDisMissPopupwindowListener(DismissPopupwindowListener disMissPopupwindowListener){

        this.disMissPopupwindowListener = disMissPopupwindowListener;
    }
}
