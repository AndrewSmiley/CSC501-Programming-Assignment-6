import java.util.ArrayList;

class AVLTree {
    protected AVLNode root;             // first node of tree

    // -------------------------------------------------------------
    public AVLTree()                  // constructor
    {
        root = null;
    }            // no nodes in tree yet
// -------------------------------------------------------------

    /**
     * Get the root node
     * @return
     */
    public AVLNode getRoot() {
        return root;
    }

//--------------------------------------------------------------

    /**
     * This is our recursive search function, this function will traverse the nodes to find the value we are looking for,
     * returning nill otherwise
     *
     * @param value the value we are searching for
     * @param node  the starting node
     * @return
     */
    public AVLNode search(int value, AVLNode node) {
        //if the node we are currently on is not null, check to see if the value exists further down
        if (node != null) {
            //if we've found the value, go ahead and return it
            if (value == node.getValue()) {
                return node;
            } else if (value < node.getValue()) { //otherwise check if hte vlaue falls less than the node we are on
                //if it does, we know that we should search to the left
                return search(value, node.getLeft());
            } else {
                //otherwise, we should look the right
                return search(value, node.getRight());
            }
        }
        //if we get down here, we know that the node does not exist.
        return null;

    }

// -------------------------------------------------------
    /**
     * This is our recursive height traversal function, this one will traverse the nodes recursively and
     * return the farthest depth we've reached
     *
     * @param node  the root of the tree (or subtree) we are starting with
     * @return int the depth of the tree (or subtree)
     */
    int height(AVLNode node)
    {
        //base case
        if (node == null)
            return 0;
        else
        {
            //get the depth of the left and right subtrees
            //tired using max(), but some strange values came up
            int leftDepth = height(node.getLeft());
            int rightDepth = height(node.getRight());

            //basically determine which is deeper, the left or right subtrees
            //increment and return
            if (leftDepth > rightDepth)
                return leftDepth + 1;
            else
                return rightDepth + 1;
        }
    }

    /**
     * This is our left rotate function. We pass in an unbalanced subtree and left rotate to attempt to rebalance is
     * @param node the root of the subtree
     * @return AVLNode the new root
     */
    public AVLNode leftRotate(AVLNode node) {

        //first, get the left child
        AVLNode leftChild = node.left;// left child of the node
        //adjust the left child of the parent node
        node.left = leftChild.right;
        //adjust the right child of the pivot node
        leftChild.right = node;

        // finally, we need to fix the balance factors
        node.balanceFactor = height(node.left)- height(node.right);//Math.max(height(c.left), height(c.right)) + 1;
        leftChild.balanceFactor = height(leftChild.left)- height(leftChild.right);//Math.max(height(p.left), height(p.right)) + 1;
        //return the left child of the parent node to replace the parent node
        return leftChild;

    }

    /**
     * This is our right rotate method. Take the right child of an unbalanced root node, move it to the root and rotate teh
     * original root to the right
     * @param node the unbalanced parent node of hte subtree
     * @return AVLNode the new head of the subtree
     */
    public AVLNode rightRotate(AVLNode node) {

        //get the right
        AVLNode rightChild = node.right;
        //update the child of the parent noe
        node.right = rightChild.left;
        //update the right child's left node
        rightChild.left = node;

        //finally, we simply update the balances of the nodes
        node.balanceFactor =height(node.left)- height(node.right);// Math.max(height(node.left), height(node.right)) + 1;
        rightChild.balanceFactor = height(rightChild.left)- height(rightChild.right);//Math.max(height(rightChild.left), height(rightChild.right)) + 1;

        //return the fhild
        return rightChild;

    }


    /**
     * This is our left right rotate, perform a right rotate first, followed by a left
     * @param node the unbalanced parent node we need to rotate
     * @return the new parent node
     */
    public AVLNode doublerotatewithleft(AVLNode node) {

        //perform the right rotate
        node.left = rightRotate(node.left);
        //return the result of the left rotate
        return leftRotate(node);

    }


