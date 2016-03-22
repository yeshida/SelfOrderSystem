package com.centerm.selforder.net.response;

import android.os.Bundle;

import com.centerm.selforder.net.Key;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/14.
 */
public class MenuTypeListResponse extends BaseResponse {

    private List<Map<String, String>> menuTypeList;
    private Map<String, String> idMapName;

    public MenuTypeListResponse(String response) {
        super(response);
        if (getBizContent().has(Key.DATA)) {
            try {
                JSONArray array = getBizContent().getJSONArray(Key.DATA);
                menuTypeList = new ArrayList<Map<String, String>>();
                idMapName = new HashMap<String, String>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    Map<String, String> map = new HashMap<String, String>();
                    String id = item.getString(Key.MENUTP_ID);
                    String name = item.getString(Key.MENUTP_NAME);
                    String picUrl = item.getString(Key.PICTURE_LINK);
                    map.put(Key.MENUTP_ID, id);
                    map.put(Key.MENUTP_NAME, name);
                    map.put(Key.PICTURE_LINK,picUrl);
                    idMapName.put(id, name);
                    menuTypeList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Map<String, String>> getMenuTypeList() {
        return menuTypeList;
    }

    public Map<String, String> getIdMapName() {
        return idMapName;
    }
}
