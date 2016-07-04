package com.basedamo.annotation;

import com.basedamo.utils.LogController;

import java.lang.reflect.Field;

/**
 * Created by ChenHui on 2016/7/4.
 */




public class FruitInfoUtil {
    public static void getFruitInfo(Class<?> clazz) {

        String strFruitName = " 水果名称：";
        String strFruitColor = " 水果颜色：";
        String strFruitProvicer = "供应商信息：";

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(FruitName.class)) {
                FruitName fruitName = field.getAnnotation(FruitName.class);
                LogController.d(strFruitName + fruitName.value());
                LogController.d("修改后:" + strFruitName + fruitName.value());
            } else if (field.isAnnotationPresent(FruitColor.class)) {
                FruitColor fruitColor = field.getAnnotation(FruitColor.class);
                strFruitColor += fruitColor.fruitColor().toString();
                LogController.d(strFruitColor);
            } else if (field.isAnnotationPresent(FruitProvider.class)) {
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                strFruitProvicer += " 供应商编号：" + fruitProvider.id() + " 供应商名称：" + fruitProvider.name() + " 供应商地址：" + fruitProvider.address();
                LogController.d(strFruitProvicer);
            }
        }
    }
}
