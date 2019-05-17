package com.yuxin.zcommoncomponents.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yuxin.zcommoncomponents.R;
import com.yuxin.zcommoncomponents.constant.ZFilePickerConstant;
import com.yuxin.zcommoncomponents.model.BaseFile;
import com.yuxin.zcommoncomponents.model.ZFileType;
import com.yuxin.zcommoncomponents.ui.activity.ZFilePickerActivity;
import com.yuxin.zcommoncomponents.util.ZFileUtils;

import java.util.List;

/*****
 *@author zzw
 *@date 2019/1/12 16:00
 *@role
 *****/
public class FilePickAdapter extends BaseAdapter<BaseFile, FilePickAdapter.FilePickViewHolder> {
    private final String TAG = getClass().getSimpleName();


    public FilePickAdapter(Context ctx, List<BaseFile> list) {
        super(ctx, list);
    }

    @NonNull
    @Override
    public FilePickViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_file_picker, viewGroup, false);
        return new FilePickAdapter.FilePickViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FilePickViewHolder holder, int i) {
        final BaseFile base = mList.get(i);
        holder.mTvTitle.setText(ZFileUtils.getFileNameAbbreviationsStr(base.getName()));
        holder.mTvSize.setText(ZFileUtils.getSizeStr(base.getSize()));
        holder.mTvDate.setText(ZFileUtils.getFormatFileDate(base.getDate()));
        if (base.isSelected()) {
            holder.mImgChoose.setSelected(true);
        } else {
            holder.mImgChoose.setSelected(false);
        }
        if (ZFileType.XLS.contains(base.getType())) {
            holder.mImgIcon.setImageResource(R.drawable.icon_xlsx);
        } else if (ZFileType.DOC.contains(base.getType())) {
            holder.mImgIcon.setImageResource(R.drawable.icon_docx);
        } else if (ZFileType.PPT.contains(base.getType())) {
            holder.mImgIcon.setImageResource(R.drawable.icon_ppt);
        } else if (ZFileType.PDF.contains(base.getType())) {
            holder.mImgIcon.setImageResource(R.drawable.icon_pdf);
        } else if (ZFileType.TXT.contains(base.getType())) {
            holder.mImgIcon.setImageResource(R.drawable.icon_txt);
        } else if (ZFileType.ZIP.contains(base.getType())) {
            holder.mImgIcon.setImageResource(R.drawable.icon_zip);
        } else if (ZFileType.MP3.contains(base.getType())) {
            holder.mImgIcon.setImageResource(R.drawable.icon_mp3);
        } else if (ZFileType.IMAGE.contains(base.getType())) {
            Glide.with(mContext).load(base.getPath()).into(holder.mImgIcon);
        } else {
            holder.mImgIcon.setImageResource(R.drawable.icon_unknown_type);
        }
        holder.linRoot.setTag(base);
        holder.linRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZFilePickerConstant.chooseType == ZFilePickerConstant.Z_FILE_CHOOSE_TYPE_MULTIPLE) {
                    if (!base.isSelected() && ZFilePickerActivity.getSelectCount() >= ZFilePickerActivity.getMaxConut()) {
                        Toast.makeText(mContext, R.string.beyond_max_count_msg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                base.setSelected(!base.isSelected());
                if (mListener != null) {
                    mListener.OnSelectStateChanged(base.isSelected(), mList.get(holder.getAdapterPosition()));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class FilePickViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgIcon;
        private TextView mTvTitle;
        private TextView mTvSize;
        private TextView mTvDate;
        private ImageView mImgChoose;
        private LinearLayout linRoot;

        public FilePickViewHolder(View itemView) {
            super(itemView);
            mImgIcon = itemView.findViewById(R.id.item_file_picker_img_type);
            mTvTitle = itemView.findViewById(R.id.item_file_picker_tv_title);
            mTvSize = itemView.findViewById(R.id.item_file_picker_tv_size);
            mTvDate = itemView.findViewById(R.id.item_file_picker_tv_date);
            mImgChoose = itemView.findViewById(R.id.item_file_picker_img_choose);
            linRoot = itemView.findViewById(R.id.item_file_picker_lin_root);
        }
    }

}
