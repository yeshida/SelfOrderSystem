package com.centerm.selforder.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.centerm.selforder.R;
import com.centerm.selforder.bean.UserInfo;

/**
 * Created by linwanliang on 2016/3/9.
 * <p/>
 * 子界面基类，一般用于非一级界面继承使用。
 * 集成标题栏，标题栏中包含的元素包括返回按钮、标题栏、两个自定义功能按钮
 */
public class SubpageActivity extends BaseActivity {

    private LinearLayout container;
    private Button backBtn;
    private RelativeLayout base;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.base_activity_subpage);

        this.base = (RelativeLayout) findViewById(R.id.base);
        container = (LinearLayout) findViewById(R.id.root_container);
    }

    //返回relativelayout高度，方便gridview布局
    public int getbasetheight() {
        return base.getHeight() + 10;
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        this.setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        this.setContentView(view, params);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (container != null) {
            container.addView(view, params);
        }
    }

    /**
     * 设置子页面标题
     *
     * @param title
     */
    protected void setSubTitle(String title) {
        TextView titleShow = (TextView) findViewById(R.id.title_show);
        titleShow.setText(title);
    }

    /**
     * 获取自定义按钮1
     *
     * @return
     */
    protected Button getCustomeButton1() {
        return (Button) findViewById(R.id.custome_btn1);
    }

    /**
     * 获取自定义按钮2
     *
     * @return
     */
    protected Button getCustomeButton2() {
        return (Button) findViewById(R.id.custome_btn2);
    }


    protected void hideBackBtn() {
        if (backBtn != null) {
            backBtn.setVisibility(View.GONE);
        }
    }

    protected void showBackBtn() {
        if (backBtn != null) {
            backBtn.setVisibility(View.VISIBLE);
        }
    }

}
