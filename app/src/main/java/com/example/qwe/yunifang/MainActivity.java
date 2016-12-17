package com.example.qwe.yunifang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.qwe.yunifang.app.MyApplication;
import com.example.qwe.yunifang.factory.FragmentFactory;
import com.example.qwe.yunifang.view.ShowingPage;
import com.zhy.autolayout.AutoLayoutActivity;


public class MainActivity extends AutoLayoutActivity {

    private ShowingPage showingPage;
    public ViewPager viewPager;
    public RadioGroup radioGroup;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.vp_main);
        radioGroup = (RadioGroup) findViewById(R.id.rg_main);
        // 设置加载页
        initView();
    }

    private void initView() {
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentFactory.getFragment(position);
            }

            @Override
            public int getCount() {
                return radioGroup.getChildCount();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                    if (radioButton.getId() == checkedId) {
                        if (checkedId == R.id.rb_cart && !MyApplication.intentFlag) {
                            radioButton.setChecked(false);
                            viewPager.setCurrentItem(index);
                            RadioButton childAt = (RadioButton) radioGroup.getChildAt(index);
                            childAt.setChecked(true);
                            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                            startActivity(intent);
                        } else {
                            index = i;
                            viewPager.setCurrentItem(i);
                        }
                    }

                }
            }
        });

    }


}
