package com.yuxin.zcommoncomponents.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yuxin.zcommoncomponents.R;
import com.yuxin.zcommoncomponents.ZFilePickerBuilder;
import com.yuxin.zcommoncomponents.adapter.FilePickViewPagerAdapter;
import com.yuxin.zcommoncomponents.constant.ZFilePickerConstant;
import com.yuxin.zcommoncomponents.model.BaseFile;
import com.yuxin.zcommoncomponents.model.MessageEvnet;
import com.yuxin.zcommoncomponents.model.ZFileSuppertEntity;
import com.yuxin.zcommoncomponents.model.ZFileType;
import com.yuxin.zcommoncomponents.ui.fragment.CustomFileFragment;
import com.yuxin.zcommoncomponents.util.ZFileUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/*****
 *@author zzw
 *@date 2019/1/12 14:11
 *@role
 *****/
public class ZFilePickerActivity extends FragmentActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView tvTitle, tvCenter, tvTotalSize, tvClose;
    private RelativeLayout rltBottomRoot;
    private FilePickViewPagerAdapter mPagerAdapter;
    private long mTotalSize = 0;
    private static int selectCount = 0;
    private ArrayList<BaseFile> selectLists;
    private static final String TAG = "ZFilePickerActivity";
    public static final String EXTRA_RESULT_SELECTION = "extra_result_selection";
    public static final String BUNDLE_CODE = "bundle_code";
    public static final String FILE_SUPPORT = "file_support";
    private ArrayList<ZFileSuppertEntity> entities;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getBundleExtra(BUNDLE_CODE);
            entities = bundle.getParcelableArrayList(FILE_SUPPORT);
        }
        initView();
        initData();
        initTopBar();
        setListener();
    }

    private void initView() {
        viewPager = findViewById(R.id.file_picker_viewpager);
        tabLayout = findViewById(R.id.file_picker_tab);
        tvTitle = findViewById(R.id.file_picker_tv_title);
        tvClose = findViewById(R.id.file_picker_tv_close);
        tvCenter = findViewById(R.id.file_picker_tv_center);
        tvTotalSize = findViewById(R.id.file_picker_tv_total_size);
        rltBottomRoot = findViewById(R.id.file_picker_rlt_bottom_root);
        if (ZFilePickerConstant.chooseType == ZFilePickerConstant.Z_FILE_CHOOSE_TYPE_SINGLE) {
            rltBottomRoot.setVisibility(View.GONE);
        } else {
            rltBottomRoot.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        EventBus.getDefault().register(this);
        selectLists = new ArrayList<>();
        initTabs();
    }


    private void initTabs() {
        initFileSuppert();
        List<Fragment> mFragments = new ArrayList<>();
        List<String> mTitles = new ArrayList<>();
        for (ZFileSuppertEntity entity : entities) {
            mTitles.add(entity.getTitle());
            mFragments.add(CustomFileFragment.getIntent(entity.getType()));
            tabLayout.addTab(tabLayout.newTab().setText(entity.getTitle()));
        }
        mPagerAdapter = new FilePickViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        viewPager.setAdapter(mPagerAdapter);
        tabLayout.setTabsFromPagerAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(mTitles.size());
    }

    private void initFileSuppert() {
        if (entities == null || entities.size() == 0) {
            entities = new ArrayList<>();
            entities.add(new ZFileSuppertEntity("文档", ZFileType.DOC));
            entities.add(new ZFileSuppertEntity("表格", ZFileType.XLS));
            entities.add(new ZFileSuppertEntity("图片", ZFileType.IMAGE));
            entities.add(new ZFileSuppertEntity("PDF", ZFileType.PDF));
            entities.add(new ZFileSuppertEntity("文本", ZFileType.TXT));
        }
    }

    private void initTopBar() {
        tvTitle.setText(ZFilePickerConstant.zFilePickerTitle);
    }

    private void setListener() {
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectCount == 0) {
                    return;
                }
                finishResult();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSelect(MessageEvnet messageEvent) {
        if (ZFilePickerConstant.chooseType == ZFilePickerConstant.Z_FILE_CHOOSE_TYPE_SINGLE) {
            selectLists.add(messageEvent.obj);
            finishResult();
        } else {
            if (messageEvent != null && messageEvent.obj != null) {
                if (messageEvent.isSelect) {
                    if (!selectLists.contains(messageEvent.obj)) {
                        selectLists.add(messageEvent.obj);
                        selectCount++;
                        mTotalSize += messageEvent.obj.getSize();
                        refreshCenterBtnStatus();
                        refreshTotalSize();
                    }
                } else {
                    if (selectLists.contains(messageEvent.obj)) {
                        selectLists.remove(messageEvent.obj);
                        selectCount--;
                        mTotalSize -= messageEvent.obj.getSize();
                        refreshCenterBtnStatus();
                        refreshTotalSize();
                    }
                }
            }
        }


    }

    @SuppressLint({"SetTextI18n"})
    private void refreshCenterBtnStatus() {
        if (selectCount == 0) {
            tvCenter.setBackgroundResource(R.drawable.file_send_btn_normal_bg);
            tvCenter.setTextColor(getResources().getColor(R.color.sendBtnNormalFontColor));
            tvCenter.setText("确定");
        } else {

            if (ZFilePickerConstant.chooseType == ZFilePickerConstant.Z_FILE_CHOOSE_TYPE_MULTIPLE) {
                tvCenter.setBackgroundResource(R.drawable.file_send_btn_select_bg);
                tvCenter.setTextColor(Color.WHITE);
                tvCenter.setText("确定(" + selectCount + "/" + ZFilePickerConstant.maxCount + ")");
            } else {
                tvCenter.setBackgroundResource(R.drawable.file_send_btn_select_bg);
                tvCenter.setTextColor(Color.WHITE);
                tvCenter.setText("确定(" + selectCount  +")");
            }
        }
    }

    private void refreshTotalSize() {
        if (mTotalSize == 0) {
            tvTotalSize.setText("已选");
        } else {
            tvTotalSize.setText("已选" + ZFileUtils.getSizeStr(mTotalSize));
        }

    }

    private void finishResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESULT_SELECTION, selectLists);
        setResult(ZFilePickerBuilder.SUCCESS, intent);
        finish();
    }

    public static int getMaxConut() {
        return ZFilePickerConstant.maxCount;
    }

    public static int getSelectCount() {
        return selectCount;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        selectLists.clear();
        selectCount = 0;
    }
}
