package com.basedamo.slideviewpaper;

import com.basedamo.R;

import android.app.Activity;
import android.os.Bundle;

public class ViewPaperActivity extends Activity {
	private com.basedamo.slideviewpaper.SlideShowView sv_photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpaper);
		initViews();
		initDatas();
	}

	private void initViews() {
		sv_photo = (SlideShowView) findViewById(R.id.sv_photo);
	}

	private void initDatas() {
		String[] imageUrls = {
				"http://img5.imgtn.bdimg.com/it/u=1914015457,3086721878&fm=21&gp=0.jpg",
				"http://pic15.nipic.com/20110720/7434631_111124480000_2.jpg",
				"http://img3.imgtn.bdimg.com/it/u=3389105597,3842890376&fm=21&gp=0.jpg",
				"http://pica.nipic.com/2008-03-28/200832810200350_2.jpg" ,
				"http://pic1a.nipic.com/2009-02-20/2009220152758211_2.jpg" ,
				"http://pic23.nipic.com/20120918/10031483_133215033311_2.jpg" ,
				"http://img0.imgtn.bdimg.com/it/u=2065685890,1762233842&fm=21&gp=0.jpg" ,
				"http://img1.imgtn.bdimg.com/it/u=191698150,3774242073&fm=21&gp=0.jpg" };
		String[] urls = {};
		sv_photo.setView(imageUrls);
		sv_photo.setUrl(urls);
	}
}
