package com.medlinker.track.sourcepath.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.medlinker.track.sourcepath.TrackTestUtil;
import com.medlinker.track.sourcepath.demo.R;
import com.medlinker.track.sourcepath.util.FragmentPagerAdapter2;
import com.medlinker.track.sourcepath.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页的fragment
 * Created by heaven7 on 2016/4/28.
 */
public class FirstPageFragment extends BaseTrackTestFragment {

    private static final String TAG = "FirstPageFragment";
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    private FragmentPagerAdapter2 mAdapter;

    /** index of child fragment */
    public int mSelectPosition = 0;

    @Override
    protected int getlayoutId() {
        return R.layout.frag_first_page;
    }

    @Override
    protected void initView(Context context) {
        mViewPager = getViewHelper().getView(R.id.vp);
        mSlidingTabLayout = getViewHelper().getView(R.id.sliding_tab_layout);
       //在切换之前调用
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                onPreSelect(position);
            }
            @Override
            public void onPageSelected(int position) {
                onPreSelect(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        initSlidingTabLayout();
    }

    // handle track
    private void onPreSelect(int position) {
        int lastIndex = mSlidingTabLayout.getLastSelectIndex();
        if (position == lastIndex) {
            return;
        }
        BaseTrackTestFragment fragment = (BaseTrackTestFragment) mAdapter.getItem(position);
        fragment.trackThisFragment();
        TrackTestUtil.logIfNeed(TAG,"onPreSelect", "track: "+ fragment.getPageName());
    }

    @Override
    protected void initData(Context context, Bundle savedInstanceState) {
        DynamicFragment dynamic = new DynamicFragment();
        CaseLibFragment caseIib = new CaseLibFragment();
        VideoFragment video = new VideoFragment();

        showActually(dynamic,caseIib,video);
    }

    private void showActually(BaseTrackTestFragment... fragments) {
        if(fragments==null || fragments.length ==0)
            return;
        List<Fragment> frags = new ArrayList<>();
        List<CharSequence> mTitles = new ArrayList<>();
        for (int i = 0, size = fragments.length; i < size; i++) {
            BaseTrackTestFragment fragment = fragments[i];
            //fragment.setCallback(this);
            frags.add(fragment);
            mTitles.add(fragment.getPageName());
        }
        mViewPager.setAdapter(mAdapter = new FragmentPagerAdapter2(getChildFragmentManager(), mTitles, frags));
        mSlidingTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    private void initSlidingTabLayout() {
        mSlidingTabLayout.setEnableIndicator(false);
        mSlidingTabLayout.setCustomTabView(R.layout.tab_view_position, R.id.tv_title);
        mSlidingTabLayout.setOnInitTitleListener(new SlidingTabLayout.OnInitTitleListener() {
            @Override
            public void onInitTitle(int position, TextView title) {
                title.setTextColor(position !=0 ? Color.BLACK : Color.BLUE);
            }
        });
        //int c = R.color.common_background_line;
       // mSlidingTabLayout.setDividerColors(Color.argb(35,Color.red(c),Color.green(c),Color.blue(c)));
        mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                onSelected(position);
            }

            public void onPageSelected(int position) {
                onSelected(position);
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void onSelected(int position) {
        int lastIndex = mSlidingTabLayout.getLastSelectIndex();
        if (position == lastIndex) {
            return;
        }
        mSelectPosition = position;
        List<TextView> titleTextViews = mSlidingTabLayout.getTitleTextViews();
        for (int i = 0, size = titleTextViews.size(); i < size; i++) {
            titleTextViews.get(i).setTextColor(position == i ? Color.BLACK : Color.BLUE);
        }
    }

    //================= empty implements ===================
    @Override
    public void onClickBt(View v) {
    }
    @Override
    public void onClickTag(View v) {

    }

    @Override
    protected int getLevel() {
        return 1;
    }

}
