package com.yuxin.zcommoncomponents.util;

/*****
 *@author zzw
 *@date 2019/1/2 10:07
 *@role 文本帮助类
 *****/
public class ZTextUtil {

    /**
     * 判断String是否为空
     *
     * @param str
     * @return true 是  false  不是
     */
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
