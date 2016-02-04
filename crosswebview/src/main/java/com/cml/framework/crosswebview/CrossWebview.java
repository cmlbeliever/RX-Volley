package com.cml.framework.crosswebview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cmlBeliever on 2016/2/3.
 */
public class CrossWebview extends WebView {

    private static final String TAG = CrossWebview.class.getSimpleName();

    public static final String NATIVE_ID = "native_id";
    private Map<String, Extension> extensions = new HashMap<String, Extension>();

    public CrossWebview(Context context) {
        super(context);
    }

    public CrossWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CrossWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CrossWebview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CrossWebview(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }


    @Override
    public void addJavascriptInterface(Object object, String name) {
        throw new IllegalStateException("addJavascriptInterface not support! see addExtension !!");
    }

    /**
     * 添加拓展功能，多个相同的extension只会保存第一个
     *
     * @param extension
     */
    public void addExtension(String alias, Extension extension) {

        if (TextUtils.isEmpty(alias)) {
            throw new IllegalArgumentException("alias can not be null!!");
        }

        if (extensions.containsKey(alias)) {
            return;
        }
        super.addJavascriptInterface(new ProxyExtension(extension), alias);
        this.extensions.put(alias, extension);

        String baseJs = new JsBuilder(getContext(), alias).build();

        loadUrl(baseJs);
    }

    /**
     * 调用js的方法
     *
     * @param alias
     * @param object
     */
    public void callJs(final String alias, final JSONObject object) {

        if (object.optInt(NATIVE_ID, -1) == -1) {
            if (Log.isLoggable(TAG, Log.WARN)) {
                Log.w(TAG, "callJs() : native_id is required!!");
            }
            return;
        }

        post(new Runnable() {
            @Override
            public void run() {
                String jsFormat = "javascript:window.%sExtension.callbackJs(%s)";
                loadUrl(String.format(jsFormat, alias, object.toString()));
            }
        });
    }

    public static class ProxyExtension {

        private Handler asyncHandler;
        private Extension extension;

        public ProxyExtension(Extension extension) {
            this.extension = extension;
            HandlerThread thread = new HandlerThread("DefaultExtension");
            thread.start();
            asyncHandler = new Handler(thread.getLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    postMessage(msg.arg1, (String) msg.obj, false);
                }
            };
        }

        @JavascriptInterface
        public void postMessage(int instanceId, String message, boolean isSync) {
            this.extension.postMessage(instanceId, message, isSync);
        }

        /**
         * 异步处理消息
         *
         * @param instanceId
         * @param message
         */
        @JavascriptInterface
        public void postAsyncMessage(final int instanceId, final String message) {
            Message msg = asyncHandler.obtainMessage();
            msg.arg1 = instanceId;
            msg.obj = message;
            asyncHandler.sendMessage(msg);
        }
    }

    static class JsBuilder {
        private String baseJs;
        private String alias;

        public JsBuilder(Context context, String alias) {
            baseJs = context.getString(R.string.js_format);
            this.alias = alias;
        }

        public String build() {
            baseJs = baseJs.replace("#{alias}", alias);
            return "javascript:" + baseJs;
        }
    }


}
