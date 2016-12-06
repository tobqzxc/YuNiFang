package com.example.qwe.yunifang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.qwe.yunifang.adapter.MyDescriptionShowAdapter;
import com.example.qwe.yunifang.bean.HomeRoot;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.view.MyGridView;
import com.example.qwe.yunifang.view.MyListView;

import java.io.Serializable;
import java.util.List;

public class DescriptionActivity extends AppCompatActivity {

    private HomeRoot.DataEntity.SubjectsEntity bean;
    private TextView tv_describe_title;
    private TextView tv_describe_detail;
    private MyGridView gv_describe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_description);
        tv_describe_title = (TextView) findViewById(R.id.tv_describe_title);
        tv_describe_detail = (TextView) findViewById(R.id.tv_describe_detail);
        gv_describe = (MyGridView) findViewById(R.id.gv_describe);
        // 得到解析对象
        bean = (HomeRoot.DataEntity.SubjectsEntity) getIntent().getSerializableExtra("bean");
        if(bean!=null){
            tv_describe_title.setText(bean.getTitle());
            tv_describe_detail.setText(bean.getDetail());
            List<HomeRoot.DataEntity.SubjectsEntity.GoodsListEntity> goodsList = bean.getGoodsList();
            gv_describe.setAdapter(new MyDescriptionShowAdapter(this,goodsList));
            gv_describe.setHorizontalSpacing(CommonUtils.dip2px(10));
            gv_describe.setVerticalSpacing(CommonUtils.dip2px(10));
        }


    }
}
