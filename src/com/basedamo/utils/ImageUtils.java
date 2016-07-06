package com.basedamo.utils;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.basedamo.MyApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;


/**
 * 图片加载帮助类
 * universal-image-loader插件
 */
public class ImageUtils {
    private static final String TAG = "ImageUtils";
    private static ImageUtils imageUtils;
    private DisplayImageOptions defaultOptions;

    private ImageUtils() {

    }

    static {
        init();
    }

    public static ImageUtils getInstance() {
        if (imageUtils == null) {
            imageUtils = new ImageUtils();

        }
        return imageUtils;

    }
//
//    public void displayResource(ImageView iv, int id) {
//        ImageLoader.getInstance().displayImage("drawable://" + id, iv);
//    }
//
//    public void displayResource(ImageView iv, int id,
//                                ImageLoadingListener listener) {
//        ImageLoader.getInstance()
//                .displayImage("drawable://" + id, iv, listener);
//    }
//
//    public void displayImage(String url, ImageView iv, int defaultID,
//                             int errorID) {
//        Log.i(TAG, "porurl>>>>" + url);
//        url = getCorrectUrl(url);
//        Log.i(TAG, "porurl>>>>" + url);
//        if (!imageAddressLegal(url)) {
//            iv.setImageResource(errorID);
//        } else {
//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .cacheInMemory(true)
//                    .cacheOnDisk(true)
//                    .showImageOnLoading(defaultID)
//                    .showImageOnFail(errorID)
//                    .build();
//
//            ImageLoader.getInstance().displayImage(url, iv, options);
//
//        }
//    }

//    public void displayImage(String target, final String thumbnail,
//                             final int failedBkg, final ImageView iv) {
//        final String mTarget = getCorrectUrl(target);
//        if (!imageAddressLegal(target)) {
//            if (failedBkg != -1) {
//                iv.setImageResource(failedBkg);
//            }
//        } else {
//            if (imageAddressLegal(thumbnail)) {
//                ImageLoader.getInstance().loadImage(thumbnail,
//                        new ImageLoadingListener() {
//
//                            @Override
//                            public void onLoadingStarted(String imageUri,
//                                                         View view) {
//                                
//
//                            }
//
//                            @Override
//                            public void onLoadingFailed(String imageUri,
//                                                        View view, FailReason failReason) {
//                                
//                                ImageLoader.getInstance().displayImage(mTarget,
//                                        iv);
//                            }
//
//                            @Override
//                            public void onLoadingComplete(String imageUri,
//                                                          View view, Bitmap loadedImage) {
//                                
//                                iv.setImageBitmap(loadedImage);
//                                displayImage(mTarget, iv, failedBkg);
//                            }
//
//                            @Override
//                            public void onLoadingCancelled(String imageUri,
//                                                           View view) {
//                                
//
//                            }
//                        });
//            } else {
//                ImageLoader.getInstance().displayImage(target, iv);
//            }
//
//        }
//    }

//    public void displayImage(String target, final String thumbnail,
//                             final int failedBkg, final ImageView iv, final Handler handler) {
//        final String mTarget = getCorrectUrl(target);
//        if (!imageAddressLegal(target)) {
//            if (failedBkg != -1) {
//                iv.setImageResource(failedBkg);
//            }
//        } else {
//            if (imageAddressLegal(thumbnail)) {
//                ImageLoader.getInstance().loadImage(thumbnail,
//                        new ImageLoadingListener() {
//
//                            @Override
//                            public void onLoadingStarted(String imageUri,
//                                                         View view) {
//                                
//
//                            }
//
//                            @Override
//                            public void onLoadingFailed(String imageUri,
//                                                        View view, FailReason failReason) {
//                                
//                                displayImage(mTarget, iv, failedBkg, handler);
//                            }
//
//                            @Override
//                            public void onLoadingComplete(String imageUri,
//                                                          View view, Bitmap loadedImage) {
//                                
//                                iv.setImageBitmap(loadedImage);
//                                displayImage(mTarget, iv, failedBkg, handler);
//                            }
//
//                            @Override
//                            public void onLoadingCancelled(String imageUri,
//                                                           View view) {
//                                
//
//                            }
//                        });
//            } else {
//                displayImage(mTarget, iv, failedBkg, handler);
//            }
//
//        }
//    }

//    public void displayImage(String mTarget, ImageView iv, int failedBkg) {
//        
//        if (!imageAddressLegal(mTarget)) {
//            iv.setImageResource(failedBkg);
//        } else {
//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .cacheInMemory(true).cacheOnDisk(true)
//                    .showImageOnFail(failedBkg).build();
//
//            ImageLoader.getInstance().displayImage(mTarget, iv, options);
//
//        }
//    }
//
//    public void displayImage(String mTarget, final ImageView iv, int failedBkg,
//                             final Handler handler) {

