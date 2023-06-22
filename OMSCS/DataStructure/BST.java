import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The new data should become a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Should be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to add to the tree.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data==null) {
            throw new IllegalArgumentException();
        }
        root = rAdd(root, data);
        size ++;
    }

    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (curr==null) {
            return new BSTNode<T>(data);
        }
        else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        }
        else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));            
        }
        else {
            // Do Nothing.
            size--;
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the SUCCESSOR to
     * replace the data. You should use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If data is null.
     * @throws java.util.NoSuchElementException   If the data is not in the tree.
     */
    public T remove(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data==null) {
            throw new IllegalArgumentException();
        }
        BSTNode<T> prob = new BSTNode<>(null); // a prob to retrieve the removed data
        // even though java use pass-by-value, the copy of class reference can still take the data within out of the heap
        root = rRemove(root, data, prob);
        size --;
        return prob.getData();
    }

    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> prob) {
        // target not found
        if (curr==null) {
            throw new NoSuchElementException();
        }
        // in the recursion
        else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, prob)); 
        }
        else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(curr.getRight(), data, prob)); 
        }
        // target found
        else {
            prob.setData(curr.getData());
            // no child
            if (curr.getLeft()==null && curr.getRight()==null) {
                return null;
            }
            // 1-child
            else if (curr.getLeft()==null && curr.getRight()!=null) {
                return curr.getRight();
            }
            else if (curr.getLeft()!=null && curr.getRight()==null) {
                return curr.getLeft();
            }
            // 2-children
            else {
                curr.setRight(findSuccessor(curr.getRight(), prob));
                curr.setData(prob.getData()); // assign the successor data to removed node
            }
        }
        return curr;
    }

    private BSTNode<T> findSuccessor(BSTNode<T> curr, BSTNode<T> prob) {
        if (curr.getLeft()==null) {
            prob.setData(curr.getData());
            return curr.getRight();
        }
        else {
            curr.setLeft(findSuccessor(curr.getLeft(), prob));
        }
        return curr;
    }
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}