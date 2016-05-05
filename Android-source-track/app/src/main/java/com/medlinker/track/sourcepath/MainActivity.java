package com.medlinker.track.sourcepath;

import com.medlinker.track.sourcepath.demo.SourceTrackTestActivity;

import java.util.List;

public class MainActivity extends AbsMainActivity {

    @Override
    protected void addDemos(List<ActivityInfo> list) {
        list.add(new ActivityInfo(SourceTrackTestActivity.class , "SourceTrackTestActivity"));
    }
}
