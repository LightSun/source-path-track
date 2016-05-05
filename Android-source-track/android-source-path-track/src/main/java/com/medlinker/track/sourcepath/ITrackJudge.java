package com.medlinker.track.sourcepath;

import java.util.List;

/**
 * the track detach judge,
 * Created by heaven7 on 2016/5/4.
 */
public interface ITrackJudge {

    /**
     * should detach the old node duo to the new node.
     * @param oldNode the old node in track stack.
     * @param newNode the new node
     * @return  true if the old node should be detach.
     */
    boolean shouldDetach(TagNode oldNode, TagNode newNode);

    /**
     * indicate the nodes contains the target node or not,this called in {@link ITrackManager#track(TagNode)}.<p>
     * if return true , {@link ITrackManager.ITrackRepeatProcessor#process(ITrackManager, TagNode)} will be called.
     * @param nodes the track stack nodes.
     * @param target the target node.
     * @return true if contains.
     */
    boolean contains(List<TagNode> nodes, TagNode target);


}
