package com.cml.lib.rxvolley.rxvolley;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by cmlBeliever on 2016/2/1.
 */
public class GsonRequest<T> extends JsonRequest<T> {

    private static final String TAG = GsonRequest.class.getSimpleName();

    private Gson gson = new Gson();
    private Class<?> target;

    public GsonRequest(Class<?> target, int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        this.target = target;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "response str : " + parsed);
        }
        return (Response<T>) Response.success(gson.fromJson(parsed, target), HttpHeaderParser.parseCacheHeaders(response));
    }
}
