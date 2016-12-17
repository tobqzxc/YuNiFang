package com.example.qwe.yunifang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.bean.CategoryFaceRoot;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by qwe on 2016/12/13.
 */
public class MyGridCategoryAdapter extends BaseAdapter{
    private List<CategoryFaceRoot.DataEntity> list;
    private Context context;

    public MyGridCategoryAdapter(List<CategoryFaceRoot.DataEntity> list, Context context) {
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
        ViewHoler holer = null;
        if(convertView == null){
            convertView = CommonUtils.inflate(R.layout.last_gv_item);
            holer = new ViewHoler();
            holer.imageView = (ImageView) convertView.findViewById(R.id.last_iv);
            holer.tv_title = (TextView) convertView.findViewById(R.id.last_tv_title);
            holer.tv_content = (TextView) convertView.findViewById(R.id.last_tv_content);
            holer.tv_price = (TextView) convertView.findViewById(R.id.last_tv_price);
            convertView.setTag(holer);
        }else{
            holer = (ViewHoler) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getGoods_img(),holer.imageView, ImageLoaderUtils.initOptions());
        holer.tv_title.setText(list.get(position).getGoods_name());
        holer.tv_content.setText(list.get(position).getEfficacy());
        holer.tv_price.setText(list.get(position).getShop_price()+"");
        return convertView;
    }
    class ViewHoler{
        ImageView imageView;
        TextView tv_title;
        TextView tv_content;
        TextView tv_price;
    }
}
