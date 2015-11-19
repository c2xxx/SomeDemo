package com.basedamo.slideviewpaper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.basedamo.R;
import com.basedamo.volley.BitmapCache;

public class SlideShowView extends FrameLayout {
	private final static boolean isAutoPlay = true;
	private String[] imageUrls;
	private String[] urls;
	private List<ImageView> imageViewsList;
	private List<View> dotViewsList;

	private ViewPager viewPager;
	private int currentItem = 0;
	private ScheduledExecutorService scheduledExecutorService;

	private Context context;

	// Handler
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			viewPager.setCurrentItem(currentItem);
		}

	};

	public SlideShowView(Context context) {
		this(context, null);
	}

	public SlideShowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initData();
	}

	public void setView(String[] imageUrls) {
		this.imageUrls = imageUrls;
		initUI(context);
		if (isAutoPlay) {
			startPlay();
		}
	}

	public void setUrl(String[] urls) {
		this.urls = urls;
	}

	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 2,2,
				TimeUnit.SECONDS);
	}

	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}

	private void initData() {
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();

	}

	private void initUI(Context context) {
		Log.d("---", "initUI");
		
		if (imageUrls == null || imageUrls.length == 0)
			return;
		LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this,
				true);
		LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
		dotLayout.removeAllViews();

		for (int i = 0; i < imageUrls.length; i++) {
			ImageView view = new ImageView(context);
			view.setTag(imageUrls[i]);
			view.setTag("img"+i);
			if (i == 0)
				view.setBackgroundResource(R.drawable.appmain_subject_1);
			view.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(view);
			ImageView dotView = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 4;
			params.rightMargin = 4;
			dotLayout.addView(dotView, params);
			dotViewsList.add(dotView);
		}

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setFocusable(true);
		viewPager.setAdapter(new PhotoAdapter(imageUrls));
		viewPager.setOnPageChangeListener(new MyPageChangeListener());

	}

	private class PhotoAdapter extends PagerAdapter {
		private String[] images;
		private LayoutInflater inflater;
//		List<View> mViewList = new ArrayList<View>();  
		PhotoAdapter(String[] images) {
			this.images = images;

		}

		@Override
		public void destroyItem(View container, int position, Object object) {
//			((ViewPager) container).removeView(imageViewsList.get(position));
			View view = (View)object;  
	        ((ViewPager) container).removeView(view);  
//	        mViewList.add(view);  
		}

		@Override
		public Object instantiateItem(View container, final int position) {
//			View view = null;  
//	        if (mViewList!=null&&mViewList.size()>position) {   
//	                view = mViewList.remove(0);
//	        }  

			Log.d("---", "instantiateItem"+"  position="+position+"  imageViewsList.size()"+imageViewsList.size());
			if (position > imageViewsList.size() - 1
					|| images.length < position + 1) {
				return null;
			}

			ImageView imageView = imageViewsList.get(position);
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
//					if (!TextUtils.isEmpty(urls[position])) {
//						Intent mIntent = new Intent(context,
//								WebViewActivity.class);
//						mIntent.putExtra("title", "");
//						mIntent.putExtra("url", urls[position]);
//						context.startActivity(mIntent);
//					}
				}
			});
			if (!TextUtils.isEmpty(images[position])) {
//				new BaseRequest().loadImageByVolley(MyApplication.getContext(),
//						images[position], imageView, R.drawable.lunbo,
//						R.drawable.lunbo);
 
					if(!"loaded".equals(imageView.getTag()+"")){
						imageView.setTag("loaded");
						Log.d("---", "ImageLoader   tag="+imageView.getTag());
						RequestQueue queue = Volley.newRequestQueue(getContext());
						ImageLoader imageLoader = new ImageLoader(queue,BitmapCache.getInstance()); 
						ImageListener listener = ImageLoader.getImageListener(imageView,
								R.drawable.img_default, R.drawable.img_err);
						imageLoader.get(images[position], listener);
					}
			}

			((ViewPager) container).addView(imageViewsList.get(position));
			return imageViewsList.get(position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageViewsList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * ViewPager
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = false;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 1:
				isAutoPlay = false;
				break;
			case 2:
				isAutoPlay = true;
				break;
			case 0:
				if (viewPager.getCurrentItem() == viewPager.getAdapter()
						.getCount() - 1 && !isAutoPlay) {
					viewPager.setCurrentItem(0);
				} else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
					viewPager
							.setCurrentItem(viewPager.getAdapter().getCount() - 1);
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int pos) {
			// TODO Auto-generated method stub

			currentItem = pos;
			for (int i = 0; i < dotViewsList.size(); i++) {
				if (i == pos) {
					((View) dotViewsList.get(pos))
							.setBackgroundResource(R.drawable.dot_focus);
				} else {
					((View) dotViewsList.get(i))
							.setBackgroundResource(R.drawable.dot_blur);
				}
			}
		}

	}

	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	private void destoryBitmaps() {

		for (int i = 0; i < imageViewsList.size(); i++) {
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null) {
				// 
				drawable.setCallback(null);
			}
		}
	}

}