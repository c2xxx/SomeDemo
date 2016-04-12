package com.basedamo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.view.ColorPickerView;

/**
 * Created by ChenHui on 2016/4/12.
 */
public class ColorPicker2Activity extends BaseActivity {

    private ColorPickerView cpv_colorpicker2;
    private TextView tv_colorpicker2_currentcolor;
    private int currentcolor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_colorpicker2);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        tv_colorpicker2_currentcolor = (TextView) findViewById(R.id.tv_colorpicker2_currentcolor);
        tv_colorpicker2_currentcolor.setBackgroundColor(currentcolor);
        cpv_colorpicker2 = (ColorPickerView) findViewById(R.id.cpv_colorpicker2);
        cpv_colorpicker2.setStartColor(currentcolor);
        cpv_colorpicker2.setOnPickColorListener(new ColorPickerView.OnPickColorListener() {
            @Override
            public void onChangedColor(int newColor) {
                currentcolor = newColor;
                tv_colorpicker2_currentcolor.setBackgroundColor(currentcolor);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
