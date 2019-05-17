package com.yuxin.zcommoncomponents;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;


import com.yuxin.zcommoncomponents.constant.ZFilePickerConstant;
import com.yuxin.zcommoncomponents.model.BaseFile;
import com.yuxin.zcommoncomponents.model.ZFileSuppertEntity;
import com.yuxin.zcommoncomponents.ui.activity.ZFilePickerActivity;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/*****
 *@author zzw
 *@date 2019/1/14 13:54
 *@role
 *****/
public class ZFilePickerBuilder implements EasyPermissions.PermissionCallbacks {
    private Activity mContext;
    public final static int SUCCESS = 1;
    public final static int ERROR = -1;
    private Bundle bundle;
    private ArrayList<ZFileSuppertEntity> entities;
    private final int READ_EXTERNAL_STORAGE = 1001;
    private int code;

    public static ZFilePickerBuilder from(Activity context) {
        return new ZFilePickerBuilder(context);
    }

    public ZFilePickerBuilder(Activity context) {
        mContext = context;
        entities = new ArrayList<>();
        bundle = new Bundle();
    }

    public ZFilePickerBuilder setChooseType(int type) {
        ZFilePickerConstant.chooseType = type;
        return this;
    }

    /**
     * 设置最大选中数目
     *
     * @param count
     * @return
     */

    public ZFilePickerBuilder setMaxCount(int count) {
        ZFilePickerConstant.maxCount = count;
        return this;
    }

    /***
     * 设置选择文件的标题
     * @param title 标题（默认为 选择文件）
     * @return
     */
    public ZFilePickerBuilder setTitle(String title) {
        ZFilePickerConstant.zFilePickerTitle = title;
        return this;
    }

    /**
     * 添加选择文件的类型
     *
     * @param title 显示的tab名称
     * @param type  文件类型
     * @return
     */
    public ZFilePickerBuilder addFileSuppert(String title, String type) {
        entities.add(new ZFileSuppertEntity(title, type));
        return this;
    }

    /***
     * 添加选择文件的类型
     * @param entity
     * @return
     */
    public ZFilePickerBuilder addFileSuppert(ZFileSuppertEntity entity) {
        entities.add(entity);
        return this;
    }

    public void forResult(int code) {
        this.code = code;
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            pickFile();
        } else {
            EasyPermissions.requestPermissions(mContext, "请授予读取权限后才能使用",
                    READ_EXTERNAL_STORAGE, perms);
        }

    }

    /**
     * 从Intent中获取选中文件的数据
     *
     * @param data
     * @return
     */
    public static List<BaseFile> obtainResult(Intent data) {
        return data.getParcelableArrayListExtra(ZFilePickerActivity.EXTRA_RESULT_SELECTION);
    }

    /***
     * 启动选择文件
     */

    private void pickFile() {
        Intent intent = new Intent(mContext, ZFilePickerActivity.class);
        bundle.putParcelableArrayList(ZFilePickerActivity.FILE_SUPPORT, entities);
        intent.putExtra(ZFilePickerActivity.BUNDLE_CODE, bundle);
        mContext.startActivityForResult(intent, code);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        pickFile();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(mContext, R.string.permission_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {

    }
}
