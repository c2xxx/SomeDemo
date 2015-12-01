package com.chen.utils;


import android.content.Context;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;


/*
* 语音输入类
* 在使用之前一定要注册，在打开使用语音输入的Activity之前注册
* */
public abstract class XunFeiVoiceInputHelper implements RecognizerDialogListener {


    private Context context;
    // 识别窗口
    private RecognizerDialog iatDialog;

    public XunFeiVoiceInputHelper(Context context) {
        this.context = context;
    }


    /**
     * 显示听写对话框.
     *
     * @param
     */
    public void start() {
        if (null == iatDialog) {
            // 初始化听写Dialog
            iatDialog = new RecognizerDialog(context, new InitListener() {
                @Override
                public void onInit(int code) {

                }
            });
        }

        // 清空Grammar_ID，防止识别后进行听写时Grammar_ID的干扰
        iatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);

        // 设置听写Dialog的引擎
        iatDialog.setParameter(SpeechConstant.DOMAIN, "auto");

        // 设置采样率参数，支持8K和16K
        iatDialog.setParameter(SpeechConstant.SAMPLE_RATE, "16000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        iatDialog.setParameter(SpeechConstant.ASR_PTT, "0");

        // 显示听写对话框
        iatDialog.setListener(this);
        iatDialog.show();

    }

    @Override
    public void onResult(RecognizerResult recognizerResult, boolean b) {
        String text = parseIatResult(recognizerResult.getResultString());
        onReceiveText(text);
    }

    @Override
    public void onError(SpeechError speechError) {

    }


    /**
     * @param text 收到语音输入结果
     */
    protected abstract void onReceiveText(String text);

    private String parseIatResult(String json) {
        StringBuffer ret = new StringBuffer();
        try {
            JSONTokener tokener = new JSONTokener(json);
            JSONObject joResult = new JSONObject(tokener);

            JSONArray words = joResult.getJSONArray("ws");
            for (int i = 0; i < words.length(); i++) {
                // 转写结果词，默认使用第一个结果
                JSONArray items = words.getJSONObject(i).getJSONArray("cw");
                JSONObject obj = items.getJSONObject(0);
                ret.append(obj.getString("w"));
                //如果需要多候选结果，解析数组其他字段
//			for(int j = 0; j < items.length(); j++)
//			{
//					JSONObject obj2 = items.getJSONObject(j);
//					ret.append(obj2.getString("w"));
//			}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret.toString();
    }

}
