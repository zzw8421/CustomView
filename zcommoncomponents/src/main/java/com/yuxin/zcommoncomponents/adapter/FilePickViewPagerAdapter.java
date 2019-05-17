package com.yuxin.zcommoncomponents.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/*****
 *@author zzw
 *@date 2019/1/9 16:49
 *@role
 *****/
public class FilePickViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mList;
    private List<String> mTitles;

    public FilePickViewPagerAdapter(FragmentManager fm, List<Fragment> mList, List<String> mTitles) {
        super(fm);
        this.mList = mList;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
