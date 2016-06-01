package pcedev.bencomu.raw.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by perecullera on 2/12/15.
 */
public class Singleton extends Application {

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Singleton mInstance;

    public static final String TAG = Singleton.class.getName();

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static synchronized Singleton getInstance() {
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }

    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancel() {
        mRequestQueue.cancelAll(TAG);
    }

}
