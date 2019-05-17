package com.yuxin.zcommoncomponents.model;

import android.os.Parcel;
import android.os.Parcelable;

/*****
 *@author zzw
 *@date 2019/1/12 11:20
 *@role
 *****/
public class BaseFile implements Parcelable {
    private long id;
    private String name;
    private String path;
    private long size;   //byte
    private String bucketId;  //Directory ID
    private String bucketName;  //Directory Name
    private long date;  //Added Date
    private boolean isSelected;
    private String type;


    public BaseFile() {
    }

    protected BaseFile(Parcel in) {
        id = in.readLong();
        name = in.readString();
        path = in.readString();
        size = in.readLong();
        bucketId = in.readString();
        bucketName = in.readString();
        date = in.readLong();
        isSelected = in.readByte() != 0;
        type = in.readString();
    }

    public static final Creator<BaseFile> CREATOR = new Creator<BaseFile>() {
        @Override
        public BaseFile createFromParcel(Parcel in) {
            return new BaseFile(in);
        }

        @Override
        public BaseFile[] newArray(int size) {
            return new BaseFile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(path);
        dest.writeLong(size);
        dest.writeString(bucketId);
        dest.writeString(bucketName);
        dest.writeLong(date);
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeString(type);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getBucketId() {
        return bucketId;
    }

    public void setBucketId(String bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
