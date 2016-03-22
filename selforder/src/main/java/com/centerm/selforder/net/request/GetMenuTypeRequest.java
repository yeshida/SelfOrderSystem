package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/14.
 * 获取菜单类型列表
 */
public class GetMenuTypeRequest extends BaseRequest {

    public GetMenuTypeRequest(String merchantId) {
        super("menu.get_menutp");
        Map<String, String> bizContent = new HashMap<String, String>();
        bizContent.put(Key.MCHNT_CD, merchantId);
        setBizContent(bizContent);
    }
}
