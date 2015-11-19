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

import com.basedamo.R;

public class WebViewActivity extends Activity {
	protected static final String TAG = WebViewActivity.class.getSimpleName();
	private WebView wv_webview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		initViews();
		initDatas();
	}

	private void initDatas() {
		// wv_webview.loadUrl("file:///android_asset/demo.html");//���ر���html
		wv_webview.loadUrl("http://www.shoujikanbing.com");
	}

	private void initViews() {
		wv_webview = (WebView) findViewById(R.id.wv_webview);

		// ֧��javascript//����
		wv_webview.getSettings().setJavaScriptEnabled(true);
		// ���ÿ���֧������
		wv_webview.getSettings().setSupportZoom(true);
		wv_webview.getSettings().setBuiltInZoomControls(true);

		// ���������ݷ���webview�ȿ��һ���С������ܻ����ҳ��������ʧЧ��
		wv_webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// �������������,���ô����ԣ�������������š�
		wv_webview.getSettings().setUseWideViewPort(true);
		// ����Ӧ��Ļ
		wv_webview.getSettings().setLoadWithOverviewMode(true);

		// �������أ���ת��url
		wv_webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String targetURL,
					Bitmap favicon) {
				super.onPageStarted(view, targetURL, favicon);
				Log.d(TAG, "targetURL=" + targetURL);// ���ػ�����ת��url
			}
		});// ʹ�õ�ǰWebview������
	}
}
