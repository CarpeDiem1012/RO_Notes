import java.util.Comparator;

/**
 * Your implementation of various iterative sorting algorithms.
 */
public class Sorting {

    /**
     * Implement bubble sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n)
     *
     * NOTE: You should implement bubble sort with the last swap optimization.
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)! 
        int stopIndex = arr.length - 1;
        while (stopIndex > 0) {
            int index = 0;
            int lastSwappedIndex = 0;
            while (index < stopIndex) {
                if (comparator.compare(arr[index], arr[index+1]) > 0) {
                    T temp = arr[index];
                    arr[index] = arr[index+1];
                    arr[index+1] = temp;
                    lastSwappedIndex = index;
                }
                index ++;
            }
            stopIndex = lastSwappedIndex;
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n^2)
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        int selectIndex = arr.length - 1;
        while (selectIndex > 0) {
            int index = 1;
            int max = 0;
            while (index <= selectIndex) {
                if (comparator.compare(arr[index], arr[max]) > 0) {
                    max = index;
                }
                index ++;
            }
            T temp = arr[max];
            arr[max] = arr[selectIndex];
            arr[selectIndex] = temp;
            selectIndex --;
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of: O(n^2)
     * And a best case running time of: O(n)
     *
     * You may assume that the passed in array and comparator
     * are both valid and will never be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array that must be sorted after the method runs.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        int presortedIndex = 1;
        while (presortedIndex < arr.length) {
            int index = presortedIndex;
            while (index > 0) {
                if (comparator.compare(arr[index-1], arr[index]) > 0) {
                    T temp = arr[index];
                    arr[index] = arr[index-1];
                    arr[index-1] = temp;
                }
                else {
                    break;
                }
                index --;
            }
            presortedIndex ++;
        }
    }
}