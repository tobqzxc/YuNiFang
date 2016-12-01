package com.example.qwe.yunifang.factory;

import android.support.v4.app.Fragment;


import com.example.qwe.yunifang.fragment.CartFragment;
import com.example.qwe.yunifang.fragment.CategoryFragment;
import com.example.qwe.yunifang.fragment.HomeFragment;
import com.example.qwe.yunifang.fragment.MineFragment;

import java.util.HashMap;

/**
 * Created by zhiyuan on 16/9/28.
 */
public class FragmentFactory {
    //集合
    public static HashMap<Integer, Fragment> fragmentMap = new HashMap<>();

    public static Fragment getFragment(int position) {
        Fragment fragment = fragmentMap.get(position);
        if (fragment != null) {
            return fragment;
        }
        switch (position) {
            case 0:
                fragment=new HomeFragment();
                break;
            case 1:
                fragment=new CategoryFragment();
                break;
            case 2:
                fragment=new CartFragment();
                break;
            case 3:
                fragment=new MineFragment();
                break;
        }
        //添加到集合中
        fragmentMap.put(position,fragment);
        return  fragment;
    }
}
