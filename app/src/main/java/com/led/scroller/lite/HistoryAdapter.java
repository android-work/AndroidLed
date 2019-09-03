package com.led.scroller.lite;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.led.scroller.lite.activity.HistoryActivity;
import com.led.scroller.lite.bean.ScrollContentBean;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private HistoryActivity historyActivity;
    private ArrayList<ScrollContentBean> scrollContentBeans;
    private RvItemClickListener rvItemClickListener;
    private RvItemLongClickListener rvItemLongClickListener;

    public HistoryAdapter(HistoryActivity historyActivity, ArrayList<ScrollContentBean> scrollContentBeans) {
        this.historyActivity = historyActivity;
        this.scrollContentBeans = scrollContentBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(historyActivity).inflate(R.layout.history_item_layout, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rvItemClickListener!=null){
                    rvItemClickListener.itemClick(view,position);
                }
            }
        });

        holder.item_rl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (rvItemLongClickListener!=null){
                    rvItemLongClickListener.itemLongClick(view,position);
                }
                return true;
            }
        });

        holder.item_tv.setText(scrollContentBeans.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        Log.e("tag","size;"+scrollContentBeans.size());
        return scrollContentBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final RelativeLayout item_rl;
        private final TextView item_tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_rl = itemView.findViewById(R.id.item_rl);
            item_tv = itemView.findViewById(R.id.item_tv);
        }
    }

    public interface RvItemClickListener{
        public void itemClick(View view,int position);
    }

    public void setRvItemClickListener(RvItemClickListener rvItemClickListener){
        this.rvItemClickListener = rvItemClickListener;
    }

    public interface RvItemLongClickListener{
        public void itemLongClick(View view,int position);
    }

    public void setRvItemLongClickListener(RvItemLongClickListener rvItemLongClickListener){
        this.rvItemLongClickListener = rvItemLongClickListener;
    }
}
