
public class TreeLeaf implements TreeNode
{
    private Point point;
    private TreeBranch parent;

    public TreeLeaf(Point point, TreeBranch parent)
    {
        this.parent = parent;
        this.point = point;
    }

    public TreeLeaf(Point point)
    {
        this(point, null);
    }

    public Point getPoint()
    {
        return point;
    }

    @Override
    public boolean isLeaf()
    {
        return true;
    }

    public double distance(TreeLeaf that)
    {
        return this.point.distance(that.point);
    }

    @Override
    public boolean isFull()
    {
        return true;
    }

    @Override
    public boolean hasMin()
    {
        return true;
    }

    @Override
    public boolean contains(TreeNode that)
    {
        return false;
    }

    @Override
    public TreeBranch getParent()
    {
        return parent;
    }

    @Override
    public void setParent(TreeBranch parent)
    {
        this.parent = parent;

    }

    @Override
    public Rect getBoundingRect()
    {
        return new Rect(this.point.getX(), this.point.getY(), 0, 0);
    }
}
