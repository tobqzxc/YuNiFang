package com.example.qwe.yunifang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.bean.HomeRoot;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by qwe on 2016/12/2.
 */
public class MyGridAdapter extends BaseAdapter {
    private List<HomeRoot.DataEntity.Ad5Entity> list;
    private Context context;

    public MyGridAdapter(List<HomeRoot.DataEntity.Ad5Entity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
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
        convertView = CommonUtils.inflate(R.layout.gv_title);
        ImageView iv_gv_title = (ImageView) convertView.findViewById(R.id.iv_gv_title);
        TextView tv_gv_title = (TextView) convertView.findViewById(R.id.tv_gv_title);
        ImageLoader.getInstance().displayImage(list.get(position).getImage(),iv_gv_title,ImageLoaderUtils.initOptions());
        tv_gv_title.setText(list.get(position).getTitle());
        return convertView;
    }
}
