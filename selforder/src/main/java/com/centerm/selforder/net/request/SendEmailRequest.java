package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class SendEmailRequest extends BaseRequest{
    public SendEmailRequest(String EMAIL) {
        super("user.email_check");

        Map bizContent = new HashMap();
        bizContent.put(Key.EMAIL, EMAIL);
        setBizContent(bizContent);
    }
}
