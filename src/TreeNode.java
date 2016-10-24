
public interface TreeNode {
	boolean isLeaf();
	boolean isFull();
	boolean hasMin();
	boolean contains(TreeNode that);
	Point getPoint();
	Rect getBoundingRect();
	TreeBranch getParent();
	void setParent(TreeBranch parent);
}
