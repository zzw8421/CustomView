package com.yuxin.zcommoncomponents.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;

import com.yuxin.zcommoncomponents.R;


public class ZUpdatePromptUtils {
    private int layoutId = R.layout.dialog_update_view;
    private ZDialogUtils ZLoadUtils;
    private Context mContext;
    private boolean isFristDownloadProgress = true;

    public ZUpdatePromptUtils(Activity context) {
        mContext = context;
        ZLoadUtils = new ZDialogUtils(context, layoutId);
    }


    /**
     * 是否进行强制安装
     *
     * @param b true强制安装 false非强制安装
     * @return
     */
    public ZUpdatePromptUtils whetherToAllowCancel(boolean b) {
        b = !b;
        ZLoadUtils.setCancelable(b);
        ZLoadUtils.setCanceledOnTouchOutside(b);
        return this;
    }


    /**
     * 设置最新版本名称
     *
     * @param version
     * @return
     */
    public ZUpdatePromptUtils setVersionName(String version) {
        if (version == null || version.length() == 0) {
            version = "当前版本:V" + getVersionName(mContext);
        }
        if (!version.contains("V") || !version.contains("v")) {
            version = "V" + version;
        }
        ZLoadUtils.setText(R.id.dialog_update_tv_versionname, version);
        return this;
    }

    /***
     * 设置版本更新内容
     * @param content
     * @return
     */
    public ZUpdatePromptUtils setUpdateTip(String content) {
        if (content == null || content.length() == 0) {
            content = "此版本进行了许多更新";
        }
        ZLoadUtils.setText(R.id.dialog_update_tv_content, content);
        return this;
    }

    /**
     * 设置点击更新按钮事件
     *
     * @param listener
     * @return
     */
    public ZUpdatePromptUtils setOnClickUpdateListener(View.OnClickListener listener) {
        ZLoadUtils.setOnClickListener(R.id.dialog_update_btn_update, listener);
        return this;
    }

    /**
     * 设置下载监听事件
     *
     * @param progress
     * @param listener
     * @return
     */
    public ZUpdatePromptUtils setDownloadProgress(int progress, OnUpdateDownloadProgressListener listener) {
        if (isFristDownloadProgress) {
            listener.onStart();
            isFristDownloadProgress = false;
        }
        if (progress < 0) {
            listener.onFailure("进度必须为正数");
        } else if (progress <= 100) {
            listener.onProgress(progress);
            if (progress == 100) {
                listener.onFinish();
            }
        }
        return this;
    }

    /**
     * 展示下载进度
     *
     * @param progress
     * @return
     */
    public ZUpdatePromptUtils showProgress(int progress) {
        if (progress > 0) {
            if (ZLoadUtils.getViewIsVisible(R.id.dialog_update_btn_update)) {
                ZLoadUtils.setGone(R.id.dialog_update_btn_update);
            }
            if (!ZLoadUtils.getViewIsVisible(R.id.dialog_update_progress)) {
                ZLoadUtils.setVisible(R.id.dialog_update_progress);
            }
            ZLoadUtils.setProgress(R.id.dialog_update_progress, progress);
        }
        return this;
    }

    /**
     * 展示安装按钮
     *
     * @return
     */
    public ZUpdatePromptUtils showInstallApkButton() {
        if (ZLoadUtils.getViewIsVisible(R.id.dialog_update_progress)) {
            ZLoadUtils.setGone(R.id.dialog_update_progress);
        }
        if (ZLoadUtils.getViewIsVisible(R.id.dialog_update_btn_update)) {
            ZLoadUtils.setGone(R.id.dialog_update_btn_update);
        }
        if (!ZLoadUtils.getViewIsVisible(R.id.dialog_update_btn_install)) {
            ZLoadUtils.setVisible(R.id.dialog_update_btn_install);
        }

        return this;
    }

    /***
     * 设置点击安装按钮事件
     * @param listener
     * @return
     */
    public ZUpdatePromptUtils setOnInstallClickListener(View.OnClickListener listener) {
        ZLoadUtils.setOnClickListener(R.id.dialog_update_btn_install, listener);
        return this;
    }

    /**
     * 显示弹窗
     *
     * @return
     */
    public ZUpdatePromptUtils show() {
        ZLoadUtils.show();
        return this;
    }

    /***
     * 距离底部多少显示弹窗
     * @param bottom 距离底部距离
     * @return
     */
    public ZUpdatePromptUtils show(int bottom) {
        ZLoadUtils.setDistanceForBottom(bottom).show();
        return this;
    }

    /**
     * 关闭升级弹窗
     *
     * @return
     */
    public ZUpdatePromptUtils dismiss() {
        ZLoadUtils.dismiss();
        return this;
    }

    /***
     * 判断是否有新版本
     * @param code
     * @return
     */
    public boolean judgeIsNewVersion(int code) {
        int thisCode = getVersionCode(mContext);
        if (code > thisCode) {
            return true;
        } else {
            return false;
        }
    }

    private int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public interface OnUpdateDownloadProgressListener {
        void onProgress(int progress);

        void onFinish();

        void onFailure(String reason);

        void onStart();
    }
}
