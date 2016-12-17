package com.example.qwe.yunifang;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.qwe.yunifang.utlis.CommonUtils;
import com.zhy.autolayout.AutoLinearLayout;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register_name;
    private TextView register_phone;
    private EditText ed_phone;
    private EditText ed_password;
    private EditText ed_getCode;
    private TextView tv_word;
    private TextView tv_btn;
    private AutoLinearLayout ll_phone;
    private TextView tv_Deng;
    private ImageView iv_locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        // 查找控件
        initView();
        // 点击事件
        initLister();
    }

    private void initLister() {
        register_name.setOnClickListener(this);
        register_phone.setOnClickListener(this);
        tv_Deng.setOnClickListener(this);
        iv_locate.setOnClickListener(this);
    }

    private void initView() {
        register_name = (TextView) findViewById(R.id.register_name);
        register_phone = (TextView) findViewById(R.id.register_phone);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_password = (EditText) findViewById(R.id.ed_password);
        ed_getCode = (EditText) findViewById(R.id.ed_getCode);
        tv_word = (TextView) findViewById(R.id.tv_word);
        tv_btn = (TextView) findViewById(R.id.tv_btn);
        ll_phone = (AutoLinearLayout) findViewById(R.id.ll_phone);
        tv_Deng = (TextView) findViewById(R.id.tv_Deng);
        iv_locate = (ImageView) findViewById(R.id.iv_locate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_name:
                register_name.setTextColor(getResources().getColor(R.color.color_true));
                register_phone.setTextColor(Color.GRAY);
                register_name.setBackgroundColor(getResources().getColor(R.color.registerchecked));
                register_phone.setBackgroundColor(getResources().getColor(R.color.no_registerchecked));
                ed_getCode.setVisibility(View.GONE);
                tv_btn.setVisibility(View.GONE);
                ed_password.setVisibility(View.VISIBLE);
                tv_word.setVisibility(View.VISIBLE);
                break;
            case R.id.register_phone:
                register_phone.setTextColor(getResources().getColor(R.color.color_true));
                register_name.setTextColor(Color.GRAY);
                register_phone.setBackgroundColor(getResources().getColor(R.color.registerchecked));
                register_name.setBackgroundColor(getResources().getColor(R.color.no_registerchecked));
                ed_getCode.setVisibility(View.VISIBLE);
                tv_btn.setVisibility(View.VISIBLE);
                tv_word.setVisibility(View.INVISIBLE);
                ed_password.setVisibility(View.GONE);
                break;
            case R.id.tv_Deng:
                View view = CommonUtils.inflate(R.layout.pop_layout);
                RadioButton qq_land = (RadioButton) view.findViewById(R.id.qq_land);
                PopupWindow popupWindow = new PopupWindow(view,LinearLayout.LayoutParams.MATCH_PARENT,180);
                popupWindow.setFocusable(true);
                popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupWindow.showAtLocation(RegisterActivity.this.tv_Deng, Gravity.BOTTOM,0,0);
                qq_land.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                break;
            case R.id.iv_locate:
                finish();
                overridePendingTransition(R.anim.activity3_in,R.anim.activity3_out);
                break;
        }

    }
}
