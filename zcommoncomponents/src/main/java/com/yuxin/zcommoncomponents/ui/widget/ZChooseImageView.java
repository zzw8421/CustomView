package com.yuxin.zcommoncomponents.ui.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuxin.zcommoncomponents.R;
import com.yuxin.zcommoncomponents.listener.OnChooseImageListener;
import com.yuxin.zcommoncomponents.model.ZImageLoader;
import com.yuxin.zcommoncomponents.model.ZImageSectionEntity;

import java.util.ArrayList;
import java.util.List;

/*****
 *@author zzw
 *@date 2019/1/8 9:56
 *@role 选择图片
 *****/
public class ZChooseImageView extends LinearLayout {
    private final String TAG = getClass().getSimpleName();
    private Context mContext;
    private int viewWidth, viewHeight;
    private LinearLayout linImageRoot;
    private TextView tvTitle;
    private List<View> imgViews;
    private ZImageLoader loader;
    private List<ZImageSectionEntity> mDatas;
    private boolean isCanAdd = true;
    private OnChooseImageListener listener;

    public ZChooseImageView(Context context) {
        super(context);
        init(context, null);
    }

    public ZChooseImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public ZChooseImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ZChooseImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.lin_item_style_choose_image, this);
        linImageRoot = findViewById(R.id.lin_item_style_choose_imgage_lin_imgs);
        imgViews = new ArrayList<>();
        mDatas = new ArrayList<>();
        if (attrs != null) {

        }
    }


    public void setListener(OnChooseImageListener listener) {
        this.listener = listener;
    }

    private void initAddView() {
        ZImageSectionEntity imgaddItem = new ZImageSectionEntity();
        imgaddItem.setAdd(true);
        if (isCanAdd) {
            mDatas.add(imgaddItem);
        }
    }

    private void delAddData() {
        for (ZImageSectionEntity sectionEntity : mDatas) {
            if (sectionEntity.getIsAdd()) {
                mDatas.remove(sectionEntity);
            }
        }
    }


    /***
     * 设置显示图片的loader  此举是为了统一显示图片的框架或者进行某些处理
     * @param loader
     */
    public void setImageLoader(ZImageLoader loader) {
        this.loader = loader;
        notifyChanged();
    }


    public void addView(final ZImageSectionEntity entity, final int postion) {
        View imgView = LayoutInflater.from(mContext).inflate(R.layout.item_choose_image, null);
        imgView.setTag(postion);
        ImageView imgDel = imgView.findViewById(R.id.item_choose_image_img_del);
        ImageView imgRoot = imgView.findViewById(R.id.item_choose_imgae_img);
        if (!entity.isDelete) {
            imgDel.setVisibility(INVISIBLE);
        } else {
            imgDel.setVisibility(VISIBLE);
            imgDel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDelItemListener(postion);
                }
            });
        }
        imgRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.getIsAdd()) {
                    listener.onAddClickListener();
                } else {
                    listener.onSelectItemListener(postion);
                }
            }
        });
        if (entity.getIsAdd()) {
            loader.displayImage(mContext, R.drawable.add_photo, imgRoot);
        } else {
            loader.displayImage(mContext, entity.url, imgRoot);
        }
        imgViews.add(imgView);
        linImageRoot.addView(imgView);
    }


    public void setData(List<ZImageSectionEntity> data) {
        mDatas.clear();
        linImageRoot.removeAllViews();
        mDatas.addAll(data);
        notifyChanged();
    }

    public void addData(List<ZImageSectionEntity> data) {
        linImageRoot.removeAllViews();
        delAddData();
        mDatas.addAll(data);
        notifyChanged();
    }

    public void addData(ZImageSectionEntity data) {
        delAddData();
        mDatas.add(data);
        notifyChanged();
    }

    public List<ZImageSectionEntity> getDatas() {
        return mDatas;
    }

    public void removeData(int postion) {
        mDatas.remove(postion);
        delAddData();
        notifyChanged();
    }

    private void notifyChanged() {
        imgViews.clear();
        linImageRoot.removeAllViews();
        initAddView();
        for (int i = 0; i < mDatas.size(); i++) {
            addView(mDatas.get(i), i);
            Log.i(TAG, "notifyChanged: " + i);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
    }
}
