package com.example.qwe.yunifang.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.qwe.yunifang.MainActivity;
import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.RegisterActivity;
import com.example.qwe.yunifang.adapter.MyShopCarAdapter;
import com.example.qwe.yunifang.app.MyApplication;
import com.example.qwe.yunifang.base.BaseData;
import com.example.qwe.yunifang.base.BaseFragment;
import com.example.qwe.yunifang.sql.Product;
import com.example.qwe.yunifang.sql.ProductDao;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.view.ShowingPage;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

/**
 * Created by qwe on 2016/11/28.
 */
public class CartFragment extends BaseFragment {
    private String data;
    private ListView cart_listView;
    private TextView tv_carshop_show;
    private TextView tv_change;
    private CheckBox all_select_cb;
    private Button pay_button;
    private TextView cart_sumMoney_tv;
    private AutoLinearLayout ll_no_shoping;
    private AutoRelativeLayout relative;
    private Button bt_guang;
    private View view;


    @Override
    protected View createSuccessView() {
            view = CommonUtils.inflate(R.layout.cartfragment_item);
            cart_listView = (ListView) view.findViewById(R.id.cart_listView);
            tv_carshop_show = (TextView) view.findViewById(R.id.tv_carshop_show);
            tv_change = (TextView) view.findViewById(R.id.tv_change);
            all_select_cb = (CheckBox) view.findViewById(R.id.all_select_cb);
            pay_button = (Button) view.findViewById(R.id.pay_button);
            cart_sumMoney_tv = (TextView) view.findViewById(R.id.cart_sumMoney_tv);
            ll_no_shoping = (AutoLinearLayout) view.findViewById(R.id.ll_no_shoping);
            relative = (AutoRelativeLayout) view.findViewById(R.id.relative);
            bt_guang = (Button) view.findViewById(R.id.bt_guang);
            initData();
            return view;
    }

    private void initData() {
        // 将数据库中商品取出展示
        final ProductDao dao = new ProductDao(getActivity());
        ArrayList<Product> query = dao.query();
        cart_listView.setAdapter(new MyShopCarAdapter(query, getActivity(), tv_change, all_select_cb, pay_button, tv_carshop_show, cart_sumMoney_tv, ll_no_shoping, relative));
        tv_carshop_show.setText("购物车(" + query.size() + ")");

        if (query.size() == 0) {
            ll_no_shoping.setVisibility(View.VISIBLE);
            relative.setVisibility(View.GONE);
        } else {
            ll_no_shoping.setVisibility(View.GONE);
            relative.setVisibility(View.VISIBLE);
        }

        // 点击滑动到选择商品
        bt_guang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).viewPager.setCurrentItem(0);
                RadioButton childAt = (RadioButton) ((MainActivity) getActivity()).radioGroup.getChildAt(0);
                childAt.setChecked(true);
            }
        });

    }

    @Override
    protected void onload() {
            showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }
}
