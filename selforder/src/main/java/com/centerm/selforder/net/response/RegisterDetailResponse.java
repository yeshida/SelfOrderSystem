package com.centerm.selforder.net.response;

import com.centerm.selforder.bean.UserInfo;
import com.centerm.selforder.net.Key;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class RegisterDetailResponse extends  BaseResponse{

    private UserInfo info;

    public RegisterDetailResponse(String response) {
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
