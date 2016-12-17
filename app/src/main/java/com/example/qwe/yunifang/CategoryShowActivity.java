package com.example.qwe.yunifang;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.qwe.yunifang.adapter.MyGridCategoryAdapter;
import com.example.qwe.yunifang.base.BaseData;
import com.example.qwe.yunifang.bean.CategoryFaceRoot;
import com.example.qwe.yunifang.bean.CategoryrRoot;
import com.example.qwe.yunifang.fragment.CategoryShowFragment;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.view.MyGridView;
import com.example.qwe.yunifang.view.ShowingPage;
import com.google.gson.Gson;

import java.util.List;

public class CategoryShowActivity extends AppCompatActivity {

    private RadioGroup rg_category;
    private List<CategoryrRoot.DataEntity.CategoryEntity.ChildrenEntity> face_list;
    private ViewPager vp_category;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_category_show);
        initView();
        // 得到集合中的数据
        face_list = (List<CategoryrRoot.DataEntity.CategoryEntity.ChildrenEntity>) getIntent().getSerializableExtra("face");
        // 拿到对应id
        position = getIntent().getIntExtra("position", 0);
        // 添加button
        initBtn();
        // 添加fragment
        initData();
        vp_category.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < rg_category.getChildCount(); i++) {
                    RadioButton childAt = (RadioButton) rg_category.getChildAt(i);
                    if(i == position){
                        childAt.setBackgroundResource(R.drawable.button_underline);
                        childAt.setTextColor(getResources().getColor(R.color.color_true));
                    }else{
                        childAt.setBackground(null);
                        childAt.setTextColor(Color.BLACK);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rg_category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rg_category.getChildCount(); i++) {
                    RadioButton childAt = (RadioButton) rg_category.getChildAt(i);
                    if(childAt.getId() == checkedId){
                        vp_category.setCurrentItem(i);
                    }
                }
            }
        });
    }

    private void initData() {

        vp_category.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = CategoryShowFragment.getFragment(face_list.get(position).getId());
                return fragment;
            }

            @Override
            public int getCount() {
                return rg_category.getChildCount();
            }
        });

    }

    private void initBtn() {
        for (int i = 0; i < face_list.size(); i++) {
            RadioButton radio_btn_cate = (RadioButton) CommonUtils.inflate(R.layout.radio_btn_item);
            radio_btn_cate.setText(face_list.get(i).getCat_name());
            if (i == position) {
                radio_btn_cate.setTextColor(getResources().getColor(R.color.color_true));
                radio_btn_cate.setBackgroundResource(R.drawable.button_underline);
            } else {
                radio_btn_cate.setTextColor(Color.BLACK);
            }


            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT, 1.0f);
            rg_category.addView(radio_btn_cate, params);
        }
    }

    private void initView() {
        rg_category = (RadioGroup) findViewById(R.id.rg_category);
        vp_category = (ViewPager) findViewById(R.id.vp_category);
    }
}
