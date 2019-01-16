package com.capstone.alerrt_app.classes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.capstone.alerrt_app.fragment.NewsfeedFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabArray = new String[]{"My Location","Notifications"};

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CharSequence getPageTitle(int position){
        return tabArray[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new NewsfeedFragment();
            case 1:
                return new NewsfeedFragment();
            default:
                return new NewsfeedFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
