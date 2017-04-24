import java.util.ArrayList;


/**
 * This is our main test runner class. Main method will generate a random sequence of ints and do some standard testing operations on that file,
 * such as inserting values into our avl tree like insertion, searching and tree traversal.
 */
public class Main {

    public static void main(String[] args) {
        //create our AVL tree
        AVLTree tree = new AVLTree();
        //get something to store our random ints
        ArrayList<Integer> numbers = new ArrayList<>();
        //iterate for the number of random ints we want
        for (int i = 0; i < 200; i++) {
            //generate the random ints and add htem to our array list
            numbers.add(0 + (int) (Math.random() * ((10000 - 0) + 1)));
        }

        for (int i : numbers) {
            tree.insert(i);
        }
        //preorder
        System.out.print("Pre-Order: ");
        tree.preorder(tree.root);
        System.out.println();

        //inorder
        System.out.print("InOrder: ");
        tree.inorder(tree.root);
        System.out.println();

        //Now the postorder styff
        System.out.print("Post-Order: ");
        tree.postorder(tree.root);
        System.out.println();

        /**
         * Next, search for every fifth element of the array. Some of these won’t
         be found. The result of a search should merely output if the value was found or not
         */
        for (int i = 0; i < numbers.size(); i = i + 5) {
            AVLNode result = tree.search(numbers.get(i), tree.root);
            System.out.println(numbers.get(i) + ((result == null) ? " Not Found" : " Found"));
        }

        //preorder
        System.out.print("Pre-Order: ");
        tree.preorder(tree.root);
        System.out.println();

        //inorder
        System.out.print("InOrder: ");
        tree.inorder(tree.root);
        System.out.println();

        //Now the postorder styff
        System.out.print("Post-Order: ");
        tree.postorder(tree.root);
        System.out.println();


    }


}