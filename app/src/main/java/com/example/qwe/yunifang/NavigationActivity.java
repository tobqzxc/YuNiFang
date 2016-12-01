package com.example.qwe.yunifang;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.qwe.yunifang.utlis.CommonUtils;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener {

    private int i = 5;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            start.setText(i + " s");
            i--;
            if (i == 0) {
                StartIntent();
            }
            handler.sendEmptyMessageDelayed(0, 1000);
        }
    };

    private void StartIntent() {
        Intent in = new Intent(NavigationActivity.this, MainActivity.class);
        startActivity(in);
        overridePendingTransition(R.anim.activity2_in, R.anim.activity2_out);
        handler.removeCallbacksAndMessages(null);
    }

    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_navigation);
        start = (Button) findViewById(R.id.start);
        start.setOnClickListener(this);

        new Thread() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        }.start();


    }

    @Override
    public void onClick(View v) {
        StartIntent();
    }
}
