package com.medlinker.track.sourcepath;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heaven7 on 2016/5/5.
 */
public abstract class AbstractPathTracker implements ITrackManager{

	private static final ITrackJudge DEFAULT_DETACH_JUDGE = new ITrackJudge() {
		@Override
		public boolean shouldDetach(TagNode oldNode, TagNode newNode) {
			return oldNode.level >= newNode.level;
		}
		@Override
		public boolean contains(List<TagNode> nodes, TagNode target) {
			return nodes.contains(target);
		}
	};

	protected final List<TagNode> mNodes;
	protected final List<IReportCallback> mCallbacks;
	protected final List<CareAction> mCareActions;

	private final TagNode mLastNode;

	private ITrackJudge mDetachJudge = DEFAULT_DETACH_JUDGE;
	private ITrackManager.ITrackRepeatProcessor mProcessor;


	public AbstractPathTracker() {
		super();
		mNodes = new ArrayList<>();
		mCallbacks = new ArrayList<>(3);
		mCareActions = new ArrayList<>();

		mLastNode = new TagNode();
	}
	@Override
	public void setTrackRepeatProcessor(ITrackManager.ITrackRepeatProcessor processor){
         if(processor == null){
			throw  new NullPointerException();
		}
		this.mProcessor = processor;
	}
	@Override
	public ITrackManager.ITrackRepeatProcessor getTrackRepeatProcessor(){
		return mProcessor;
	}
	@Override
	public ITrackJudge getTrackJudge() {
		return mDetachJudge;
	}
	@Override
	public void setTrackJudge(ITrackJudge judge) {
		if(judge == null){
			throw new NullPointerException();
		}
		this.mDetachJudge = judge;
	}
	@Override
	public void addCareAction(CareAction action) {
		if(action.mNodes.size()==0){
			throw new IllegalStateException("CareAction must have the care list mNodes !");
		}
		mCareActions.add(action);
	}
	@Override
	public void addCallback(IReportCallback callback){
		if(callback == null){
			throw new NullPointerException();
		}
		mCallbacks.add(callback);
	}
	@Override
	public void removeCallback(IReportCallback callback){
		mCallbacks.remove(callback);
	}

	@Override
	public TagNode getLastNode() {
		return mLastNode;
	}

	@Override
	public void untrackAll(){
		final List<TagNode> mNodes = this.mNodes;
		for(int i=0,size = mNodes.size() ; i<size ;i++){
			TagNode.POOL.recycle(mNodes.get(i));
		}
		mNodes.clear();
	}
}
