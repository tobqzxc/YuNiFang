package com.example.qwe.yunifang.lanuch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qwe.yunifang.R;
import com.example.qwe.yunifang.bean.VersionInfo;
import com.example.qwe.yunifang.utlis.DataClearManager;
import com.google.gson.Gson;
import com.zhy.autolayout.AutoLinearLayout;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class LocateActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_locate;
    private AutoLinearLayout ll_locate_huancen;
    private TextView tv_locate_show;
    private File cacheDir;
    private long folderSize;
    private TextView tv_locate_huancun;
    private AutoLinearLayout linearLayout_locate_huancun;
    private TextView tv_locate_geng;
    private TextView tv_locate_geng_show;
    private boolean flag = false;
    private VersionInfo versionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_locate);
        //获取系统路径
        cacheDir = this.getCacheDir();
        //获取文件大小
        try {
            folderSize = DataClearManager.getFolderSize(cacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //转换为M
        String formatSize = DataClearManager.getFormatSize(folderSize);
        // 查找控件
        initView();
        // 监听事件
        initLister();
        // 获得内存大小
        tv_locate_show.setText(formatSize);
        //进来的时候请求网络获取版本信息
        RequestParams params = new RequestParams("http://169.254.62.93:8080/versioninfo.txt");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Gson gson = new Gson();
                versionInfo = gson.fromJson(s, VersionInfo.class);
                String versionName = versionInfo.getVersionName();
                if(versionName.equals(getVersionName())){
                    tv_locate_geng_show.setText("当前版本是最新版本");
                    flag = true;
                }else{
                    tv_locate_geng_show.setText("当前最新版本为:  "+versionName);
                    flag = false;
                }

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


    }


    private void initLister() {
        iv_locate.setOnClickListener(this);
        tv_locate_huancun.setOnClickListener(this);
        tv_locate_geng.setOnClickListener(this);
    }

    private void initView() {
        iv_locate = (ImageView) findViewById(R.id.iv_locate);
        tv_locate_show = (TextView) findViewById(R.id.tv_locate_show);
        tv_locate_huancun = (TextView) findViewById(R.id.tv_locate_huancun);
        tv_locate_geng = (TextView) findViewById(R.id.tv_locate_geng);
        tv_locate_geng_show = (TextView) findViewById(R.id.tv_locate_geng_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_locate:
                finish();
                overridePendingTransition(R.anim.activity3_in, R.anim.activity3_out);
                break;
            case R.id.tv_locate_huancun:
                try {
                    Toast.makeText(LocateActivity.this, "wodianjik ", Toast.LENGTH_SHORT).show();
                    DataClearManager.deleteCache(cacheDir);
                    long folderSize = DataClearManager.getFolderSize(cacheDir);
                    String formatSize = DataClearManager.getFormatSize(folderSize);
                    tv_locate_show.setText(formatSize);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_locate_geng:
                if(!flag){
                    String downloadUrl = versionInfo.getDownloadUrl();
                    RequestParams params = new RequestParams(downloadUrl);
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.show();
                    x.http().get(params, new Callback.ProgressCallback<File>() {
                        @Override
                        public void onWaiting() {

                        }

                        @Override
                        public void onStarted() {

                        }

                        @Override
                        public void onLoading(long total, long current, boolean b) {
                            progressDialog.setMax((int) total);
                            progressDialog.setProgress((int) current);
                        }

                        @Override
                        public void onSuccess(File file) {
                            progressDialog.dismiss();
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");

                            Log.i("AAAA","-----"+file.getAbsolutePath());
                            intent.setDataAndType(
                                    Uri.fromFile(file),
                                    "application/vnd.android.package-archive");
                            startActivity(intent);
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

                }

                break;
        }

    }
    public String getVersionName() {
        PackageManager packageManager = getPackageManager();
        //包名
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
