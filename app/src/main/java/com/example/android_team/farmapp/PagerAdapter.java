package com.example.android_team.farmapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.android_team.farmapp.Fragments.FruitsFragment;
import com.example.android_team.farmapp.Fragments.OffersFragment;
import com.example.android_team.farmapp.Fragments.VegeFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    //Setting up the View Pager with tabs
    private int mTabsNum;
    public PagerAdapter(FragmentManager fm, int tabsNum) {
        super(fm);
        this.mTabsNum = tabsNum;
    }

    //Here we control the flow of the pager, What Fragment to go on clicking to which Tab..
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OffersFragment();
            case 1:
                return new FruitsFragment();
            case 2:
                return new VegeFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabsNum;
    }
}