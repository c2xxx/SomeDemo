package com.basedamo.utils;

/**
 * Created by ChenHui on 2016/4/25.
 */
public class MyCrashHandler implements Thread.UncaughtExceptionHandler {


    private static MyCrashHandler myCrashHandler;

    //1.私有化构造方法
    private MyCrashHandler() {

    }

    public static synchronized MyCrashHandler getInstance() {
        if (myCrashHandler != null) {
            return myCrashHandler;
        } else {
            myCrashHandler = new MyCrashHandler();
            return myCrashHandler;
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        LogController.e("ERR Thread =" + thread.getName());
        LogController.printExceptionInfo(ex);
    }

    /**
     * 初始化
     */
    public void init() {
        Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
