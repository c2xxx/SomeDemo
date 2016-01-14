package com.basedamo.volley;

import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basedamo.R;

public class VolleyDemoActivity extends Activity{
	private String TAG=VolleyDemoActivity.class.getSimpleName();
	private ImageView iv_img;
	private NetworkImageView network_image_view;
	private TextView tv_show_response;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volleydemo);
		initViews();
	}
	
	private void initViews() {
		tv_show_response=(TextView) findViewById(R.id.tv_show_response);
		iv_img = (ImageView) findViewById(R.id.iv_img);
		network_image_view=(NetworkImageView) findViewById(R.id.network_image_view);
	}
	


	/**
	 * 显示文本
	 * @param response
	 */
	private void showText(String response) {
		if(response==null)response="";
		if(response.length()>100){
			response=response.substring(0,100);
		}
		tv_show_response.setText(response);
	}

	/**
	 * 访问文本资源
	 */
	public void requestForString(View v) {

		StringRequest stringRequest = new StringRequest(Method.POST,"http://www.baidu.com",
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.d(TAG, response);
						showText(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, error.getMessage(), error);
					}
				});

		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		mQueue.add(stringRequest);
	}

	/**
	 * 访问文本资源(post)
	 */
	public void requestForStringPost(View v) {

		String url = "http://www.baidu.com";
		StringRequest stringRequest = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d(TAG, response);
						showText(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, error.getMessage(), error);
					}
				}) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return super.getParams();
			}

			/*
			 * 设置优先级
			 */
			@Override
			public com.android.volley.Request.Priority getPriority() {
				return Priority.HIGH;
			}

		};

		RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
		mQueue.add(stringRequest);
	}

	/**
	 * 访问JSON资源
	 */
	public void jsonRequest(View v) {
		String url = "http://218.244.134.220:555/api/user_passport/login";
		JsonObjectRequest objRequest = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject obj) {
						Log.d(TAG, "JSON=" + obj + "");
						showText(obj.toString());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, error.getMessage(), error);
					}
				});
		RequestQueue queue = Volley.newRequestQueue(this);
		objRequest.setTag("obj");
		queue.add(objRequest);
	}

	/**
	 * 访问图片资源
	 */
	public void imageRequest(View v) {
		ImageRequest irequest = new ImageRequest(
				"https://www.baidu.com/img/baidu_jgylogo3.gif",
//				"http://7xk6kd.com2.z0.glb.qiniucdn.com/pic/186266160113090335.png",
				new Response.Listener<Bitmap>() {
					@SuppressWarnings("deprecation")
					@Override
					public void onResponse(Bitmap bitmap) {
						iv_img.setImageBitmap(bitmap);
					}
				}, 0, 0, Config.ARGB_8888, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				});
		RequestQueue queue = Volley.newRequestQueue(this);
		queue.add(irequest);
	}

	/**
	 * 访问图片资源ImageLoader
	 */
	public void imageLoader(View v) {
		RequestQueue queue = Volley.newRequestQueue(this);
		ImageLoader imageLoader = new ImageLoader(queue, new ImageCache() {
			@Override
			public void putBitmap(String url, Bitmap bitmap) {
			}

			@Override
			public Bitmap getBitmap(String url) {
				return null;
			}
		});
		ImageListener listener = ImageLoader.getImageListener(iv_img,
				R.drawable.img_default, R.drawable.img_err);
		imageLoader.get("http://img.blog.csdn.net/20140413205455484?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3VvbGluX2Jsb2c=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast", listener,200,200);
	}
	/**
	 * 访问图片资源ImageLoader(使用LruCache缓存)
	 */
	public void imageLoader2(View v) {
		RequestQueue queue = Volley.newRequestQueue(this);
		ImageLoader imageLoader = new ImageLoader(queue, new BitmapCache()); 
		ImageListener listener = ImageLoader.getImageListener(iv_img,
				R.drawable.img_default, R.drawable.img_err);
		
		imageLoader.get("http://img.blog.csdn.net/20140413205455484?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZ3VvbGluX2Jsb2c=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast", listener,200,200);
	}
	
	
	
	/**
	 * 使用NetworkImageView加载图片
	 */
	int network_image_view_index=1;
	public void networkImageView(View v) {
		String[] strs={"https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=1325988666,1290452431&fm=58","http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg"};
		String url=strs[(network_image_view_index++)%2];
		RequestQueue queue = Volley.newRequestQueue(this);
		network_image_view.setDefaultImageResId(R.drawable.img_default);
		network_image_view.setErrorImageResId(R.drawable.img_err);
		ImageLoader imageLoader = new ImageLoader(queue, new BitmapCache()); 
		network_image_view.setImageUrl(url,
						imageLoader);
	}
	
	public void clear(View v){
		tv_show_response.setText("");
		iv_img.setImageResource(R.drawable.ic_launcher);
	}
}
