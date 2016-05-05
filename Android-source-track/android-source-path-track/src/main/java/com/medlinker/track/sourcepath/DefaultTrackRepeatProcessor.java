package com.medlinker.track.sourcepath;

/**
 * the default implements while retrack occoured.
 * Created by heaven7 on 2016/5/4.
 */
public class DefaultTrackRepeatProcessor implements ITrackManager.ITrackRepeatProcessor {

    @Override
    public boolean process(ITrackManager manager, TagNode node) {
        manager.detachNodesForTarget(node);
        manager.attachNode(node);
        manager.getLastNode().copyFrom(node);
        return true;
    }
}
