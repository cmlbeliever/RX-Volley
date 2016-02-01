package com.cml.lib.rxvolley.rxvolley;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.RequestFuture;
import com.cml.lib.rxvolley.framework.RequestManager;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by cmlBeliever on 2016/2/1.
 * <p>
 * 将Volley请求封装成RxJava结构返回，注意：所有的请求都在IO线程中处理
 */
public class RxRequest {
    /**
     * 发送post请求
     *
     * @param url
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Observable<T> post(String url, Class<?> target) {
        return request(url, target, Request.Method.POST, new DefaultRetryPolicy());
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Observable<T> post(String url, Class<?> target, RetryPolicy retryPolicy) {
        return request(url, target, Request.Method.POST, retryPolicy);
    }

    /**
     * 发送Get请求
     *
     * @param url
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Observable<T> get(String url, Class<?> target) {
        return request(url, target, Request.Method.GET, new DefaultRetryPolicy());
    }

    /**
     * 发送Get请求
     *
     * @param url
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Observable<T> get(String url, Class<?> target, RetryPolicy retryPolicy) {
        return request(url, target, Request.Method.GET, retryPolicy);
    }

    public static <T> Observable<T> request(String url, Class<?> target, int method, RetryPolicy retryPolicy) {

        final RequestFuture<T> requestFuture = RequestFuture.newFuture();

        GsonRequest request = new GsonRequest(target, method, url, null, requestFuture, requestFuture);
        request.setRetryPolicy(retryPolicy);

        RequestManager.getRequestQueue().add(request);

        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(requestFuture.get());
                    }
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    subscriber.onError(e);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }
}
