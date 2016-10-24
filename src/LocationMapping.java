
public class LocationMapping
{

    public TreeBranch head;

    public LocationMapping(TreeLeaf[] leaves)
    {
        this.head = new TreeBranch();
        for (TreeNode leaf : leaves)
        {
            this.head = this.head.insert(leaf);
        }
    }

    public void addLeaf(TreeLeaf leaf)
    {
        this.head = this.head.insert(leaf);
    }

    /*public TreeBranch SearchChildren(TreeBranch branch, TreeNode query)
    {
        // TODO
        return null;
    }*/
}
