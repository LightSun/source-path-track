package com.medlinker.track.sourcepath;

import java.util.List;

/**
 * the report callback
	 * @author heaven7
	 */
public interface IReportCallback{

		/**
		 * called when the care action is occoured.
		 * @param mTempNodes the temp nodes in the TagTracker's stack.
		 * @param action the care action
		 */
		void onReportCareAction(List<TagNode> mTempNodes, CareAction action);

}
