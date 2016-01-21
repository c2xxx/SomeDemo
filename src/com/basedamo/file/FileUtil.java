package com.basedamo.file;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.basedamo.utils.MD5Util;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 文件处理
 * Created by hui on 2015/12/28.
 */
public class FileUtil {

    /**
     * 唯一标识，仅用于取名时不与其他应用文件名冲突
     */
    private static String packageName = null;
    private static String cacheDir;//缓存路径
    private static String cacheTempDir;//临时文件缓存路径
    private static Map<String, FileDownLoad> loadingUrlMap = new HashMap<>();


    public static void init(Context context) {

        packageName = context.getPackageName();

        String strPath = null;
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdFile = Environment.getExternalStorageDirectory();
            strPath = sdFile.getAbsolutePath() + "/basedemo/";
            if (!FileUtil.makeDirExist(new File(strPath))) {
                return;
            }
        } else {
            String strCacheDir = context.getCacheDir().getAbsolutePath();
            strPath = strCacheDir + "/basedemo/";
        }

        setCachePath(strPath);
    }

    /**
     * 设置图片缓存路径
     */
    private static void setCachePath(String strPicCachePath) {
        if (TextUtils.isEmpty(strPicCachePath)) {
            return;
        }
        cacheDir = strPicCachePath;
        cacheTempDir = strPicCachePath + "/temp/";

    }

    /**
     * 如果文件夹不存在则创建
     * if necessary, create directory of parameter dir
     *
     * @param dirFile File
     * @return boolean
     */
    public static boolean makeDirExist(File dirFile) {
        if (dirFile.exists()) {
            return true;
        }

        if (dirFile.mkdirs()) {
            return true;
        }

        return false;
    }


    /**
     * @param url    远程路径
     * @param result 返回结果的处理
     * @see FileUtil#download(String, DownLoadResult, boolean)
     */
    public static void download(final String url, final DownLoadResult result) {
        download(url, result, false);
    }

    /**
     * 下载文件<br>
     * 如果多次下载同一个文件，只会下载一次
     *
     * @param url                  远程路径
     * @param result               返回结果的处理
     * @param isOnlyCallLastResult 是否只响应最后一次回调
     */
    public static void download(final String url, final DownLoadResult result, boolean isOnlyCallLastResult) {
        final String tempPath = getTempPath();
        final String localPath = getLocolPath(url);
        final File fileLocol = new File(localPath);
        if (fileLocol.exists()) {
            result.onSuccess(fileLocol);
            return;
        }

        FileDownLoad fileDownLoad = null;

        RequestCallBack<File> requestCallBack = new RequestCallBack<File>() {

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                result.onLoading(total, current, isUploading);
            }

            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                result.onSuccess(fileLocol);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                result.onFailure(e, s);
            }
        };
        if (loadingUrlMap.containsKey(localPath)) {
            fileDownLoad = loadingUrlMap.get(localPath);
            if (isOnlyCallLastResult) {
                fileDownLoad.reSetCallBack(requestCallBack);
            } else {
                fileDownLoad.addRequestCallBack(requestCallBack);
            }
        } else {
            List<RequestCallBack> callBacks = new ArrayList<>();
            RequestCallBack<File> defaultCallBack = new RequestCallBack<File>() {
                @Override
                public void onCancelled() {
                    loadingUrlMap.remove(localPath);
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    loadingUrlMap.remove(localPath);
                    if (responseInfo.result.exists()) {
                        if (fileLocol.exists()) {
                            fileLocol.delete();
                        }
                        responseInfo.result.renameTo(fileLocol);
                    }
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    loadingUrlMap.remove(localPath);
                }
            };
            callBacks.add(requestCallBack);
            fileDownLoad = new FileDownLoad(url, tempPath, defaultCallBack, callBacks);
            fileDownLoad.download();
            loadingUrlMap.put(localPath, fileDownLoad);
        }
    }

    /**
     * 下载文件结果处理
     */
    public static abstract class DownLoadResult {
        public void onLoading(long total, long current, boolean isUploading) {
        }

        public abstract void onSuccess(File file);

        public abstract void onFailure(HttpException e, String s);
    }


    /**
     * @param remoteURL 文件远程路径
     * @return 临时文件名称
     */
    public static String getLocolPath(String remoteURL) {

        File file = new File(cacheDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return cacheDir + "/" + MD5Util.MD5(packageName + remoteURL) + getExtension(remoteURL);
    }

    /**
     * @return 临时文件名称
     */
    public static String getTempPath() {
        File file = new File(cacheTempDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return cacheTempDir + "/" + UUID.randomUUID().toString();
    }

    /**
     * @param extension 文件后缀名
     * @return 临时文件名称
     */
    public static String getTempPath(String extension) {
        return getTempPath() + "." + extension;
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getExtension(String fileName) {
        if (fileName == null || fileName.indexOf(".") == -1) {
            return "";
        }

        String prefix = fileName.substring(fileName.lastIndexOf("."));
        return prefix;
    }
}
