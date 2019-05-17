package com.yuxin.zcommoncomponents.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/*****
 *@author zzw
 *@date 2019/1/8 9:49
 *@role bitmap帮助类
 *****/
public class ZBitmapUtil {

    /**
     * 将bitmap压缩到指定宽高
     *
     * @param bitmap    需要压缩的bitmap
     * @param newWidth  新宽度
     * @param newHeight 新高度
     * @return
     */
    public static Bitmap changeBitmapSize(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //计算压缩的比率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        //获取新的bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.getWidth();
        bitmap.getHeight();
        return bitmap;
    }

    /***
     * 将bitmap压缩到指定宽高
     * @param bitmap  需要压缩的bitmap
     * @param newWidth 新宽度 (高度等同)
     * @return
     */
    public static Bitmap changeBitmapSize(Bitmap bitmap, int newWidth) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //计算压缩的比率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newWidth) / height;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        //获取新的bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.getWidth();
        bitmap.getHeight();
        return bitmap;
    }

}
