package com.example.qwe.yunifang.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.qwe.yunifang.base.BaseData;
import com.example.qwe.yunifang.base.BaseFragment;
import com.example.qwe.yunifang.view.ShowingPage;

/**
 * Created by qwe on 2016/11/28.
 */
public class CartFragment extends BaseFragment {
    private String data;

    @Override
    protected View createSuccessView() {
        TextView  textView = new TextView(getContext());
        textView.setText(data);
        return textView;
    }

    @Override
    protected void onload(){
        BaseData baseData  = new BaseData() {
            @Override
            public void setResultData(String data) {
                CartFragment.this.data = data;
                CartFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
            }

            @Override
            protected void setResulttError(ShowingPage.StateType stateLoadError) {

            }
        };
        baseData.getData("http://www.baidu.com",null,0,0);
    }
}
