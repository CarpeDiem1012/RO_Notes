import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

     /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * This is the constructor that constructs a new MinHeap.
     *
     * Recall that Java does not allow for regular generic array creation,
     * so instead we cast a Comparable[] to a T[] to get the generic typing.
     */
    public MinHeap() {
        //DO NOT MODIFY THIS METHOD!
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * Method should run in amortized O(log n) time.
     *
     * @param data The data to add.
     * @throws java.lang.IllegalArgumentException If the data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data==null) {
            throw new IllegalArgumentException();
        }
        // add new data
        if (backingArray.length - 1 == size) {
            T[] newArray = (T[]) new Comparable[backingArray.length*2];
            for (int i=0; i<backingArray.length; i++) {
                newArray[i] = backingArray[i];
            }
            newArray[size+1] = data;
            backingArray = newArray;
        }
        else {
            backingArray[size+1] = data;
        }
        size ++;
        // reorder subheap
        int id = size/2;
        while (id>0) {
            reorderSubHeap(id);
            id = id/2;
        }
    }

    private void reorderSubHeap(int start) {
        // base case: no child, do nothing
        if (start > size/2) {
        }
        else {
            // swap condition check
            T curr = backingArray[start];
            T left = backingArray[start*2];
            T right = start*2+1 > size? null:backingArray[start*2+1];
            int smallerId = right==null || left.compareTo(right)<0 ? (start*2):(start*2+1);
            if (curr.compareTo(backingArray[smallerId]) > 0) {
                backingArray[start] = backingArray[smallerId]; // swap
                backingArray[smallerId] = curr;
            }   
            reorderSubHeap(smallerId);
        }
        return;
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * Method should run in O(log n) time.
     *
     * @return The data that was removed.
     * @throws java.util.NoSuchElementException If the heap is empty.
     */
    public T remove() {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (size==0) {
            throw new NoSuchElementException();
        }
        T pop = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size --;
        reorderSubHeap(1);
        return pop;
    }

    @Override
    public String toString() {
        String output = "";
        for (T i:backingArray) {
            output += " " + i;
        }
        return output;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}