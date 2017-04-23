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

    public AVLNode search(int key, AVLNode foo)  // RECURSIVE search node with given key
    {

        if (foo == null)
            return null;
        else if (foo.value == key)
            return foo;
        else if (foo.value < key)
            return search(key, foo.right);
        else
            return search(key, foo.left);

        // return null;  // pointless return to satisfy idiot compiler

    }  // end search()

// -------------------------------------------------------

    public int height(AVLNode x)  // return balanceFactor of tree rooted at x
    {
        if (x == null) return -1;
        else return x.balanceFactor;
    }

    public AVLNode rotatewithleft(AVLNode c) {
        AVLNode p;  // left child of c

        p = c.left;
        c.left = p.right;
        p.right = c;

        c.balanceFactor = Math.max(height(c.left), height(c.right)) + 1;
        p.balanceFactor = Math.max(height(p.left), height(p.right)) + 1;

        return p;

    }

    public AVLNode rotatewithright(AVLNode c) {

        AVLNode p;  // right child of c

        p = c.right;
        c.right = p.left;
        p.left = c;

        c.balanceFactor = Math.max(height(c.left), height(c.right)) + 1;
        p.balanceFactor = Math.max(height(p.left), height(p.right)) + 1;

        return p;

    }

    public AVLNode doublerotatewithleft(AVLNode c) {

        AVLNode tmp;

        c.left = rotatewithright(c.left);
        tmp = rotatewithleft(c);

        return tmp;

    }


    public AVLNode doublerotatewithright(AVLNode c) {

        AVLNode tmp;

        c.right = rotatewithleft(c.right);
        tmp = rotatewithright(c);

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

                        newpar = rotatewithleft(par);

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

                        newpar = rotatewithright(par);


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
            par.balanceFactor = Math.max(height(par.left), height(par.right)) + 1;

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