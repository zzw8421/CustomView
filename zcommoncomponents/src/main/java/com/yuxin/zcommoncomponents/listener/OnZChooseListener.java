package com.yuxin.zcommoncomponents.listener;
import com.yuxin.zcommoncomponents.model.BaseFile;

/*****
 *@author zzw
 *@date 2019/1/15 11:27
 *@role
 *****/
public interface OnZChooseListener {
    void onAddClickListener();

    void onSelectItemListener(BaseFile baseFile);

    void onDelItemListener(BaseFile baseFile, int postion);
}
