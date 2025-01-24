import java.util.NoSuchElementException;

public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public Node<T> getHead() {
        return this.head;
    }

    public Node<T> getTail() {
        return this.tail;
    }

    @Override
    public void addAtIndex(T data, int index) {
        // Exceptions
        if (data==null) {
            throw new IllegalArgumentException();
        }
        if (index<0 || index>size) {
            throw new IllegalArgumentException();
        }
        // 1. null list
        if (size==0 && index==0) {
            head = new Node<>(data, null);
            tail = head;
        }
        // 2. single list
        if (size==1 && index<=1) {
            if (index==0) {
                head = new Node<T>(data, head);
            }
            if (index==1) {
                tail = new Node<T>(data, null);
                head.setNext(tail);
            }
        }
        // 3. multi list [index-1(current), index, index+1]
        if (size>1) {
            if(index==0) {
                Node<T> newNode = new Node<>(data, head);
                head = newNode;
            }
            else {
                Node<T> current = head;
                for (int i=0; i<index-1 ; i++) {
                    current = current.getNext();
                }
                Node<T> newNode = new Node<>(data, current.getNext());
                current.setNext(newNode);
                if (index==size) {
                    tail = newNode;
                }
            }
        }

        size++;
        return;
    }

    @Override
    public T getAtIndex(int index) {
        // Exceptions
        if (index<0 || index>=size) {
            throw new IllegalArgumentException();
        }

        Node<T> current = head;
        int i = 0;
        while(current!=null && i!=index) {
            current = current.getNext();
            i++;
        }
        return current.getData();
    }

    @Override
    public T removeAtIndex(int index) {
        T retData;

        // Exceptions
        if (index<0 || index>=size) {
            throw new IllegalArgumentException();
        }
        // 1. single list
        else if (size == 1) {
            retData = head.getData();
            head = null;
            tail = null;
        }
        // 2. two list
        else if (size == 2) {
            if (index == 0) {
                retData = head.getData();
                head = tail;
            }
            else {
                retData = tail.getData();
                head.setNext(null);
                tail = head;
            }
        } 
        // 3. multi(>2) list [index-1(current), index, index+1]
        else {
            Node<T> current = head;
            if (index==0) {
                retData = head.getData();
                head = head.getNext();
            }
            else {
                for (int i=0; i<index-1 ; i++) {
                    current = current.getNext();
                }
                retData = current.getNext().getData();
                current.setNext(current.getNext().getNext());
                if (index == size-1) {
                    tail = current;
                }
            }
        }
        
        size--;
        return retData;
    }

    @Override
    public T remove(T data) {
        if (data==null) {
            throw new IllegalArgumentException("You cannot remove null data from the list.");
        }

        int i = 0;
        Node<T> current = head;
        while(current!=null && !current.getData().equals(data)) {
            current = current.getNext();
            i++;
        }
        
        if (current==null) {
            throw new NoSuchElementException("The data is not present in the list.");
        }

        return removeAtIndex(i);
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    // @Override
    // public String toString() {
    //     String out = "";
    //     for(Node<T> current = head; current!=null; current=current.getNext()) {
    //         out += current.getData() + " ";
    //     }
    //     return out + "\n";
    // }
}