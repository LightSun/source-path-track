package com.medlinker.track.sourcepath.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.heaven7.core.util.BundleHelper;
import com.medlinker.track.sourcepath.TagNode;
import com.medlinker.track.sourcepath.TrackFactory;
import com.medlinker.track.sourcepath.TrackTestUtil;
import com.medlinker.track.sourcepath.demo.R;
import com.medlinker.track.sourcepath.demo.ShowTextActivity;


/**
 * Created by heaven7 on 2016/4/28.
 */
public  abstract class BaseTrackTestFragment extends BaseFragment{

    @Override
    protected int getlayoutId() {
        return R.layout.frag_track_test;
    }

    @Override
    protected void initView(Context context) {
         getViewHelper().setOnClickListener(R.id.bt, new View.OnClickListener() {
             public void onClick(View v) {
                 onClickBt(v);
             }
         }).setText(R.id.bt, getButtonName())
         .setText(R.id.bt_tag , getSmallTagName())
         .setOnClickListener(R.id.bt_tag, new View.OnClickListener() {
             public void onClick(View v) {
                 onClickTag(v);
             }
         });
    }

    @Override
    protected void initData(Context context, Bundle savedInstanceState) {

    }

    /**
     * 进入下一个界面后自动track and untrack.
     */
    protected void launchShowTextActivity(String text){
        getActivity2().getIntentExecutor().launchActivity(ShowTextActivity.class, new BundleHelper()
                .putString(ShowTextActivity.KEY_TEXT, text)
                .putInt(ShowTextActivity.KEY_LEVEL, getLevel() + 1)
                .getBundle());
    }

    public void onClickBt(View v){
        launchShowTextActivity(getButtonName());
    }

    public  void onClickTag(View v){
       // Logger.w(getPageName(),"onClickTag_before_event", TrackFactory.getDefaultTagTracker().mNodes.toString());
        TrackFactory.getDefaultTagTracker().trackEvent(TagNode.obtain(
                getLevel() + 1 , getSmallTagName()));
    }

    //----------------------------------
    public String getPageName(){
        return TrackTestUtil.getName(getClass());
    }
    protected String getButtonName(){
        return TrackTestUtil.getButtonName(getClass());
    }
    protected String getSmallTagName(){
        return TrackTestUtil.getSmallTagName(getClass());
    }

    protected abstract int getLevel();
    //----------------------------------------------------

    public void trackThisFragment(){
        TrackFactory.getDefaultTagTracker().track(getLevel(), getPageName());
    }

}
