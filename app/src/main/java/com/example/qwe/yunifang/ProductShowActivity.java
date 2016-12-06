package com.example.qwe.yunifang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductShowActivity extends AppCompatActivity {

    private WebView wv_show;
    private TextView tv_product_title;
    private ImageView iv_product_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_show);
        tv_product_title = (TextView) findViewById(R.id.tv_product_title);
        iv_product_back = (ImageView) findViewById(R.id.iv_product_back);
        wv_show = (WebView) findViewById(R.id.wv_show);
        //展示
        String url = getIntent().getStringExtra("url");
        wv_show.loadUrl(url);
        wv_show.setWebViewClient(new WebViewClient());
    }
}
