package com.led.scroller.lite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.led.scroller.lite.Content;
import com.led.scroller.lite.R;
import com.led.scroller.lite.Utils;
import com.led.scroller.lite.bean.ScrollContentBean;
import com.led.scroller.lite.db.DBOperation;
import com.led.scroller.lite.view.CircleChoseLinearLayout;
import com.led.scroller.lite.view.TextChoseSizeLinearLayout;

public class SetSizeColorActivity extends AppCompatActivity {

    private TextView set_next;
    private ImageView set_back;
    private CircleChoseLinearLayout set_color_chose;
    private TextChoseSizeLinearLayout set_size_chose;
    private int color = Content.fontColorMap[0];
    private float size = Content.fontSizeMap[0];
    private float speed = 20 / 2;
    private int bg = 0;
    private String content;
    private SeekBar set_speed_bar;
    private CircleChoseLinearLayout set_bg_chose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.setState(this);

        Intent intent = getIntent();
        content = intent.getStringExtra("content");

        setContentView(R.layout.set_size_color_layout);

        set_next = findViewById(R.id.set_next);
        set_back = findViewById(R.id.set_size_color_back);
        set_color_chose = findViewById(R.id.set_text_color_chose);
        set_size_chose = findViewById(R.id.set_text_size_chose);
        set_speed_bar = findViewById(R.id.set_speed_seekbar);
        set_bg_chose = findViewById(R.id.set_text_bg_chose);

        setListener();
    }

    private void setListener() {
        set_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollContentBean scrollContentBean = new ScrollContentBean();
                scrollContentBean.setBg(bg);
                scrollContentBean.setColor(color);
                scrollContentBean.setContent(content);
                scrollContentBean.setCreateTime(System.currentTimeMillis()+"");
                scrollContentBean.setSize(size);
                scrollContentBean.setSpeed(speed);

                //保存信息
                DBOperation.getInstance().insertDB(scrollContentBean);

                Intent intent = new Intent(SetSizeColorActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("obj",scrollContentBean);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
                finish();
            }
        });

        set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        set_color_chose.setCallbackColor(new CircleChoseLinearLayout.CallbackColor() {


            @Override
            public void callbackColor(int color) {

                SetSizeColorActivity.this.color = color;
            }
        });

        set_size_chose.setTextSizeCallback(new TextChoseSizeLinearLayout.TextSizeCallback() {

            @Override
            public void textSizeCallback(float size) {

                SetSizeColorActivity.this.size = size;
            }
        });

        set_bg_chose.setCallbackColor(new CircleChoseLinearLayout.CallbackColor() {
            @Override
            public void callbackColor(int color) {
                bg = color;
            }
        });

        set_speed_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                speed = i / 5;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
