package com.example.qwe.yunifang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.adapter.MyGridCategoryAdapter;
import com.example.qwe.yunifang.base.BaseData;
import com.example.qwe.yunifang.bean.CategoryFaceRoot;
import com.example.qwe.yunifang.utlis.URLUtils;
import com.example.qwe.yunifang.view.MyGridView;
import com.example.qwe.yunifang.view.ShowingPage;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by qwe on 2016/12/13.
 */
public class CategoryShowFragment extends Fragment {

    private GridView gv_category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.grid_category_item, null);
        gv_category = (GridView) view.findViewById(R.id.gv_category);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String id = this.getArguments().getString("id");
        MyNetData data = new MyNetData();
        data.getData(URLUtils.CategoryShow,id,0,BaseData.NOTIME);
    }

    public static Fragment getFragment(String id){
        CategoryShowFragment fragment = new CategoryShowFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",id);
        fragment.setArguments(bundle);
        return fragment;
    }
    class MyNetData extends BaseData{

        @Override
        public void setResultData(String data) {
            Gson gson = new Gson();
            CategoryFaceRoot categoryFaceRoot = gson.fromJson(data, CategoryFaceRoot.class);
            List<CategoryFaceRoot.DataEntity> entityList = categoryFaceRoot.getData();
            gv_category.setAdapter(new MyGridCategoryAdapter(entityList,getActivity()));
        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {

        }
    }
}
