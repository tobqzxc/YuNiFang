package com.example.qwe.yunifang.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.adapter.MyGridAdapter;
import com.example.qwe.yunifang.adapter.MyHotSubjectAdapter;
import com.example.qwe.yunifang.adapter.MyLastShowAdapter;
import com.example.qwe.yunifang.base.BaseData;
import com.example.qwe.yunifang.base.BaseFragment;
import com.example.qwe.yunifang.bean.HomeRoot;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.example.qwe.yunifang.utlis.LogUtils;
import com.example.qwe.yunifang.utlis.URLUtils;
import com.example.qwe.yunifang.view.MyRoolViewPager;
import com.example.qwe.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2016/11/28.
 */
public class HomeFragment extends BaseFragment implements SpringView.OnFreshListener {
    private HomeRoot root;
    private AutoLinearLayout ll_dots;
    private SpringView sv_spring;
    private MyRoolViewPager viewPager;
    private View view;
    int[] dotArray = new int[]{R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};

    ArrayList<String> imgUrlList = new ArrayList<>();
    ArrayList<ImageView> dotList = new ArrayList<>();
    private GridView gv_content;
    private AutoLinearLayout hsv_linear;
    private ListView lv_rm;
    private GridView more_gv;

    @Override
    protected void onload() {
        MyHomeData myHomeData = new MyHomeData();
        myHomeData.getData(URLUtils.homeUrl, URLUtils.homeArgs, 0, BaseData.NORMALTIME);
        HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }


    @Override
    protected View createSuccessView() {
        // 查找控件
        initView();
        // 初始化轮播图
        initRollViewPager();
        // 添加GridView
        initGrid();
        // 添加横向gv展示
        initHsvGv();
        // 添加竖向展示
        initlv();
        // 最下便的Grid展示
        initLaseGrid();
        return view;
    }

    /**
     * 最下边的商品展示
     */

    private void initLaseGrid() {
        List<HomeRoot.DataEntity.DefaultGoodsListEntity> defaultGoodsList = root.getData().getDefaultGoodsList();
        more_gv.setAdapter(new MyLastShowAdapter(getActivity(),defaultGoodsList));
        more_gv.setHorizontalSpacing(CommonUtils.dip2px(10));
        more_gv.setVerticalSpacing(CommonUtils.dip2px(10));
    }

    /**
     * 热门专题
     */

    private void initlv() {
        List<HomeRoot.DataEntity.SubjectsEntity> subjects = root.getData().getSubjects();
        lv_rm.setAdapter(new MyHotSubjectAdapter(getActivity(),subjects));

    }

    /**
     * 本周热销展示商品
     */

    private void initHsvGv() {
        hsv_linear.removeAllViews();
        List<HomeRoot.DataEntity.BestSellersEntity.GoodsListEntity> goodsList = root.getData().getBestSellers().get(0).getGoodsList();
        for (int i = 0; i < 6; i++) {
            View view = CommonUtils.inflate(R.layout.hsv_gv_item);
            ImageView iv_hsv_gv = (ImageView) view.findViewById(R.id.iv_hsv_gv);
            TextView tv_hsv_title = (TextView) view.findViewById(R.id.tv_hsv_title);
            TextView tv_hsv_price = (TextView) view.findViewById(R.id.tv_hsv_price);
            tv_hsv_title.setText(goodsList.get(i).getGoods_name());
            tv_hsv_price.setText("￥: "+goodsList.get(i).getShop_price());
            ImageLoader.getInstance().displayImage(goodsList.get(i).getGoods_img(), iv_hsv_gv, ImageLoaderUtils.initOptions());
            tv_hsv_title.setGravity(Gravity.CENTER);
            tv_hsv_price.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(CommonUtils.dip2px(140),LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5,5,5,5);
            hsv_linear.addView(view,params);
        }
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageResource(R.mipmap.other);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(5,5,5,5);
        hsv_linear.addView(imageView);

    }

    private void initRollViewPager() {
        System.out.println(root);
        System.out.println(root.getData());
        List<HomeRoot.DataEntity.Ad1Entity> ad1 = root.getData().getAd1();
        for (int i = 0; i < ad1.size(); i++) {
            imgUrlList.add(ad1.get(i).getImage());
        }
        initDots(ad1);
        viewPager.initData(imgUrlList, dotArray, dotList);
        viewPager.startAdapter();

    }

    /**
     * 展示GridViewTitle
     *
     * @param
     */
    public void initGrid() {
        List<HomeRoot.DataEntity.Ad5Entity> ad5 = root.getData().getAd5();

        MyGridAdapter adapter = new MyGridAdapter(ad5, getActivity());

        gv_content.setAdapter(adapter);

        gv_content.setSelector(new ColorDrawable(Color.TRANSPARENT));

    }

    private void initDots(List<HomeRoot.DataEntity.Ad1Entity> list) {
        dotList.clear();
        ll_dots.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            if (i == 0) {
                imageView.setImageResource(dotArray[0]);
            } else {
                imageView.setImageResource(dotArray[1]);
            }
            dotList.add(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 5, 10, 5);
            ll_dots.addView(imageView, params);
        }

    }

    @NonNull
    private View initView() {
        view = CommonUtils.inflate(R.layout.homefragment_item);
        viewPager = (MyRoolViewPager) view.findViewById(R.id.viewPager);
        sv_spring = (SpringView) view.findViewById(R.id.sv_spring);
        ll_dots = (AutoLinearLayout) view.findViewById(R.id.ll_dots);
        gv_content = (GridView) view.findViewById(R.id.gv_content);
        hsv_linear = (AutoLinearLayout) view.findViewById(R.id.hsv_linear);
        lv_rm = (ListView) view.findViewById(R.id.lv_rm);
        more_gv = (GridView) view.findViewById(R.id.more_gv);
        sv_spring.setListener(this);
        return view;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadmore() {

    }

    class MyHomeData extends BaseData {
        @Override
        public void setResultData(String data) {
            LogUtils.d("TAG", data);
            Gson gson = new Gson();
            root = gson.fromJson(data, HomeRoot.class);
            HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            //失败
            HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        }
    }

}
