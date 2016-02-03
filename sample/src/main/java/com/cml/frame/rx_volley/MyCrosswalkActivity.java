package com.cml.frame.rx_volley;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.json.JSONObject;

public class MyCrosswalkActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_crosswalk);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        webView = (WebView) findViewById(R.id.webview);

        webView.addJavascriptInterface(new JsCall(), "nativeApp");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.loadUrl("javascript:" + getString(R.string.js2));

        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
//        webView.loadUrl("http://www.baidu.com");
        webView.loadUrl("file:///android_asset/test.html");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:testCall();");
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void callJs(View v) throws Exception {
        JSONObject object = new JSONObject();
        object.put("native_id", 1);
        object.put("result", "我是系统主动调用：" + System.currentTimeMillis());
        webView.loadUrl("javascript:window.extension.callbackJs(" + object.toString() + ")");
    }

    class JsCall {
        @JavascriptInterface
        public void send(Object obj) {
            Toast.makeText(getApplicationContext(), "send call" + Thread.currentThread().getId(), Toast.LENGTH_LONG).show();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:alert('ll')");
                }
            });
        }

        @JavascriptInterface
        public void postMsg(final int id, final String params) {
//            Toast.makeText(getApplicationContext(), "postMsg call id:" + id + ",params:" + params + Thread.currentThread().getId(), Toast.LENGTH_LONG).show();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject paramObject = new JSONObject(params);
                        JSONObject object = new JSONObject();
                        object.put("native_id", paramObject.getInt("native_id"));
                        object.put("result", "我是系统返回：" + System.currentTimeMillis());
                        webView.loadUrl("javascript:window.extension.callbackJs(" + object.toString() + ")");
                    } catch (Exception e) {
                    }
                }
            });
        }
    }

}
