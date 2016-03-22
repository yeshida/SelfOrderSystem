package com.centerm.selforder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.centerm.selforder.R;
import com.centerm.selforder.base.SubpageActivity;
import com.centerm.selforder.net.request.RegisterRequest;
import com.centerm.selforder.net.request.UserUniqueRequest;
import com.centerm.selforder.net.response.BaseResponse;
import com.centerm.selforder.net.response.RegisterResponse;
import com.centerm.selforder.net.response.ResponseHandler;
import com.centerm.selforder.utils.StringUtils;
import com.centerm.selforder.utils.ViewUtils;
import com.centerm.selforder.view.EmailAutoCompleteTextView;

/**
 * Created by caiqingyuan on 16/3/8.
 * 注册新用户界面
 */
public class RegisterPageOneActivity extends SubpageActivity {


    private EditText et_manager_name,  et_pass, et_pass_again;
    private String string_manager_name = "", string_pass=null, string_pass_again = null;
    public static String string_eamil_address = null;
    public static boolean[] is_data_allright = new boolean[4];
    private Handler handler;
    private Message msg;
    private static final int MANAGER_NAME_CHANGED = 0;
    private ImageView img_check_manager_name, img_check_email, img_check_pass, img_pass_strength, img_check_pass_again,
            img_pass_strength_again;
    private Button button_register_real;//注册按钮
    private boolean is_unique;//表示用户名是否唯一
    private boolean is_register_succ;//表示注册是否成功


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init_data();

