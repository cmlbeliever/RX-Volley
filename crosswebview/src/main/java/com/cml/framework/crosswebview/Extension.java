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
     */
    @JavascriptInterface
    public abstract void postMessage(int instanceId, String message);
}
