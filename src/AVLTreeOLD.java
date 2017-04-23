import java.util.ArrayList;

/**
 * Created by Andrew on 3/29/17.
 */

/**
 * This class represents a binary tree. standard methods are  insert, serach, delete, etc. Some functions, such as insert and delete,
 * include helper functions, named by the function name plus 'recursive'. Essentially, binary tree is a tree in which each node
 * only has 2 children, hte left being < the value at the node and the right value being  >= the value at the node
 */
public class AVLTreeOLD {


    //todo  Get balanceFactor: compute and return the balanceFactor of the tree

    protected TreeNode root;
    private int size;




    /**
     * This is our empty constructor which allows us to
     * create an empty binary tree
     */
    public AVLTreeOLD() {
        size = 0;
    }

    /**
     * This is our insertion starter function. This method calls insertrecursive, which is our actual tree traversal
     *
     * @param value
     */
    public void insert(int value) {
        if(this.root == null){
            this.root = new TreeNode(value, 0);
        }else{
            this.root = insertRecursive(new TreeNode(value),  this.root); //do the insertion and update the tree
        }
        size++; //increment the size
    }

    /**
     * This is our recursive insertion function. This will be called by our actual insertRecursive() function. Traverse the tree
     * and try to determine where to place the value
     *
     * @param tree  the starting tree or subtree
     * @param value the value to insertRecursive
     * @return the updated tree
     */
    public TreeNode insertRecursive(TreeNode newNode, TreeNode par) {

        TreeNode newpar = par;  // root of subtree par

        if (newNode.getValue() < par.getValue())
        {
            if (par.getLeft() == null)
            {

                par.setLeft( newNode);  //attach new node as leaf

            }
            else {

                par.setLeft(insertRecursive(newNode, par.getLeft()));   // branch left

                if ((height(par.getLeft()) - height(par.getRight())) == 2) {

                    if (newNode.getValue() < par.getLeft().getValue()) {

                        newpar=leftRotation(par);

                    }
                    else {

                        newpar=leftRightRotation(par);

                    } //else
                } //if
            } // else
        } // if

        else if (newNode.getValue() > par.getValue())  // branch right
        {
            if (par.getRight() == null)
            {

                par.setRight(newNode);   // attach new node as leaf

            }
            else {

                par.setRight(insertRecursive(newNode, par.getRight()));  // branch right

                if ((height(par.getRight()) - height(par.getLeft())) == 2) {

                    if (newNode.getValue()> par.getRight().getValue()) {

                        newpar=rightRotation(par);


                    } //if
                    else {

                        newpar=rightLeftRotation(par);

                    } //else
                } //if
            } //else
        }  // else if

        else System.out.println("Duplicate Key");

        // Update the balanceFactor, just in case

        if ((par.getLeft() == null) && (par.getRight()!= null))
            par.setBalanceFactor(par.getRight().getBalanceFactor()+ 1);
        else if ((par.getRight() == null) && (par.getLeft()!= null))
            par.setBalanceFactor(par.getLeft().getBalanceFactor()+ 1);
        else
            par.setBalanceFactor(Math.max(height(par.getLeft()), height(par.getRight())) + 1);

        return newpar; // return new root of this subtree

//
//        //if we are working with a null tree, go ahead and drop the new value at the top
//        if (tree == null) {
//            TreeNode node = new TreeNode(value);
//            //set the balance factor. The difference between the left and right subtree
//            node.setBalanceFactor(balanceFactor(node.getLeft())- balanceFactor(node.getRight()));
//            node.setParent(parent);
//            return node;
//        }
//        //otherwise, logically determine whether we should inch down to the left or the right
//        else if (value < tree.getValue()) {
//            //set the left subtree to the final result. essentially, this recursion will ultimately return the whole
//            //binary tree
//            tree.setLeft(insertRecursive(tree, tree.getLeft(), value));
//            tree.setBalanceFactor(balanceFactor(tree.getLeft())- balanceFactor(tree.getRight()));
//            tree = balance(tree);
//            return tree;
//        } else {
//            //set the right subtree and recurse to the final result. essentially, this recursion will ultimately return the whole
//            //binary tree
//            tree.setRight(insertRecursive(tree, tree.getRight(), value));
//            tree.setBalanceFactor(balanceFactor(tree.getLeft())- balanceFactor(tree.getRight()));
//            tree = balance(tree);
//            return tree;
//        }
    }


    /**
     * This function will do our rebalancing after adding
     * @param node
     */
    public TreeNode balance(TreeNode node){
        //this case tells us that the tree is left heavy
        if (node.getBalanceFactor() == -2){
            if(height(node.getLeft()) >= height(node.getRight())){
                node = rightRotation(node);
            }else{
                node = leftRightRotation(node);
            }
        }else if(node.getBalanceFactor() == 2){
            if(height(node.getRight()) >= height(node.getLeft())){
                node = leftRotation(node);
            }else{
                node = rightLeftRotation(node);
            }
        }
        if (node.getParent() != null) {
            balance(node.getParent());
        } else {
            root = node;
        }
        return node;
    }


    /**
     * This is our delete starter method, this method will call the deleteRecursive method, we use this as a starter method
     * and pass in the root of our binary tree and our value to insert
     *
     * @param value the value we want to delete
     */
    public void delete(int value) {
        //update the binary tree as a whole
        this.root = this.deleteRecursive(value, this.root); //the value to delete into the binary tree
        size--;//decrease the size

    }

