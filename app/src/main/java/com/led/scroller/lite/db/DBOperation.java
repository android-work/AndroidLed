package com.led.scroller.lite.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.led.scroller.lite.App;
import com.led.scroller.lite.bean.ScrollContentBean;

import java.util.ArrayList;

public class DBOperation {

    private static DBOperation dbOperation;
    private DBHelp scroll;

    private DBOperation(){
        scroll = new DBHelp(App.getApp(), "SCROLL", null, 1);
    }

    public static DBOperation getInstance(){
        if (dbOperation==null){
            synchronized (DBOperation.class){
                dbOperation = new DBOperation();
            }
        }
        return dbOperation;
    }

    public void insertDB(ScrollContentBean scrollContentBean){
        if (scrollContentBean == null){
            return;
        }

        Log.e("tag","bg:"+scrollContentBean.getBg()+":color:"+scrollContentBean.getColor()+":speed:"+scrollContentBean.getSpeed()+":createTime:"+scrollContentBean.getCreateTime()+":size:"+scrollContentBean.getSize());

        SQLiteDatabase db = scroll.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("bg",scrollContentBean.getBg());
        contentValues.put("color",scrollContentBean.getColor());
        contentValues.put("speed",scrollContentBean.getSpeed());
        contentValues.put("createTime",scrollContentBean.getCreateTime());
        contentValues.put("content",scrollContentBean.getContent());
        contentValues.put("size",scrollContentBean.getSize());

        long insert = db.insert(DBHelp.TABLES, null, contentValues);
        Log.e("tag","insert:"+insert);
    }

    public ArrayList<ScrollContentBean> queryDB(){
        ArrayList<ScrollContentBean> scrollContentBeans = new ArrayList<>();
        SQLiteDatabase db = scroll.getReadableDatabase();
        Cursor query = db.query(DBHelp.TABLES, null, null, null, null, null, null);
        Log.e("tag","query:"+query);
        if (query!=null){
            while(query.moveToNext()){

                ScrollContentBean scrollContentBean = new ScrollContentBean();

                int bg = query.getInt(0);
                int color = query.getInt(1);
                float speed = query.getFloat(2);
                String createTime = query.getString(3);
                String content = query.getString(4);
                float size = query.getFloat(5);

                scrollContentBean.setSpeed(speed);
                scrollContentBean.setSize(size);
                scrollContentBean.setCreateTime(createTime);
                scrollContentBean.setContent(content);
                scrollContentBean.setColor(color);
                scrollContentBean.setBg(bg);

                scrollContentBeans.add(scrollContentBean);
            }
        }
        return scrollContentBeans;
    }

    public void deleteDB(String createTime){
        SQLiteDatabase db = scroll.getReadableDatabase();
        int delete = db.delete(DBHelp.TABLES, "createTime = ?", new String[]{createTime});
        Log.e("tag","delete:"+delete);
    }

    public void updataDB(ScrollContentBean scrollContentBean){
        if (scrollContentBean == null){
            return;
        }

        SQLiteDatabase db = scroll.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("bg",scrollContentBean.getBg());
        contentValues.put("color",scrollContentBean.getColor());
        contentValues.put("speed",scrollContentBean.getSpeed());
        contentValues.put("createTime",scrollContentBean.getCreateTime());
        contentValues.put("content",scrollContentBean.getContent());
        contentValues.put("size",scrollContentBean.getSize());

        int update = db.update(DBHelp.TABLES, contentValues, "createTime = ?", new String[]{scrollContentBean.getCreateTime()});

        Log.e("tag","update:"+update);
    }
}
