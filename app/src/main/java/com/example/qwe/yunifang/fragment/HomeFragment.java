package com.example.qwe.yunifang.fragment;

import android.view.View;

import com.example.qwe.yunifang.base.BaseFragment;
import com.example.qwe.yunifang.view.ShowingPage;

/**
 * Created by qwe on 2016/11/28.
 */
public class HomeFragment extends BaseFragment {
    @Override
    protected View createSuccessView() {
        return null;
    }
    @Override
    protected void onload() {
        HomeFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);
    }
}
