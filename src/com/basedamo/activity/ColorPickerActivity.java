package com.basedamo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.view.ColorPickerDialog;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ColorPickerActivity extends BaseActivity {

    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_colorpicker);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        tvText = (TextView) findViewById(R.id.tv_color_picker_result);
        Button btnColorPicker = (Button) findViewById(R.id.btn_color_picker);
        btnColorPicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ColorPickerDialog dialog = new ColorPickerDialog(ColorPickerActivity.this,
                        tvText.getTextColors().getDefaultColor(),
                        "请选择颜色",
                        new ColorPickerDialog.OnColorChangedListener() {

                            @Override
                            public void colorChanged(int color) {
                                tvText.setBackgroundColor(color);
                                tvText.setText("#" + Integer.toHexString(color).toUpperCase());
                            }
                        });
                dialog.show();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