    /**
     *
     * @param c
     * @return
     */
    public AVLNode doublerotatewithright(AVLNode c) {

        AVLNode tmp;

        c.right = leftRotate(c.right);
        tmp = rightRotate(c);

        return tmp;

    }


    public AVLNode avlinsert(AVLNode newNode, AVLNode par) {

        AVLNode newpar = par;  // root of subtree par

        if (newNode.value < par.value) {
            if (par.left == null) {

                par.left = newNode;  //attach new node as leaf

            } else {

                par.left = avlinsert(newNode, par.left);   // branch left

                if ((height(par.left) - height(par.right)) == 2) {

                    if (newNode.value < par.left.value) {

                        newpar = leftRotate(par);

                    } else {

                        newpar = doublerotatewithleft(par);

                    } //else
                } //if
            } // else
        } // if

        else if (newNode.value > par.value)  // branch right
        {
            if (par.right == null) {

                par.right = newNode;   // attach new node as leaf

            } else {

                par.right = avlinsert(newNode, par.right);  // branch right

                if ((height(par.right) - height(par.left)) == 2) {

                    if (newNode.value > par.right.value) {

                        newpar = rightRotate(par);


                    } //if
                    else {

                        newpar = doublerotatewithright(par);

                    } //else
                } //if
            } //else
        }  // else if

        else System.out.println("Duplicate Key");

        // Update the balanceFactor, just in case

        if ((par.left == null) && (par.right != null))
            par.balanceFactor = par.right.balanceFactor + 1;
        else if ((par.right == null) && (par.left != null))
            par.balanceFactor = par.left.balanceFactor + 1;
        else
            par.balanceFactor = height(par.left)- height(par.right);//Math.max(height(par.left), height(par.right)) + 1;

        return newpar; // return new root of this subtree

    }  // end avlinsert

    // -------------------------------------------------------------
    public void insert(int value) // Recursive insert
    {
        AVLNode newNode = new AVLNode(value);    // make new node

        if (root == null){
//            newNode.setBalanceFactor(1);
            root = newNode;
        }
        else {

            root = avlinsert(newNode, root);

        }
    }  // end insert()

    /**
     * This is our recursive inorder traversal.
     * basically recurse left, visit the starting node, then recurse right, should get our values in order
     *
     * @param node  the starting node
     * @param items the
     */
    public void inorder(AVLNode node, ArrayList<Integer> items) {
        //if the node is not null, we can recurse
        if (node != null) {
            //do the recursive left
            inorder(node.getLeft(), items);
            //addd the items we have visted
            items.add(node.getBalanceFactor());

            //recurse right
            inorder(node.getRight(), items);
        }
    }

    /**
     * This is our recursive preorder method. This method will visit the starting node first, (i.e. add it to the items visited)
     * then recursively visit left, followed by recursively visit right
     *
     * @param node  the starting node
     * @param items the 'nodes' we have visited
     */
    public void preorder(AVLNode node, ArrayList<Integer> items) {
        //if the node is not null, we can recurse
        if (node != null) {
            //add the node we are currently on as visited
            items.add(node.getBalanceFactor());
            //recurse to the left
            preorder(node.getLeft(), items);
            //recurse to the right
            preorder(node.getRight(), items);
        }
    }

    /**
     * Thisis our recursive postorder method. This method will recurse ot the left first, then recurse to the right,
     * finally visiting the node itself
     *
     * @param node  the starting node
     * @param items the 'nodes' we have visited
     */
    public void postorder(AVLNode node, ArrayList<Integer> items) {
        //if the node is not null, we can recurse
        if (node != null) {
            //first hit all hte lefts
            postorder(node.getLeft(), items);
            //then hit all the rights,
            postorder(node.getRight(), items);
            //finally, visit the node
            items.add(node.getBalanceFactor());

        }
    }

}  // end class AVLTree
 // end class TreeApp