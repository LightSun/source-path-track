package com.medlinker.track.sourcepath;

import com.medlinker.track.sourcepath.util.Cacher;

/**
 * the tag node indicate a node of a track. if you want to track an order of multi mNodes. {@link CareAction}
	 * <li>equals two TagNodes return true often means the node's level and tagName is the same.
	 * <li>the extra data don't participate the method 'equals'.
	 */
public  class TagNode{

	   static final Cacher<TagNode,Void> POOL = new Cacher<TagNode,Void>(20){
			@Override
			public TagNode create(Void p) {
				return new TagNode();
			}
			protected void onRecycleSuccess(TagNode t) {
				t.extra = null;
			}
	   };
		/** the level decide whether to untrack or not, switch same whether means untrack success. */
		int level;        //level
		String tagName;
		Object extra;

		TagNode() {
			super();
		}
		public void copyFrom(TagNode src) {
			this.level = src.level;
			this.tagName = src.tagName;
			this.extra = src.extra;
		}
		public void copyTo(TagNode target){
			target.level = this.level;
			target.tagName = this.tagName;
			target.extra = this.extra;
		}

		void set(int level, String tagName) {
			this.level = level;
			this.tagName = tagName;
		}

		void set(int level, String tagName, Object extra) {
			this.level = level;
			this.tagName = tagName;
			this.extra = extra;
		}

		public Object getExtra(){
			return extra;
		}
		public int getLevel(){
			return level;
		}
		public String getTagName(){
			return tagName;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + level;
			result = prime * result
					+ ((tagName == null) ? 0 : tagName.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TagNode other = (TagNode) obj;
			if (level != other.level)
				return false;
			if (tagName == null) {
				if (other.tagName != null)
					return false;
			} else if (!tagName.equals(other.tagName))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "TagNode{" +
					"level=" + level +
					", tagName='" + tagName + '\'' +
					", extra=" + extra +
					'}';
		}

		public static TagNode obtain(int level, String tagName){
			TagNode node = POOL.obtain();
			node.set(level, tagName);
			return node;
		}

		public static TagNode obtain(int level, String tagName,Object extra){
			TagNode node = POOL.obtain();
			node.set(level, tagName,extra);
			return node;
		}
	}
