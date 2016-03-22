package com.centerm.selforder.net.response;

import com.centerm.selforder.bean.Dish;
import com.centerm.selforder.net.Key;
import com.centerm.selforder.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.centerm.selforder.net.Key.*;


/**
 * Created by linwanliang on 2016/3/14.
 * 解析菜单详情
 */
public class MenuListResponse extends BaseResponse {

    private int total;
    private String menuId;
    private List<Dish> entityList;
    private List<Map<String, String>> dataList;

    public MenuListResponse(String response) {
        super(response);
        try {
            JSONObject biz = getBizContent();
            total = biz.getInt(Key.TOTAL);
            if (biz.has(Key.DATA)) {
                JSONArray array = biz.getJSONArray(DATA);
                dataList = new ArrayList<Map<String, String>>();
                entityList = new ArrayList<Dish>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    Map<String, String> map = JsonUtils.json2Map(item);
                    dataList.add(map);
                    Dish entity = new Dish(item.getString(Key.PRODUCT_ID), item.getString(Key.MENUTP_ID)
                            , item.getString(Key.PRODUCT_DETAIL), item.getString(Key.PRODUCT_NAME)
                            , item.getString(Key.PICTURE_LINK), item.getDouble(Key.PRICE)
                            , item.getString(Key.TASTE));
                    entity.setInventory(item.getInt(Key.INVENTORY));
                    entityList.add(entity);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getTotal() {
        return total;
    }

    public String getMenuId() {
        return menuId;
    }

    public List<Map<String, String>> getDataList() {
        return dataList;
    }

    public List<Dish> getEntityList() {
        return entityList;
    }
}
