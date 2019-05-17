package com.yuxin.zcommoncomponents.model;

import android.os.Parcel;
import android.os.Parcelable;

/*****
 *@author zzw
 *@date 2019/1/14 14:59
 *@role
 *****/
public class ZFileSuppertEntity implements Parcelable {
    private String title;
    private String type;

    public ZFileSuppertEntity() {
    }

    public ZFileSuppertEntity(String title, String type) {
        this.title = title;
        this.type = type;
    }

    protected ZFileSuppertEntity(Parcel in) {
        title = in.readString();
        type = in.readString();
    }

    public static final Creator<ZFileSuppertEntity> CREATOR = new Creator<ZFileSuppertEntity>() {
        @Override
        public ZFileSuppertEntity createFromParcel(Parcel in) {
            return new ZFileSuppertEntity(in);
        }

        @Override
        public ZFileSuppertEntity[] newArray(int size) {
            return new ZFileSuppertEntity[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(type);
    }
}
