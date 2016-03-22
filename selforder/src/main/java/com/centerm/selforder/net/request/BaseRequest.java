package com.centerm.selforder.net.request;

import com.centerm.selforder.net.Key;
import com.centerm.selforder.net.NetConstants;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linwanliang on 2016/3/2.
 * 请求对象的基类，对通用报文头进行封装
 */
public class BaseRequest {

    private String method;
    private boolean isJsonStream;
    private Map<String, String> params;
    private JSONObject bizContent;

    private static String commonUrl = NetConstants.ADDRESS;

    /**
     * 构造函数
     *
     * @param method
     */
    public BaseRequest(String method) {
        this.isJsonStream = true;
        params = new HashMap<String, String>();
        reset(method);
    }

    /**
     * 构造函数
     *
     * @param isJsonStream 参数传递是否使用json流
     * @param method       接口规范中的方法名称
     */
    public BaseRequest(boolean isJsonStream, String method) {
        this.isJsonStream = isJsonStream;
        params = new HashMap<String, String>();
        reset(method);
    }

    /**
     * 复位。参数容器恢复到初始状态。
     *
     * @param method
     */
    public void reset(String method) {
        params.clear();
        this.method = method;
        params.put(Key.METHOD, method);
        params.put(Key.TIMESTAMP, getTimeStamp());
        params.put(Key.SESSION_ID, getSessionId());
        params.put(Key.MAC, getMac());
    }

    private String getTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }

    private String getSessionId() {
        return "";
    }

    private String getMac() {
        return "";
    }

    /**
     * 设置业务参数
     *
     * @param bizContent
     */
    public void setBizContent(Map<String, String> bizContent) {
        if (bizContent != null) {
            this.bizContent = new JSONObject(bizContent);
            params.put(Key.BIZ_CONTENT, this.bizContent.toString());
        }
    }

    /**
     * 设置业务参数
     *
     * @param bizContent
     */
    public void setBizContent(JSONObject bizContent) {
        this.bizContent = bizContent;
        if (this.bizContent != null) {
            params.put(Key.BIZ_CONTENT, this.bizContent.toString());
        }
    }

    public Map<String, String> getParams() {
        return params;
    }

    public boolean isJsonStream() {
        return isJsonStream;
    }

    public JSONObject getBizContent() {
        return bizContent;
    }

    public String paramsToJsonString() {
        JSONObject object = new JSONObject(params);
        return object.toString();
    }

    public String getMethod() {
        return method;
    }

    /**
     * 获取共同的访问地址
     *
     * @return
     */
    public static String getCommonUrl() {
        return commonUrl;
    }

    /**
     * 设置共同的访问地址
     *
     * @param commonUrl
     */
    public static void setCommonUrl(String commonUrl) {
        BaseRequest.commonUrl = commonUrl;
    }
}
