package com.medlinker.track.sourcepath.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class FragmentPagerAdapter2 extends FragmentPagerAdapter {

        private List<CharSequence> mTitles;
        private List<Fragment> mFragments;

        public FragmentPagerAdapter2(FragmentManager fm, List<CharSequence> mTitles, List<Fragment> mFragments) {
            super(fm);
            this.mTitles = mTitles;
            this.mFragments = mFragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
