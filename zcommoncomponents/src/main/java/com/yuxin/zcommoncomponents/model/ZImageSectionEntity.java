package com.yuxin.zcommoncomponents.model;

/*****
 *@author zzw
 *@date 2019/1/8 11:46
 *@role
 *****/
public class ZImageSectionEntity<T> {
    public T url;
    public Object value;
    public boolean isDelete;
    private boolean isAdd = false;

    public ZImageSectionEntity() {

    }

    public ZImageSectionEntity(T url, Object value) {
        this.url = url;
        this.value = value;
        this.isDelete = true;
    }

    public ZImageSectionEntity(T url, boolean isCanDel, Object value) {
        this.url = url;
        this.value = value;
        this.isDelete = isCanDel;
    }

    public ZImageSectionEntity(T url) {
        this.url = url;
        this.isDelete = true;

    }

    public ZImageSectionEntity(T url, boolean isCanDel) {
        this.url = url;
        this.isDelete = isCanDel;

    }


    public boolean getIsAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

}
