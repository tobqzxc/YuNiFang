package com.example.qwe.yunifang;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class LanuchActivity extends AppCompatActivity {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            handler.removeCallbacksAndMessages(null);
            Intent in = new Intent(LanuchActivity.this, NavigationActivity.class);
            startActivity(in);
            overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lanuch);
        new Thread() {
            @Override
            public void run() {
                    handler.sendEmptyMessageDelayed(0, 2000);
            }
        }.start();
    }

}
