package com.basedamo.activity;

import com.alibaba.fastjson.JSON;
import com.basedamo.data.IpInfo;
import com.basedamo.utils.LogController;
import com.basedamo.utils.ToastUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by ChenHui on 2016/11/10.
 */
public class RetrofitActivity extends OneButtonActivity {

    @Override
    protected void executeTest() {
        ToastUtil.show("结果看Log");
        testGithubApi();
        testIpApi2();
    }

    private void testIpApi2() {
        //创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.weather.com.cn/")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build();

        GitHubApi apiStores = retrofit.create(GitHubApi.class);
        Call<IpInfo> ipInfoCall = apiStores.getIpInfo2();
        ipInfoCall.enqueue(new Callback<IpInfo>() {
            @Override
            public void onResponse(Call<IpInfo> call, Response<IpInfo> response) {
                LogController.d("请求得到的数据：code=" + response.code());
                LogController.d("请求得到的数据：city=" + response.body().getWeatherinfo().getCity());
                LogController.d("请求得到的数据：原文：" + new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<IpInfo> call, Throwable t) {
                LogController.d("请求失败：" + JSON.toJSONString(t));
            }
        });
    }

    private void testGithubApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://static.blog.csdn.net/")
                .build();
        GitHubApi repo = retrofit.create(GitHubApi.class);

        Call<ResponseBody> call = repo.getSimpleGet();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    LogController.d("请求结果字符串：" + response.body().source().readUtf8().toString());
                } catch (IOException e) {
                    LogController.d("请求结果解析失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                LogController.d("请求失败=" + call);
            }
        });
    }

    public interface GitHubApi {

        @GET("scripts/ad.js")
        Call<ResponseBody> getSimpleGet();


        //ResponseBody是Retrofit自带的返回类，
        @GET("data/sk/101110101.html")
        Call<IpInfo> getIpInfo2();
    }
}
