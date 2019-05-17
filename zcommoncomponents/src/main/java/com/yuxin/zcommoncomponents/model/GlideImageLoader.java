package com.yuxin.zcommoncomponents.model;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/*****
 *@author zzw
 *@date 2019/1/8 14:16
 *@role
 *****/
public class GlideImageLoader extends ZImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
