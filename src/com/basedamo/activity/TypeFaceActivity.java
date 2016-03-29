package com.basedamo.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.R;

/**
 * Created by Administrator on 2016/3/29.
 */
public class TypeFaceActivity extends BaseActivity {

    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_typeface);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_typeface_type_a).setOnClickListener(this);
        findViewById(R.id.btn_typeface_type_b).setOnClickListener(this);
        findViewById(R.id.btn_typeface_type_c).setOnClickListener(this);
        findViewById(R.id.btn_typeface_type_d).setOnClickListener(this);
        findViewById(R.id.btn_typeface_type_e).setOnClickListener(this);
        editText = (EditText) findViewById(R.id.et_typeface_display);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_typeface_type_a:
                TextView tv = null;
                if (v instanceof TextView) {
                    tv = (TextView) v;
                }
                if (tv != null) {
                    editText.setTypeface(tv.getTypeface());
                }
                break;
            case R.id.btn_typeface_type_b:
                Typeface face = Typeface.SERIF;
                editText.setTypeface(face);
                break;
            case R.id.btn_typeface_type_c:
                editText.setTypeface(Typeface.MONOSPACE);
                break;
            case R.id.btn_typeface_type_d:
                setTypeFace(editText, "fonts/a.ttf");
                break;
            case R.id.btn_typeface_type_e:
                setTypeFace(editText, "fonts/b.ttf");
                break;
        }
    }

    private void setTypeFace(TextView tv, String ttfFile) {
        Typeface face = Typeface.createFromAsset(getAssets(), ttfFile);
        tv.setTypeface(face);
    }


}
