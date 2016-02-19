package com.cml.frame.rx_volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.cml.frame.rx_volley.myscroller.MyScroller;

/**
 * Created by cmlBeliever on 2016/2/19.
 */
public class CanonTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.YELLOW);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
//        Window window = getWindow();
//        window.requestFeature(Window.FEATURE_NO_TITLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);


        //开启沉浸式菜单
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }


        setContentView(R.layout.activity_canon);

//        final SystemBarTintManager tr = new SystemBarTintManager(this);
//        tr.setStatusBarTintEnabled(true);
//        tr.setNavigationBarTintEnabled(true);
//        tr.setTintAlpha(0.5f);
//        tr.setTintColor(Color.RED);
//        tr.setTintAlpha(0.5f);

//
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setMax(255);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                toolbar.setAlpha(progress/255f);
//                tr.setTintAlpha(progress / 255f);
//                tr.setTintColor(Color.BLUE);
//                tr.setNavigationBarAlpha(progress / 255f);
//                tr.setStatusBarTintColor(Color.BLUE);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


//        final int height = toolbar.getMeasuredHeight();

        MyScroller scrollView = (MyScroller) findViewById(R.id.scroller);
        scrollView.setListener(new MyScroller.ScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                Log.e("ee", "onScrollChanged:" + l + "," + t + ",oldl:" + oldl + "," + oldt + ",,");
            }
        });

//        CollapsingToolbarLayout layout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        layout.setTitle("我是title");
    }


    private void hideStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }

    private void showStatusBar() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
    }
}
