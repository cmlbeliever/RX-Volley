package com.cml.frame.rx_volley.myscroller;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by cmlBeliever on 2016/2/19.
 */
public class MyScroller extends NestedScrollView {

    public static interface ScrollListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    private ScrollListener listener;

    public MyScroller(Context context) {
        super(context);
    }

    public MyScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(null!=listener){
            listener.onScrollChanged(l,t,oldl,oldt);
        }
    }


    public void setListener(ScrollListener listener) {
        this.listener = listener;
    }
}
