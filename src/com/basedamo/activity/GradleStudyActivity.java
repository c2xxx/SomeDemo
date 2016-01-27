package com.basedamo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.BuildConfig;
import com.basedamo.R;
import com.basedamo.utils.ReadMainFestsInfo;

/**
 * Created by hui on 2016/1/26.
 */
public class GradleStudyActivity extends BaseActivity {
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gradle_study);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        tvMsg = (TextView) findViewById(R.id.tv_activity_gradle_study_msg);
    }

    @Override
    protected void initData() {
        StringBuilder sb = new StringBuilder();
        String versionName = ReadMainFestsInfo.readVersion(this, ReadMainFestsInfo.VERSION_NAME);
        String versioncode = ReadMainFestsInfo.readVersion(this, ReadMainFestsInfo.VERSION_CODE);
        String currentapiVersion = android.os.Build.VERSION.SDK_INT + "";
        String UMENG_CHANNEL = ReadMainFestsInfo.readMetaData(this, "UMENG_CHANNEL");

        sb.append("\n versionName=" + versionName);
        sb.append("\n versioncode=" + versioncode);
        sb.append("\n currentapiVersion=" + currentapiVersion);
        sb.append("\n UMENG_CHANNEL=" + UMENG_CHANNEL);


        sb.append("\n BuildConfig.DEBUG=" + BuildConfig.DEBUG);
        sb.append("\n BuildConfig.VERSION_NAME=" + BuildConfig.VERSION_NAME);
        sb.append("\n BuildConfig.VERSION_CODE=" + BuildConfig.VERSION_CODE);
        sb.append("\n BuildConfig.FLAVOR=" + BuildConfig.FLAVOR);
        sb.append("\n BuildConfig.CHEN_VERSION_NAME=" + BuildConfig.CHEN_VERSION_NAME);
        sb.append("\n BuildConfig.CHEN_DEBUG=" + BuildConfig.CHEN_DEBUG);
        sb.append("\n BuildConfig.BUILD_TYPE=" + BuildConfig.BUILD_TYPE);
        sb.append("\n BuildConfig.BUILD_TIME=" + BuildConfig.BUILD_TIME);


        tvMsg.setText(sb);
    }
}
