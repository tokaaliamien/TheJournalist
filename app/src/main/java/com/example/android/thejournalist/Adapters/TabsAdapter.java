package com.example.android.thejournalist.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.thejournalist.Fragments.HomeFragment;
import com.example.android.thejournalist.Utilites.Helper;

/**
 * Created by Toka on 2018-07-04.
 */

public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Helper.displayLog("TabsAdapter", "Position" + position);
        return HomeFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 4;
    }

}