    //        if (!imageAddressLegal(mTarget)) {
//            iv.setImageResource(failedBkg);
//        } else {
//            ImageLoader.getInstance().displayImage(mTarget, iv,
//                    new ImageLoadingListener() {
//
//                        @Override
//                        public void onLoadingStarted(String imageUri, View view) {
//                            
//                            handler.sendEmptyMessage(0x1111);
//                        }
//
//                        @Override
//                        public void onLoadingFailed(String imageUri, View view,
//                                                    FailReason failReason) {
//                            
//                            ImageLoader.getInstance()
//                                    .displayImage(imageUri, iv);
//                            handler.sendEmptyMessage(0x123);
//                        }
//
//                        @Override
//                        public void onLoadingComplete(String imageUri,
//                                                      View view, Bitmap loadedImage) {
//                            
//                            handler.sendEmptyMessage(0x123);// 隐藏加载进度条
//                            iv.setImageBitmap(loadedImage);
//                        }
//
//                        @Override
//                        public void onLoadingCancelled(String imageUri,
//                                                       View view) {
//                            
//
//                        }
//                    });
//        }
//    }
//
    public void downloadImage(String target, ImageLoadingListener listener) {
        target = getCorrectUrl(target);
        ImageLoader.getInstance().loadImage(target, listener);
    }
//
//    public void displayImage(String target, ImageView iv,
//                             ImageLoadingListener listener) {
//        target = getCorrectUrl(target);
//        if (imageAddressLegal(target)) {
//            ImageLoader.getInstance().displayImage(target, iv, listener);
//        } else {
//            listener.onLoadingFailed("", iv, null);
//        }
//    }
//
//    public void displayImage(String target, ImageView iv,
//                             BitmapDrawable defaultBmp, BitmapDrawable failedBmp) {
//        target = getCorrectUrl(target);
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true).cacheOnDisk(true)
//                .showImageOnLoading(defaultBmp).showImageOnFail(failedBmp)
//                .build();
//        if (!imageAddressLegal(target)) {
//            iv.setImageDrawable(failedBmp);
//        } else {
//            ImageLoader.getInstance().displayImage(target, iv, options);
//        }
//    }

    public void displayImage(String url, ImageView iv) {
        url = getCorrectUrl(url);
        if (imageAddressLegal(url)) {
            if (defaultOptions == null) {
                defaultOptions = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
//                    .showImageOnLoading(defaultID)
//                    .showImageOnFail(errorID)
                        .build();
            }
            ImageLoader.getInstance().displayImage(url, iv, defaultOptions);
        }
    }

//    /**
//     * 根据宽高得到缩略图
//     */
//    public String getThumbnail(String url, int width, int height, int quality) {
//        url = getCorrectUrl(url);
//        String prefix = url.substring(url.lastIndexOf(".") + 1);
//        String image_suffix = "@0e_" + (int) width + "w_" + (int) height
//                + "h_0c_0i_1o_" + quality + "Q_1x." + prefix;
//        return url + image_suffix;
//    }

    private boolean imageAddressLegal(String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            return false;
        }
        String temp = imageUrl.toLowerCase();

        if (temp.startsWith("http")) {
            return true;
        } else {
            return false;
        }
    }

//    public static String addImageSuffix(String url, String suffix) {
//        if (TextUtils.isEmpty(url)) {
//            return url;
//        }
//        String result = url.toLowerCase();
//        if (result.startsWith(Constants.picRootAddress)
//                && !result.contains("@") && !result.contains("|")) {
//
//            url = url + suffix;
//            if (result.contains(".png")) {
//                url = url.replace(".jpg", ".png");
//                url = url.replace(".JPG", ".png");
//            }
//        }
//        return url;
//    }

    public String getCorrectUrl(String url) {

        return Uri.decode(url);

    }

    public static void init() {
        Context context = MyApplication.getContext();
        File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                "bee_k77/Cache");// 获取到缓存的目录地址
        // 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                // max width, max height，即保存的每个缓存文件的最大长宽
                .memoryCacheExtraOptions(480, 800)
                // Can slow ImageLoader, use it carefully (Better don't use it)设置缓存的详细信息，最好不要设置这个
                //.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null)
                // 线程池内加载的数量
                .threadPoolSize(5)
                // 线程优先级
                .threadPriority(Thread.NORM_PRIORITY - 2)
            /*
             * When you display an image in a small ImageView
             *  and later you try to display this image (from identical URI) in a larger ImageView
             *  so decoded image of bigger size will be cached in memory as a previous decoded image of smaller size.
             *  So the default behavior is to allow to cache multiple sizes of one image in memory.
             *  You can deny it by calling this method:
             *  so when some image will be cached in memory then previous cached size of this image (if it exists)
             *   will be removed from memory cache before.
             */
                //.denyCacheImageMultipleSizesInMemory()

                // You can pass your own memory cache implementation你可以通过自己的内存缓存实现
                // .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // .memoryCacheSize(2 * 1024 * 1024)
                //硬盘缓存50MB
                .diskCacheSize(50 * 1024 * 1024)
                //将保存的时候的URI名称用MD5
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                // 加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())//将保存的时候的URI名称用HASHCODE加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCacheFileCount(500) //缓存的File数量
                .diskCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                // .imageDownloader(new BaseImageDownloader(context, 5 * 1000,
                // 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }

//	public static String checkPortrait(String portraitUrl, int width) {
//		String result = portraitUrl;
//		if (!portraitUrl.startsWith(Constants.picRootAddress)) {
//			return result;
//		}
//		if (TextUtils.isEmpty(result)) {
//			return result;
//		}
//		if (portraitUrl.contains("@")) {
//			if (portraitUrl.contains("|")) {
//				return result;
//			}
//			result = portraitUrl + "|" + width + "w_" + width + "h";
//		} else {
//			result = portraitUrl + "@" + width + "w_" + width + "h";
//		}
//		if (result.contains("png")) {
//			result += ".png";
//		} else {
//			result += ".jpg";
//		}
//		return result;
//	}
}
