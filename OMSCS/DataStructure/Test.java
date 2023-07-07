import java.util.List;
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

        int[] arr = new int[]{-2147483648, -2147483648, -9};
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i=0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }
        int base = 1;
        int k = 1;

        // calculate max base `base` and max digit `k`
        for (int item : arr) {
            while (item/base >=10 || item/base <=-10) {
                base = base*10;
                k ++;
            }
        }
        
        for (int n=0, b=1; n < k; n++, b*=10) {
            // push into buckets
            for (int item : arr) {
                int i = (item/b) % 10; // last signigicant digit [-9, 9]
                buckets[i + 9].add(item);
            }

            // pop from buckets
            int i = 0;
            for (int bucketID=0; bucketID < buckets.length; bucketID++) {
                while (! buckets[bucketID].isEmpty()) {
                    arr[i] = buckets[bucketID].removeFirst();
                    i ++;
                }
            }
        }
        
        for (int i=0; i<arr.length ; i++) {
            System.out.println(arr[i]);
        }
    }
}