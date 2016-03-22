package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class GetUserInfoRequest extends  BaseRequest {

    public GetUserInfoRequest(String MCHNT_CD) {

        super("user.get_userinfo");
        Map bizContent = new HashMap();
        bizContent.put(Key.MCHNT_CD, MCHNT_CD);
        setBizContent(bizContent);
    }
}
