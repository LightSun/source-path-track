package com.medlinker.track.sourcepath.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.medlinker.track.sourcepath.BaseActivity;
import com.medlinker.track.sourcepath.TrackTestUtil;
import com.medlinker.track.sourcepath.fragment.BaseTrackTestFragment;
import com.medlinker.track.sourcepath.fragment.ChuzhenFragment;
import com.medlinker.track.sourcepath.fragment.CircleFragment;
import com.medlinker.track.sourcepath.fragment.FirstPageFragment;
import com.medlinker.track.sourcepath.fragment.MessageFragment;

import butterknife.InjectView;

/**
 * Created by heaven7 on 2016/4/28.
 */
public class SourceTrackTestActivity extends BaseActivity {

    private static final String TAG = "SourceTrackTestActivity";
    @InjectView(R.id.rg)
    RadioGroup mRg;

    @InjectView(R.id.rb_first_page)
    RadioButton mRb_first ;
    @InjectView(R.id.rb_chuzhen)
    RadioButton mRb_chuzhen ;
    @InjectView(R.id.rb_circle)
    RadioButton mRb_circle ;
    @InjectView(R.id.rb_msg)
    RadioButton mRb_message ;

    private final SparseArrayCompat<BaseTrackTestFragment> mFragments = new SparseArrayCompat<>();
    private int mLastTabId = -1;

    @Override
    protected int getlayoutId() {
        return R.layout.ac_src_track_main;
    }

    @Override
    protected void initView() {
        TrackTestUtil.initTrack();

        mFragments.put(R.id.rb_first_page, new FirstPageFragment());
        mFragments.put(R.id.rb_chuzhen, new ChuzhenFragment());
        mFragments.put(R.id.rb_circle, new CircleFragment());
        mFragments.put(R.id.rb_msg, new MessageFragment());
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //untrack and track
               /*  if(mLastTabId != -1){
                     final BaseTrackTestFragment fragment = mFragments.get(mLastTabId);
                     TrackTestUtil.logIfNeed(TAG,"onCheckedChanged", "untrack: "+ fragment.getPageName());
                     fragment.untrackThisFragment();
                 }*/
                final BaseTrackTestFragment fragment = mFragments.get(checkedId);
                switch (checkedId){
                    case R.id.rb_first_page:
                        mRb_first.setTextColor(Color.MAGENTA);
                        mRb_chuzhen.setTextColor(Color.BLACK);
                        mRb_circle.setTextColor(Color.BLACK);
                        mRb_message.setTextColor(Color.BLACK);
                        break;

                    case R.id.rb_chuzhen:
                        mRb_first.setTextColor(Color.BLACK);
                        mRb_chuzhen.setTextColor(Color.MAGENTA);
                        mRb_circle.setTextColor(Color.BLACK);
                        mRb_message.setTextColor(Color.BLACK);
                        break;

                    case R.id.rb_circle:
                        mRb_first.setTextColor(Color.BLACK);
                        mRb_chuzhen.setTextColor(Color.BLACK);
                        mRb_circle.setTextColor(Color.MAGENTA);
                        mRb_message.setTextColor(Color.BLACK);
                        break;

                    case R.id.rb_msg:
                        mRb_first.setTextColor(Color.BLACK);
                        mRb_chuzhen.setTextColor(Color.BLACK);
                        mRb_circle.setTextColor(Color.BLACK);
                        mRb_message.setTextColor(Color.MAGENTA);
                        break;

                    default:
                        throw new RuntimeException();
                }
                mLastTabId = checkedId;
                replaceFragment(R.id.fl_container, fragment);

                TrackTestUtil.logIfNeed(TAG,"onCheckedChanged", "track: "+ fragment.getPageName());
                fragment.trackThisFragment();
            }
        });
        mRg.check(R.id.rb_first_page);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
