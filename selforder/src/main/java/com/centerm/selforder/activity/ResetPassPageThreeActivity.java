package com.centerm.selforder.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;

public class ResetPassPageThreeActivity extends SubpageActivity {

    private TextView tv_title;
    private Button title_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass_suc);
        initdata();
    }

    private void initdata() {

        setSubTitle("重置密码");

    }
}
