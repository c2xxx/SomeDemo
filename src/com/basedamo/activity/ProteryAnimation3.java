package com.basedamo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.protertyanimation.MyAnimView;

import java.util.ArrayList;
import java.util.List;

public class ProteryAnimation3 extends BaseActivity implements OnClickListener {
    private MyAnimView mav_test;
    private float initY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_proteryanimation3);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initViews() {
        mav_test = (MyAnimView) findViewById(R.id.mav_test);
        mav_test.post(new Runnable() {
            @Override
            public void run() {
                initY = mav_test.getY();
            }
        });

        List<Button> buttons = new ArrayList<Button>();
        buttons.add((Button) findViewById(R.id.btn_property_btn1));
        buttons.add((Button) findViewById(R.id.btn_property_btn2));
        buttons.add((Button) findViewById(R.id.btn_property_btn3));
        buttons.add((Button) findViewById(R.id.btn_property_btn4));
        buttons.add((Button) findViewById(R.id.btn_property_btn5));
        buttons.add((Button) findViewById(R.id.btn_property_btn6));
        buttons.add((Button) findViewById(R.id.btn_property_btn7));
        for (Button btn : buttons) {
            btn.setOnClickListener(this);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_property_btn1:
                mav_test.startAnimation_vertical();
                break;
            case R.id.btn_property_btn2:
                mav_test.startAnimation_vertical2();
                break;
            case R.id.btn_property_btn3:
                mav_test.startAnimation_vertical3();
                break;
            case R.id.btn_property_btn4:
                mav_test.startAnimation_vertical4();
                break;
            case R.id.btn_property_btn5:
                mav_test.animate().x(200).y(200).setDuration(3000);
                break;
            case R.id.btn_property_btn6:
                mav_test.animate().alpha(0.3f);
                break;
            case R.id.btn_property_btn7:
                mav_test.animate().x(0).y(initY).alpha(1f).setDuration(1000);
                break;
        }
    }
}
