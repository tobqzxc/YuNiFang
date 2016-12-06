package com.example.qwe.yunifang.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qwe.yunifang.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by qwe on 2016/12/1.
 */
public class MyRoolViewPager extends ViewPager {

    private static final int ROOL = 0;
    private DisplayImageOptions options;
    private ArrayList<String> imgUrlList;
    private ArrayList<ImageView> doList;
    private MyPagerAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentItem = MyRoolViewPager.this.getCurrentItem();
            currentItem++;
            MyRoolViewPager.this.setCurrentItem(currentItem);
            handler.sendEmptyMessageDelayed(ROOL, 2000);
        }
    };
    private OnPageClickListener onPageClickListener;

    public MyRoolViewPager(Context context) {
        super(context);
        init();
    }


    public MyRoolViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 加载ImageLolder
        options = ImageLoaderUtils.initOptions();
    }

    /**
     * 初始化数据
     */

    public void initData(final ArrayList<String> imgUrlList, final int[] dotArray, final ArrayList<ImageView> dotList) {
        this.imgUrlList = imgUrlList;
        this.doList = dotList;
        this.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotList.size(); i++) {
                    if (position % dotList.size() == i) {
                        doList.get(i).setImageResource(dotArray[0]);
                    } else {
                        doList.get(i).setImageResource(dotArray[1]);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public void startAdapter() {
        if (adapter == null) {
            adapter = new MyPagerAdapter();
        }
        MyRoolViewPager.this.setAdapter(adapter);
        handler.sendEmptyMessageDelayed(ROOL, 2000);
    }


    @Override
    //当这一个页面消失的时候退出滑动
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final View view = CommonUtils.inflate(R.layout.roolviewpage_item);
            ImageView iv_roolpage = (ImageView) view.findViewById(R.id.iv_roolpage);
            ImageLoader.getInstance().displayImage(imgUrlList.get(position % imgUrlList.size()), iv_roolpage, options);
            container.addView(view);
            view.setOnTouchListener(new OnTouchListener() {
                private long downTime;
                private float downY;
                private float downX;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            downX = event.getX();
                            downY = event.getY();
                            downTime = System.currentTimeMillis();
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            break;
                        case MotionEvent.ACTION_UP:
                            float upX = event.getX();
                            float upY = event.getY();
                            if (upX == downX && upY == downY && System.currentTimeMillis() - downTime < 1000) {
                                //设置点击事件
//                                Toast.makeText(getContext(), "点击", Toast.LENGTH_SHORT).show();
                                //跳转界面
                                //设置监听的回调
                                if (onPageClickListener != null) {
                                    onPageClickListener.setOnPage(position);
                                }
                            }

                        case MotionEvent.ACTION_CANCEL:
                            handler.sendEmptyMessageDelayed(ROOL, 2000);
                            break;
                    }

                    return true;
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

    }

    public interface OnPageClickListener {
        public void setOnPage(int position);
    }

    public void setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.onPageClickListener = onPageClickListener;
    }
}
