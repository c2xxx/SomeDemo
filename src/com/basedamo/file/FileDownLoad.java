package com.basedamo.file;

import com.basedamo.utils.LogController;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.util.List;

/**
 * 文件下载
 * Created by hui on 2015/12/29.
 */
public class FileDownLoad {
    private List<RequestCallBack> callBackList = null;
    private RequestCallBack defaultRequestCallBack = null;
    private String url = null;
    private String targetPath = null;

    /**
     * @param url          文件远程路径
     * @param targetPath   文件本地路径
     * @param callBackList 回调
     */
    public FileDownLoad(String url, String targetPath, RequestCallBack defaultRequestCallBack, List<RequestCallBack> callBackList) {
        this.url = url;
        this.targetPath = targetPath;
        this.callBackList = callBackList;
        this.defaultRequestCallBack = defaultRequestCallBack;
    }

    public void addRequestCallBack(RequestCallBack callBack) {
        if (callBack != null) {
            callBackList.add(callBack);
        }
    }

    public void reSetCallBack(RequestCallBack callBack) {
        if (callBack != null) {
            callBackList.clear();
            callBackList.add(callBack);
        }
    }

    public void download() {
        HttpUtils http = new HttpUtils();
        http.download(url, targetPath, new RequestCallBack<File>() {

            @Override
            public void onCancelled() {
                defaultRequestCallBack.onCancelled();
                for (RequestCallBack callBack : callBackList) {
                    callBack.onCancelled();
                }
            }

            @Override
            public void onStart() {
                defaultRequestCallBack.onStart();
                for (RequestCallBack callBack : callBackList) {
                    callBack.onStart();
                }
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                defaultRequestCallBack.onLoading(total, current, isUploading);
                for (RequestCallBack callBack : callBackList) {
                    callBack.onLoading(total, current, isUploading);
                }
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                defaultRequestCallBack.onSuccess(responseInfo);
                for (RequestCallBack callBack : callBackList) {
                    callBack.onSuccess(responseInfo);
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                defaultRequestCallBack.onFailure(e, s);
                for (RequestCallBack callBack : callBackList) {
                    callBack.onFailure(e, s);
                }
            }
        });
    }

    @Override
    public String toString() {
        return url;
    }
}
