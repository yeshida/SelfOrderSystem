package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;
import com.centerm.selforder.utils.MD5Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/10.
 */
public class LoginRequest extends BaseRequest {

    public LoginRequest(String userId, String passwd, String terminalSn, boolean encryptPwd) {
        super("user.login");
        Map<String, String> map = new HashMap<String, String>();
        map.put(Key.USER_ID, userId);
        if (encryptPwd) {
            passwd = encryptPwd(passwd);
        }
        map.put(Key.PASSWD, passwd);
        map.put(Key.TERMINAL_SN, terminalSn);
        setBizContent(map);
    }

    private String encryptPwd(String pwd) {
        return MD5Utils.getMD5Str(pwd);
    }


}
