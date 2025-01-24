import java.util.Comparator;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class Exam {
    /**
     * Implement merge sort.
     *
     * It should be: out-of-place stable not adaptive
     *
     * Have a worst case running time of: O(n log n) And a best case running time
     * of: O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: You may need to create a helper method that merges two arrays back into
     * the original T[] array. If two data are equal when merging, think about which
     * subarray you should pull from first.
     *
     * You may assume that the passed in array and comparator are both valid and
     * will not be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array to be sorted.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (arr.length == 0 || arr.length==1) {
            return;
        }
        
        T[] left = (T[]) new Object[arr.length/2];
        T[] right = (T[]) new Object[arr.length - arr.length/2];
        for (int i=0; i<left.length; i++) {
            left[i] = arr[i];
        }
        for (int i=0; i<right.length; i++) {
            right[i] = arr[i + arr.length/2];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        // mergeArray
        int i = 0;
        int j = 0;
        while (i<left.length && j<right.length) {
            if (comparator.compare(left[i], right[j]) <= 0) {
                arr[i + j] = left[i];
                i ++;
            }
            else {
                arr[i + j] = right[j];
                j ++;
            }
        }

        while (i<left.length) {
            arr[i + j] = left[i];
            i ++;
        }
        while (j<right.length) {
            arr[i + j] = right[j];
            j ++;
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be: in-place unstable not adaptive
     *
     * Have a worst case running time of: O(n^2) And a best case running time of:
     * O(n^2)
     *
     * You may assume that the passed in array and comparator are both valid and
     * will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        for (int last = arr.length-1; last > 0; last --) {
            int maxId = 0;
            int searchId = 1;
            while (searchId <= last) {
                if (comparator.compare(arr[searchId], arr[maxId]) > 0) {
                    maxId = searchId;
                }
                searchId ++;
            }
            T temp = arr[maxId];
            arr[maxId] = arr[last];
            arr[last] = temp;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * It should be: out-of-place stable not adaptive
     *
     * Have a worst case running time of: O(kn) And a best case running time of:
     * O(kn)
     *
     * For this question, you are given k: the number of digits in the greatest
     * magnitude number in arr. Because of this, you do not need to make an initial
     * O(n) pass through to determine this number.
     *
     * At no point should you find yourself needing a way to exponentiate a number;
     * any such method would be non-O(1). Think about how how you can get each power
     * of BASE naturally and efficiently as the algorithm progresses through each
     * digit.
     *
     * You may use an ArrayList or LinkedList if you wish. Be sure the List
     * implementation you choose allows for stability while being as efficient as
     * possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * You may assume that the passed in array is valid and will not be null.
     *
     * @param arr The array to be sorted.
     * @param k   The number of digits in the greatest magnitude number in arr.
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
