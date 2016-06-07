package pcedev.bencomu.VolleyUtils;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by perecullera on 2/12/15.
 */
public class Singleton extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "hBykRv3sAk66EGXPIxHEKlOV7";
    private static final String TWITTER_SECRET = "LzX0zSo1SjLwf7771BZ2c7IeiwklC3LUTDHmx0FQTnHnZvWQCG";





    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Singleton mInstance;

    public static final String TAG = Singleton.class.getName();

    @Override
    public void onCreate(){
        super.onCreate();


        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
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
