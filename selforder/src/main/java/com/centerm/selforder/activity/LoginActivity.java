package com.centerm.selforder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.centerm.selforder.base.BaseActivity;
import com.centerm.selforder.constant.ValueDeliverKey;
import com.centerm.selforder.net.request.GetMenuTypeRequest;
import com.centerm.selforder.net.request.LoginRequest;
import com.centerm.selforder.net.response.LoginResponse;
import com.centerm.selforder.net.response.MenuTypeListResponse;
import com.centerm.selforder.net.response.ResponseHandler;
import com.centerm.selforder.utils.StringUtils;
import com.centerm.selforder.utils.ViewUtils;
import com.centerm.selforder.R;

/**
 * 登陆主界面
 */
public class LoginActivity extends BaseActivity {

    private EditText et_user_name, et_password;
    private Button button_login, button_register, button_forget_pass;
    private String string_user_id= null, string_password = null;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_user_name = (EditText) findViewById(R.id.edittext_user_edit);
        et_password = (EditText) findViewById(R.id.edittext_pass__edit);
        button_login = (Button) findViewById(R.id.button_login_button);
        button_register = (Button) findViewById(R.id.button_register);
        button_forget_pass = (Button) findViewById(R.id.button_forget_pass);

        //由于用户名和密码需要与服务器进行交互才能判断到底是有没有注册过或者是主管和操作员，此处先略过

        //登陆点击响应事件
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断用户名和密码是否合理(只是先简单判断一下而已)
                string_user_id = et_user_name.getText().toString();
                string_password = et_password.getText().toString();

                if (StringUtils.isStrNull(string_user_id)) {
                    //用户名为空
                    ViewUtils.showToast(LoginActivity.this, "用户名不能为空");
                } else {

                    //用户名不为空，开启判断密码
                    if (StringUtils.isStrNull(string_password)) {

                        ViewUtils.showToast(LoginActivity.this, "密码不能为空");

                    } else {

                        //用户名密码都不为空
                        //使用user.login接口与后台交互数据，判断是管理员 操作员 和 系统管理员
                        //.......
                        LoginRequest request = new LoginRequest(string_user_id,string_password,get_terminal_sn(),true);
                        ResponseHandler handler = new ResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, String response) {
                                LoginResponse result = new LoginResponse(response);
                                if (result.isSuccess()) {
                                   if(result.getTYPE().equals("1"))
                                   {
                                       //跳转到操作员界面
                                   }else
                                   {
                                       //细分账户状态
                                       if(result.getSTATUS().equals("0"))
                                       {
                                           //正常
                                           intent = new Intent(LoginActivity.this,MainManagerActivity.class);

                                       }else if(result.getSTATUS().equals("1"))
                                       {
                                           //待激活
                                           intent = new Intent(LoginActivity.this,RegisterPageTwoActivity.class);

                                       }else if(result.getSTATUS().equals("2"))
                                       {
                                           //已激活 跳转到补充资料界面(未实现)
                                           intent = new Intent(LoginActivity.this,SupplementRegisterDataActivity.class);
                                       }else
                                       {
                                           //停用
                                           ViewUtils.showToast(LoginActivity.this,"该账户已经停用");
                                       }

                                       startActivity(intent);

                                   }
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, String response, Throwable error) {

                            }
                        };
                        httpClient.post(LoginActivity.this, request, handler);

                        Intent intent = new Intent(LoginActivity.this, MainManagerActivity.class);
                        startActivity(intent);


                    }
                }


            }
        });
        //注册新用户按钮点击响应事件
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterPageOneActivity.class);
                startActivity(intent);
            }
        });
        //忘记密码按钮点击响应事件
        button_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ResetPassPageOneActivity.class);
                startActivity(intent);
            }
        });


    }

    //获取终端sn号(未实现)
    public String get_terminal_sn()
    {
        return null;
    }
    /**
     * 获取菜单类型列表的网络请求。
     * 菜单类型作为程序初始化信息的必要组成部分，在登录成功后应立即获取该信息；
     * 如果无法获取到该信息，则需要用户重新登录，否则无法进入到主界面。
     * 获取成功后，将数据通过Intent传递给下一个Activity
     *
     * @param merchantId
     */
    private void requestMenuTypeList(String merchantId) {
        GetMenuTypeRequest request = new GetMenuTypeRequest(merchantId);
        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                MenuTypeListResponse result = new MenuTypeListResponse(response);
                if (result.isSuccess()) {
                    //// TODO: 2016/3/14 此处还没结束，还需要根据登录用户的不同，决定下一个去往的界面和传递的数据
                    Intent intent = new Intent(LoginActivity.this, MainOrderingActivity.class);
                    intent.putExtra(ValueDeliverKey.KEY_MENU_TYPE_RESPONSE, response);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {

            }
        };
        httpClient.post(this, request, handler);
    }
}