    /**
     * This is our  actual delete function. Recursively walk down the tree to search the value to delete and remove it
     *
     * @param value the value we wish to deleteRecursive
     * @param node  the starting node
     * @return the tree node we have deleted
     */
    public TreeNode deleteRecursive(int value, TreeNode node) {
        //if we get down here, the value is not in the tree, return null and print a message
        if (node == null) {
            System.out.println("AVLTreeOLD does not contain " + value);
            return null;
        }
        //otherwise, if the tree contains the node
        else if (node.getValue() == value) {
            //if the left child of the node to deleteRecursive is null, them return the right
            //because we are moving the right up to the deleted node's position
            if (node.getLeft() == null) {
                return node.getRight();
            }
            //if the right child of the node to deleteRecursive is null, them return the right
            //because we are moving the left up to the deleted node's position
            else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                //if nether children are null, we need to adjust accordingly
                //start by getting the right child of the node to deleteRecursive
                TreeNode temp = node.getRight();
                //then iterate through its lefts because they will always be less than the value at node.getRight().getValue()
                while (temp.getLeft() != null) {
                    temp = temp.getLeft();
                }
                //finally, update the node we 'deleted' by setting the value of the leftmost right child
                node.setValue(temp.getValue());
                //then deleteRecursive the leftmost child of node.getRight()
                node.setRight(deleteRecursive(temp.getValue(), node.getRight()));

                return node;
            }
        } else {
            //if we make it down here, then here's the thing, we need to continue recursing
            //Just do a logical check to determine where to inch down to the left or the right depending upon how we want to
            if (value < node.getValue()) {
                node.setLeft(deleteRecursive(value, node.getLeft()));
                return node;
            } else {
                node.setRight(deleteRecursive(value, node.getRight()));
                return node;
            }
        }


    }

    /**
     * This is our recursive balanceFactor traversal function, this one will traverse the nodes recursively and
     * return the farthest depth we've reached
     *
     * @param node the root of the tree (or subtree) we are starting with
     * @return int the depth of the tree (or subtree)
     */
    int height(TreeNode node) {
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
     * This is our recursive inorder traversal.
     * basically recurse left, visit the starting node, then recurse right, should get our values in order
     *
     * @param node  the starting node
     * @param items the
     */
    public void inorder(TreeNode node, ArrayList<Integer> items) {
        //if the node is not null, we can recurse
        if (node != null) {
            //do the recursive left
            inorder(node.getLeft(), items);
            //addd the items we have visted
            items.add(node.getValue());

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
    public void preorder(TreeNode node, ArrayList<Integer> items) {
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
    public void postorder(TreeNode node, ArrayList<Integer> items) {
        //if the node is not null, we can recurse
        if (node != null) {
            //first hit all hte lefts
            postorder(node.getLeft(), items);
            //then hit all the rights,
            postorder(node.getRight(), items);
            //finally, visit the node
            items.add(node.getValue());

        }
    }


    /**
     * This is our recursive search function, this functino will traverse the nodes to search the value we are looking for,
     * returning nill otherwise
     *
     * @param value the value we are searching for
     * @param node  the starting node
     * @return
     */
    public TreeNode search(int value, TreeNode node) {
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
     * This method allows us to get the size (number of elements) of the binary tree
     *
     * @return the size of the binary tree
     */
    public int getSize() {
        return size;
    }


    /**
     * This method will perform a left rotate on an unbalanced right tree
     * @param node
     */
    public TreeNode leftRotation(TreeNode node){
       TreeNode child = node.getLeft();
        node.setLeft(child.getRight());
        child.setRight(node);

//        TreeNode b = node.getRight();
//        b.setParent(node.getParent());
//        node.setRight(b.getLeft());
//        if(node.getRight() != null)
//            node.getRight().setParent(node);
//
//        b.setLeft(node);
//        node.setParent(b);
//
//        if(b.getParent()!= null){
//            if(b.getParent().getRight() == node){
//                b.getParent().setRight(b);
//            }else{
//                b.getParent().setLeft(b);
//            }
//        }

        node.setBalanceFactor(height(node.getLeft())- height(node.getRight()));

        child.setBalanceFactor(height(child.getLeft())- height(child.getRight()));
     return child;
    }

    /**
     * This method will perform a right rotation on an unbalanced let tree
     * @param node
     */
    public TreeNode rightRotation(TreeNode node){
        TreeNode child = node.getRight();
        node.setRight(child.getLeft());
        child.setLeft(node);
//        TreeNode oldParent= node;
//        node = node.getLeft();
//        oldParent.setRight(node.getLeft());
//        node.setRight(oldParent);
//        TreeNode b = node.getLeft();
//
//        b.setParent(node.getParent());
//
//        node.setLeft(b.getRight());
//
//        if (node.getLeft()!= null)
//            node.getLeft().setParent( node);
//
//        b.setRight(node);
//        node.setParent(b);
//
//        if (b.getParent() != null) {
//            if (b.getParent().getRight() == node) {
//                b.getParent().setRight(b);
//            } else {
//                b.getParent().setLeft(b);
//            }
//        }

        node.setBalanceFactor(height(node.getLeft())- height(node.getRight()));
        child.setBalanceFactor(height(child.getLeft())- height(child.getRight()));
        return child;
    }

    /**
     * This method will perform a left right rotation on an unbalanced tree
     * @param node the parent node in which we would like to rotate
     */
    public TreeNode leftRightRotation(TreeNode node){
        //perform right rotation first
        node = rightRotation(node);
        // then the left
        return leftRotation(node);


    }

    public TreeNode rightLeftRotation(TreeNode node){
        //perform first the left rotation
        node = leftRotation(node);
        //then perform the right
        return rightRotation(node);
    }
}

