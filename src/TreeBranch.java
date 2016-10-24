
public class TreeBranch implements TreeNode
{
    private TreeBranch parent;
    private TreeNode[] children;
    private Rect boundingRect;
    private int numChildren = 0;
    private int current;

    public TreeBranch(TreeNode[] children)
    {
        this(children, null);
    }

    public TreeBranch(TreeNode[] children, TreeBranch parent)
    {
        this.parent = parent;
        this.children = children;
        for (TreeNode child : children)
        {
            if (child != null)
            {
                this.numChildren++;
            }
        }
        current = -1;
        // compute bounding rect
        double minX = Constants.WINDOW_WIDTH, maxX = 0, minY = Constants.WINDOW_HEIGHT, maxY = 0;
        for (TreeNode child : children)
        {
            if (child instanceof TreeLeaf)
            {
                Point p = child.getPoint();
                minX = Math.min(minX, p.getX());
                maxX = Math.max(maxX, p.getX());
                minY = Math.min(minY, p.getY());
                maxY = Math.max(maxY, p.getY());
            } else if (child != null)
            {
                Rect r = ((TreeBranch) child).getBoundingRect();
                minX = Math.min(minX, r.getX());
                maxX = Math.max(maxX, r.getX() + r.getWidth());
                minY = Math.min(minY, r.getY());
                maxY = Math.max(maxY, r.getY() + r.getHeight());
            }
        }
        this.boundingRect = new Rect(minX, minY, maxX - minX, maxY - minY);
    }

    public TreeBranch()
    {
        this(new TreeNode[Constants.NODE_CHILDREN_MAX]);
    }

    public Rect getBoundingRect()
    {
        return this.boundingRect;
    }

    public Rect getBoundingRectWithAdditionalNode(TreeNode node)
    {
        double minX, maxX, minY, maxY;
        minX = Math.min(this.boundingRect.getX(), node.getBoundingRect().getX());
        minY = Math.min(this.boundingRect.getY(), node.getBoundingRect().getY());
        maxX = Math.max(this.boundingRect.getX() + this.boundingRect.getWidth(), node.getBoundingRect().getX() + node.getBoundingRect().getWidth());
        maxY = Math.max(this.boundingRect.getY() + this.boundingRect.getHeight(), node.getBoundingRect().getY() + node.getBoundingRect().getHeight());
        return new Rect(minX, minY, maxX-minX, maxY-minY);
    }

    public TreeBranch insert(TreeNode node)
    {
        TreeBranch returnVal = null;
        for (int i = 0; i < this.children.length; i++)
        {
            if (this.children[i] != null && this.children[i].contains(node))
            {
                this.children[i] = ((TreeBranch)this.children[i]).insert(node);
                returnVal = this;
                double minX = Constants.WINDOW_WIDTH, maxX = 0, minY = Constants.WINDOW_HEIGHT, maxY = 0;
                for (TreeNode child : returnVal.children)
                {
                    if (child instanceof TreeLeaf)
                    {
                        Point p = child.getPoint();
                        minX = Math.min(minX, p.getX());
                        maxX = Math.max(maxX, p.getX());
                        minY = Math.min(minY, p.getY());
                        maxY = Math.max(maxY, p.getY());
                    } else if (child != null)
                    {
                        Rect r = ((TreeBranch) child).getBoundingRect();
                        minX = Math.min(minX, r.getX());
                        maxX = Math.max(maxX, r.getX() + r.getWidth());
                        minY = Math.min(minY, r.getY());
                        maxY = Math.max(maxY, r.getY() + r.getHeight());
                    }
                }
                returnVal.boundingRect = new Rect(minX, minY, maxX - minX, maxY - minY);
                return returnVal;
            }
        }
        if (this.allChildrenPresent())
        {
            returnVal = this.splitNode(node);
            double minX = Constants.WINDOW_WIDTH, maxX = 0, minY = Constants.WINDOW_HEIGHT, maxY = 0;
            for (TreeNode child : returnVal.children)
            {
                if (child instanceof TreeLeaf)
                {
                    Point p = child.getPoint();
                    minX = Math.min(minX, p.getX());
                    maxX = Math.max(maxX, p.getX());
                    minY = Math.min(minY, p.getY());
                    maxY = Math.max(maxY, p.getY());
                } else if (child != null)
                {
                    Rect r = ((TreeBranch) child).getBoundingRect();
                    minX = Math.min(minX, r.getX());
                    maxX = Math.max(maxX, r.getX() + r.getWidth());
                    minY = Math.min(minY, r.getY());
                    maxY = Math.max(maxY, r.getY() + r.getHeight());
                }
            }
            returnVal.boundingRect = new Rect(minX, minY, maxX - minX, maxY - minY);
            return returnVal;
        } else
        {
            for (int i = 0; i < this.children.length; i++)
            {
                if (this.children[i] == null)
                {
                    this.children[i] = node;
                    returnVal = this;
                    double minX = Constants.WINDOW_WIDTH, maxX = 0, minY = Constants.WINDOW_HEIGHT, maxY = 0;
                    for (TreeNode child : returnVal.children)
                    {
                        if (child instanceof TreeLeaf)
                        {
                            Point p = child.getPoint();
                            minX = Math.min(minX, p.getX());
                            maxX = Math.max(maxX, p.getX());
                            minY = Math.min(minY, p.getY());
                            maxY = Math.max(maxY, p.getY());
                        } else if (child != null)
                        {
                            Rect r = ((TreeBranch) child).getBoundingRect();
                            minX = Math.min(minX, r.getX());
                            maxX = Math.max(maxX, r.getX() + r.getWidth());
                            minY = Math.min(minY, r.getY());
                            maxY = Math.max(maxY, r.getY() + r.getHeight());
                        }
                    }
                    returnVal.boundingRect = new Rect(minX, minY, maxX - minX, maxY - minY);
                    return returnVal;
                }
            }
        }
        // compute new bounding rectangle
        
        
        return returnVal;
    }

