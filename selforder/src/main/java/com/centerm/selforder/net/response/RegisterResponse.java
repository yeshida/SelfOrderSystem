package com.centerm.selforder.net.response;

import com.centerm.selforder.bean.UserInfo;
import com.centerm.selforder.net.Key;
import com.centerm.selforder.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static com.centerm.selforder.net.Key.DATA;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class RegisterResponse extends  BaseResponse{

    private UserInfo info;

    public RegisterResponse(String response) {
        super(response);

        info = UserInfo.getInstance();
        try {
            JSONObject biz = getBizContent();
            if (biz != null) {

                info.setUserId(biz.getString(Key.USER_ID));
                info.setMchntCd(biz.getString(Key.MCHNT_CD));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
