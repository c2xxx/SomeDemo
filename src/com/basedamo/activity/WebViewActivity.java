package com.basedamo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.basedamo.BaseActivity;
import com.basedamo.R;

public class WebViewActivity extends BaseActivity {
	protected static final String TAG = WebViewActivity.class.getSimpleName();
	private WebView wv_webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_webview);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		// wv_webview.loadUrl("file:///android_asset/demo.html");//加载本地html
		wv_webview.loadUrl("http://www.shoujikanbing.com");
	}

	@Override
	protected void initViews() {
		wv_webview = (WebView) findViewById(R.id.wv_webview);

		// 支持javascript//必须
		wv_webview.getSettings().setJavaScriptEnabled(true);
		// 设置可以支持缩放
		wv_webview.getSettings().setSupportZoom(true);
		wv_webview.getSettings().setBuiltInZoomControls(true);

		// 把所有内容放在webview等宽的一列中。（可能会出现页面中链接失效）
		wv_webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// 扩大比例的缩放,设置此属性，可任意比例缩放。
		wv_webview.getSettings().setUseWideViewPort(true);
		// 自适应屏幕
		wv_webview.getSettings().setLoadWithOverviewMode(true);

		// 监听加载，跳转的url
		wv_webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String targetURL,
					Bitmap favicon) {
				super.onPageStarted(view, targetURL, favicon);
				Log.d(TAG, "targetURL=" + targetURL);// 加载或者跳转的url
			}
		});// 使用当前Webview打开链接
	}

}
