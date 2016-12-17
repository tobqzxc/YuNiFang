package com.example.qwe.yunifang.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.bean.AllGoodsRoot;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * Created by qwe on 2016/12/7.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHoler> {
    private Context context;
    private List<AllGoodsRoot.DataEntity> list;

    public MyRecyclerViewAdapter(Context context, List<AllGoodsRoot.DataEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyRecyclerViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = CommonUtils.inflate(R.layout.last_gv_item);

        MyRecyclerViewHoler holer = new MyRecyclerViewHoler(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHoler holder, int position) {
        ImageLoader.getInstance().displayImage(list.get(position).getGoods_img(),holder.last_iv, ImageLoaderUtils.initOptions());
        holder.last_tv_title.setText(list.get(position).getEfficacy());
        holder.last_tv_content.setText(list.get(position).getGoods_name());
        holder.last_tv_price.setText("￥: "+list.get(position).getShop_price());
    }



    @Override
    public int getItemCount() {
        return  list.size();
    }
}
