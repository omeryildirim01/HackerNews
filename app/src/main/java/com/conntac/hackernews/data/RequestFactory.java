package com.conntac.hackernews.data;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Omer YILDIRIM on 7/17/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class RequestFactory {
    private static RequestFactory instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private RequestFactory(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestFactory getInstance(Context context) {
        if (instance == null) {
            instance = new RequestFactory(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
