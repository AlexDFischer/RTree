import org.junit.Before;
import org.junit.Test;

public class RTreetests {

	private LocationMapping lmap1;
	private LocationMapping lmap2;

	@Before
	public void before() {
		lmap1 = new LocationMapping(Constants.generateLocations(23, 40));
		lmap2 = new LocationMapping(Constants.generateLocations(53, 40));
	}
	@Test 
	public void testLink() {

	}
	
	public int testLevels(TreeNode node, int count){
		if (node.isLeaf())
			return count;
		int current = testLevels(((TreeBranch) node).getChildren()[0],count);
		for(TreeNode child : ((TreeBranch) node).getChildren()){
			if (current != testLevels(child, count)){
				return -1;
			}
		}
		return current;

	}
}
