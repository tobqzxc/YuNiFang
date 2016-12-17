package com.example.qwe.yunifang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;

import com.example.qwe.yunifang.adapter.MyGridCategoryAdapter;
import com.example.qwe.yunifang.base.BaseData;
import com.example.qwe.yunifang.bean.CategoryFaceRoot;
import com.example.qwe.yunifang.utlis.URLUtils;
import com.example.qwe.yunifang.view.ShowingPage;
import com.google.gson.Gson;

import java.util.List;

public class CategoryTypeActivity extends AppCompatActivity {

    private GridView gv_category_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_category_type);
        gv_category_type = (GridView) findViewById(R.id.gv_category_type);
        String type_id = getIntent().getStringExtra("type_id");
        MyDataType dataType = new MyDataType();
        dataType.getData(URLUtils.CategoryShow,type_id,0,BaseData.NOTIME);



    }

    class MyDataType extends BaseData{

        @Override
        public void setResultData(String data) {
            Gson gson =new Gson();
            CategoryFaceRoot categoryFaceRoot = gson.fromJson(data, CategoryFaceRoot.class);
            List<CategoryFaceRoot.DataEntity> entityList = categoryFaceRoot.getData();
            gv_category_type.setAdapter(new MyGridCategoryAdapter(entityList,CategoryTypeActivity.this));

        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {

        }
    }




}
