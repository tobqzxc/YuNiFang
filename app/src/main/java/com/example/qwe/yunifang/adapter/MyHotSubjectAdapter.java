package com.example.qwe.yunifang.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qwe.yunifang.DescriptionActivity;
import com.example.qwe.yunifang.ProductDetailsActivity;
import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.bean.HomeRoot;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * Created by qwe on 2016/12/2.
 */
public class MyHotSubjectAdapter extends BaseAdapter {
    private Context context;
    private List<HomeRoot.DataEntity.SubjectsEntity> list;

    public MyHotSubjectAdapter(Context context, List<HomeRoot.DataEntity.SubjectsEntity> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler;
        if(convertView==null){
            viewHoler = new ViewHoler();
            convertView = CommonUtils.inflate(R.layout.rm_lv_item);
            viewHoler.iv_rm = (ImageView) convertView.findViewById(R.id.iv_rm);
            viewHoler.linearLayout = (AutoLinearLayout) convertView.findViewById(R.id.rm_linear);
            convertView.setTag(viewHoler);
        }else{
            viewHoler = (ViewHoler) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getImage(),viewHoler.iv_rm, ImageLoaderUtils.initOptions());
        viewHoler.iv_rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DescriptionActivity.class);
                intent.putExtra("bean",  list.get(position));
                context.startActivity(intent);
            }
        });
        viewHoler.iv_rm.setScaleType(ImageView.ScaleType.FIT_XY);
        viewHoler.linearLayout.removeAllViews();
        for (int i = 0; i <7; i++) {
            View view = CommonUtils.inflate(R.layout.hsv_gv_item);
            ImageView iv_hsv_gv = (ImageView) view.findViewById(R.id.iv_hsv_gv);
            TextView tv_hsv_title = (TextView) view.findViewById(R.id.tv_hsv_title);
            TextView tv_hsv_price = (TextView) view.findViewById(R.id.tv_hsv_price);
            tv_hsv_title.setText(list.get(position).getGoodsList().get(i).getGoods_name());
            tv_hsv_price.setText("￥: "+list.get(position).getGoodsList().get(i).getShop_price());
            ImageLoader.getInstance().displayImage(list.get(position).getGoodsList().get(i).getGoods_img(),iv_hsv_gv,ImageLoaderUtils.initOptions());
            tv_hsv_title.setGravity(Gravity.CENTER);
            tv_hsv_price.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(CommonUtils.dip2px(140),LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,5,5,5);
            viewHoler.linearLayout.addView(view,params);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    intent.putExtra("product_bean",list.get(position).getGoodsList().get(finalI));
                    context.startActivity(intent);
                }
            });
        }
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.mipmap.other);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(5,5,5,5);
        viewHoler.linearLayout.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DescriptionActivity.class);
                intent.putExtra("bean",  list.get(position));
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    class ViewHoler{
        ImageView iv_rm;
        AutoLinearLayout linearLayout;
    }
}
