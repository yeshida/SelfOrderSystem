package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class ChangePassRequest extends BaseRequest{

    public ChangePassRequest(String MCHNT_CD,String OLDPASSWD,String NEWPASSWD) {
        super("user.change_passwd");

        Map bizContent = new HashMap();
        bizContent.put(Key.MCHNT_CD, MCHNT_CD);
        bizContent.put(Key.OLDPASSWD, OLDPASSWD);
        bizContent.put(Key.NEWPASSWD, NEWPASSWD);
        setBizContent(bizContent);
    }
}
