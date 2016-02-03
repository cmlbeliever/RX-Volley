package com.cml.framework.crosswebview;

import android.webkit.JavascriptInterface;

/**
 * Created by cmlBeliever on 2016/2/3.
 * CorssWebview插件拓展类
 */
public abstract class Extension {
    /**
     * 处理js调用消息，注意子类需要加上注解： @JavascriptInterface
     *
     * @param instanceId
     * @param message
     * @param isSync     true 同步处理，false 异步处理消息
     */
    @JavascriptInterface
    public abstract void postMessage(int instanceId, String message, boolean isSync);

    /**
     * 异步处理消息
     *
     * @param instanceId
     * @param message
     */
    @JavascriptInterface
    public void postAsyncMessage(final int instanceId, final String message) {
        new Thread() {
            @Override
            public void run() {
                postMessage(instanceId, message, false);
            }
        }.start();
    }
}
