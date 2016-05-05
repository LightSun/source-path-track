package com.medlinker.track.sourcepath;

/**
 * the track manager
 * Created by heaven7 on 2016/5/4.
 */
public interface ITrackManager {

    /**
     * set the track repeat processor
     * @param processor the target
     */
    void setTrackRepeatProcessor(ITrackRepeatProcessor processor);

    /**
     * get the current track repeat processor
     */
    ITrackRepeatProcessor getTrackRepeatProcessor();

    /**
     * get the track judge
     */
    ITrackJudge getTrackJudge();

    /**
     * set the track judge
     */
    void setTrackJudge(ITrackJudge judge);

    /**
     * add a care action
     * @param action the action to add
     */
    void addCareAction(CareAction action);

    /**
     * add a report callback
     * @param callback the callback to add
     */
    void addCallback(IReportCallback callback);

    /**
     * remove a report callback
     * @param callback
     */
    void removeCallback(IReportCallback callback);

    /**
     * untrack the all nodes. that means after call this the track stack is empty.
     */
    void untrackAll();


    /**
     * track the node as a event. that means the node will not add to the current track stack.
     * @param node a event node.
     */
    void trackEvent(TagNode node);

    /**
     * track the node
     * @param node a node
     */
    void track(TagNode node);

    /**
     * dispatch the callback  if need
     * @param addToReportList indicate whether the care action can repeat report or not.
     *                        true indicate the care action can't repeat report until the track stack switch level.
     *                        eg: the more to see in {@link TagTracker#dispatchCallbackIfNeed(boolean)}
     */
    void dispatchCallbackIfNeed(boolean addToReportList);

    /**
     * directly detach the nodes ( that was tracked previous ) from the track stack for the target node.
     * @param node the target node
     */
    void detachNodesForTarget(TagNode node);

    /**
     * directly attach a new node to the track stack.
     * @param node the node to attach
     */
    void attachNode(TagNode node);

    /**
     * get the last node , this indicate is the end node of the track stack.
     */
    TagNode getLastNode();

    /**
     * the  processor while track repeat node.
     */
    interface ITrackRepeatProcessor {

        /**
         * @param manager the track manager
         * @param node the node that was repeated track.
         * @return true if you handled the repeat track .
         */
        boolean process(ITrackManager manager, TagNode node);

    }


}
