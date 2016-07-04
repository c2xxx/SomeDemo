package com.basedamo.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ChenHui on 2016/7/1.
 */
public class FileHelp {

    /**
     * 将asserts目录中的文件拷贝到sd卡上
     *
     * @param context
     * @param assertSource
     * @param targetFileName
     */
    public static void copyAssertsFile(Context context, String assertSource, String targetFileName) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(assertSource);
            File targetFile = new File(targetFileName);
            File parent = targetFile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            fileOutputStream = new FileOutputStream(targetFile);
            byte[] buffer = new byte[512];
            int count;
            while ((count = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, count);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            LogController.printExceptionInfo(e);
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
            }
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }
}
