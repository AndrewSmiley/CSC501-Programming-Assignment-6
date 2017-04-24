/**
 * This class represents an individual node in our binary avl tree. Members are left, right and value. Eseentially, this
 * is a POJO class that contains the getters and setters of the members. No logic is contained in this class.
 */
public class AVLNode {

    /**
     * The left node of the current node
     */
    protected AVLNode left;

    /**
     * The right node the current node
     */
    protected AVLNode right;

    /**
     * The value contained in this node
     */
    protected Integer value;

    /**
     * The balance factor of the node
     */
    protected int balanceFactor;

    /**
     * Default, empty constructor
     */
    public AVLNode() {
    }


    /**
     * This constructor allows us to instanciate a node and pass in a value contained in this node
     * @param value the value we want to pass into this node
     */
    public AVLNode(Integer value) {
        this.value = value;

    }

    /**
     * Get the left child node of the current node
     * @return the left child node of the current node
     */
    public AVLNode getLeft() {
        return left;
    }

    /**
     * Set the left child node of the current node
     * @param left the new node to add
     */
    public void setLeft(AVLNode left) {
        //just some debugging if we are adding the values incorrectly
//        if((left != null) && left.getValue() >= this.getValue())
//            System.out.println("WARNING: You are adding a value incorrectly (left) to node ");
        this.left = left;
    }

    /**
     * Get the right node of the current node
     * @return the right node of the current node
     */
    public AVLNode getRight() {
        return right;
    }

    /**
     * Get hte balance factor of the node
     * @return the balance factor of the node
     */
    public int getBalanceFactor() {
        return balanceFactor;
    }

    /**
     * Set the balance factor of hte node
     * @param balanceFactor the balance factor of the node to set
     */
    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    /**
     * Set the right node of the current node
     * @param right the new node to add to the current node at the right child position
     */
    public void setRight(AVLNode right) {
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