package com.example.qwe.yunifang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.sql.Product;
import com.example.qwe.yunifang.sql.ProductDao;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2016/12/14.
 */
public class MyShopCarAdapter extends BaseAdapter implements View.OnClickListener {
    private AutoRelativeLayout relative;
    private AutoLinearLayout ll_no_shoping;
    private List<Product> list;
    private Context context;
    private TextView change;
    private boolean flag = false;
    private int num = 0;
    private CheckBox all_select_cb;
    private Button pay_button;
    private final ProductDao productDao;
    private TextView tv_carshop_show;
    private TextView cart_sumMoney_tv;
    private float number = 0;
    private int num1;

    public MyShopCarAdapter(List<Product> list, Context context, TextView change, CheckBox all_select_cb, Button pay_button, TextView tv_carshop_show, TextView cart_sumMoney_tv, AutoLinearLayout ll_no_shoping, AutoRelativeLayout relative) {
        this.list = list;
        this.context = context;
        this.change = change;
        this.all_select_cb = all_select_cb;
        this.pay_button = pay_button;
        this.tv_carshop_show = tv_carshop_show;
        this.cart_sumMoney_tv = cart_sumMoney_tv;
        this.ll_no_shoping = ll_no_shoping;
        this.relative = relative;
        productDao = new ProductDao(context);
        change.setOnClickListener(this);
        all_select_cb.setOnClickListener(this);
        pay_button.setOnClickListener(this);
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
        final ViewHoler holer;
        if (convertView == null) {
            holer = new ViewHoler();
            convertView = CommonUtils.inflate(R.layout.fragment_car_lv_item);
            holer.imageView = (ImageView) convertView.findViewById(R.id.cart_lv_item_img);
            holer.tv_name = (TextView) convertView.findViewById(R.id.cart_lv_item_goods_name);
            holer.tv_price = (TextView) convertView.findViewById(R.id.cart_lv_item_show_price);
            holer.tv_Number = (TextView) convertView.findViewById(R.id.cart_lv_item_goods_num);
            holer.checkBox = (CheckBox) convertView.findViewById(R.id.cart_all_check_item);
            holer.add_number = (Button) convertView.findViewById(R.id.add_number);
            holer.reduce_number = (Button) convertView.findViewById(R.id.reduce_number);
            holer.linearLayout = (AutoLinearLayout) convertView.findViewById(R.id.cart_lv_item_linearLayout);
            holer.show_number = (Button) convertView.findViewById(R.id.show_number);
            convertView.setTag(holer);
        } else {
            holer = (ViewHoler) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(list.get(position).getUrl(), holer.imageView, ImageLoaderUtils.initOptions());
        holer.tv_name.setText(list.get(position).getName());
        holer.tv_price.setText("￥:" + list.get(position).getPrice());
        holer.tv_Number.setText("数量:" + list.get(position).getNum());
        if (flag) {
            holer.linearLayout.setVisibility(View.VISIBLE);
        } else {
            holer.tv_Number.setText("数量:" + list.get(position).getNum());
            holer.linearLayout.setVisibility(View.GONE);
        }
        if (num == list.size()) {
            all_select_cb.setChecked(true);
            all_select_cb.setText("反选");
            cart_sumMoney_tv.setText("￥" + number);
        } else {
            all_select_cb.setChecked(false);
            all_select_cb.setText("全选");
            cart_sumMoney_tv.setText("￥" + number);
        }
        // 商品选中按钮
        holer.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isChecked()) {
                    list.get(position).setChecked(false);
                    num--;
                    if (num == 0) {
                        number = 0;
                        cart_sumMoney_tv.setText("￥" + number);
                    } else {
                        number -= Float.parseFloat(list.get(position).getPrice()) * list.get(position).getNum();
                        cart_sumMoney_tv.setText("￥" + number);
                    }
                } else {
                    list.get(position).setChecked(true);
                    num++;
                    number += Float.parseFloat(list.get(position).getPrice()) * list.get(position).getNum();
                    cart_sumMoney_tv.setText("￥" + number);
                }
                notifyDataSetChanged();
            }
        });

        holer.add_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1 = list.get(position).getNum();
                num1++;
                holer.show_number.setText(num1 + "");
                productDao.update(list.get(position).getNum(), num1);
                list.get(position).setNum(num1);
                notifyDataSetChanged();
            }
        });
        holer.reduce_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1 = list.get(position).getNum();
                if (num1 == 1) {
                    holer.reduce_number.setTextColor(Color.GRAY);
                } else {
                    num1--;
                    productDao.update(list.get(position).getNum(), num1);
                }
                list.get(position).setNum(num1);
                holer.show_number.setText(num1 + "");
                notifyDataSetChanged();
            }
        });

        holer.checkBox.setChecked(list.get(position).isChecked());
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_change:
                flag = !flag;
                if (flag) {
                    change.setText("完成");
                    pay_button.setText("删除");
                    cart_sumMoney_tv.setVisibility(View.GONE);
                } else {
                    change.setText("编辑");
                    pay_button.setText("结算");
                    cart_sumMoney_tv.setVisibility(View.VISIBLE);
                    cart_sumMoney_tv.setText("￥" + 0);
                    ArrayList<Product> query = productDao.query();
                    if(query.size()==0) {
                        ll_no_shoping.setVisibility(View.VISIBLE);
                        relative.setVisibility(View.GONE);
                    }
                }

                break;
            case R.id.all_select_cb:
                if (all_select_cb.isChecked()) {
                    num = list.size();
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChecked(true);
                        number += Float.parseFloat(list.get(i).getPrice()) * list.get(i).getNum();
                    }
                } else {
                    num = 0;
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChecked(false);
                        number = 0;
                    }
                }
                break;
            case R.id.pay_button:
                if (flag) {
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isChecked()) {
                            productDao.delete(list.get(i));
                            list.remove(i);
                        }
                    }
                    tv_carshop_show.setText("购物车(" + list.size() + ")");
                } else {

                }
                break;
        }
        notifyDataSetChanged();
    }

    class ViewHoler {
        ImageView imageView;
        TextView tv_name;
        TextView tv_price;
        TextView tv_Number;
        AutoLinearLayout linearLayout;
        CheckBox checkBox;
        Button reduce_number;
        Button add_number;
        Button show_number;
    }

}
