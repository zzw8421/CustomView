package com.yuxin.zcommoncomponents.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/*****
 *@author zzw
 *@date 2019/1/12 11:25
 *@role 文件操作帮助类
 *****/
public class ZFileUtils {
    private static final String TAG = "ZFileUtils";

    /**
     * 判断该路径文件是否存在
     *
     * @param path 文件路径
     * @return true 存在  false  不存在
     */
    public static boolean isExists(String path) {
        try {
            return new File(path).exists();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将文件大小从数值转换为文本
     *
     * @param size 数值大小(B)
     * @return 文件大小文本
     */

    public static String getSizeStr(long size) {


        final float CON = 1024.0f;
        float b = (float) size / CON;
        if (b > CON) {
            float kb = b / CON;
            if (kb > CON) {
                float mb = kb / CON;
                return getPreciseDecimal(mb, 2) + "G";
            } else {
                return getPreciseDecimal(kb, 2) + "MB";
            }
        } else {
            return getPreciseDecimal(b, 2) + "KB";
        }
    }

    public static float getPreciseDecimal(float decimal, int size) {
        BigDecimal bd = new BigDecimal(decimal);
        bd = bd.setScale(size, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /***
     *转换文件时间
     * @param s 文件时间
     * @return 转换后的文件时间(yyyy - MM - dd)
     */
    public static String getFormatFileDate(Long s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s * 1000);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 获取文件名称省略名称
     *
     * @param str 文件名称
     * @return 文件缩略名称
     */
    public static String getFileNameAbbreviationsStr(String str) {

        try {
            if (str.length() > 16) {
                String[] items = str.split("\\.");
                String path = items[0];
                String type = "." + items[1];
                int len = path.length() - 16 + type.length();
                String tempStr = "";
                tempStr += path.substring(0, 8);
                tempStr += "..." + path.substring(len + 8, path.length());
                return tempStr + type;
            } else {
                return str;
            }
        } catch (Exception e) {
            return str;
        }
    }

}
