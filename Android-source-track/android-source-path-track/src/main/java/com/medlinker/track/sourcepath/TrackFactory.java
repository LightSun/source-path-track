package com.medlinker.track.sourcepath;

/**
 * Created by heaven7 on 2016/5/5.
 */
public final class TrackFactory {

    public static TagTracker getDefaultTagTracker(){
        return Creator.TRACKER;
    }

    private static class Creator{
        public static final TagTracker TRACKER = new TagTracker();
    }
}
