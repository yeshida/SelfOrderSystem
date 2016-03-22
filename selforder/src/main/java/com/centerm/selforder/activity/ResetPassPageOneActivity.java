package com.centerm.selforder.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;
import com.centerm.selforder.net.request.SendEmailRequest;
import com.centerm.selforder.net.response.BaseResponse;
import com.centerm.selforder.net.response.ResponseHandler;
import com.centerm.selforder.utils.StringUtils;
import com.centerm.selforder.utils.ViewUtils;

/**
 *
 * 找回密码
 */
public class ResetPassPageOneActivity extends SubpageActivity implements View.OnClickListener{
    private TextView tv_title;
    private Button title_back;
    private EditText et_phone_or_email, left_verification_code;
    private Button change_verification_code, next_step_one,back_btn;
    private ImageView right_verification_code;
    private Bitmap verification_code_bitmap;
    private String string_left_verification_code;
    private String string_right_verification_code;
    private String string_phone_or_email;
    private boolean is_email_send_succ;//表示邮箱是否发送成功

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        init_data();

        //生成验证码
        Change_verification_code();
    }

    private void Change_verification_code()
    {
        verification_code_bitmap = code.createBitmap();
        string_right_verification_code = code.getCode();//得到验证码
        right_verification_code.setImageBitmap(verification_code_bitmap);
    }
    private void init_data() {

        setSubTitle("重置密码");
        back_btn = (Button) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(this);
        this.et_phone_or_email = (EditText) findViewById(R.id.et_phone_or_email);
        this.left_verification_code = (EditText) findViewById(R.id.left_verification_code);
        this.change_verification_code = (Button) findViewById(R.id.change_verification_code);
        this.change_verification_code.setOnClickListener(this);
        this.next_step_one = (Button) findViewById(R.id.next_step_one);
        this.next_step_one.setOnClickListener(this);
        this.right_verification_code = (ImageView) findViewById(R.id.right_verification_code);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.back_btn:
                finish();
                break;
            case R.id.change_verification_code:
                //切换验证码
                Change_verification_code();
                break;
            case R.id.next_step_one:
                //得到手机或邮箱(后面或许需要对手机或邮箱的格式进行判断，此处先忽略，仅判断不为空)
                string_phone_or_email = et_phone_or_email.getText().toString().trim();

                if (!StringUtils.isStrNull(string_phone_or_email)) {

                    string_left_verification_code = left_verification_code.getText().toString().toLowerCase();

                    if (!StringUtils.isStrNull(string_left_verification_code)) {

                        if (string_left_verification_code.equals(string_right_verification_code)) {
                            //如果验证码相等,通知服务器发送验证码给响应的手机或者邮箱(暂时忽略)，进入下一个activity
                            //发送服务器

                            send_emial(string_phone_or_email);
                            if(is_email_send_succ)
                            {
                                Intent intent = new Intent(ResetPassPageOneActivity.this, ResetPassPageTwoActivity.class);
                                intent.putExtra("phone_or_email", string_phone_or_email);
                                startActivity(intent);
                                finish();
                            }else
                            {
                                ViewUtils.showToast(ResetPassPageOneActivity.this,"验证码发送失败");
                            }


                        }
                    } else {

                        ViewUtils.showToast(ResetPassPageOneActivity.this, "验证码不能为空");

                    }

                } else {
                    ViewUtils.showToast(ResetPassPageOneActivity.this, "请输入正确的手机或邮箱");

                }

                break;
        }
    }
    //邮箱验证
    public void send_emial(String email_address)
    {
        SendEmailRequest request = new SendEmailRequest(email_address);
        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                BaseResponse result = new BaseResponse(response);
                if (result.isSuccess()) {

                    is_email_send_succ = true;
                }else
                {
                    is_email_send_succ = false;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {

            }
        };
        httpClient.post(this, request, handler);
    }

}
