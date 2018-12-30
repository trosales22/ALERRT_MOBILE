package com.capstone.alerrt_app.classes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.capstone.alerrt_app.fragment.MenuFragment;
import com.capstone.alerrt_app.fragment.NewsfeedFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    NewsfeedFragment newsfeedFragment = new NewsfeedFragment();
    MenuFragment menuFragment = new MenuFragment();

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return newsfeedFragment;
            case 1:
                return newsfeedFragment;
            case 2:
                return newsfeedFragment;
            case 3:
                return menuFragment;
            default:
                return newsfeedFragment;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