        //修改用户名，好像 et_manager_name.setText(string_manager_name);无法在监听器里实现
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MANAGER_NAME_CHANGED:
                        et_manager_name.setText(string_manager_name);
                        ViewUtils.showToast(RegisterPageOneActivity.this, "请不要输入不符合用户名要求的字符");
                        break;
                }
            }
        };
        //用户名判断是否标准
        this.et_manager_name = (EditText) findViewById(R.id.et_manager_name);
        this.et_manager_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //字母，数字和下划线

                string_manager_name = "";
                //去除不正确的用户名输入
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '_')) {
                        //判断输入字符是否符合要求
                        string_manager_name += c;

                    } else {
                        // 检测到输入错误，修改显示内容，告诉用户出错
                        msg = new Message();
                        msg.what = MANAGER_NAME_CHANGED;
                        handler.sendMessage(msg);
                    }
                }

                //判断当前是否大于2个字符并且不为纯数字,唯一
                if ((string_manager_name.length() > 2 && string_manager_name.length() < 20 &&
                        !StringUtils.checkPasswdStrIsNum(string_manager_name))) {
                    //用户名正确 添加唯一性判断
                    is_user_name_unique(string_manager_name);
                    if(is_unique)
                    {
                        //唯一
                        is_data_allright[0] = true;
                        img_check_manager_name.setImageResource(R.drawable.cqy_face_happy);
                    }else
                    {
                        is_data_allright[0] = false;
                        img_check_manager_name.setImageResource(R.drawable.cqy_face_sad);
                        ViewUtils.showToast(RegisterPageOneActivity.this, "用户名不唯一");

                    }

                } else {
                    is_data_allright[0] = false;
                    img_check_manager_name.setImageResource(R.drawable.cqy_face_sad);
                    ViewUtils.showToast(RegisterPageOneActivity.this,"主管用户名有误");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        //---------------------------------------------------------------------------------------------
        //对密码进行验证
        et_pass = (EditText) findViewById(R.id.et_pass);
        //监听密码改变
        et_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                string_pass = s.toString();

                //对长度判断6-32

                if (StringUtils.isLengthValid(6,32,string_pass)) {

                    img_check_pass.setImageResource(R.drawable.cqy_face_happy);
                    is_data_allright[2] = true;

                    //经过长度判断，还要经过密码强度判断

                    dealCheckSecurity(StringUtils.CheckSecurity(string_pass),img_pass_strength);

                    //防止用户先设置确认密码，在设置密码

                    if( !StringUtils.isStrNull(string_pass_again)  && string_pass_again.equals(string_pass))
                    {
                        is_data_allright[3] = true;
                        img_check_pass_again.setImageResource(R.drawable.cqy_face_happy);
                    }

                } else {
                    img_check_pass.setImageResource(R.drawable.cqy_face_sad);
                    is_data_allright[2] = false;
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //---------------------------------------------------------------------------------------------------------------
        //对密码进行重新确认
        et_pass_again = (EditText) findViewById(R.id.et_pass_again);
        //监听密码改变
        et_pass_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                string_pass_again = s.toString();
                //先进行密码强度判断，在进行相等判断
                dealCheckSecurity(StringUtils.CheckSecurity(string_pass_again),img_pass_strength_again);
                //确认密码等于密码才能是正确
                if (string_pass_again.equals(string_pass)) {

                    img_check_pass_again.setImageResource(R.drawable.cqy_face_happy);
                    is_data_allright[3] = true;
                    //经过长度判断，还要经过密码强度判断

                } else {
                    img_check_pass_again.setImageResource(R.drawable.cqy_face_sad);
                    is_data_allright[3] = false;
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//---------------------------------------------------------------------------------------------------------------
// 对注册按钮进行监听
        button_register_real.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果 is_data_allright全部为true，直接进行跳转
                if(is_data_allright[0] && is_data_allright[1] && is_data_allright[2] && is_data_allright[3])
                {

                    user_register(string_manager_name,string_pass,string_eamil_address);//发送邮箱
                    if(is_register_succ)
                    {
                        Intent intent=new Intent(RegisterPageOneActivity.this, RegisterPageTwoActivity.class);
                        intent.putExtra("email_address",string_eamil_address);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        ViewUtils.showToast(RegisterPageOneActivity.this, "注册失败");
                    }


                }else if(!is_data_allright[0])
                {
                    ViewUtils.showToast(RegisterPageOneActivity.this, "用户名不符合格式或不唯一，请重新输入");

                }else if(!is_data_allright[1])
                {
                    ViewUtils.showToast(RegisterPageOneActivity.this, "输入邮箱有误，请重新输入");
                }else if(!is_data_allright[2])
                {
                    ViewUtils.showToast(RegisterPageOneActivity.this, "输入密码有误，请重新输入");

                }else if(!is_data_allright[3])
                {
                    ViewUtils.showToast(RegisterPageOneActivity.this,"两次密码输入不一致，请重新输入");

                }
            }
        });

    }

    public void init_data()
    {
        setSubTitle("注册");

        this.img_check_manager_name = (ImageView) findViewById(R.id.img_check_name);//表示用户名正确的图像
        this.img_check_email = (ImageView) findViewById(R.id.img_check_email);//表示邮箱正确的图像
        EmailAutoCompleteTextView.set_imageview(img_check_email);
        this.img_check_pass = (ImageView) findViewById(R.id.img_check_pass);//表示密码正确的图像
        this.img_pass_strength = (ImageView) findViewById(R.id.img_pass_strength);//判断密码强度的图像
        this.img_check_pass_again = (ImageView) findViewById(R.id.img_check_pass_again);//表示确认密码正确的图像
        this.img_pass_strength_again = (ImageView) findViewById(R.id.img_pass_strength_again);//判断确认密码强度的图像
        this.button_register_real = (Button) findViewById(R.id.button_register_real);//注册按钮
    }


    //根据密码强度进行处理
    private void dealCheckSecurity(int strength,ImageView img_pass) {

        if (strength == 3) {
            //三种字符全有，强度为高
            img_pass.setImageResource(R.drawable.cqy_pass_strong);

        } else if (strength == 2) {
            //任意两种组合
            img_pass.setImageResource(R.drawable.cqy_pass_middle);
        } else {
            //只有一种种类
            img_pass.setImageResource(R.drawable.cqy_pass_weak);
        }


    }

    //用户名正确 添加唯一性判断
    public void is_user_name_unique(String name)
    {
        is_unique = false;
        UserUniqueRequest request = new UserUniqueRequest(name);

        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {

                BaseResponse result = new BaseResponse(response);
                if (result.isSuccess()) {
                    //成功表示唯一
                    is_unique = true;

                }else
                {
                    is_unique = false;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {

            }
        };
        httpClient.post(this, request, handler);

    }
    //邮箱验证
    public void user_register(String id,String pass,String email)
    {
        RegisterRequest request = new RegisterRequest(id,pass,email);
        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {

                RegisterResponse result = new RegisterResponse(response);

                if (result.isSuccess()) {

                    is_register_succ = true;
                }else
                {
                    is_register_succ = false;
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {

            }
        };
        httpClient.post(this, request, handler);
    }

}
