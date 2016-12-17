package com.example.qwe.yunifang;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qwe.yunifang.adapter.MyCanShuShowAdapter;
import com.example.qwe.yunifang.adapter.MyCommentShowAdapter;
import com.example.qwe.yunifang.adapter.MyPicAdapter;
import com.example.qwe.yunifang.base.BaseData;
import com.example.qwe.yunifang.bean.HomeRoot;
import com.example.qwe.yunifang.bean.PicRoot;
import com.example.qwe.yunifang.bean.ProductRoot;
import com.example.qwe.yunifang.sql.Product;
import com.example.qwe.yunifang.sql.ProductDao;
import com.example.qwe.yunifang.utlis.CommonUtils;
import com.example.qwe.yunifang.utlis.ImageLoaderUtils;
import com.example.qwe.yunifang.utlis.URLUtils;
import com.example.qwe.yunifang.view.MyListView;
import com.example.qwe.yunifang.view.MyRoolViewPager;
import com.example.qwe.yunifang.view.MyScrollView;
import com.example.qwe.yunifang.view.ShowingPage;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity implements MyScrollView.OnScrollListener, View.OnClickListener {

    private ProductRoot productRoot;
    private HomeRoot.DataEntity.SubjectsEntity.GoodsListEntity product_bean;
    private MyRoolViewPager vp_product_details;
    private TextView tv_product_details_title;
    private ArrayList<String> imgUrlList = new ArrayList<>();
    private int[] dotArray = new int[]{R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    private ArrayList<ImageView> dotList = new ArrayList<>();
    private AutoLinearLayout ll_product_dots;
    private TextView tv_product_price;
    private TextView tv_product_first;
    private TextView tv_product_second;
    private TextView tv_product_third;
    private MyListView lv_product;
    private CheckBox cb_partculars;
    private RadioGroup rg_product_one;
    private RadioGroup rg_product_two;
    private MyScrollView sv_product;
    private int searchLayoutTop;
    private MyListView lv_product_show;
    private RadioButton btn1;
    private RadioButton btn2;
    private RadioButton btn3;
    private List<ProductRoot.DataEntity.GoodsEntity.AttributesEntity> attributes;
    private List<String> list;
    private List<ProductRoot.DataEntity.CommentsEntity> comments;
    private Button btn_product_join;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    backgroundAlpha((float) msg.obj);
                    break;
            }
        }
    };
    private int defaultShowNumber = 1;
    private PopupWindow popupWindow;
    private Button reduce_number;
    private Button show_number;
    private Button add_number;
    private Button cart_pop_sure_button;
    private Button btn_product_join_buy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_details);
        initView();
        product_bean = (HomeRoot.DataEntity.SubjectsEntity.GoodsListEntity) getIntent().getSerializableExtra("product_bean");
        if (product_bean != null) {
            ProductData productData = new ProductData();
            productData.getData(URLUtils.productDetails, product_bean.getId(), 0, BaseData.NOTIME);
        }
        // 对Scrollview进行监听、操作
        sv_product.setOnScrollListener(this);

        rg_product_two.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                for (int i = 0; i < rg_product_two.getChildCount(); i++) {
                    RadioButton childAt_two = (RadioButton) rg_product_two.getChildAt(i);
                    if (childAt_two.getId() == checkedId) {
                        childAt_two.setTextColor(getResources().getColor(R.color.color_true));
                        if (i == 0) {
                            lv_product_show.setAdapter(new MyPicAdapter(list, ProductDetailsActivity.this));
                        }
                        if (i == 1) {
                            Log.i("TAG", "onCheckedChanged: ------------" + attributes);
                            lv_product_show.setAdapter(new MyCanShuShowAdapter(ProductDetailsActivity.this, attributes));
                        }
                        if (i == 2) {
                            lv_product_show.setAdapter(new MyCommentShowAdapter(ProductDetailsActivity.this, comments));
                        }
                    } else {
                        childAt_two.setTextColor(Color.BLACK);
                    }
                }
            }
        });
    }

    /**
     * bottom弹出框
     *
     * @param view
     */
    public void bottomwindow(View view) {
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        View inflate = CommonUtils.inflate(R.layout.cart_pop);
        initPopupWindowView(inflate);
        popupWindow = new PopupWindow(inflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加pop窗口关闭事件，主要是实现关闭时改变背景的透明度
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hidePopupWindow();
            }
        });
        backgroundAlpha(1f);
    }

    /**
     * 显示弹出框
     */
    private void showPopupWidow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                float alpha = 1f;
                while (alpha > 0.5f) {
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha -= 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
    }

    /**
     * 查找popup中的控件
     *
     * @param inflate
     */
    private void initPopupWindowView(View inflate) {
        ImageView pop_cart_pop_image = (ImageView) inflate.findViewById(R.id.cart_pop_image);
        TextView pop_shop_price = (TextView) inflate.findViewById(R.id.pop_show_price);
        TextView pop_stock_number = (TextView) inflate.findViewById(R.id.stock_number);
        TextView pop_restrict_purchase_num = (TextView) inflate.findViewById(R.id.restrict_purchase_num);


        ImageLoader.getInstance().displayImage(productRoot.getData().getGoods().getGoods_img(), pop_cart_pop_image, ImageLoaderUtils.initOptionsCircol());
        pop_shop_price.setText("￥" + productRoot.getData().getGoods().getShop_price());
        pop_stock_number.setText("库存" + productRoot.getData().getGoods().getStock_number() + "件");
        pop_restrict_purchase_num.setText("限购" + productRoot.getData().getGoods().getRestrict_purchase_num() + "件");

        reduce_number = (Button) inflate.findViewById(R.id.reduce_number);
        show_number = (Button) inflate.findViewById(R.id.show_number);
        add_number = (Button) inflate.findViewById(R.id.add_number);
        cart_pop_sure_button = (Button) inflate.findViewById(R.id.cart_pop_sure_button);

        show_number.setText(defaultShowNumber + "");

        reduce_number.setOnClickListener(this);
        add_number.setOnClickListener(this);
        cart_pop_sure_button.setOnClickListener(this);
    }

    /**
     * 隐藏弹出框
     */
    private void hidePopupWindow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //此处while的条件alpha不能<= 否则会出现黑屏
                float alpha = 0.5f;
                while (alpha < 0.9f) {
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("HeadPortrait", "alpha:" + alpha);
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    alpha += 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
    }

    /**
     * 背景透明
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //获取searchLayout的顶部位置
            searchLayoutTop = lv_product_show.getTop();
        }
    }

    /**
     * 查找ID
     */
    private void initView() {
        vp_product_details = (MyRoolViewPager) findViewById(R.id.vp_product_details);
        tv_product_details_title = (TextView) findViewById(R.id.tv_product_details_title);
        ll_product_dots = (AutoLinearLayout) findViewById(R.id.ll_product_dots);
        tv_product_price = (TextView) findViewById(R.id.tv_product_price);
        lv_product = (MyListView) findViewById(R.id.lv_product);
        cb_partculars = (CheckBox) findViewById(R.id.cb_partculars);
        rg_product_one = (RadioGroup) findViewById(R.id.rg_product_one);
        rg_product_two = (RadioGroup) findViewById(R.id.rg_product_two);
        sv_product = (MyScrollView) findViewById(R.id.sv_product);
        lv_product_show = (MyListView) findViewById(R.id.lv_product_show);
        btn_product_join = (Button) findViewById(R.id.btn_product_join);
        btn_product_join_buy = (Button) findViewById(R.id.btn_product_join_buy);
        btn1 = (RadioButton) findViewById(R.id.btn1);
        btn2 = (RadioButton) findViewById(R.id.btn2);
        btn3 = (RadioButton) findViewById(R.id.btn3);
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY > searchLayoutTop) {
            rg_product_one.setVisibility(View.VISIBLE);
            btn1.setVisibility(View.VISIBLE);
            btn2.setVisibility(View.VISIBLE);
            btn3.setVisibility(View.VISIBLE);
        } else {
            rg_product_one.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // 减少数量
            case R.id.reduce_number:
                if (defaultShowNumber <= productRoot.getData().getGoods().getRestrict_purchase_num()) {
                    if (defaultShowNumber != 1) {
                        defaultShowNumber--;
                        show_number.setText(defaultShowNumber + "");
                        reduce_number.setTextColor(Color.BLACK);
                    }
                    if (defaultShowNumber == 1) {
                        reduce_number.setTextColor(Color.GRAY);
                        add_number.setTextColor(Color.BLACK);
                    }
                }
                break;
            //增加数量
            case R.id.add_number:
                if (defaultShowNumber <= productRoot.getData().getGoods().getRestrict_purchase_num()) {
                    if (defaultShowNumber != productRoot.getData().getGoods().getRestrict_purchase_num()) {
                        defaultShowNumber++;
                        show_number.setText(defaultShowNumber + "");
                        add_number.setTextColor(Color.BLACK);
                    }
                    if (defaultShowNumber == productRoot.getData().getGoods().getRestrict_purchase_num()) {
                        add_number.setTextColor(Color.GRAY);
                        reduce_number.setTextColor(Color.BLACK);
                    }
                }
                break;


            case R.id.cart_pop_sure_button:
                ProductDao dao = new ProductDao(ProductDetailsActivity.this);
                String name = productRoot.getData().getGoods().getGoods_name();
                String price = productRoot.getData().getGoods().getShop_price() + "";
                String id = productRoot.getData().getGoods().getId();
                String url = productRoot.getData().getGoods().getGoods_img();
                Product product = new Product(name, price, id, url, defaultShowNumber);
                // url productRoot.getData().getGoods().getGoods_img()
                // name
                //price productRoot.getData().getGoods().getShop_price()
                dao.add(product, defaultShowNumber);
                Toast.makeText(ProductDetailsActivity.this, "恭喜您,成功添加到购物车", Toast.LENGTH_SHORT).show();
                break;


        }


    }


    /**
     * 请求数据
     */
    class ProductData extends BaseData {

        private ProductDetailsActivity productDetailsActivity;

        @Override
        public void setResultData(String data) {
            String s = data.toString();
            Gson gson = new Gson();
            productRoot = gson.fromJson(data, ProductRoot.class);
            // 得到参数的集合
            attributes = productRoot.getData().getGoods().getAttributes();
            // 得到评论的集合
            comments = productRoot.getData().getComments();
            //设置适配器
            initPager();
            // 填写数据
            initData();
            // 监听
            initLister();
            //展示最下方数据
            initLastProduct();


        }


        private void initLister() {
            // 判断多选框是否选中
            cb_partculars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        cb_partculars.setText("已收藏");
                        cb_partculars.setTextColor(getResources().getColor(R.color.color_true));
                    } else {
                        cb_partculars.setText("收藏");
                        cb_partculars.setTextColor(Color.BLACK);
                    }
                }
            });
            // 点击加入购物车监听
            btn_product_join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductDetailsActivity.this, "点击啦", Toast.LENGTH_SHORT).show();
                    bottomwindow(v);
                    showPopupWidow();
                }
            });
            // 点击购买监听
            btn_product_join_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ProductDetailsActivity.this, "点击啦", Toast.LENGTH_SHORT).show();
                    bottomwindow(v);
                    showPopupWidow();
                }
            });
        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {

        }
    }

    private void initLastProduct() {
        String goods_desc = productRoot.getData().getGoods().getGoods_desc();
        Log.i("zzzzzzzz", goods_desc);
        Gson gson = new Gson();
        PicRoot[] picRoots = gson.fromJson(goods_desc, PicRoot[].class);
        list = new ArrayList<String>();
        for (int i = 0; i < picRoots.length; i++) {
            list.add(picRoots[i].getUrl());
        }
        lv_product_show.setAdapter(new MyPicAdapter(list, ProductDetailsActivity.this));
    }

    /**
     * 设置适配器
     */
    private void initPager() {
        List<ProductRoot.DataEntity.GoodsEntity.GalleryEntity> gallery = productRoot.getData().getGoods().getGallery();
        for (int i = 0; i < gallery.size(); i++) {
            imgUrlList.add(gallery.get(i).getNormal_url());
        }
        initDots(gallery);
        vp_product_details.initData(imgUrlList, dotArray, dotList);
        vp_product_details.startAdapter();
    }

    /**
     * 填写数据
     */

    private void initData() {
        tv_product_details_title.setText(productRoot.getData().getGoods().getGoods_name());
        tv_product_price.setText("￥:" + productRoot.getData().getGoods().getShop_price());

        lv_product.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return productRoot.getData().getActivity().size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                convertView = CommonUtils.inflate(R.layout.particulars_activity_listview_item);
                TextView tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                tv_title.setText(productRoot.getData().getActivity().get(position).getTitle());
                return convertView;
            }
        });
        lv_product.setSelector(new ColorDrawable(Color.TRANSPARENT));
    }

    /**
     * 添加小点
     *
     * @param gallery
     */
    private void initDots(List<ProductRoot.DataEntity.GoodsEntity.GalleryEntity> gallery) {
        dotList.clear();
        ll_product_dots.removeAllViews();
        for (int i = 0; i < gallery.size(); i++) {
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setImageResource(dotArray[0]);
            } else {
                imageView.setImageResource(dotArray[1]);
            }
            dotList.add(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 5, 10, 5);
            ll_product_dots.addView(imageView, params);
        }
    }
}
