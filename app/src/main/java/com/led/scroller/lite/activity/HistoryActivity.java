package com.led.scroller.lite.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.led.scroller.lite.App;
import com.led.scroller.lite.HistoryAdapter;
import com.led.scroller.lite.R;
import com.led.scroller.lite.Utils;
import com.led.scroller.lite.bean.ScrollContentBean;
import com.led.scroller.lite.db.DBOperation;
import com.work.load.scoreguide.SccorePopupWindow;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView setting;
    private ImageView his_add;
    private RecyclerView his_rv;
    private boolean isExit = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.setState(this);

        setContentView(R.layout.history_layout);

        setting = findViewById(R.id.seting);
        his_add = findViewById(R.id.his_add);
        his_rv = findViewById(R.id.his_rv);

        setting.setOnClickListener(this);
        his_add.setOnClickListener(this);

        his_rv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        his_rv.addItemDecoration(new MyItemDecoration(20));
    }

    @Override
    protected void onResume() {
        super.onResume();

        //设置适配器
        new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<ScrollContentBean> scrollContentBeans = DBOperation.getInstance().queryDB();
                Log.e("tag","=======================:"+scrollContentBeans.size());
                if (scrollContentBeans==null || scrollContentBeans.size() == 0){

                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setAdapter(scrollContentBeans);

                    }
                });
            }
        }).start();
    }

    private void setAdapter(final ArrayList<ScrollContentBean> scrollContentBeans) {
        final HistoryAdapter historyAdapter = new HistoryAdapter(HistoryActivity.this, scrollContentBeans);
        his_rv.setAdapter(historyAdapter);
        historyAdapter.setRvItemClickListener(new HistoryAdapter.RvItemClickListener() {
            @Override
            public void itemClick(View view, int position) {
                Intent intent = new Intent(HistoryActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("obj",scrollContentBeans.get(position));
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });

        historyAdapter.setRvItemLongClickListener(new HistoryAdapter.RvItemLongClickListener() {
            @Override
            public void itemLongClick(View view, final int position) {
                View inflate = LayoutInflater.from(HistoryActivity.this).inflate(R.layout.item_dialog_layout, null);

                TextView delete_no = inflate.findViewById(R.id.delete_no);
                TextView delete_yes = inflate.findViewById(R.id.delete_yes);

                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                builder.setView(inflate);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                delete_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                delete_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //删除数据库数据
                        DBOperation.getInstance().deleteDB(scrollContentBeans.get(position).getCreateTime());
                        alertDialog.dismiss();
                        scrollContentBeans.remove(position);
                        historyAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.seting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.his_add:
                startActivity(new Intent(this, AddActivity.class));
                break;
        }
    }

    public static class MyItemDecoration extends RecyclerView.ItemDecoration{
        private int top;

        public MyItemDecoration(int top){

            this.top = top;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = top;
        }
    }

    @Override
    public void onBackPressed() {
        if (isExit){
            isExit = false;
            boolean aBoolean = Utils.getBoolean(this, App.ISFIRST, true);
            if (aBoolean){
                Utils.saveBoolean(this,App.ISFIRST,false);
                SccorePopupWindow sccorePopupWindow = new SccorePopupWindow(this);
                sccorePopupWindow.show(his_rv,getString(R.string.app_name));
            }
        }else{
            finish();
        }
    }
}
