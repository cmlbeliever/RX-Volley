package com.cml.frame.rx_volley;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.cml.framework.crosswebview.CrossWebview;
import com.cml.framework.crosswebview.Extension;

import org.json.JSONObject;

public class MyCrosswalkLibActivity extends AppCompatActivity {

    private CrossWebview webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_crosswalk2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        webView = (CrossWebview) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.setWebViewClient(new WebViewClient());

        webView.addExtension("native", new MyExtension());

        webView.loadUrl("file:///android_asset/test3.html");

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
        webView.callJs("native", object);
    }


    public void startNewActivity(View v) {
        startActivity(new Intent(this, MyCrosswalkLibActivity.class));
    }

    class MyExtension extends Extension {

        public void postMessage(int instanceId, final String message, boolean isSync) {
            try {
                //模拟很长业务处理
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                JSONObject paramObject = new JSONObject(message);
                JSONObject object = new JSONObject();
                object.put("native_id", paramObject.getInt("native_id"));
                object.put("result", "我是系统返回："+isSync + System.currentTimeMillis());
                webView.callJs("native", object);
            } catch (Exception e) {
            }
        }
    }
}
