package com.example.qwe.yunifang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.bean.ProductRoot;
import com.example.qwe.yunifang.utlis.CommonUtils;

import java.util.List;

/**
 * Created by qwe on 2016/12/9.
 */
public class MyCanShuShowAdapter extends BaseAdapter {
    private Context context;
    private List<ProductRoot.DataEntity.GoodsEntity.AttributesEntity> list;

    public MyCanShuShowAdapter(Context context, List<ProductRoot.DataEntity.GoodsEntity.AttributesEntity> list) {
        this.context = context;
        this.list = list;
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

        convertView = CommonUtils.inflate(R.layout.canshu_show_item);
        TextView tv_canshu_title = (TextView) convertView.findViewById(R.id.tv_canshu_title);
        TextView tv_canshu_content = (TextView) convertView.findViewById(R.id.tv_canshu_content);
        tv_canshu_title.setText(list.get(position).getAttr_name());
        tv_canshu_content.setText(list.get(position).getAttr_value());
        return convertView;
    }
}
