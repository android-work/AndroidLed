package com.led.scroller.lite.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.led.scroller.lite.R;
import com.led.scroller.lite.Utils;
import com.work.load.scoreguide.FeedbackActivity;
import com.work.load.scoreguide.SccorePopupWindow;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends Activity implements View.OnClickListener {

    private LinearLayout set_evaluation;
    private LinearLayout set_feedback;
    private TextView set_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.setState(this);

        setContentView(R.layout.set_layout);

        set_evaluation = findViewById(R.id.set_evaluation);
        set_feedback = findViewById(R.id.set_feedback);
        set_version = findViewById(R.id.set_version);

        set_evaluation.setOnClickListener(this);
        set_feedback.setOnClickListener(this);

        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            set_version.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_evaluation:
                SccorePopupWindow sccorePopupWindow = new SccorePopupWindow(this);
                sccorePopupWindow.show(set_evaluation,getString(R.string.app_name));
                break;
            case R.id.set_feedback:
                startActivity(new Intent(this, FeedbackActivity.class));
                break;
        }
    }


}
