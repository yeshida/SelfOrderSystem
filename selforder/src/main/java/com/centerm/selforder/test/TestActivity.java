package com.centerm.selforder.test;

import android.os.Bundle;

import com.centerm.selforder.R;
import com.centerm.selforder.base.BaseActivity;
import com.centerm.selforder.base.SubpageActivity;

/**
 * Created by linwanliang on 2016/3/9.
 */
public class TestActivity extends SubpageActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu_show);
        setSubTitle("就看见了福建省的理发师 ");
    }
}
