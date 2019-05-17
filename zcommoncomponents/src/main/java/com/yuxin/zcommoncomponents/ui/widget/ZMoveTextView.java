package com.yuxin.zcommoncomponents.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;


/*****
 *@author zzw
 *@date 2018/12/13 9:54
 *@role 代码有缺陷   未修复之前禁止使用
 *****/
public class ZMoveTextView extends android.support.v7.widget.AppCompatTextView {
    private int x, y;
    private float firstX, lastX;
    private int r, l, t, b;
    private OnClickListener listener;
    private boolean isClick = false;
    private final String TAG = getClass().getSimpleName();

    public ZMoveTextView(Context context) {
        super(context);
        init();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        listener = l;
    }

    public ZMoveTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZMoveTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX();
        y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstX = lastX = event.getX();// 记录下当前的最开始在屏幕的值
                break;
            case MotionEvent.ACTION_MOVE:
                l = (int) (x + getTranslationX() - getWidth() / 2 + getLeft());
                t = (int) (y + getTranslationY() - getHeight() / 2 + getTop());
                r = l + getWidth();
                b = t + getHeight();
                layout(l, t, r, b);
                break;
            case MotionEvent.ACTION_UP:
                lastX = event.getX();
                if (lastX - firstX <= 0) {
                    // 这是点击事件
                    if (listener != null) {
                        listener.onClick(this);
                    }
                    Log.i(TAG, "onTouchEvent: 点击");
                } else {
                    //发生了拖拽
                    int fl = (int) (x + getTranslationX() - getWidth() / 2 + getLeft());
                    int ft = (int) (y + getTranslationY() - getHeight() / 2 + getTop());
                    Log.i(TAG, "onTouchEvent: 拖拽" + fl + "------" + ft);
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
                    layoutParams.setMargins(fl, ft, 0, 0);
                }
                break;
        }
        return true;
    }


}
