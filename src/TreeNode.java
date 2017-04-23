
/**
 * Created by Andrew on 3/29/17.
 */

/**
 * This class represents an individual node in our binary tree. Members are left, right and value. Eseentially, this
 * is a POJO class that contains the getters and setters of the members. No logic is contained in this class.
 */
public class TreeNode {

    /**
     * The left node of the current node
     */
    private TreeNode left;

    /**
     * The right node the current node
     */
    private TreeNode right;

    /**
     * The value contained in this node
     */
    private Integer value;

    private int balanceFactor; //this represents our balance factor for a given node
    /**
     * Get the balance factor of the current node
     * @return int the balance factor of the current node
     */
    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    /**
     * Default, empty constructor
     */
    public TreeNode() {
    }

    /**
     * This constructor allows us to instanciate a node and pass in a value contained in this node
     * @param value the value we want to pass into this node
     */
    public TreeNode(Integer value) {
        this.value = value;

    }

    public TreeNode(Integer value, int balanceFactor) {
        this.value = value;
        this.balanceFactor = balanceFactor;

    }

    /**
     * Get the left child node of the current node
     * @return the left child node of the current node
     */
    public TreeNode getLeft() {
        return left;
    }

    /**
     * Set the left child node of the current node
     * @param left the new node to add
     */
    public void setLeft(TreeNode left) {
        //just some debugging if we are adding the values incorrectly
//        if((left != null) && left.getValue() >= this.getValue())
//            System.out.println("WARNING: You are adding a value incorrectly (left) to node ");
        this.left = left;
    }

    /**
     * Get the right node of the current node
     * @return the right node of the current node
     */
    public TreeNode getRight() {
        return right;
    }

    /**
     * Set the right node of the current node
     * @param right the new node to add to the current node at the right child position
     */
    public void setRight(TreeNode right) {
        //just some debugging if we are adding the values incorrectly
//        if((right != null) && right.getValue() < this.getValue())
//            System.out.println("WARNING: You are adding a value incorrectly (right) to node "+this.getValue());

        this.right = right;
    }

    /**
     * Get the value stored in the current node
     * @return the value stored in the current node
     */
    public Integer getValue() {
        return value;
    }

    /**
     * set the value of the current node
     * @param value the new value of the node
     */
    public void setValue(Integer value) {
        this.value = value;
    }
}