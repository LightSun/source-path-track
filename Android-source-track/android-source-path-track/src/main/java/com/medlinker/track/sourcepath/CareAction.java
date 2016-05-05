package com.medlinker.track.sourcepath;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * the care action. contains a list mNodes, and must in order.
	 */
public  class CareAction{
		/**
		 * the start mode .indicate the mNodes is the start.
		 *  that means the action will be callback all the time if action matched..
		 */
		public static final byte MODE_START   = 1;
	/**
	 * the end mode. that means while the track stack ends with this action.
	 */
		public static final byte MODE_END     = 2;
	/**
	 * the full mode. that means while the track stack equals with this action.
	 */
		public static final byte MODE_FULL    = 3;

		private static final INodeComparetor DEFAULT_COMPARETOR = new INodeComparetor() {
			@Override
			public boolean equals(TagNode node1, TagNode node2) {
				return node1.equals(node2);
			}
		};
		/**
		 * the care nodes in order
		 */
		final List<TagNode> mNodes;
		/** the care mode . default is full */
		byte mCareMode = MODE_FULL;

		INodeComparetor mComparetor = DEFAULT_COMPARETOR;

		@IntDef({MODE_START, MODE_END, MODE_FULL })
		@Retention(RetentionPolicy.SOURCE)
		public @interface CareMode{
		}

		/**
		 * the node comparetor
		 */
		public interface INodeComparetor{
             boolean equals(TagNode node1, TagNode node2);
		}

		public CareAction() {
			super();
			mNodes = new ArrayList<>(7);
		}
		public CareAction addCareNode(TagNode node){
			mNodes.add(node);
			return this;
		}
		public CareAction addCareNode(int level ,String tagName){
			return addCareNode(TagNode.obtain(level,tagName));
		}
		public List<TagNode> getCareNodes() {
			return mNodes;
		}
		/** return the care mode default is full */
		public byte getCareMode(){
			return mCareMode;
		}
		public void setCareMode(@CareMode byte careMode){
			this.mCareMode = careMode;
		}

		public INodeComparetor getNodeComparetor() {
			return mComparetor;
		}
		public void setNodeComparetor(INodeComparetor comparetor) {
			if(comparetor == null){
				throw  new NullPointerException();
			}
			this.mComparetor = comparetor;
		}

		public void copyFrom(CareAction action){
			this.mNodes.clear();
			this.mNodes.addAll(action.getCareNodes());
			this.mCareMode = action.mCareMode;
			this.mComparetor = action.mComparetor;
		}

		@Override
		public boolean equals(Object obj) {
			if(obj == this)
				return true;
			if(! (obj instanceof CareAction)){
				return false;
			}
			CareAction tmp = (CareAction) obj;
			if(this.getCareMode() != tmp.getCareMode() || !this.getNodeComparetor().equals(tmp.getNodeComparetor())){
               return false;
			}
			//only mode and comparetor equals. can go below.
			final List<TagNode> list1 = this.getCareNodes();
			final List<TagNode> list2 = tmp.getCareNodes();
			if(list1.size() != list2.size()){
				return false;
			}
			final INodeComparetor comparetor = getNodeComparetor();
			for(int i=0,size = list1.size()  ; i<size ; i++){
				if(!comparetor.equals(list1.get(i),list2.get(i))){
					return false;
				}
			}
			return true;
		}

		@Override
		public String toString() {
			return "CareAction{" +
					"mNodes=" + mNodes +
					", mCareMode=" + mCareMode +
					", mComparetor=" + mComparetor +
					'}';
		}

	public static class Builder{

		private final CareAction mAction = new CareAction();

		public Builder addCareNode(TagNode node){
			mAction.addCareNode(node);
			return this;
		}
		public Builder addCareNode(int level ,String tagName){
			addCareNode(TagNode.obtain(level,tagName));
			return this;
		}

		public Builder setCareMode(@CareAction.CareMode byte mode){
			mAction.setCareMode(mode);
			return this;
		}
		public Builder setNodeComparetor(CareAction.INodeComparetor comparetor){
			mAction.setNodeComparetor(comparetor);
			return this;
		}

		public CareAction build(){
			return mAction;
		}
	}
}
