package com.yuxin.zcommoncomponents.ui.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuxin.zcommoncomponents.R;
import com.yuxin.zcommoncomponents.listener.OnZChooseListener;
import com.yuxin.zcommoncomponents.model.BaseFile;
import com.yuxin.zcommoncomponents.model.ZFileType;
import com.yuxin.zcommoncomponents.util.ZFileUtils;

import java.util.ArrayList;
import java.util.List;

/*****
 *@author zzw
 *@date 2019/1/15 10:19
 *@role 选择文件view
 *****/
public class ZChooseFileView extends LinearLayout {
    private List<BaseFile> mDatas;
    private String mTitle;
    private boolean isRequired;
    private LinearLayout linItemRoot;
    private ImageView imgAdd;
    private List<View> mViews;
    private Context mContext;
    private OnZChooseListener mListener;

    public ZChooseFileView(Context context) {
        super(context);
        init(context, null);
    }

    public ZChooseFileView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZChooseFileView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ZChooseFileView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mDatas = new ArrayList<>();
        mViews = new ArrayList<>();

        LayoutInflater.from(context).inflate(R.layout.lin_item_style_choose_file, this);
        linItemRoot = findViewById(R.id.lin_item_style_choose_file_lin_item_root);
        imgAdd = findViewById(R.id.lin_item_style_choose_file_img_add);
        imgAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onAddClickListener();
                }
            }
        });

    }

    /**
     * 设置选择的文件数据
     *
     * @param mList 选中文件数据
     */
    public void setData(List<BaseFile> mList) {
        mDatas.addAll(mList);
        refresh();
    }

    /***
     * 刷新选中的文件数据
     */
    public void refresh() {
        removeAllItem();
        for (BaseFile file : mDatas) {
            addItem(file);
        }
    }

    /***
     * 刷新选中的文件数据
     * @param mLists
     */
    public void refreshData(List<BaseFile> mLists) {
        mDatas.clear();
        setData(mLists);
    }

    /***
     * 设置选择文件回调
     * @param listener
     */
    public void setOnChooseListener(OnZChooseListener listener) {
        mListener = listener;
    }

    private void addItem(final BaseFile baseFile) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_choose_file, null);
        TextView tvSize = itemView.findViewById(R.id.item_choose_file_tv_size);
        TextView tvTitle = itemView.findViewById(R.id.item_choose_file_tv_title);
        ImageView imgIcon = itemView.findViewById(R.id.item_choose_file_img_icon);
        ImageView imgDel = itemView.findViewById(R.id.item_choose_file_img_del);
        tvTitle.setText(ZFileUtils.getFileNameAbbreviationsStr(baseFile.getName()));
        tvSize.setText(ZFileUtils.getSizeStr(baseFile.getSize()));

        if (ZFileType.XLS.contains(baseFile.getType())) {
            imgIcon.setImageResource(R.drawable.icon_xlsx);
        } else if (ZFileType.DOC.contains(baseFile.getType())) {
            imgIcon.setImageResource(R.drawable.icon_docx);
        } else if (ZFileType.PPT.contains(baseFile.getType())) {
            imgIcon.setImageResource(R.drawable.icon_ppt);
        } else if (ZFileType.PDF.contains(baseFile.getType())) {
            imgIcon.setImageResource(R.drawable.icon_pdf);
        } else if (ZFileType.TXT.contains(baseFile.getType())) {
            imgIcon.setImageResource(R.drawable.icon_txt);
        } else if (ZFileType.ZIP.contains(baseFile.getType())) {
            imgIcon.setImageResource(R.drawable.icon_zip);
        } else if (ZFileType.MP3.contains(baseFile.getType())) {
            imgIcon.setImageResource(R.drawable.icon_mp3);
        } else if (ZFileType.IMAGE.contains(baseFile.getType())) {
            Glide.with(mContext).load(baseFile.getPath()).into(imgIcon);
        } else {
            imgIcon.setImageResource(R.drawable.icon_unknown_type);
        }

        imgDel.setTag(baseFile);
        imgDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseFile b = (BaseFile) v.getTag();
                removeItem(b);
            }
        });

        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onSelectItemListener(baseFile);
                }
            }
        });
        mViews.add(itemView);
        linItemRoot.addView(itemView);
    }


    private void removeItem(BaseFile baseFile) {
        int index = mDatas.indexOf(baseFile);
        if (index >= 0 && index <= mDatas.size()) {
            View delView = mViews.remove(index);
            linItemRoot.removeView(delView);
            mDatas.remove(baseFile);
            if (mListener != null) {
                mListener.onDelItemListener(baseFile, index);
            }
        }
    }


    private void removeAllItem() {
        linItemRoot.removeAllViews();
    }


    /**
     * 获取某个item数据
    * @param postion item下标
     * @return
     */

    public BaseFile getItemData(int postion) {
        return mDatas.get(postion);
    }

    /***
     * 获取所有选中数据的数量
     * @return
     */
    public int getDataSize() {
        return mDatas.size();
    }

    /***
     * 获取到所有选中的数据
     * @return
     */
    public List<BaseFile> getDatas() {
        return mDatas;
    }
}
