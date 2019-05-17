package com.yuxin.zcommoncomponents.model;

/*****
 *@author zzw
 *@date 2019/1/2 17:09
 *@role
 *****/
public class ZChooseSectionEntity {
    public String title;
    public Object value;
    public boolean isSelect = false;

    public ZChooseSectionEntity(String title, Object value) {
        this.title = title;
        this.value = value;
    }


    public void changeIsSelect() {
        isSelect = !isSelect;
    }

}
