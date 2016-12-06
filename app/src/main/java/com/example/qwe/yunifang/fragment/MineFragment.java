package com.example.qwe.yunifang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.RegisterActivity;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.lanuch.LocateActivity;

/**
 * Created by qwe on 2016/11/28.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private Button locate;
    private Button entry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = CommonUtils.inflate(R.layout.mine_view);
        // 查找控件
        initView(view);
        // 设置监听
        initeLister();


        return view;

    }

    private void initeLister() {
        locate.setOnClickListener(this);
        entry.setOnClickListener(this);

    }

    private void initView(View view) {
        locate = (Button) view.findViewById(R.id.locate);
        entry = (Button) view.findViewById(R.id.entry);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.locate:
                Intent in = new Intent(getActivity(), LocateActivity.class);
                startActivity(in);
                getActivity().overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
                break;
            case R.id.entry:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
                break;
        }

    }
}
