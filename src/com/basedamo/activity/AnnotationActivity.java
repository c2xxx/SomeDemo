package com.basedamo.activity;

import com.basedamo.annotation.Apple;
import com.basedamo.annotation.FruitInfoUtil;
import com.basedamo.utils.LogController;

/**
 * 自定义注解示例
 * Created by ChenHui on 2016/7/4.
 */
public class AnnotationActivity extends OneButtonActivity {
    @Override
    protected void executeTest() {
        LogController.d("执行。。。");
        FruitInfoUtil.getFruitInfo(Apple.class);

        Apple apple = new Apple();
        //这种方式没有去解释注解
        LogController.d("apple:" + apple.getAppleName());
    }
}
