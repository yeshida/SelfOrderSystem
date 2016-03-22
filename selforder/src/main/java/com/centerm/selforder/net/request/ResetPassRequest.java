package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;
import com.centerm.selforder.utils.MD5Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class ResetPassRequest extends BaseRequest{

    public ResetPassRequest(String EMAIL,String PASSWD,String CHECK_CODE) {
        super("user.reset_passwd");

        Map bizContent = new HashMap();
        bizContent.put(Key.EMAIL, EMAIL);
        bizContent.put(Key.PASSWD, MD5Utils.getMD5Str(PASSWD));
        bizContent.put(Key.CHECK_CODE, CHECK_CODE);
        setBizContent(bizContent);
    }
}
