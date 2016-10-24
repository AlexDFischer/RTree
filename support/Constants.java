import java.util.Random;

public class Constants {

	public final static int WINDOW_WIDTH = 500;
	public final static int WINDOW_HEIGHT = 500;
	public final static int NODE_CHILDREN_MAX = 2;
	public final static int NODE_CHILDREN_MIN = 1;

	public static TreeLeaf[] generateLocations(int seed, int length) {
		TreeLeaf[] results = new TreeLeaf[length];
		Random random = new Random(seed);
		for(int i = 0; i < length; i ++){
			double x = random.nextDouble();
			double y = random.nextDouble();
			results[i] = new TreeLeaf(new Point(x, y));
		}
		return results;
	}
}
