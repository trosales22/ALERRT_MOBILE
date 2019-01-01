package com.capstone.alerrt_app.classes.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.capstone.alerrt_app.fragment.NewsfeedFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private String[] tabArray = new String[]{"Newsfeed","Places","Notifications"};
    private Integer tabNumber = 3;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CharSequence getPageTitle(int position){
        return tabArray[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                NewsfeedFragment newsfeedFragment = new NewsfeedFragment();
                return newsfeedFragment;
            case 1:
                NewsfeedFragment placesFragment = new NewsfeedFragment();
                return placesFragment;
            case 2:
                NewsfeedFragment notificationFragment = new NewsfeedFragment();
                return notificationFragment;
            default:
                NewsfeedFragment newsfeedFragment1 = new NewsfeedFragment();
                return newsfeedFragment1;
        }
    }

    @Override
    public int getCount() {
        return tabNumber;
    }
}
