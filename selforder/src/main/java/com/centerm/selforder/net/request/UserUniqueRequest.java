package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class UserUniqueRequest extends BaseRequest{

    public UserUniqueRequest(String user_name) {
        super("user.id.unique");
        Map bizContent = new HashMap();
        bizContent.put(Key.USER_ID,user_name);
        setBizContent(bizContent);
    }
}
