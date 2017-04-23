// demonstrates AVL tree

class AVLNode
{
    public int value;              // data item (key)
    public int balanceFactor;             // balanceFactor of node
    public AVLNode left;         // this node's left child
    public AVLNode right;        // this node's right child

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }

    public AVLNode getLeft() {
        return left;
    }

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public AVLNode getRight() {
        return right;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }

    public AVLNode(int id) 			// constructor
    {
        value = id;
        balanceFactor = 0;
        left = null;
        right = null;
    }

}  // end class AVLNode
