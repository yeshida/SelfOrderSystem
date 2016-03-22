package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/14.
 * 根据菜单ID，获取具体的菜单列表
 */
public class GetMenuRequest extends BaseRequest {
    public GetMenuRequest(String merchantId, String menuId, int num, int index) {
        super("menu.get_menu");
        Map bizContent = new HashMap();
        bizContent.put(Key.MCHNT_CD, merchantId);
        bizContent.put(Key.MENUTP_ID, menuId);
        bizContent.put(Key.NUM, num);
        bizContent.put(Key.SERIAL, index);
        setBizContent(bizContent);
    }
}
