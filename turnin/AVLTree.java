
/**
 *This is our AVL Tree class. Contains members for the root and size of the tree. For insertions, we automatically rebalance
 * the tree as we traverse. Includes standard preorder, inorder and postorder insertions
 */
class AVLTree {
    /**
     * This is our root node of our tree
     */
    protected AVLNode root;
    /**
     * Just store the size of the ree
     */
    protected int size;

    /**
     * Default no arg constructor,
     */
    public AVLTree()
    {
        root = null;
    }
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

    /**
     * This is our recursive height traversal function, this one will traverse the nodes recursively and
     * return the farthest depth we've reached
     *
     * @param node the root of the tree (or subtree) we are starting with
     * @return int the depth of the tree (or subtree)
     */
    int height(AVLNode node) {
        //base case
        if (node == null)
            return 0;
        else {
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
     *
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
        node.balanceFactor = height(node.left) - height(node.right);//Math.max(height(c.left), height(c.right)) + 1;
        leftChild.balanceFactor = height(leftChild.left) - height(leftChild.right);//Math.max(height(p.left), height(p.right)) + 1;
        //return the left child of the parent node to replace the parent node
        return leftChild;

    }

    /**
     * This is our right rotate method. Take the right child of an unbalanced root node, move it to the root and rotate teh
     * original root to the right
     *
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
        node.balanceFactor = height(node.left) - height(node.right);// Math.max(height(node.left), height(node.right)) + 1;
        rightChild.balanceFactor = height(rightChild.left) - height(rightChild.right);//Math.max(height(rightChild.left), height(rightChild.right)) + 1;

        //return the child
        return rightChild;

    }


    /**
     * This is our left right rotate, perform a right rotate first, followed by a left
     *
     * @param node the unbalanced parent node we need to rotate
     * @return the new parent node
     */
    public AVLNode leftRightRotate(AVLNode node) {

        //perform the right rotate
        node.left = rightRotate(node.left);
        //return the result of the left rotate
        return leftRotate(node);

    }


    /**
     * this is our right left rotate function. Essentially, perform a left rotate first, followed by a right rotate
     *
     * @param node the parent node of an unbalanced subtree we need to rotate
     * @return AVLNode the new head of an unbalanced subtree
     */
    public AVLNode righLeftRotate(AVLNode node) {
        //update the right child of the node
        node.right = leftRotate(node.right);
        //then return hte result of the right rotate
        return rightRotate(node);
    }


    /**
     * This is our recursive insertion function. Essentially, traverse the nodes and rebalance the subtree(s) as needed
     * @param subNode the new node we want to add. Could just add this as value but we're too far down the rabbithole to change
     * @param parentNode the parent of the subtree we are looking for. This allows us to update references as needed. This will be null when we first iterate
     * @return AVLNode the new root of our internal subtree
     */
    public AVLNode insertRecursive(AVLNode subNode, AVLNode parentNode) {

        //save a copy of parent of the subnode
        AVLNode newParent = parentNode;

//        todo fix this ugly ass logic. There has to be a more efficient way to insert into AVL tree
        //determine whethee we should go left or right down the tree
        if (subNode.value < parentNode.value) {
            // if we go left check if we need to add the node here or not
            if (parentNode.left == null) {

                parentNode.left = subNode;
            //if not, then recurse to the left
            } else {
                // update the left child of the parent
                parentNode.left = insertRecursive(subNode, parentNode.left);

                //now we start doing some checking for balance factors
                //if the calculated BF of the parent is 2, then we know it's left heavy
                if ((height(parentNode.left) - height(parentNode.right)) == 2) {

                    //check to see whether to do a left rotate or a left right rotate based upon the value stored in
                    //our 'working node' or the left child of the parent
                    if (subNode.value < parentNode.left.value) {

                        //do the left rotate
                        newParent = leftRotate(parentNode);
                    } else {
                        //do the left right rotatet
                        newParent = leftRightRotate(parentNode);
                    }
                }
            }
            //way down here, we take the actions from branching to the right
        } else if (subNode.value > parentNode.value)  // branch right
        {
            //do the same as above, check if we can just add as a right child or not
            if (parentNode.right == null) {
                parentNode.right = subNode;

            } else {
                //if we need to keep traversing, traverse
                parentNode.right = insertRecursive(subNode, parentNode.right);  // branch right

                //now do the check again to determine if we are unbalanced or not
                if ((height(parentNode.right) - height(parentNode.left)) == 2) {

                    //if we are, then we need to right rotate or double right rotate, depending upon how the tree is stacked
                    if (subNode.value > parentNode.right.value) {

                        //do the single right rotate
                        newParent = rightRotate(parentNode);
                    }
                    else {
                        newParent = righLeftRotate(parentNode);
                    }
                }
            }
        }

        else{
            System.out.println("Trying to add a duplicate value to the tree");
        }

        // Update the balanceFactor
        if ((parentNode.left == null) && (parentNode.right != null))
           //pretty self explanatory, if it's got a right child, increase from the right
            parentNode.balanceFactor = parentNode.right.balanceFactor + 1;
        else if ((parentNode.right == null) && (parentNode.left != null))
            //pretty self explanatory, if it's got a left child, increase from the left
            parentNode.balanceFactor = parentNode.left.balanceFactor + 1;
        else
        //otherwise just do the simple calculation
            parentNode.balanceFactor = height(parentNode.left) - height(parentNode.right);

        return newParent; // return new root of this subtree

    }


    /**
     * This is our helper insert function, essentially, check if we have an empty AVL tree, if we do, then just create a new
     * root. Otherwise, what we'll do is call the recursive insertion functoin, insertRecursive.
     *
     * @param value the value we wish to insert
     */
    public void insert(int value) {
        if (root == null) {
            root = new AVLNode(value);
        } else {
            root = insertRecursive(new AVLNode(value), root);
        }
        size++;
    }  // end insert()

    /**
     * This is our recursive inorder traversal.
     * basically recurse left, visit the starting node, then recurse right, should get our values in order
     *
     * @param node  the starting node
     */
    public void inorder(AVLNode node) {
        //if the node is not null, we can recurse
        if (node != null) {
            //do the recursive left
            inorder(node.getLeft());
            //print the visited
            System.out.print(node.getValue() + " ");

            //recurse right
            inorder(node.getRight());
        }
    }

    /**
     * This is our recursive preorder method. This method will visit the starting node first, (i.e. add it to the items visited)
     * then recursively visit left, followed by recursively visit right
     * @param node  the starting node
     */
    public void preorder(AVLNode node) {
        //if the node is not null, we can recurse
        if (node != null) {
            //print the node we are currently on as visited
            System.out.print(node.getValue() + " ");

            //recurse to the left
            preorder(node.getLeft());
            //recurse to the right
            preorder(node.getRight());
        }
    }

    /**
     * Thisis our recursive postorder method. This method will recurse ot the left first, then recurse to the right,
     * finally visiting the node itself
     * @param node  the starting node
     */
    public void postorder(AVLNode node) {
        //if the node is not null, we can recurse
        if (node != null) {
            //first hit all hte lefts
            postorder(node.getLeft());
            //then hit all the rights,
            postorder(node.getRight());
            //print the node we are currently on as visited
            System.out.print(node.getValue() + " ");


        }
    }

}
