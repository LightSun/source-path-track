package com.medlinker.track.sourcepath;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/**
 * track an order Nodes. help we fast track the care action(indicate the source path track).
 * {@link CareAction}.
 * <li>this class is thread safe.</li>
 * <li>you must careful of the {@link TagNode}'s level , while some previous mNodes level >= new node's level,they will auto untrack </li>
 * <p>usage see /${project}/temp/TagTrackerTest.java</p>
 * @author heaven7
 */
public final class TagTracker extends AbstractPathTracker implements ITrackManager {

	private static final boolean DEBUG = false;

	private final List<CareAction> mReportedActions;
	private final CareAction mTempAction;
	private final List<TagNode> mTempNodes;

	TagTracker() {
		super();
		mTempAction = new CareAction();
		mTempNodes = new ArrayList<>();
		mReportedActions = new ArrayList<>();
	}


	@Override
	public synchronized void untrackAll(){
		super.untrackAll();
		mReportedActions.clear();
	}

	/**
	 * track the id with tagName of node.
	 * @param tagName the tag of this track
	 * @param level  the level of this track
	 */
	public void track(int level, String tagName){
		track(TagNode.obtain(level,tagName));
	}
	/**
	 * track the id with tagName of node. and carry extra data.
	 * @param tagName the tag of this track
	 * @param level  the level of this track
	 * @param  extra the extra data.
	 */
	public void track(int level, String tagName, Object extra){
		track(TagNode.obtain(level,tagName,extra));
	}
	/**
	 * track the id with tagName of the event node. and carry extra data.
	 * @param tag the tag of this track
	 * @param level  the level of this track
	 * @param  extra the extra data. will not participate the 'equals' method.
	 */
	public void trackEvent(int level, String tag, Object extra){
		trackEvent(TagNode.obtain(level,tag, extra));
	}
	/**
	 * track the id with tagName of the event node..
	 * @param tag the tag of this track
	 * @param level  the level of this track
	 */
	public void trackEvent(int level, String tag){
		trackEvent(TagNode.obtain(level,tag));
	}
	/**
	 * track a event node just use once
	 */
	@Override
	public void trackEvent(TagNode node){
		trackInternal(node,true);
	}
	/**
	 * track a node , you should call untrack while the page switched.
	 */
	@Override
	public  void track(TagNode node){
		trackInternal(node,false);
	}

	/**
	 * track the target node. and check the level if need untrack.
	 * @param node the target
	 * @param event is event
     */
	private synchronized void trackInternal(TagNode node,boolean event){
		if(getTrackJudge().contains(mNodes, node )){
			if(getTrackRepeatProcessor() == null || !getTrackRepeatProcessor().process(this,node)){
				System.err.println("the node already exists. " + node);
			}
			return;
		}
		//check level if old node >= while new node's level,that means it will be auto untrack
		detachNodesForTarget(node);
		attachNode(node);

		dispatchCallbackIfNeed(!event);

		//event is once node. so need delete
		if(event){
			List<TagNode> mNodes = this.mNodes;
			TagNode.POOL.recycle(mNodes.remove(mNodes.size()-1));
		}else{
			getLastNode().copyFrom(node);
		}
	}

