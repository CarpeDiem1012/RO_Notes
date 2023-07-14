import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {
        // ArrayQueue<Integer> myList = new ArrayQueue<Integer>();
        // myList.enqueue(0);
        // System.out.println(myList);
        // myList.enqueue(9);
        // System.out.println(myList);
        // myList.enqueue(1);
        // System.out.println(myList);
        // myList.enqueue(8);
        // System.out.println(myList);
        // // myList.addToFront(null);
        // // System.out.println(myList);
        // myList.dequeue();
        // System.out.println(myList);
        // myList.dequeue();
        // System.out.println(myList);
        // myList.dequeue();
        // System.out.println(myList);
        // myList.dequeue();
        // System.out.println(myList);
        // myList.dequeue();
        // System.out.println(myList);
        // myList.dequeue();
        // System.out.println(myList);

        // TreeNode<Integer> myTree = new TreeNode<>(50);
        // myTree.setLeft(new TreeNode<>(25));
        // TreeNode<Integer> left = myTree.getLeft();
        // left.setLeft(new TreeNode<Integer>(10));
        // myTree.setRight(new TreeNode<>(100));
        // TreeNode<Integer> right = myTree.getRight();
        // right.setLeft(new TreeNode<>(75));
        // right.setRight(new TreeNode<>(125));
        // right = right.getRight();
        // right.setLeft(new TreeNode<Integer>(110));

        String text = "COMPUTERSCIENCE";
        String pattern = "NCE";
        // Map<Character, Integer> lastTable = PatternMatching.buildLastTable(pattern);
        CharacterComparator myComparator = new CharacterComparator();
        List<Integer> myList = PatternMatching.boyerMoore(pattern, text, myComparator);
        for (Integer item:myList) {
            System.out.println(item);
        }
    }
}