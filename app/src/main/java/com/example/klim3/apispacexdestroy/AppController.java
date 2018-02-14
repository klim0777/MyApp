package com.example.klim3.apispacexdestroy;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    private final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance(){
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }
    public ImageLoader getImageLoader(){
        getRequestQueue();
        if(mImageLoader == null){
            mImageLoader = new ImageLoader(this.mRequestQueue,new BitmapCache());
        }
        return this.mImageLoader;
    }

    /***
     * @param request adds to requestQueue
     * @param <T> means we can add to request different types of request
     *            as JsonArrayRequest, ImageRequest, etc.
     */
    public <T> void addToRequesQueue(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

}
