package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;
import com.centerm.selforder.utils.MD5Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class RegisterRequest extends  BaseRequest{

    public RegisterRequest(String USER_ID,String PASSWORD,String EMAIL) {
        super("user.regist");
        Map bizContent = new HashMap();
        bizContent.put(Key.USER_ID, USER_ID);
        bizContent.put(Key.PASSWD, encryptPwd(PASSWORD));
        bizContent.put(Key.EMAIL, EMAIL);
        setBizContent(bizContent);
    }

    private String encryptPwd(String pwd) {
        return MD5Utils.getMD5Str(pwd);
    }
}
