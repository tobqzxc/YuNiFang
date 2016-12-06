package com.example.qwe.yunifang.fragment;

import android.view.View;
import android.widget.ImageView;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.adapter.MyCategoryLastShowAdapter;
import com.example.qwe.yunifang.base.BaseData;
import com.example.qwe.yunifang.base.BaseFragment;
import com.example.qwe.yunifang.bean.CategoryrRoot;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.example.qwe.yunifang.utlis.LogUtils;
import com.example.qwe.yunifang.utlis.URLUtils;
import com.example.qwe.yunifang.view.MyGridView;
import com.example.qwe.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by qwe on 2016/11/28.
 */
public class CategoryFragment extends BaseFragment {


    private String s;
    private ImageView imageView;
    private MyData myData;
    private CategoryrRoot categoryrRoot;
    private View view;
    private ImageView cate_iv_big;
    private ImageView ate_iv_online_first;
    private ImageView cate_iv_online_first;
    private ImageView cate_iv_twoline_first;
    private ImageView cate_iv_twoline_second;
    private ImageView cate_iv_threeline_only;
    private MyGridView cate_iv_gv;

    @Override
    protected View createSuccessView() {
        //查找控件
        initView();
        // 指定图片的大小
        initPix();
        // 给GV设置适配器
        initGV();


        return view;
    }

    /**
     * 最后的商品展示
     */

    private void initGV() {

        List<CategoryrRoot.DataEntity.GoodsBriefEntity> goodsBrief = categoryrRoot.getData().getGoodsBrief();

        cate_iv_gv.setAdapter(new MyCategoryLastShowAdapter(getActivity(),goodsBrief));
        cate_iv_gv.setHorizontalSpacing(CommonUtils.dip2px(10));
        cate_iv_gv.setVerticalSpacing(CommonUtils.dip2px(10));

    }

    /**
     * 查找控件
     */

    private void initView() {
        view = CommonUtils.inflate(R.layout.categoryfragment);
        cate_iv_big = (ImageView) view.findViewById(R.id.cate_iv_big);
        ate_iv_online_first = (ImageView) view.findViewById(R.id.cate_iv_online_first);
        cate_iv_online_first = (ImageView) view.findViewById(R.id.cate_iv_online_second);
        cate_iv_twoline_first = (ImageView) view.findViewById(R.id.cate_iv_twoline_first);
        cate_iv_twoline_second = (ImageView) view.findViewById(R.id.cate_iv_twoline_second);
        cate_iv_threeline_only = (ImageView) view.findViewById(R.id.cate_iv_threeline_only);
        cate_iv_gv = (MyGridView) view.findViewById(R.id.cate_iv_gv);
    }

    /**
     * 指定图片大小
     */
    private void initPix(){
        cate_iv_big .setScaleType(ImageView.ScaleType.FIT_XY);
        ate_iv_online_first .setScaleType(ImageView.ScaleType.FIT_XY);
        cate_iv_online_first .setScaleType(ImageView.ScaleType.FIT_XY);
        cate_iv_twoline_first .setScaleType(ImageView.ScaleType.FIT_XY);
        cate_iv_twoline_second .setScaleType(ImageView.ScaleType.FIT_XY);
        cate_iv_threeline_only .setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @Override
    protected void onload() {

        myData = new MyData();
        myData.getData(URLUtils.categoryUrl, URLUtils.categoryArgs, 0, BaseData.NORMALTIME);

    }

    class MyData extends BaseData {

        @Override
        public void setResultData(String data) {
            Gson gson = new Gson();
            categoryrRoot = gson.fromJson(data, CategoryrRoot.class);
            CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }
    }
}
