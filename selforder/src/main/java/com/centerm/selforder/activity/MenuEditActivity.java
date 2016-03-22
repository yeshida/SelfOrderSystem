package com.centerm.selforder.activity;

import android.os.Bundle;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;

/**
 * Created by linwanliang on 2016/3/18.
 * 某一类型的菜单编辑界面
 */
public class MenuEditActivity extends SubpageActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_edit_activity);
    }
}
