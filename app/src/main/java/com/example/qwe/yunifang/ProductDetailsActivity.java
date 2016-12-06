package com.example.qwe.yunifang;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qwe.yunifang.base.BaseData;
import com.example.qwe.yunifang.bean.HomeRoot;
import com.example.qwe.yunifang.bean.ProductRoot;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.URLUtils;
import com.example.qwe.yunifang.view.MyListView;
import com.example.qwe.yunifang.view.MyRoolViewPager;
import com.example.qwe.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ProductRoot productRoot;
    private HomeRoot.DataEntity.SubjectsEntity.GoodsListEntity product_bean;
    private MyRoolViewPager vp_product_details;
    private TextView tv_product_details_title;
    private ArrayList<String> imgUrlList = new ArrayList<>();
    private int[] dotArray = new int[]{R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    private ArrayList<ImageView> dotList = new ArrayList<>();
    private AutoLinearLayout ll_product_dots;
    private TextView tv_product_price;
    private TextView tv_product_first;
    private TextView tv_product_second;
    private TextView tv_product_third;
    private MyListView lv_product;
    private CheckBox cb_partculars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_details);
        initView();
        product_bean = (HomeRoot.DataEntity.SubjectsEntity.GoodsListEntity) getIntent().getSerializableExtra("product_bean");
        if (product_bean != null) {
            ProductData productData = new ProductData();
            productData.getData(URLUtils.productDetails, product_bean.getId(), 0, BaseData.NOTIME);

        }

    }

    /**
     * 查找ID
     */
    private void initView() {
        vp_product_details = (MyRoolViewPager) findViewById(R.id.vp_product_details);
        tv_product_details_title = (TextView) findViewById(R.id.tv_product_details_title);
        ll_product_dots = (AutoLinearLayout) findViewById(R.id.ll_product_dots);
        tv_product_price = (TextView) findViewById(R.id.tv_product_price);
        lv_product = (MyListView) findViewById(R.id.lv_product);
        cb_partculars = (CheckBox) findViewById(R.id.cb_partculars);
    }

    /**
     * 请求数据
     */
    class ProductData extends BaseData {

        private ProductDetailsActivity productDetailsActivity;

        @Override
        public void setResultData(String data) {
            String s = data.toString();
            Gson gson = new Gson();
            productRoot = gson.fromJson(data, ProductRoot.class);
            //设置适配器
            initPager();
            // 填写数据
            initData();
            // 监听
            initLister();


        }

        private void initLister() {
            // 判断多选框是否选中
            cb_partculars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        cb_partculars.setText("已收藏");
                        cb_partculars.setTextColor(getResources().getColor(R.color.color_true));
                    }else{
                        cb_partculars.setText("收藏");
                        cb_partculars.setTextColor(Color.BLACK);
                    }
                }
            });
        }


        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {

        }
    }

    /**
     * 设置适配器
     */
    private void initPager() {
        List<ProductRoot.DataEntity.GoodsEntity.GalleryEntity> gallery = productRoot.getData().getGoods().getGallery();
        for (int i = 0; i < gallery.size(); i++) {
            imgUrlList.add(gallery.get(i).getNormal_url());
        }
        initDots(gallery);
        vp_product_details.initData(imgUrlList,dotArray,dotList);
        vp_product_details.startAdapter();
    }

    /**
     * 填写数据
     */

    private void initData() {
        tv_product_details_title.setText(productRoot.getData().getGoods().getGoods_name());
        tv_product_price.setText("￥:"+productRoot.getData().getGoods().getShop_price());

        lv_product.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return productRoot.getData().getActivity().size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = CommonUtils.inflate(R.layout.particulars_activity_listview_item);
                TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                tv_title.setText(productRoot.getData().getActivity().get(position).getTitle());
                return convertView;
            }
        });
        lv_product.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 添加小点
     * @param gallery
     */
    private void initDots(List<ProductRoot.DataEntity.GoodsEntity.GalleryEntity> gallery) {
        dotList.clear();
        ll_product_dots.removeAllViews();
        for (int i = 0; i < gallery.size(); i++) {
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setImageResource(dotArray[0]);
            } else {
                imageView.setImageResource(dotArray[1]);
            }
            dotList.add(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 5, 10, 5);
            ll_product_dots.addView(imageView, params);
        }
    }
}
