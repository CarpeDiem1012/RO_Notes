import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Your implementation of the pre-order, in-order, and post-order
 * traversals of a tree.
 */
public class Traversals<T extends Comparable<? super T>> {

    /**
     * DO NOT ADD ANY GLOBAL VARIABLES!
     */
    private void preorderRecursive(TreeNode<T> root, List<T> output) {
        if (root!=null) {
            output.add(root.getData());
            preorderRecursive(root.getLeft(), output);
            preorderRecursive(root.getRight(), output);
            return;
        }
        else {
            return; // base case
        }
    }

    private void inorderRecursive(TreeNode<T> root, List<T> output) {
        if (root!=null) {
            inorderRecursive(root.getLeft(), output);
            output.add(root.getData());
            inorderRecursive(root.getRight(), output);
            return;
        }
        else {
            return; // base case
        }
    }

    private void postorderRecursive(TreeNode<T> root, List<T> output) {
        if (root!=null) {
            postorderRecursive(root.getLeft(), output);
            postorderRecursive(root.getRight(), output);
            output.add(root.getData());
            return;
        }
        else {
            return; // base case
        }
    }

    /**
     * Given the root of a binary search tree, generate a
     * pre-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the pre-order traversal of the tree.
     */
    public List<T> preorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> output = new ArrayList<>();
        preorderRecursive(root, output);
        return output;
    }

    /**
     * Given the root of a binary search tree, generate an
     * in-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the in-order traversal of the tree.
     */
    public List<T> inorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> output = new ArrayList<>();
        inorderRecursive(root, output);
        return output;
    }

    /**
     * Given the root of a binary search tree, generate a
     * post-order traversal of the tree. The original tree
     * should not be modified in any way.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @param <T> Generic type.
     * @param root The root of a BST.
     * @return List containing the post-order traversal of the tree.
     */
    public List<T> postorder(TreeNode<T> root) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<T> output = new ArrayList<>();
        postorderRecursive(root, output);
        return output;
    }
}