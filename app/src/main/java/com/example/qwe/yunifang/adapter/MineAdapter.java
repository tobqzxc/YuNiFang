package com.example.qwe.yunifang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.utlis.CommonUtils;

import java.util.List;

/**
 * Created by qwe on 2016/11/29.
 */
public class MineAdapter extends BaseAdapter {
    private Context context;
    private List<String> title_list;
    private List<Integer> pic_list;

    public MineAdapter(Context context, List<String> title_list, List<Integer> pic_list) {
        this.context = context;
        this.title_list = title_list;
        this.pic_list = pic_list;
    }

    @Override
    public int getCount() {
        return title_list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = CommonUtils.inflate(R.layout.mine_list_item);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_mine);
        TextView tv_title = (TextView) convertView.findViewById(R.id.tv_mine_title);
        imageView.setImageResource(pic_list.get(position));
        tv_title.setText(title_list.get(position));
        return convertView;
    }
}
