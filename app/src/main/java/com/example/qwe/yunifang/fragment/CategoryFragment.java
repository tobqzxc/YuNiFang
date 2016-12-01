package com.example.qwe.yunifang.fragment;

import android.view.View;
import android.widget.ImageView;

import com.example.qwe.yunifang.base.BaseFragment;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.example.qwe.yunifang.utlis.LogUtils;
import com.example.qwe.yunifang.view.ShowingPage;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by qwe on 2016/11/28.
 */
public class CategoryFragment extends BaseFragment {


    private String s ;
    private ImageView imageView;

    @Override
    protected View createSuccessView() {
        imageView = new ImageView(getContext());
        ImageLoader.getInstance().displayImage("http://www.people.com.cn/mediafile/pic/20161103/40/15297183894447913252.jpg",imageView, ImageLoaderUtils.initOptions());

        return imageView;
    }

    @Override
    protected void onload() {
       /* RequestParams requestParams = new RequestParams("http://www.people.com.cn/mediafile/pic/20161103/40/15297183894447913252.jpg");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                LogUtils.d("TAG",s+"+++++++++++++++++");
                CategoryFragment.this.s= s;
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
        */
        CategoryFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);

    }
}
