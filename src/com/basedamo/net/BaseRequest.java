package com.basedamo.net;

import android.text.TextUtils;

import com.basedamo.utils.LogController;
import com.basedamo.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hui on 2016/1/21.
 */
public class BaseRequest {

    private final static String TAG = "BaseRequest";
    private static final int DEFAULT_TIMEOUT = 15000;


    /**
     * 网络服务请求
     *
     * @param url
     * @param params
     * @param onParseHttpResponseList
     */
    public void doAsyncHttpFormPost(String url, Map<String, ? extends Object> params,
                                    OnParseHttpResponse onParseHttpResponseList) {

        List<OnParseHttpResponse> list = new ArrayList<>();
        list.add(onParseHttpResponseList);
        doAsyncHttpFormPost(url, params, list);

    }

    /**
     * 网络服务请求
     *
     * @param url
     * @param params
     * @param onParseHttpResponseList
     */
    public void doAsyncHttpFormPost(String url, Map<String, ? extends Object> params,
                                    List<OnParseHttpResponse> onParseHttpResponseList) {
        if (url.startsWith("/")) {
            url = getApiServer() + url;
        }
        LogController.d(url);

        if (null == params) {
            params = new HashMap<>();
        }

        final List<OnParseHttpResponse> list = new ArrayList<>();
        list.add(new DefaultImplOnParseHttpResponse());
        if (null != onParseHttpResponseList) {
            list.addAll(onParseHttpResponseList);
            onParseHttpResponseList.clear();
        }

        RequestParams requestParams = new RequestParams();

        //add global params
        Map<String, String> globalParams = getGlobalParams();
        for (Map.Entry<String, String> entry : globalParams.entrySet()) {
            requestParams.addBodyParameter(entry.getKey(), entry.getValue());
            LogController.d(entry.getKey() + ": " + entry.getValue());
        }

        //add api params
        for (Map.Entry<String, ? extends Object> entry : params.entrySet()) {
            Object obj = entry.getValue();
            if (obj instanceof String) {
                String str = (String) obj;
                if (TextUtils.isEmpty(str)) {
                    continue;
                }
                requestParams.addBodyParameter(entry.getKey(), str);
            } else if (obj instanceof File) {
                File f = (File) obj;
                requestParams.addBodyParameter(entry.getKey(), f);
            }
            LogController.d(entry.getKey() + ": " + entry.getValue());
        }

        HttpUtils httpUtils = new HttpUtils(DEFAULT_TIMEOUT);
        final String finalUrl = url;
        httpUtils.send(HttpRequest.HttpMethod.POST, url, requestParams, new RequestCallBack<String>()

                {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogController.d("URL=" + finalUrl + "\n" + responseInfo.result);
                        if (null != list) {
                            for (OnParseHttpResponse onParseHttpResponse : list) {
                                if (null != onParseHttpResponse) {
                                    onParseHttpResponse.onParseHttpResponse(responseInfo.result);
                                }
                            }
                            list.clear();
                        }
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        LogController.e(e.getExceptionCode() + "");
                        LogController.e(e.toString());
                        LogController.e(s);
                        ToastUtil.show("网络繁忙，请稍后重试。\n[" + e.getExceptionCode() + "]");

//                        if (null != list) {
//                            for (OnParseHttpResponse onParseHttpResponse : list) {
//                                if (null != onParseHttpResponse) {
//                                    onParseHttpResponse.onParseHttpResponse(httpResponse);
//                                }
//                            }
//                            list.clear();
//                        }
                    }
                }
        );
    }

    private class DefaultImplOnParseHttpResponse implements OnParseHttpResponse {

        @Override
        public void onParseHttpResponse(String response) {

        }
    }

    public String getApiServer() {
        return "";
    }

    public Map<String, String> getGlobalParams() {
        return new HashMap<>();
    }
}
