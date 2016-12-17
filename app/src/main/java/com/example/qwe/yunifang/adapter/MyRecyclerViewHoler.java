package com.example.qwe.yunifang.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qwe.yunifang.R;

/**
 * Created by qwe on 2016/12/7.
 */
public class MyRecyclerViewHoler extends RecyclerView.ViewHolder {

    public ImageView last_iv;
    public TextView last_tv_title;
    public TextView last_tv_content;
    public TextView last_tv_price;

    public MyRecyclerViewHoler(View itemView) {
        super(itemView);
        last_iv = (ImageView) itemView.findViewById(R.id.last_iv);
        last_tv_title = (TextView) itemView.findViewById(R.id.last_tv_title);
       last_tv_content = (TextView) itemView.findViewById(R.id.last_tv_content);
       last_tv_price = (TextView) itemView.findViewById(R.id.last_tv_price);
    }
}
