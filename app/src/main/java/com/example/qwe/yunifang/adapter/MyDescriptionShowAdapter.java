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
public class MyDescriptionShowAdapter extends BaseAdapter {
    private Context context;
    private List<HomeRoot.DataEntity.SubjectsEntity.GoodsListEntity> list;

    public MyDescriptionShowAdapter(Context context, List<HomeRoot.DataEntity.SubjectsEntity.GoodsListEntity> list) {
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
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = CommonUtils.inflate(R.layout.last_gv_item);
            holder.iv_last = (ImageView) convertView.findViewById(R.id.last_iv);
            holder.tv_title = (TextView) convertView.findViewById(R.id.last_tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.last_tv_content);
            holder.tv_price = (TextView) convertView.findViewById(R.id.last_tv_price);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getGoods_img(),holder.iv_last, ImageLoaderUtils.initOptions());
        holder.tv_title.setText(list.get(position).getEfficacy());
        holder.tv_content.setText(list.get(position).getGoods_name());
        holder.tv_price.setText("￥: "+list.get(position).getShop_price());
        return convertView;
    }

    class ViewHolder{
        ImageView iv_last;
        TextView tv_title;
        TextView tv_content;
        TextView tv_price;
    }
}
