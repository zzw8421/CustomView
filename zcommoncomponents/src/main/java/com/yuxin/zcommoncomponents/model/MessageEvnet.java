package com.yuxin.zcommoncomponents.model;

/*****
 *@author zzw
 *@date 2019/1/12 17:35
 *@role
 *****/
public class MessageEvnet {
    public boolean isSelect;
    public BaseFile obj;

    public MessageEvnet() {
    }

    public MessageEvnet(boolean isSelect, BaseFile obj) {
        this.isSelect = isSelect;
        this.obj = obj;
    }
}
