package com.example.qwe.yunifang.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.bean.ProductRoot;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by qwe on 2016/12/9.
 */
public class MyCommentShowAdapter extends BaseAdapter {
    private Context context;
    private List<ProductRoot.DataEntity.CommentsEntity> list;

    public MyCommentShowAdapter(Context context, List<ProductRoot.DataEntity.CommentsEntity> list) {
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
        convertView = CommonUtils.inflate(R.layout.comment_show_item);
        TextView tv_comment_name = (TextView) convertView.findViewById(R.id.tv_comment_name);
        TextView tv_comment_cotent = (TextView) convertView.findViewById(R.id.tv_comment_cotent);
        TextView tv_comment_time = (TextView) convertView.findViewById(R.id.tv_comment_time);
        ImageView iv_comment_title = (ImageView) convertView.findViewById(R.id.iv_comment_title);
        tv_comment_cotent.setText(list.get(position).getContent());
        tv_comment_time.setText(list.get(position).getCreatetime());
        tv_comment_name.setText(list.get(position).getUser().getNick_name());
        ImageLoader.getInstance().displayImage(list.get(position).getUser().getIcon(),iv_comment_title, ImageLoaderUtils.initOptionsCircol());
        return convertView;
    }
}