	@Override
	public void dispatchCallbackIfNeed(boolean addToReportList) {
		final List<TagNode> mTempNodes = this.mTempNodes;
		final List<TagNode> mNodes = this.mNodes;
		final List<CareAction> mCareActions = this.mCareActions;

		mTempNodes.addAll(mNodes);

		TagNode tmp;
		int index;
		CareAction.INodeComparetor nodeComparetor;
		//match action
		for(CareAction action : mCareActions){
			nodeComparetor = action.getNodeComparetor();
			switch (action.getCareMode()){
				case CareAction.MODE_START:
					if (isFullList(mNodes, action.mNodes, nodeComparetor)) {
						dispatchCallbackonCareActionOccoured(mTempNodes,action, false, true);
					}
					break;

				case CareAction.MODE_FULL :
				case CareAction.MODE_END : {
					int nodeSize = action.mNodes.size();//4    7   7-4
					tmp = action.mNodes.get(0);
					index = -1;
					//resolve d-> b-> C -> d -> b
					for(int size = mNodes.size(),i = size - nodeSize; i >= 0 ; i--){
						if(nodeComparetor.equals(mNodes.get(i),tmp)){
							index = i;
							break;
						}
					}
					if (index == -1) {
						break;
					}
					if (isFullList(mNodes.subList(index, mNodes.size()), action.mNodes, nodeComparetor)) {
						dispatchCallbackonCareActionOccoured(mTempNodes,action, addToReportList, false);
					}
				}
					break;
				default: throw  new RuntimeException();
			}
		}
		mTempNodes.clear();
	}

	@Override
	public void detachNodesForTarget(TagNode node) {
		final List<TagNode> mTempNodes = this.mTempNodes;
		mTempNodes.addAll(this.mNodes);

		final ITrackJudge judge = this.getTrackJudge();
		TagNode tmp;
		for(int i=0,size = mTempNodes.size() ; i<size ;i++){
			tmp = mTempNodes.get(i);
            if(judge.shouldDetach(tmp, node)){
				untrackImpl(tmp);
				logDebug("detachNodesForTarget", "the node("+tmp+") was detached for target("+node+").");
			}
		}
		mTempNodes.clear();
	}

	@Override
	public void attachNode(TagNode node) {
		this.mNodes.add(node);
		logDebug("attachNode","the track stack is : " + mNodes.toString());
	}

	private boolean untrackImpl(TagNode node){
		int index = mNodes.indexOf(node);
		if(index < 0){
			return false;
		}
		//remove from report list if need.
		ListIterator<CareAction> it = mReportedActions.listIterator();

		CareAction action;
		boolean remove;
		while(it.hasNext()){
			remove = false;
			action = it.next();
			for(int i = 0, size = action.getCareNodes().size(); i<size ; i++ ){
                if(action.getNodeComparetor().equals(action.getCareNodes().get(i),node )){
					remove = true;
					break;
				}
			}
			if(remove){
				it.remove();
				logDebug("untrackImpl", " the action is removed from report list. action = "+ action +" ,node = " + node);
			}
		}
		//remove and recycle
		mNodes.remove(index);
		TagNode.POOL.recycle(node);
		return true;
	}

	private static void logDebug(String method, String msg) {
		if(DEBUG) {
			System.out.println("called [ " + method + "() ]: " + msg);
		}
	}

	/** dispatch the callback
	 * @param mTempNodes the temp nodes of the current tracker stack. will be clear after callback.
	 * @param action the care action.
	 * @param addToReportList  whether  add the action to the report list or not.
	 * @param canRepeatReport  whether  can repeat the action to the report list or not.   */
	private void dispatchCallbackonCareActionOccoured(List<TagNode> mTempNodes, CareAction action,
													  boolean addToReportList, boolean canRepeatReport) {
		if(!canRepeatReport && mReportedActions.contains(action)){
			return ;
		}
		if(addToReportList) {
			mReportedActions.add(action);
		}
		mTempAction.copyFrom(action);
		final CareAction tmp = mTempAction;
		for(IReportCallback callback : mCallbacks){
			callback.onReportCareAction(mTempNodes, tmp);
		}
	}
	/** is src contains the all mNodes of target and must in order。*/
	private static boolean isFullList(List<TagNode> src, List<TagNode> target,CareAction.INodeComparetor comparetor) {
		if(src.size() < target.size()){
			return false;
		}
		for(int i= 0 , size = target.size() ; i< size ;i++){
			if(!comparetor.equals(src.get(i), target.get(i) )){
				return false ;
			}
		}
		return true;
	}



}
