package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class RegisterDetailRequest extends BaseRequest {

    public RegisterDetailRequest(String USER_ID,String USER_NAME,String ID_CARD,String MOBILE,
                                 String MCHNT_NAME,String MCHNT_ADDR) {

        super("user.regist_detail");
        Map bizContent = new HashMap();
        bizContent.put(Key.USER_ID, USER_ID);
        bizContent.put(Key.USER_NAME, USER_NAME);
        bizContent.put(Key.ID_CARD, ID_CARD);
        bizContent.put(Key.MOBILE, MOBILE);
        bizContent.put(Key.MCHNT_NAME, MCHNT_NAME);
        bizContent.put(Key.MCHNT_ADDR, MCHNT_ADDR);
        setBizContent(bizContent);
    }
}
