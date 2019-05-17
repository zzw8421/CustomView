package com.yuxin.zcommoncomponents.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.yuxin.zcommoncomponents.model.BaseFile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*****
 *@author zzw
 *@date 2019/1/12 11:13
 *@role 获取文件
 *****/
public class ZFileManager {
    private final String TAG = getClass().getSimpleName();

    private static ZFileManager mInstance;
    private static Context mContext;
    private static ContentResolver mContentResolver;
    private static Object mLock = new Object();

    public static ZFileManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (mLock) {
                if (mInstance == null) {
                    mInstance = new ZFileManager();
                    mContext = context;
                    mContentResolver = context.getContentResolver();
                }
            }
        }
        return mInstance;
    }


    public List<BaseFile> getMusicFiles() {
        ArrayList<BaseFile> musics = new ArrayList<>();
        Cursor c = null;
        try {
            c = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));// 路径

                if (ZFileUtils.isExists(path)) {
                    continue;
                }
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)); // 歌曲名
                long date = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));// 大小
                BaseFile baseFile = new BaseFile();
                baseFile.setName(name);
                baseFile.setPath(path);
                baseFile.setSize(size);
                baseFile.setDate(date);
                musics.add(baseFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return musics;
    }

    public List<BaseFile> getFiles(String[] types) {
        String[] columns = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.MIME_TYPE, MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.DATE_MODIFIED, MediaStore.Files.FileColumns.DATA};
        StringBuilder select = new StringBuilder();
        if (types.length > 0) {
            select.append("( ");
            for (int i = 0; i < types.length; i++) {
                if (i == 0) {
                    select.append(MediaStore.Files.FileColumns.DATA + "  LIKE  '%.").append(types[i]).append("'");
                } else {
                    select.append(" or " + MediaStore.Files.FileColumns.DATA + "  LIKE  '%.").append(types[i]).append("'");
                }
            }
            select.append(" )");
        }
        Log.i(TAG, "getFiles: " + select.toString());
        List<BaseFile> dataList = new ArrayList<BaseFile>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns, select.toString(), null, null);

        int columnIndexOrThrow_ID = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID);
        int columnIndexOrThrow_MIME_TYPE = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE);
        int columnIndexOrThrow_DATA = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
        int columnIndexOrThrow_SIZE = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.SIZE);
        int columnIndexOrThrow_DATE_MODIFIED = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED); // 创建时间，更改时间

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String path = cursor.getString(columnIndexOrThrow_DATA);
                int position_do = path.lastIndexOf(".");
                if (position_do == -1) continue;
                String type = path.substring(position_do + 1, path.length());
                int position_x = path.lastIndexOf(File.separator);
                if (position_x == -1) continue;
                String displayName = path.substring(position_x + 1, path.length());
                int id = cursor.getInt(columnIndexOrThrow_ID);
                String mimeType = cursor.getString(columnIndexOrThrow_MIME_TYPE);
                long size = cursor.getLong(columnIndexOrThrow_SIZE);
                long modified_date = cursor.getLong(columnIndexOrThrow_DATE_MODIFIED);
                BaseFile file = new BaseFile();
                file.setName(displayName);
                file.setSize(size);
                file.setDate(modified_date);
                file.setPath(path);
                file.setId(id);
                file.setType(type);
                dataList.add(file);
            }
        }
        return dataList;
    }


}
