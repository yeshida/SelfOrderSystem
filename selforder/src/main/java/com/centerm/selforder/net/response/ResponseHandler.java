package com.centerm.selforder.net.response;

/**
 * Created by linwanliang on 2016/3/3.
 */
public interface ResponseHandler {
    /**
     * 请求成功。
     *
     * @param statusCode 状态码。
     * @param response   返回的字符内容。
     */
    void onSuccess(int statusCode, String response);

    /**
     * 请求失败
     *
     * @param statusCode 状态码。
     * @param response   大多数情况下为空。
     * @param error      异常对象。
     */
    void onFailure(int statusCode, String response, Throwable error);
}
