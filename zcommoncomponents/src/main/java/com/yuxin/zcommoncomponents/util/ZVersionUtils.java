package com.yuxin.zcommoncomponents.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


/*****
 *@author zzw
 *@date 2019/1/2 10:07
 *@role APP版本帮助类
 *****/
public class ZVersionUtils {

    /***
     * 获取版本名称
     * @param context 上下文
     * @return 版本名称
     */
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

    /***
     * 获取版本号
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 判断是否有新版本
     *
     * @param context 上下文
     * @param code    需要判断的版本号
     * @return true 为新版本  false 不是新版本
     */
    public static boolean judgeIsNewVersion(Context context, int code) {
        int thisCode = getVersionCode(context);
        if (code > thisCode) {
            return true;
        } else {
            return false;
        }
    }

}
