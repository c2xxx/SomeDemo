package com.basedamo.httpserver;

/**
 * Created by ChenHui on 2016/7/1.
 */

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.UnsupportedEncodingException;

public class DataHandle {

    private String _receiveInfo = "";
    private HttpHeader _httpHeader = null;
    private String _encoding = "utf-8";
    private String _serverName = "Simple Web Server";
    private String _responseCode = "200 OK";
    private String _contentType = "text/html";

    public DataHandle(byte[] recieveData) {
        try {
            this._receiveInfo = new String(recieveData, _encoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        _httpHeader = new HttpHeader(_receiveInfo);
    }

    public byte[] fetchContent() {
        byte[] backData = null;
        if (!isSupportMethod()) {
            backData = fetchNotSupportMethodBack();
            return backData;
        }

        String filePath = fetchFilePath();
        try {
            filePath = java.net.URLDecoder.decode(filePath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        boolean hasFile = FileSp.isExist(filePath);
        Log.e(MyLog.getPosition(3, 4) + "FilePath", filePath + "   " + hasFile);
        if (!hasFile) {
            backData = fetchNotFoundBack();
            return backData;
        }

        if (!isSupportExtension()) {
            backData = fetchNotSupportFileBack();
            return backData;
        }

        backData = fetchBackFromFile(filePath);
        return backData;
    }

    public byte[] fetchHeader(int contentLength) {
        byte[] header = null;
        try {
            header = ("HTTP/1.1 " + _responseCode + "\r\n"
                    + "Server: " + _serverName + "\r\n"
                    + "Content-Length: " + contentLength + "\r\n"
                    + "Connection: close\r\n"
                    + "Content-Type: " + _contentType + ";charset=" + _encoding + "\r\n\r\n").getBytes(_encoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return header;
    }

    @SuppressLint("DefaultLocale")
    private boolean isSupportMethod() {
        String method = _httpHeader.getMethod();
        if (method == null || method.length() <= 0) {
            return false;
        }
        method = method.toUpperCase();
        if (method.equals("GET") || method.equals("POST")) {
            return true;
        }

        return false;
    }

    private boolean isSupportExtension() {
        String contentType = _httpHeader.getContentType();
        if (contentType == null || contentType.length() <= 0) {
            return false;
        }
        _contentType = contentType;
        return true;
    }

    private byte[] fetchNotSupportMethodBack() {
        byte[] backData = null;
        String notImplementMethod = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><h2>"
                + _serverName
                + "</h2><div>501 - Method Not Implemented</div></body></html>";
        try {
            backData = notImplementMethod.getBytes(_encoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return backData;
    }

    /**
     * 类型不支持的错误
     *
     * @return
     */
    private byte[] fetchNotSupportFileBack() {
        byte[] backData = null;
        String notImplementMethod = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><h2>"
                + _serverName
                + "</h2><div>404.7 Not Found(Type Not Support)</div></body></html>";
        try {
            backData = notImplementMethod.getBytes(_encoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return backData;
    }

    private byte[] fetchBackFromFile(String filePath) {

        byte[] content;

        content = FileSp.read(filePath);
        return content;
    }

    private String fetchFilePath() {
        String root = Defaults.getRoot();

        String fileName = _httpHeader.getFileName();
        String filePath;

        if (fileName.startsWith("/") || fileName.startsWith("\\")) {
            filePath = root + fileName.substring(1);
        } else {
            filePath = root + fileName;
        }

        return filePath;
    }

    private byte[] fetchNotFoundBack() {
        byte[] notFoundData = null;
        String notFoundStr = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><h2>"
                + _serverName + "</h2><div>404 - Not Found</div></body></html>";
        try {
            notFoundData = notFoundStr.getBytes(_encoding);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return notFoundData;
    }
}