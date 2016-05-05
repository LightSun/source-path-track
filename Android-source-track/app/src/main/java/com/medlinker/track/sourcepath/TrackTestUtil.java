package com.medlinker.track.sourcepath;


import com.heaven7.core.util.Logger;
import com.medlinker.track.sourcepath.fragment.BaseTrackTestFragment;
import com.medlinker.track.sourcepath.fragment.CaseLibFragment;
import com.medlinker.track.sourcepath.fragment.ChuzhenFragment;
import com.medlinker.track.sourcepath.fragment.DynamicFragment;
import com.medlinker.track.sourcepath.fragment.FirstPageFragment;
import com.medlinker.track.sourcepath.fragment.VideoFragment;

import java.util.Arrays;
import java.util.List;


/**
 * Created by heaven7 on 2016/4/28.
 */
public class TrackTestUtil {

    private static final String TAG = "TrackTestUtil";
    private static final boolean DEBUG = true;

    public static void logIfNeed(String tag, String method, String msg){
       if(DEBUG)
           Logger.d(tag,method,msg);
    }

    public static String getButtonName(Class<? extends BaseTrackTestFragment> clazz){
        return "Button_"+ clazz.getSimpleName();
    }
    public static String getSmallTagName(Class<? extends BaseTrackTestFragment> clazz){
        return "Small_Tag_"+ clazz.getSimpleName();
    }
    public static String getName(Class<? extends BaseTrackTestFragment> clazz){
        return clazz.getSimpleName();
    }

    public static void initTrack() {
        //DynamicFragment.主按钮->下一个界面
        addCareAction(
                new TrackParam(FirstPageFragment.class.getSimpleName(),1) ,
                new TrackParam(DynamicFragment.class.getSimpleName(),2) ,
                new TrackParam(DynamicFragment.class, 3, false)
                );

        //DynamicFragment. 小标签点击
        addCareAction(
                new TrackParam(FirstPageFragment.class.getSimpleName(),1) ,
                new TrackParam(DynamicFragment.class.getSimpleName(),2) ,
                new TrackParam(DynamicFragment.class, 3, true)
                );


        //CaseLibFragment. 主按钮->下一个界面
        addCareAction(
                new TrackParam(FirstPageFragment.class.getSimpleName(),1) ,
                new TrackParam(CaseLibFragment.class.getSimpleName(),2) ,
                new TrackParam(CaseLibFragment.class, 3, false)
        );
        //CaseLibFragment. 小标签点击
        addCareAction(
                new TrackParam(FirstPageFragment.class.getSimpleName(),1) ,
                new TrackParam(CaseLibFragment.class.getSimpleName(),2) ,
                new TrackParam(CaseLibFragment.class, 3, true)
                );

        //VideoFragment. 主按钮->下一个界面
        addCareAction(
                new TrackParam(FirstPageFragment.class.getSimpleName(),1) ,
                new TrackParam(VideoFragment.class.getSimpleName(),2) ,
                new TrackParam(VideoFragment.class, 3, false)
        );
        //VideoFragment. 小标签点击
        addCareAction(
                new TrackParam(FirstPageFragment.class.getSimpleName(),1) ,
                new TrackParam(VideoFragment.class.getSimpleName(),2) ,
                new TrackParam(VideoFragment.class, 3, true)
                );

        //出诊，主按钮进入的下一个界面
        addCareAction(
                new TrackParam(ChuzhenFragment.class.getSimpleName(),1) ,
                new TrackParam(ChuzhenFragment.class, 2, false)
        );
        //出诊，小标签，点击
        addCareAction(
                new TrackParam(ChuzhenFragment.class.getSimpleName(),1) ,
                new TrackParam(ChuzhenFragment.class, 2, true)
        );
        TrackFactory.getDefaultTagTracker().setTrackRepeatProcessor(new DefaultTrackRepeatProcessor());
        TrackFactory.getDefaultTagTracker().addCallback(new IReportCallback() {
            @Override
            public void onReportCareAction(List<TagNode> mTempNodes, CareAction action) {
                Logger.i(TAG , "onReportCareAction", action.getCareNodes().toString());
            }
        });

    }
    private static void addCareAction(TrackParam...tracks) {
        addCareAction(Arrays.asList(tracks));
    }

    private static void addCareAction(List<TrackParam> tracks) {
        TagTracker tracker = TrackFactory.getDefaultTagTracker();
        final CareAction action = new CareAction();
        for (TrackParam param : tracks){
            action.addCareNode(param.level, param.tag);
        }
        tracker.addCareAction(action);
    }

    static class TrackParam{
        int level;
        String tag;

        /** used to track button and small tag */
        public TrackParam(Class<? extends BaseTrackTestFragment> clazz, int level, boolean smallTag) {
            this.level = level;
            this.tag = smallTag ?  getSmallTagName(clazz) :  getButtonName(clazz);
        }

        /** fragment page */
        public TrackParam(String tag, int level) {
            this.tag = tag;
            this.level = level;
        }
    }
}
