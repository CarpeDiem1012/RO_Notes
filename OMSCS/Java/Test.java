public class Test<T> {
    private class Node<E> {
        E data;
        Node<E> next;
        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<T> head;
    public Test() {
        Node<T> tail = new Node<T>((T)"Z", null);
        head = new Node<T>((T)"A", tail);
    }

    public static void main(String[] args) {
        Test<String> aList = new Test<>();
        aList.kindLatin();
    }

    public void kindLatin() {
        Node<T> current = head;
        while (current != null) {
            System.out.println(current.data.toString() + "pls");
            current = current.next;
        }
    }
}