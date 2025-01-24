import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your implementation of various divide & conquer sorting algorithms.
 */

public class NewSorting {

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(n log n)
     * And a best case running time of: O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: You may need to create a helper method that merges two arrays
     * back into the original T[] array. If two data are equal when merging,
     * think about which subarray you should pull from first.
     *
     * You may assume that the passed in array and comparator are both valid
     * and will not be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array to be sorted.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (arr.length==0 || arr.length==1) {
            return;
        }

        T[] left = (T[]) new Object[arr.length/2];
        T[] right = (T[]) new Object[arr.length - arr.length/2];
        for (int i=0; i< left.length; i++) {
            left[i] = arr[i];
        }
        for (int i=0; i < right.length; i++) {
            right[i] = arr[arr.length/2 + i];
        }
        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int leftID=0;
        int rightID=0;
        while (leftID < left.length && rightID < right.length) {
            if (comparator.compare(left[leftID], right[rightID]) <= 0) {
                arr[leftID+rightID] = left[leftID];
                leftID ++;
            }
            else {
                arr[leftID+rightID] = right[rightID];
                rightID ++;
            }
        }
        while (leftID < left.length) {
            arr[leftID+rightID] = left[leftID];
            leftID ++;
        }
        while (rightID < right.length) {
            arr[leftID+rightID] = right[rightID];
            rightID ++;
        }
        return;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(kn)
     * And a best case running time of: O(kn)
     *
     * Feel free to make an initial O(n) passthrough of the array to
     * determine k, the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * You may use an ArrayList or LinkedList if you wish, but it should only
     * be used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with merge sort. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * You may assume that the passed in array is valid and will not be null.
     *
     * @param arr The array to be sorted.
     */
    public static void lsdRadixSort(int[] arr) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
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
    }
}