    private TreeBranch splitNode(TreeNode node)
    {
        TreeBranch test = new TreeBranch();
        test.children = new TreeNode[Constants.NODE_CHILDREN_MAX+1];
        for (int i = 0; i < Constants.NODE_CHILDREN_MAX; i++)
        {
            assert(test.children[i] != null);
            test.children[i] = this.children[i];
        }
        test.children[Constants.NODE_CHILDREN_MAX] = node;
        // find the two nodes that have the greatest preference for not being combined
        TreeNode node1 = children[0], node2 = children[1];
        double maxDeltaArea = 1.0;
        for (int i = 0; i < test.children.length; i++)
        {
            for (int j = 0; j < test.children.length; j++)
            {
                if (i != j)
                {
                    double combinedArea;
                    if (test.children[i].isLeaf() && test.children[j].isLeaf())
                    {
                        combinedArea = Math.abs(((((TreeLeaf)test.children[i]).getPoint()).getX() - (((TreeLeaf)test.children[j]).getPoint()).getX())*((((TreeLeaf)test.children[i]).getPoint()).getY() - (((TreeLeaf)test.children[j]).getPoint()).getY()));
                    } else if (test.children[i].isLeaf())
                    {
                        combinedArea = ((TreeBranch)test.children[j]).getBoundingRectWithAdditionalNode(test.children[i]).getArea();
                    } else
                    {
                        combinedArea = ((TreeBranch)test.children[i]).getBoundingRectWithAdditionalNode(test.children[j]).getArea();
                    }
                    double deltaArea = combinedArea - test.children[i].getBoundingRect().getArea() - test.children[j].getBoundingRect().getArea();
                    if (deltaArea > maxDeltaArea)
                    {
                        maxDeltaArea = deltaArea;
                        node1 = test.children[i];
                        node2 = test.children[j];
                    }
                }
            }
        }
        
        // now add the other nodes to the branches we'll make
        TreeBranch branch1 = new TreeBranch(), branch2 = new TreeBranch();
        branch1.insert(node1);
        branch2.insert(node2);
        for (TreeNode child : test.children)
        {
            if (child != node1 && child != node2)
            {
                if (branch1.getBoundingRectWithAdditionalNode(child).getArea() < branch2.getBoundingRectWithAdditionalNode(child).getArea())
                {
                    branch1.insert(child);
                } else
                {
                    branch2.insert(child);
                }
            }
        }
        TreeBranch returnVal = new TreeBranch();
        returnVal.children[0] = branch1;
        returnVal.children[1] = branch2;
        return returnVal;
    }
    
    public void remove(TreeNode node)
    {
        for (int i = 0; i < this.children.length; i++)
        {
            if (this.children[i] == node)
            {
                this.children[i] = null;
                return;
            }
        }
        for (int i = 0; i < this.children.length; i++)
        {
            if (this.children[i] != null && this.children[i] instanceof TreeBranch)
            {
                ((TreeBranch)children[i]).remove(node);
            }
        }
    }

    public boolean isEmpty()
    {
        for (TreeNode child : this.children)
        {
            if (child != null)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isFull()
    {
        if (allChildrenPresent())
        {
            for (TreeNode child : this.children)
            {
                if (child == null)
                {
                    return false;
                } else
                {
                    if (child instanceof TreeBranch)
                    {
                        if (!((TreeBranch) child).isFull())
                        {
                            return false;
                        }
                    }
                }
            }
        } else
        {
            return false;
        }
        return true;
    }

    public boolean hasMin()
    {
        return this.numChildren >= Constants.NODE_CHILDREN_MIN;
    }

    public boolean allChildrenPresent()
    {
        for (TreeNode child : this.children)
        {
            if (child == null)
            {
                return false;
            }
        }
        return true;
    }

    public TreeNode[] getChildren()
    {
        return children;
    }

    public TreeBranch getParent()
    {
        return parent;
    }

    @Override
    public boolean isLeaf()
    {
        return false;
    }

    @Override
    public boolean contains(TreeNode that)
    {
        for (TreeNode child : this.children)
        {
            if (child == that)
            {
                return true;
            }
        }
        for (TreeNode child : this.children)
        {
            if (child != null)
            {
                if (child.contains(that))
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Point getPoint()
    {
        Rect bound = getBoundingRect();
        return new Point(bound.getX() + bound.getWidth() / 2, bound.getY() + bound.getWidth() / 2);
    }

    @Override
    public void setParent(TreeBranch parent)
    {
        this.parent = parent;

    }
}
