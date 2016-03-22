package com.centerm.selforder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;

/**
 * 结束注册界面
 */
public class RegisterPageTwoActivity extends SubpageActivity implements View.OnClickListener{

    private TextView tv_finish_email_check,tv_register_title;
    private Button title_back,login_email,register_finish;
    private String email_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_register);


        Intent intent = getIntent();

        tv_finish_email_check = (TextView) findViewById(R.id.finish_email_check);
        tv_register_title = (TextView) findViewById(R.id.register_title);


        if(intent.getStringExtra("email_address") != null)
        {
            //表示是从注册流程跳转到当钱界面
            email_address = intent.getStringExtra("email_address");//得到传递过来的邮箱地址
            info.setEMAIL(email_address);
            init_data1();
        }else
        {
            //表示是从登陆界面 账户未激活跳转到当钱界面的
            init_data2();
        }




        //立即登陆，激活邮箱
        this.login_email = (Button) findViewById(R.id.login_eamil);
        this.login_email.setOnClickListener(RegisterPageTwoActivity.this);

        //已激活，开始登陆
        this.register_finish = (Button) findViewById(R.id.register_finish);
        this.register_finish.setOnClickListener(this);

        title_back = (Button) findViewById(R.id.back_btn);
        title_back.setOnClickListener(this);
    }


    public void init_data1()
    {
        tv_finish_email_check.setText("确认信已经发送到你的邮箱:" + email_address + ",请赶紧激活你的账号吧");

    }


    private void init_data2() {


        tv_register_title.setText("你的邮箱尚未验证,请立即验证");
        tv_finish_email_check.setText("登陆邮箱后,才能收到系统发送的各类通知");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.login_eamil:
                // 调用浏览器登陆邮箱
                Intent intent = new Intent(RegisterPageTwoActivity.this, EmailWebviewActivity.class);
                intent.putExtra("email_address", email_address);
                startActivity(intent);
                break;
            case R.id.register_finish:
                finish();
                break;
            case R.id.back_btn:
                finish();
                break;

        }
    }
}
