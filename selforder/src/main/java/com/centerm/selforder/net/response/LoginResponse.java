package com.centerm.selforder.net.response;

import com.centerm.selforder.bean.UserInfo;
import com.centerm.selforder.net.Key;
import com.centerm.selforder.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.centerm.selforder.net.Key.DATA;

/**
 * Created by caiqingyuan on 16/3/15.
 */
public class LoginResponse extends BaseResponse {

    private String TYPE;
    private String MCHNT_CD;
    private String STATUS;
    private String TERMINAL_CD;
    public LoginResponse(String response) {
        super(response);

        try {
            JSONObject biz = getBizContent();
            if (biz != null) {

                TYPE = biz.getString("TYPE");
                MCHNT_CD = biz.getString(Key.MCHNT_CD);
                STATUS = biz.getString("STATUS");
                TERMINAL_CD = biz.getString(Key.TERMINAL_SN);

                UserInfo.setTerminalSn(TERMINAL_CD);
                UserInfo.setMchntCd(MCHNT_CD);
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getTYPE() {
        return TYPE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public String getMCHNT_CD() {
        return MCHNT_CD;
    }

    public String getTERMINAL_CD() {
        return TERMINAL_CD;
    }

}
