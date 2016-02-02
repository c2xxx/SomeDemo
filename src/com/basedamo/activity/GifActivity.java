package com.basedamo.activity;

import android.os.Bundle;

import com.basedamo.BaseActivity;
import com.basedamo.R;
import com.basedamo.view.GifView;

/**
 * Created by hui on 2016/1/25.
 */
public class GifActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gif);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        GifView gifView = (GifView) findViewById(R.id.gif1);
        gifView.setMovieResource(R.drawable.youwrong);
        GifView gifView2 = (GifView) findViewById(R.id.gif2);
        gifView2.setMovieResource(R.drawable.shaoqun);
    }

    @Override
    protected void initData() {

    }
}
