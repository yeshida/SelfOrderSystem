package com.centerm.selforder.net.response;

import com.centerm.selforder.net.Key;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by linwanliang on 2016/3/2.
 * 接口成功返回时，使用该类进行解析。
 */
public class BaseResponse {
    private String response;
    private String code;
    private String msg;
        private String mac;
        private JSONObject bizContent;
        private boolean isSuccess;

        public BaseResponse(String response) {
            this.response = response;
            try {
                JSONObject root = new JSONObject(response);
                code = root.getString(Key.CODE);
                if ("10000".equals(code)) {
                    isSuccess = true;
                }
                msg = root.getString(Key.MSG);
                mac = root.getString(Key.MAC);
                bizContent = root.getJSONObject(Key.BIZ_CONTENT);
            } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getBizContent() {
        return bizContent;
    }

    public String getCode() {
        return code;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMac() {
        return mac;
    }

    public String getMsg() {
        return msg;
    }

    public String getResponse() {
        return response;
    }


}
