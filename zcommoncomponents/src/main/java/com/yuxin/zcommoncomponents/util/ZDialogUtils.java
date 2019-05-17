package com.yuxin.zcommoncomponents.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yuxin.zcommoncomponents.R;


public class ZDialogUtils {
    private Dialog dialog;
    private View myView;
    private Window dialogWin;
    private WindowManager.LayoutParams dialogParams;
    private final String TAG = getClass().getSimpleName();

    enum LocationType {
        CENTER, BOTTOM, TOP
    }

    public ZDialogUtils(Activity context, View view, int style) {
        init(context, view, style);
    }

    public ZDialogUtils(Activity context, View view) {
        myView = view;
        init(context, view, R.style.dialogAnim);
    }

    public ZDialogUtils(Activity context, int layout) {
        myView = LayoutInflater.from(context).inflate(layout, null, false);
        init(context, myView, R.style.dialogAnim);
    }


    private void init(Activity context, final View view, int style) {
        dialog = new Dialog(context, style);
        dialogWin = dialog.getWindow();
        dialogParams = dialogWin.getAttributes();
        WindowManager.LayoutParams layoutParams = context.getWindow().getAttributes();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        context.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        dialog.setContentView(view, layoutParams);
        setLayoutPrams(LocationType.CENTER);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }


    /***
     *设置该dialog在屏幕中的显示位置
     * @param type
     * @return
     */
    public ZDialogUtils setLayoutPrams(LocationType type) {
        dialogParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        if (type == LocationType.BOTTOM) {
            dialogParams.gravity = Gravity.BOTTOM;
        } else if (type == LocationType.CENTER) {
            dialogParams.gravity = Gravity.CENTER;
        } else if (type == LocationType.TOP) {
            dialogParams.gravity = Gravity.TOP;
        } else {
            dialogParams.gravity = Gravity.CENTER;
        }
        dialogWin.setAttributes(dialogParams);
        return this;
    }

    /***
     * 设置该dialog距离底部的尺寸
     *数值距离为:1-100
     * @param distance
     */
    public ZDialogUtils setDistanceForBottom(int distance) {
        dialogParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogParams.gravity = Gravity.BOTTOM;
        Log.i(TAG, "setDistanceForBottom: " + ((float) distance / 200.0f));
        dialogParams.verticalMargin = (float) distance / 200.0f;
        dialogWin.setAttributes(dialogParams);
        return this;
    }

    /***
     * 设置该dialog距离顶部的尺寸
     *数值距离为:1-100
     * @param distance
     */
    public ZDialogUtils setDistanceForTop(float distance) {
        dialogParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogParams.gravity = Gravity.TOP;
        dialogParams.verticalMargin = -distance / 200.0f;
        dialogWin.setAttributes(dialogParams);
        return this;
    }

    /***
     * 触摸Dialog不消失
     * @param b true 消失   false 不消失
     */
    public ZDialogUtils setCanceledOnTouchOutside(boolean b) {
        dialog.setCanceledOnTouchOutside(b);
        return this;
    }

    /***
     * 返回键Dialog不消失
     * @param b  true 消失   false 不消失
     */
    public ZDialogUtils setCancelable(boolean b) {
        dialog.setCancelable(b);
        return this;
    }


    public View getView() {
        return myView;
    }

    /**
     * 显示dialog
     *
     * @return
     */

    public ZDialogUtils show() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
        return this;
    }

    /**
     * 标识dialog是否在显示
     *
     * @return
     */

    public boolean isShowing() {
        return dialog.isShowing();
    }

    /**
     * 隐藏dialog
     *
     * @return
     */

    public ZDialogUtils dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        return this;
    }


    public ZDialogUtils setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);

        return this;
    }

    /**
     * 设置dialog内TextView的文本值
     *
     * @param viewId
     * @param value
     * @return
     */

    public ZDialogUtils setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    /**
     * 设置dialog内TextView的文本值
     * @param viewId
     * @param strId
     * @return
     */
    public ZDialogUtils setText(@IdRes int viewId, @StringRes int strId) {
        TextView view = getView(viewId);
        view.setText(strId);
        return this;
    }

    public ZDialogUtils setImageResource(@IdRes int viewId, @DrawableRes int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public ZDialogUtils setBackgroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ZDialogUtils setBackgroundRes(@IdRes int viewId, @DrawableRes int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ZDialogUtils setAlpha(@IdRes int viewId, float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(viewId).setAlpha(value);
        } else {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ZDialogUtils setVisible(@IdRes int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        return this;
    }

    public ZDialogUtils setGone(@IdRes int viewId) {
        View view = getView(viewId);
        view.setVisibility(View.GONE);
        return this;
    }

    public ZDialogUtils setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public boolean getViewIsVisible(@IdRes int viewId) {
        View view = getView(viewId);
        if (view.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据资源ID获取资源控件实例
     *
     * @param id
     * @return
     */
    public <T extends View> T getView(int id) {
        try {
            return (T) myView.findViewById(id);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }


}
