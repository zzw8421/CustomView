package com.yuxin.customview.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.yuxin.customview.R;
import com.yuxin.zcommoncomponents.ZFilePickerBuilder;
import com.yuxin.zcommoncomponents.constant.ZFilePickerConstant;
import com.yuxin.zcommoncomponents.listener.OnChooseImageListener;
import com.yuxin.zcommoncomponents.listener.OnZChooseListener;
import com.yuxin.zcommoncomponents.model.BaseFile;
import com.yuxin.zcommoncomponents.model.GlideEngine;
import com.yuxin.zcommoncomponents.model.GlideImageLoader;
import com.yuxin.zcommoncomponents.model.ZChooseSectionEntity;
import com.yuxin.zcommoncomponents.model.ZFileType;
import com.yuxin.zcommoncomponents.model.ZImageSectionEntity;
import com.yuxin.zcommoncomponents.ui.widget.ZChooseFileView;
import com.yuxin.zcommoncomponents.ui.widget.ZChooseImageView;
import com.yuxin.zcommoncomponents.ui.widget.ZChooseOptionView;
import com.yuxin.zcommoncomponents.ui.widget.ZCustomChooseView;
import com.yuxin.zcommoncomponents.ui.widget.ZFloatOpitionView;
import com.yuxin.zcommoncomponents.util.ZLoadUtils;
import com.yuxin.zcommoncomponents.util.ZUpdatePromptUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.main_float)
    ZFloatOpitionView opitionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        List<ZChooseSectionEntity> mList = new ArrayList<>();
        mList.add(new ZChooseSectionEntity("男", 1));
        mList.add(new ZChooseSectionEntity("女", 1));

        List<ZChooseSectionEntity> mHobbyList = new ArrayList<>();
        mHobbyList.add(new ZChooseSectionEntity("跑步", 1));
        mHobbyList.add(new ZChooseSectionEntity("跳远", 2));
        mHobbyList.add(new ZChooseSectionEntity("打篮球", 3));
        mHobbyList.add(new ZChooseSectionEntity("吃饭", 4));
        opitionView.setDatas(mHobbyList);

    }


}
