package com.basedamo.activity;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.SeekBar;

import com.basedamo.BaseActivity;
import com.basedamo.R;

/**
 * Created by hui on 2015/12/1.
 */
public class CardViewActivity extends BaseActivity {
    private CardView mCardView;
    private SeekBar sb1;
    private SeekBar sb2;
    private SeekBar sb3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_cardview);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        mCardView = (CardView) findViewById(R.id.tv_item);
        sb1 = (SeekBar) findViewById(R.id.sb_1);
        sb2 = (SeekBar) findViewById(R.id.sb_2);
        sb3= (SeekBar) findViewById(R.id.sb_3);
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mCardView.setRadius(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mCardView.setCardElevation(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mCardView.setContentPadding(i,i,i,i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    protected void initData() {

    }
}
