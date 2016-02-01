package com.cml.lib.rxvolley.rxvolley;

import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.cml.lib.rxvolley.framework.RequestManager;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by cmlBeliever on 2016/2/1.
 * <p/>
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
        return request(url, target, Request.Method.POST);
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
        return request(url, target, Request.Method.GET);
    }

    private static <T> Observable<T> request(String url, Class<?> target, int method) {

        final RequestFuture<T> requestFuture = RequestFuture.newFuture();

        GsonRequest request = new GsonRequest(target, method, url, null, requestFuture, requestFuture);

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
