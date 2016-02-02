package com.cml.frame.rx_volley;

import android.app.Application;
import android.content.Context;

import com.cml.lib.rxvolley.framework.ImageCacheManager;
import com.cml.lib.rxvolley.framework.ImageCaches;
import com.cml.lib.rxvolley.framework.RequestManager;

/**
 * Created by cmlBeliever on 2016/2/1.
 */
public class FrameworkApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        // 初始化volley以及图片缓存信息
        initVolley();
    }

    /**
     * 初始化volley以及图片缓存信息
     */
    private void initVolley() {
        // 设定volley缓存
        RequestManager.init(this);
        ImageCacheManager.getInstance().init(this, this.getPackageCodePath(),
                ImageCaches.Disk.Size, ImageCaches.Disk.Format, ImageCaches.Disk.Quality,
                ImageCacheManager.CacheType.DISK);
    }

}
