package com.example.qwe.yunifang.lanuch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.qwe.yunifang.R;

public class LocateActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_locate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_locate);
        // 查找控件
        initView();
        // 监听事件
        initLister();

    }

    private void initLister() {
        iv_locate.setOnClickListener(this);
    }

    private void initView() {
        iv_locate = (ImageView) findViewById(R.id.iv_locate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_locate:
                finish();
                overridePendingTransition(R.anim.activity3_in,R.anim.activity3_out);
                break;
        }

    }
}
