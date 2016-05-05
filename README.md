# source-path-track
this is a lib of source path track. eg : if you want yo track A->B->C or A-B->Any page or  Any page ->B ->C

<img src="/imgs/log.png" alt="Demo Screen Capture" width="900px" height="450px"/>

## what is this ?
 often we may want to track the path of user's operate. so this is the library to resolve it.
 such as:  There is some pages . A,B,C and other pages.  and if we want to track
     "A -> B ->C" with "Any -> B -> C" with "A->B->Any".

## design idea or structure

path have multi nodes and some rules. so i declare a class named 'CareAction'. 
That is a 'CareAction' indicate a path that we want to track. And the class contains some nodes. the class 'TagNode' is it. 
And the 'ITrackManager' is the manager to manage the track behaviour.  
the 'ITrackJudge'  decide hao wo do with the old node due to the new node.
the 'IReportCallback'  is the callback while the CareAction occoured.
the 'ITrackRepeatProcessor' is the processor while we track a repeat node. if we not set there is no effect.

<img src="/imgs/uml_class.png" alt="Demo Screen Capture" width="659px" height="486px"/>


## Usage.

1, you should init the operation of relative. such as CareAction.  ITrackRepeatProcessor(optional), ITrackJudge(optional).
   here is the demo.
   ``` java
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

   ```
   
   2,  call the method track() or trackEvent() when you want to track the page or tag.
   ``` java
    TrackFactory.getDefaultTagTracker().track(getLevel(), getPageName());
   ```
   and if you want to carry extra data. please use the method 
     ``` java
     void track(int level, String tag, Object extra).
       ```
   'trackEvent()'  is similar. 
   - the difference between 'track()' and 'trackEvent()' ?
     'track()' ->
                 indicate the node will be add to the end of the track stack. 
     'trackEvent()'  ->
                 indicate the node will only use once .and will not add to the track stack. 
 
