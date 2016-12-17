package com.example.qwe.yunifang.fragment;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qwe.yunifang.CategoryShowActivity;
import com.example.qwe.yunifang.CategoryTypeActivity;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2016/11/28.
 */
public class CategoryFragment extends BaseFragment implements View.OnClickListener {


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
    private List<CategoryrRoot.DataEntity.CategoryEntity.ChildrenEntity> my_list = new ArrayList<>();
    private ImageView iv_category_one;
    private ImageView iv_category_two;
    private ImageView iv_category_three;
    private ImageView iv_category_four;
    private ImageView iv_category_five;
    private TextView tv_category_one;
    private TextView tv_category_two;
    private TextView tv_category_three;
    private TextView tv_category_four;
    private TextView tv_category_five;
    private TextView tv_category_six;

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

        cate_iv_gv.setAdapter(new MyCategoryLastShowAdapter(getActivity(), goodsBrief));
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
        iv_category_one = (ImageView) view.findViewById(R.id.iv_category_one);
        iv_category_two = (ImageView) view.findViewById(R.id.iv_category_two);
        iv_category_three = (ImageView) view.findViewById(R.id.iv_category_three);
        iv_category_four = (ImageView) view.findViewById(R.id.iv_category_four);
        iv_category_five = (ImageView) view.findViewById(R.id.iv_category_five);
        tv_category_one = (TextView) view.findViewById(R.id.tv_category_one);
        tv_category_two = (TextView) view.findViewById(R.id.tv_category_two);
        tv_category_three = (TextView) view.findViewById(R.id.tv_category_three);
        tv_category_four = (TextView) view.findViewById(R.id.tv_category_four);
        tv_category_five = (TextView) view.findViewById(R.id.tv_category_five);
        tv_category_six = (TextView) view.findViewById(R.id.tv_category_six);
        cate_iv_gv = (MyGridView) view.findViewById(R.id.cate_iv_gv);
    }

    /**
     * 指定图片大小
     */
    private void initPix() {
        cate_iv_big.setScaleType(ImageView.ScaleType.FIT_XY);
        ate_iv_online_first.setScaleType(ImageView.ScaleType.FIT_XY);
        cate_iv_online_first.setScaleType(ImageView.ScaleType.FIT_XY);
        cate_iv_twoline_first.setScaleType(ImageView.ScaleType.FIT_XY);
        cate_iv_twoline_second.setScaleType(ImageView.ScaleType.FIT_XY);
        cate_iv_threeline_only.setScaleType(ImageView.ScaleType.FIT_XY);
        // 设置监听
        cate_iv_big.setOnClickListener(this);
        ate_iv_online_first.setOnClickListener(this);
        cate_iv_online_first.setOnClickListener(this);
        cate_iv_twoline_first.setOnClickListener(this);
        cate_iv_twoline_second.setOnClickListener(this);
        cate_iv_threeline_only.setOnClickListener(this);
        iv_category_one.setOnClickListener(this);
        iv_category_two.setOnClickListener(this);
        iv_category_three.setOnClickListener(this);
        iv_category_four.setOnClickListener(this);
        iv_category_five.setOnClickListener(this);
        tv_category_one.setOnClickListener(this);
        tv_category_two.setOnClickListener(this);
        tv_category_three.setOnClickListener(this);
        tv_category_four.setOnClickListener(this);
        tv_category_five.setOnClickListener(this);
        tv_category_six.setOnClickListener(this);
    }

    @Override
    protected void onload() {

        myData = new MyData();
        myData.getData(URLUtils.categoryUrl, URLUtils.categoryArgs, 0, BaseData.NORMALTIME);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 左大图
            case R.id.cate_iv_big:
                Intent in = new Intent(getActivity(), CategoryShowActivity.class);
                in.putExtra("face", (Serializable) my_list);
                getActivity().startActivity(in);
                break;
            case R.id.cate_iv_online_first:
                enterIntent("39",CategoryTypeActivity.class);
                break;
            case R.id.cate_iv_online_second:
                enterIntent("40", CategoryTypeActivity.class);
                break;
            case R.id.cate_iv_twoline_first:
                enterIntent("24",CategoryTypeActivity.class);
                break;
            case    R.id.cate_iv_twoline_second:
                enterIntent("35",CategoryTypeActivity.class);
                break;
            case R.id.cate_iv_threeline_only:
                enterIntent("33",CategoryTypeActivity.class);
                break;
            case R.id.iv_category_one:
                initStart(0,CategoryShowActivity.class);
                break;
            case R.id.iv_category_two:
                initStart(1,CategoryShowActivity.class);
                break;
            case R.id.iv_category_three:
                initStart(2,CategoryShowActivity.class);
                break;
            case R.id.iv_category_four:
                initStart(3,CategoryShowActivity.class);
                break;
            case R.id.iv_category_five:
                initStart(4,CategoryShowActivity.class);
                break;
            case R.id.tv_category_one:
                initStartTwo(0,CategoryShowActivity.class);
                break;
            case R.id.tv_category_two:
                initStartTwo(1,CategoryShowActivity.class);
                break;
            case R.id.tv_category_three:
                initStartTwo(2,CategoryShowActivity.class);
                break;
            case R.id.tv_category_four:
                initStartTwo(3,CategoryShowActivity.class);
                break;
            case R.id.tv_category_five:
                initStartTwo(4,CategoryShowActivity.class);
                break;
            case R.id.tv_category_six:
                initStartTwo(5,CategoryShowActivity.class);
                break;
        }

    }

    private void initStart(int position,Class c) {
        Intent ent = new Intent(getActivity(),c);
        ent.putExtra("face", (Serializable) categoryrRoot.getData().getCategory().get(0).getChildren());
        ent.putExtra("position",position);
        getActivity().startActivity(ent);
    }
    private void initStartTwo(int position,Class c) {
        Intent ent = new Intent(getActivity(),c);
        ent.putExtra("face", (Serializable) categoryrRoot.getData().getCategory().get(2).getChildren());
        ent.putExtra("position",position);
        getActivity().startActivity(ent);
    }

    private void enterIntent(String id,Class c) {
        Intent intent = new Intent(getActivity(),c);
        intent.putExtra("type_id", id);
        getActivity().startActivity(intent);
    }

    class MyData extends BaseData {

        @Override
        public void setResultData(String data) {
            Gson gson = new Gson();
            categoryrRoot = gson.fromJson(data, CategoryrRoot.class);
            List<CategoryrRoot.DataEntity.CategoryEntity.ChildrenEntity> children = categoryrRoot.getData().getCategory().get(1).getChildren();
            for (int i = 0; i < children.size(); i++) {
                if (children.get(i).getCat_name().equals("贴式面膜") || children.get(i).getCat_name().equals("睡眠面膜") || children.get(i).getCat_name().equals("泥浆面膜")) {
                    my_list.add(children.get(i));
                }
            }

            CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);


        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
        }
    }

}
