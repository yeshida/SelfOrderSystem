package com.centerm.selforder.base;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.centerm.selforder.MyApplication;
import com.centerm.selforder.R;
import com.centerm.selforder.net.MyHttpClient;
import com.centerm.selforder.utils.Log4d;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by linwanliang on 2016/3/2.
 * Fragment基类。作用与BaseActivity类似。参见{@link BaseActivity}
 */
public class BaseFragment extends Fragment {
    protected final static Log4d logger = Log4d.getInstance();
    private DisplayImageOptions defaultImageOptions;
    protected MyHttpClient httpClient;

    public BaseFragment() {
        super();
        httpClient = MyHttpClient.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 获取默认的异步图片加载选项
     *
     * @return
     */
    public DisplayImageOptions getDefaultImageOptions() {
        if (defaultImageOptions == null) {
            if (getActivity() instanceof BaseActivity) {
                defaultImageOptions = ((BaseActivity) getActivity()).getDefaultImageOptions();
            } else {
                defaultImageOptions = new DisplayImageOptions.Builder()
                        .delayBeforeLoading(500)
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .build();
            }
        }
        return defaultImageOptions;
    }

    public SharedPreferences getDefaultPres() {
        return PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
    }

    public MyApplication getMyApp() {
        return ((BaseActivity) getActivity()).getMyApp();
    }

}
