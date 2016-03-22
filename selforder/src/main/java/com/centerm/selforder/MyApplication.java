package com.centerm.selforder;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.mapapi.SDKInitializer;
import com.centerm.selforder.support.BaiduLocationService;
import com.centerm.selforder.bean.OrderedDish;
import com.centerm.selforder.utils.Log4d;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.List;

/**
 * Created by linwanliang on 2016/3/2.
 */
public class MyApplication extends Application {

    private final static String LOG_GLOBAL_TAG = "SELF_ORDER";

    private List<OrderedDish> orderedDishs;
    public BaiduLocationService locationService;
    public Vibrator mVibrator;
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        Log4d.setGlobalTag(LOG_GLOBAL_TAG);
/***
 * 初始化定位sdk，建议在Application中创建
 */
        locationService = new BaiduLocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
      //  SDKInitializer.initialize(getApplicationContext());

    }

    /**
     * 初始化异步图片加载组件
     */
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public List<OrderedDish> getOrderedDishs() {
        return orderedDishs;
    }

    public void setOrderedDishs(List<OrderedDish> orderedDishs) {
        this.orderedDishs = orderedDishs;
    }
}
