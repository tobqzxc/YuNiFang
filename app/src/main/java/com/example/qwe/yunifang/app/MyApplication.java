package com.example.qwe.yunifang.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;

/**
 * Created by qwe on 2016/11/28.
 */
public class MyApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainThreadId;
    public static boolean intentFlag = false;

    @Override
    public void onCreate() {
        super.onCreate();

        //默认使用的高度是设备的可用高度，也就是不包括状态栏和底部的操作栏的，如果你希望拿设备的物理高度进行百分比化：
        //可以在Application的onCreate方法中进行设置:
        AutoLayoutConifg.getInstance().useDeviceSize();
        //获取当前应用的上下文
        context = getApplicationContext();
        handler = new Handler();
        //获取主线程的线程号
        mainThreadId = Process.myTid();
        //imageLoader初始化
        ImageLoaderUtils.initConfiguration(this);
        //xutils3初始化配置
        x.Ext.init(this);
        //设置是debug模式
        x.Ext.setDebug(true);
    }

    /**
     * 获得主线程ID
     * @return
     */

    public static int getMainThreadId(){
        return mainThreadId;
    }
    /**
     * 获得Handler
     * @return
     */
    public static Handler getHandler() {
        return handler;
    }
    /**
     * 获得当前线程ID
     * @return
     */
    public static Thread getMainThread(){
        return Thread.currentThread();
    }
    /**
     * 获得这个应用的上下文
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
