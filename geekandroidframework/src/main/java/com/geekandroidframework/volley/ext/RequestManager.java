package com.geekandroidframework.volley.ext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;

public class RequestManager {
    private static RequestManager instance;
    private static ImageLoader mImageLoader;
    private RequestQueue mDataRequestQueue;
    private RequestQueue mImageQueue;

    private Context mContext;
    private Config mConfig;



    private DefaultHttpClient mDefaultHttpClient ;// = new DefaultHttpClient();;
    private HttpStack stack;

    public static class Config {
        private String mImageCachePath;
        private int mDefaultDiskUsageBytes;
        private int mThreadPoolSize;

        public Config(final String imageCachePath, final int defaultDiskUsageBytes, final int threadPoolSize) {
            this.mDefaultDiskUsageBytes = defaultDiskUsageBytes;
            this.mImageCachePath = imageCachePath;
            this.mThreadPoolSize = threadPoolSize;

        }
    }


    private RequestManager(Context context, Config config) {
        this.mContext = context;
        this.mConfig = config;
    }

    public static synchronized RequestManager initializeWith(Context context, Config config) {
        if (instance == null) {
            instance = new RequestManager(context, config);
        }
        return instance;
    }

    private synchronized RequestQueue getDataRequestQueue() {
        if (mDataRequestQueue == null) {
            Log.e("", "new request QUEUE");
            mDefaultHttpClient = new DefaultHttpClient();
            stack = new HurlStack();
            mDataRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext(), stack);
            mDataRequestQueue.start();
        }
        return mDataRequestQueue;
    }

    private synchronized RequestQueue loader() {
        if (this.mConfig == null) {
            throw new IllegalStateException(RequestManager.Config.class.getSimpleName() +
                    " is not initialized, call initializeWith(..) method first.");
        }
        if (mImageQueue == null) {
            File rootCache = mContext.getExternalCacheDir();
            if (rootCache == null) {
                rootCache = mContext.getCacheDir();
            }

            File cacheDir = new File(rootCache, mConfig.mImageCachePath);
            if(cacheDir.mkdirs()){
                HttpStack stack = new HurlStack();
                Network network = new BasicNetwork(stack);
                DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir, mConfig.mDefaultDiskUsageBytes);
                mImageQueue = new RequestQueue(diskBasedCache, network, mConfig.mThreadPoolSize);
                mImageQueue.start();
            }


        }
        return mImageQueue;
    }

    public static <T> void addRequest(Request<T> pRequest) {
        if (instance == null) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " is not initialized, call initializeWith(..) method first.");
        }
        if (pRequest.getTag() == null) {
            Log.e("RequestManager","Request Object Tag is not specified.");
        }
        pRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 30, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = instance.getDataRequestQueue();

        queue.add(pRequest);
    }

    public DefaultHttpClient getDefaultHttpClient() {
        return mDefaultHttpClient;
    }

    public synchronized static <T> void getImage(String url, ImageListener listener) {
        if (instance == null) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " is not initialized, call initializeWith(..) method first.");
        }
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(instance.loader(), new DiskCache(instance.mContext));
        }
        mImageLoader.get(url, listener);
    }

    public static void cancelPendingRequests(Object pRequestTag) {
        if (instance == null) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " is not initialized, call initializeWith(..) method first.");
        }
        if (instance.getDataRequestQueue() != null) {
            instance.getDataRequestQueue().cancelAll(pRequestTag);
        }
    }

    private static class DiskCache implements ImageCache {

        private static DiskLruImageCache mDiskLruImageCache;

        public DiskCache(Context context) {
            String cacheName = context.getPackageCodePath();
            int cacheSize = 1024 * 1024 * 10;
            mDiskLruImageCache = new DiskLruImageCache(context, cacheName, cacheSize, CompressFormat.PNG, 100);
        }

        @Override
        public Bitmap getBitmap(String pImageUrl) {
            try {
                return mDiskLruImageCache.getBitmap(createKey(pImageUrl));
            } catch (NullPointerException e) {
                throw new IllegalStateException("Disk Cache Not initialized");
            }
        }

        @Override
        public void putBitmap(String pImageUrl, Bitmap pBitmap) {
            try {
                mDiskLruImageCache.put(createKey(pImageUrl), pBitmap);
            } catch (NullPointerException e) {
                throw new IllegalStateException("Disk Cache Not initialized");
            }
        }

        private String createKey(String pImageUrl) {
            return String.valueOf(pImageUrl.hashCode());
        }
    }

}
