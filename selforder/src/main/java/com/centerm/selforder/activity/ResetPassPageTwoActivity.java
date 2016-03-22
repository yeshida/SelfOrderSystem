package com.centerm.selforder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;
import com.centerm.selforder.net.request.ResetPassRequest;
import com.centerm.selforder.net.response.BaseResponse;
import com.centerm.selforder.net.response.ResponseHandler;
import com.centerm.selforder.utils.StringUtils;

/**
 * 设置新密码
 */
public class ResetPassPageTwoActivity extends SubpageActivity implements View.OnClickListener{

    private String phone_or_name;
    private TextView tv_title;
    private Button title_back;
    private EditText et_get_new_pass,et_get_new_pass_again,et_left_yanzheng_code;
    private TextView time_re_send,tv_send_phone_or_email,tv_contact;
    private Button button_re_pass_success;
    private String string_pass = null,string_re_pass= null,string_code= null;
    private boolean is_re_send = true;
    private MyCountDownTimer mc;
    private Button back_btn;
    private boolean is_yanzheng_code_equal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_new_pass);


        initdata();

        //直接倒计时时间
        mc = new MyCountDownTimer(60000, 1000);
        mc.start();


    }

    private void initdata() {


        setSubTitle("重置密码");
        back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
        phone_or_name = this.getIntent().getStringExtra("phone_or_email");

        this.et_get_new_pass = (EditText) findViewById(R.id.et_get_new_pass);
        this.et_get_new_pass_again = (EditText) findViewById(R.id.et_get_new_pass_again);
        this.et_left_yanzheng_code = (EditText) findViewById(R.id.et_get_yanzheng);
        this.time_re_send = (TextView) findViewById(R.id.tv_re_send);
        this.tv_send_phone_or_email = (TextView) findViewById(R.id.tv_send_phone_or_email);
        //根据识别是否为手机或邮箱(先默认为手机，后面添加判断)
        tv_send_phone_or_email.setText("验证码已发送到邮箱 " +phone_or_name);
       /* if(为手机)
        {

        }else
        {
            //邮箱
            tv_send_phone_or_email.setText("验证码已发送到邮箱 " +phone_or_name);
        }*/
        this.button_re_pass_success = (Button) findViewById(R.id.button_re_pass_success);
        this.button_re_pass_success.setOnClickListener(this);
        this.tv_contact = (TextView) findViewById(R.id.tv_contact);
        //联系客服
        this.tv_contact.setText("以上方式仍无法找回密码,请联系客服:xxx");
    }

    @Override
    public void onClick(View v) {
      switch (v.getId())
      {
          case R.id.back_btn:
              finish();
              break;
          case R.id.button_re_pass_success:
              //首先判断密码
              string_pass = et_get_new_pass.getText().toString();
              string_re_pass = et_get_new_pass_again.getText().toString();

              if(  StringUtils.isStrNull(string_pass))
              {
                  is_re_send = false;
                  Toast.makeText(ResetPassPageTwoActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
              }else if(StringUtils.isStrNull(string_re_pass))
              {
                  is_re_send = false;
                  Toast.makeText(ResetPassPageTwoActivity.this,"确认密码不能为空",Toast.LENGTH_SHORT).show();

              }else  if(!string_pass.equals(string_re_pass))
              {
                  is_re_send = false;
                  Toast.makeText(ResetPassPageTwoActivity.this,"两次输入密码不相等",Toast.LENGTH_SHORT).show();

              }else
              {
                  //最后判断输入验证码是否与发送的相等
                  //通过接口去判断是否相等，如果返回不相等  is_re_send = false;
                  string_code = et_left_yanzheng_code.getText().toString();
                  if(StringUtils.isStrNull(string_code))
                  {
                      is_re_send = false;
                      Toast.makeText(ResetPassPageTwoActivity.this,"验证码为空",Toast.LENGTH_SHORT).show();
                  }else
                  {
                      is_code_equal(phone_or_name,string_pass,string_code);
                      if(!is_yanzheng_code_equal)
                      {
                          is_re_send = false;
                          Toast.makeText(ResetPassPageTwoActivity.this,"验证码不一致",Toast.LENGTH_SHORT).show();
                      }
                  }
              }

              if(is_re_send)
              {
                  mc.cancel();//取消倒计时
                  //跳转到密码重置成功界面
                  Intent intent  = new Intent(ResetPassPageTwoActivity.this,ResetPassPageThreeActivity.class);
                  startActivity(intent);
                  finish();
              }

              break;
      }
    }

    /**
     * 继承 CountDownTimer 防范
     *
     * 重写 父类的方法 onTick() 、 onFinish()
     */

    class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {

            //重新发送请求验证码的报文
            //.....
            mc.start();//结束之后重新开始
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //  Log.i("MainActivity", millisUntilFinished + "");
            time_re_send.setText( millisUntilFinished / 1000 + "秒后再次发送");
        }
    }

    public void is_code_equal(String email,String pass,String code)
    {
        ResetPassRequest request = new ResetPassRequest(email,pass,code);
        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                BaseResponse result = new BaseResponse(response);
                if (result.isSuccess()) {

                    is_yanzheng_code_equal = true;
                }else
                {
                    is_yanzheng_code_equal = false;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {

            }
        };
        httpClient.post(this, request, handler);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mc.cancel();
    }
}
