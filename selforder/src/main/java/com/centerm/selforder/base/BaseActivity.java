package com.centerm.selforder.base;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.centerm.selforder.MyApplication;
import com.centerm.selforder.bean.UserInfo;
import com.centerm.selforder.net.MyHttpClient;
import com.centerm.selforder.support.Code;
import com.centerm.selforder.utils.Log4d;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by linwanliang on 2016/3/2.
 * Activity基类。封装Activity中常用方法或者定义部分静态变量（例如日志打印工具类、网络请求工具类等）<br/>
 * 注意控制静态变量的数量。
 */
public class BaseActivity extends Activity {


    protected final static Log4d logger = Log4d.getInstance();
    protected final static Code code = Code.getInstance();
    private DisplayImageOptions defaultImageOptions;
    protected MyHttpClient httpClient;
    protected UserInfo info = UserInfo.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpClient = MyHttpClient.getInstance();
        // 适配960F，强制横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

    }

    /**
     * 获取默认的异步图片加载选项
     *
     * @return
     */
    public DisplayImageOptions getDefaultImageOptions() {
        if (defaultImageOptions == null) {
            defaultImageOptions = new DisplayImageOptions.Builder()
                    .delayBeforeLoading(500)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
        }
        return defaultImageOptions;
    }

    public SharedPreferences getDefaultPres() {
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public MyApplication getMyApp() {
        return (MyApplication) getApplication();
    }


}
