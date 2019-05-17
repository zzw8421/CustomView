package com.yuxin.zcommoncomponents.util;

import android.app.Activity;
import android.content.Context;

import com.yuxin.zcommoncomponents.R;

/*****
 *@author zzw
 *@date 2019/1/23 17:37
 *@role 显示等待弹窗帮助类
 *****/
public class ZLoadUtils {
    private static ZLoadUtils instance;
    private ZDialogUtils zDialogUtils;


    private ZLoadUtils(Activity activity) {
        zDialogUtils = new ZDialogUtils(activity, R.layout.dialog_loading);
    }

    public static ZLoadUtils getInstance(Activity activity) {
        if (instance == null) {
            synchronized (ZLoadUtils.class) {
                if (instance == null) {
                    instance = new ZLoadUtils(activity);
                }
            }
        }
        return instance;
    }

    /***
     * 显示弹窗
     */
    public void showLoadDialog() {
        zDialogUtils.setCancelable(true);
        zDialogUtils.setCanceledOnTouchOutside(true);
        zDialogUtils.show();
    }

    /***
     * 显示弹窗（默认强制不可关闭）
     */
    public void showConstraintLoadDialog() {
        zDialogUtils.setCancelable(false);
        zDialogUtils.setCanceledOnTouchOutside(false);
        zDialogUtils.show();
    }

    /**
     * 关闭弹窗
     */
    public void dimissDialog() {
        if (zDialogUtils.isShowing()){
            zDialogUtils.dismiss();
        }
    }
}
