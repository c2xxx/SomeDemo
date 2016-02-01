package com.basedamo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.basedamo.BaseActivity;
import com.basedamo.BuildConfig;
import com.basedamo.R;
import com.basedamo.utils.ReadMainFestsInfo;

/**
 * Created by hui on 2016/1/26.
 * <p/>
 * 内容查看Gradle脚本，BuildConfig由Gradle生成
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
        sb.append("\n BuildConfig.GIT_BRANCH=" + BuildConfig.GIT_BRANCH);
        sb.append("\n BuildConfig.GIT_VERSION=" + BuildConfig.GIT_VERSION);


        tvMsg.setText(sb);
    }
}
/*
* 部分代码：
*
*
//获取编译时间
def releaseTime() {
    return new Date().format("yyyy-MM-dd HH:mm:ss")
}

println "the project name is $name"
task hello << {// 运行的时候可以用：gradle hello
    println "the current task name is $name"
    println "hello world"
}
//获取git版本号
def getGitVersion() {
    //获取git版本号，首先确保git命令能执行
    return 'git rev-parse --short HEAD'.execute().text.trim();
}
//获取git分支号
def getGitBranch() {
    //获取git版本号，首先确保git命令能执行
    def versionInfo = 'git branch'.execute().text.trim();
    versionInfo = versionInfo.replace("*", "");
    if (versionInfo.indexOf("\n") > -1) {
        versionInfo = versionInfo.substring(0, versionInfo.indexOf("\n"));
    }
    return versionInfo.trim();
}



    buildTypes {
        release {
            signingConfig signingConfigs.myConfig
        }
        debug {
            def buildTime = releaseTime();
            def gitVersion = getGitVersion();
            def gitBranch = getGitBranch();



            buildConfigField "boolean", "CHEN_DEBUG", "false"
            buildConfigField "String", "CHEN_VERSION_NAME", "\"AB\"+\"CD\""

            buildConfigField "String", "BUILD_TIME", "\"${buildTime}\""
            buildConfigField "String", "GIT_VERSION", "\"${gitVersion}\""
            buildConfigField "String", "GIT_BRANCH", "\"${gitBranch}\""
            buildConfigField "String", "TaskName", "\"$name\""
        }
    }
* */