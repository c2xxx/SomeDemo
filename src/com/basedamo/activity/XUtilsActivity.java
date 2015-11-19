package com.basedamo.activity;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.basedamo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class XUtilsActivity extends Activity{
	
	protected static final String TAG = "XUtilsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xutils);
	}
	
	public void utilGet(View v)
	{
		//get带缓存，访问一次后，关闭网络也能访问到缓存的数据
		HttpUtils http = new HttpUtils();
		http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.GET,
		    "http://www.lidroid.com",
		    new RequestCallBack<String>(){
				@Override
				public void onCancelled() {
					super.onCancelled();
					Log.d(TAG, "onCancelled");
				}

				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					super.onLoading(total, current, isUploading);
					Log.d(TAG, "onLoading	"+total+"-"+current+"-"+isUploading);
				}

				@Override
				public void onStart() {
					super.onStart();
					Log.d(TAG, "onStart");
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Log.d(TAG, "onFailure");
					
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					Log.d(TAG, "onSuccess	"+responseInfo.result);
				} 
		});
	}
	
	public void utilPost(View v)
	{
		HttpUtils http = new HttpUtils();
		http.send(com.lidroid.xutils.http.client.HttpRequest.HttpMethod.POST,
		    "http://www.lidroid.com",
		    new RequestCallBack<String>(){
				@Override
				public void onCancelled() {
					super.onCancelled();
					Log.d(TAG, "onCancelled");
				}

				@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					super.onLoading(total, current, isUploading);
					Log.d(TAG, "onLoading	"+total+"-"+current+"-"+isUploading);
				}

				@Override
				public void onStart() {
					super.onStart();
					Log.d(TAG, "onStart");
				}

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Log.d(TAG, "onFailure");
					
				}

				@Override
				public void onSuccess(ResponseInfo<String> responseInfo) {
					Log.d(TAG, "onSuccess	"+responseInfo.result);
				} 
		});
	}
}
