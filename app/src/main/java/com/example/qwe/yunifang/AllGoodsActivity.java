package com.example.qwe.yunifang;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.qwe.yunifang.adapter.MyRecyclerViewAdapter;
import com.example.qwe.yunifang.base.BaseData;
import com.example.qwe.yunifang.bean.AllGoodsRoot;
import com.example.qwe.yunifang.utlis.LogUtils;
import com.example.qwe.yunifang.utlis.URLUtils;
import com.example.qwe.yunifang.view.ShowingPage;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllGoodsActivity extends AppCompatActivity {

    private RecyclerView rv_all;
    private RadioGroup rg_all;
    private List<AllGoodsRoot.DataEntity> my_list = new ArrayList<>();
    private List<AllGoodsRoot.DataEntity> default_list = new ArrayList<>();
    private MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_all_goods);
        // 查找控件
        initView();
        // 网络请求数据
        initData();
        // RadioGroup监听
        initRg();

    }

    private void initRg() {
        rg_all.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rg_all.getChildCount(); i++) {
                    RadioButton childAt = (RadioButton) rg_all.getChildAt(i);
                    if (childAt.getId() == checkedId) {
                        childAt.setTextColor(getResources().getColor(R.color.color_true));
                        childAt.setBackgroundResource(R.drawable.button_underline);
                        if(i==0){
                            my_list.clear();
                            my_list.addAll(default_list);
                            adapter.notifyDataSetChanged();
                        }
                        if(i==1){
                            Collections.sort(my_list, new Comparator<AllGoodsRoot.DataEntity>() {
                                @Override
                                public int compare(AllGoodsRoot.DataEntity lhs, AllGoodsRoot.DataEntity rhs) {
                                    if(lhs.getShop_price()>rhs.getShop_price()){
                                        return 1;
                                    }
                                    if(lhs.getShop_price()==rhs.getShop_price()){
                                        return 0;
                                    }
                                    return -1;
                                }
                            });
                            adapter.notifyDataSetChanged();
                        }
                        if(i==2){
                            Collections.sort(my_list, new Comparator<AllGoodsRoot.DataEntity>() {
                                @Override
                                public int compare(AllGoodsRoot.DataEntity lhs, AllGoodsRoot.DataEntity rhs) {
                                    if(lhs.getShop_price()<rhs.getShop_price()){
                                        return 1;
                                    }
                                    if(lhs.getShop_price()==rhs.getShop_price()){
                                        return 0;
                                    }
                                    return -1;
                                }
                            });
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        childAt.setTextColor(Color.BLACK);
                        childAt.setBackgroundColor(Color.WHITE);
                    }
                }
            }
        });
    }

    private void initData() {
        AllGoodsData data = new AllGoodsData();
        data.getData(URLUtils.AllGoodsUrl, "", 0, BaseData.NOTIME);
    }

    private void initView() {
        rv_all = (RecyclerView) findViewById(R.id.recycler);
        rg_all = (RadioGroup) findViewById(R.id.rg_all);
    }

    class AllGoodsData extends BaseData {
        @Override
        public void setResultData(String data) {
            Gson gson = new Gson();
            AllGoodsRoot goodsRoot = gson.fromJson(data, AllGoodsRoot.class);
            my_list.clear();
            default_list.clear();
            List<AllGoodsRoot.DataEntity> list = goodsRoot.getData();
            my_list.addAll(list);
            default_list.addAll(list);
            LogUtils.d("zzzz", list.size() + "++++++++++++++++++++++");
            rv_all.setLayoutManager(new GridLayoutManager(AllGoodsActivity.this, 2, GridLayoutManager.VERTICAL, false));
            adapter = new MyRecyclerViewAdapter(AllGoodsActivity.this, my_list);
            rv_all.setAdapter(adapter);
        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {

        }
    }

}
