package com.centerm.selforder.net;

import android.content.Context;

import com.centerm.selforder.net.request.BaseRequest;
import com.centerm.selforder.net.request.LoginRequest;
import com.centerm.selforder.net.response.ResponseHandler;
import com.centerm.selforder.utils.Log4d;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;

/**
 * Created by linwanliang on 2016/3/2.
 * Http请求客户端，单例模式。
 */
public class MyHttpClient {

    private final static String TAG = MyHttpClient.class.getSimpleName();
    private final static Log4d logger = Log4d.getInstance();
    private final static String DEFAULT_CHARSET = "utf-8";

    private static MyHttpClient instance;
    private AsyncHttpClient innerClient;
    private String charset = DEFAULT_CHARSET;

    private MyHttpClient() {
        innerClient = new AsyncHttpClient();
    }

    public static MyHttpClient getInstance() {
        if (instance == null) {
            synchronized (MyHttpClient.class) {
                if (instance == null) {
                    instance = new MyHttpClient();
                }
            }
        }
        return instance;
    }

    /**
     * POST一个HTTP请求
     *
     * @param context
     * @param request
     * @param handler
     */
    public void post(Context context, final BaseRequest request, final ResponseHandler handler) {
        String url = BaseRequest.getCommonUrl();
        if (context == null || url == null) {
            logger.warn(TAG, "[请求失败] - Context或者请求地址为空");
            return;
        }
        logger.info(TAG, "[请求地址] - " + url);
        RequestParams params = new RequestParams(request.getParams());
        params.setUseJsonStreamer(request.isJsonStream());
        logger.info(TAG, "[请求参数] - " + request.paramsToJsonString());
        innerClient.post(context, url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                logger.info(TAG, "[请求成功] - " + request.getMethod());
                String response = null;
                try {
                    response = new String(bytes, charset);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    response = new String(bytes);
                }
                if (null != handler) {
                    handler.onSuccess(i, response);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                logger.warn(TAG, "[请求失败] - " + request.getMethod());
                logger.warn(TAG, "[错误码][" + i + "] " + (throwable == null ? "" : throwable.toString()));
                if (null != handler) {
                    handler.onFailure(i, null, throwable);
                }
            }
        });
    }

    /**
     * 获取编码方式，默认是UTF-8
     *
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     * 设置编码方式，默认是UTF-8
     *
     * @param charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * 测试方法。测试网络接口使用，底层网络框架的调用方式也参照该方法的写法。
     * 这里以登录接口的调用为例；
     *
     * @param context
     */
    public void test(Context context) {
        //**第一步，构建请求对象
        //请求对象可以直接使用基类BaseRequest，也可以根据不同的接口，继承该基类
        //将方法名封装在类中，以及对传递进来的参数进行预处理，使其符合接口规范
        //例如登录请求可以单独封装成LoginRequest
        LoginRequest request = new LoginRequest("test", "123456", "0000000000", true);
        //**第二步，构建异步处理对象
        //由于是异步处理机制，因此在该handler接口方法中，可以直接进行UI的操作
        ResponseHandler handler = new ResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                //请求成功的处理代码
                //在这里可以使用自己封装的解析类，对返回的json字符串进行解析，生成可以供UI直接使用的数据容器
                //解析类可以继承BaseResponse,可以免去对通用返回参数的解析
            }

            @Override
            public void onFailure(int statusCode, String response, Throwable error) {
                //请求失败的处理代码
            }
        };
        //**第三步，通过HttpClient发出相关请求
        MyHttpClient client = MyHttpClient.getInstance();
        client.post(context, request, handler);
    }


}
