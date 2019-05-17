package com.yuxin.zcommoncomponents.ui.activity;

import android.content.Context;

import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.IncapableCause;
import com.zhihu.matisse.internal.entity.Item;

import java.util.Set;

/*****
 *@author zzw
 *@date 2019/1/8 15:48
 *@role
 *****/
class GifSizeFilter extends Filter {
    public GifSizeFilter(int i, int i1, int i2) {
    }

    @Override
    protected Set<MimeType> constraintTypes() {
        return null;
    }

    @Override
    public IncapableCause filter(Context context, Item item) {
        return null;
    }
}
