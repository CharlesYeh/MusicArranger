package util;

import java.util.ArrayList;

class treeNode {

	treeNode parent = null;
	ArrayList<treeNode> children;

	/**
	 * Method addChild
	 *
	 */
	public void addChild(treeNode node) {

		children.add(node);
	}
}
