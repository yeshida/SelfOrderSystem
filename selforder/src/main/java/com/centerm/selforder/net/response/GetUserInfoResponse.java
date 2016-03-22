package com.centerm.selforder.net.response;

import com.centerm.selforder.bean.UserInfo;
import com.centerm.selforder.net.Key;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class GetUserInfoResponse extends BaseResponse {

    private UserInfo info ;
    public GetUserInfoResponse(String response) {

        super(response);
        info = UserInfo.getInstance();

        try {
            JSONObject biz = getBizContent();
            if (biz != null) {

                info.setUserId(biz.getString(Key.USER_ID));
                info.setUserName(biz.getString(Key.USER_NAME));
                info.setEMAIL(biz.getString(Key.EMAIL));
                info.setIdCard(biz.getString(Key.ID_CARD));
                info.setMOBILE(biz.getString(Key.MOBILE));
                info.setMchntName(biz.getString(Key.MCHNT_NAME));
                info.setMchntAddr(biz.getString(Key.MCHNT_ADDR));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
