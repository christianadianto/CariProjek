package edu.bluejack16_2.cariprojek;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by chris on 07/09/2017.
 */

public class ViewProfilePagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragments;
    ArrayList<String> titles;

    public ViewProfilePagerAdapter(FragmentManager fm){
        super(fm);
        fragments = new ArrayList<Fragment>();
        titles = new ArrayList<String>();
    }

    public void addFragmentandTitle(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);
    }

    public CharSequence getPageTitle(int position){
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
            return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
