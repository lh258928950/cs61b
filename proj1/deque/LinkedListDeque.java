package deque;

public class LinkedListDeque<T> {
    int size;
    Node sentinel;
    public LinkedListDeque() {
        sentinel = new Node();

    }
    public LinkedListDeque(T item) {
        size = 1;
        sentinel = new Node();
        sentinel.next = new Node(item);
        sentinel.prev = sentinel.getNode(size);
    }
    public void addFirst(T item) {
        Node first = new Node(item);
        if(size == 0){
            sentinel.next = first;
            sentinel.prev = first;
            first.prev = sentinel;
        }else {
            sentinel.next.prev = first;
            sentinel.next = first;
            first.next = sentinel.next;
            first.prev = sentinel;
        }
        size = size + 1;
    }
    public void addLast(T item) {
        Node addNote = new Node(item);
        if (sentinel.prev == null){
            sentinel.prev = addNote;
            sentinel.next = addNote;
            addNote.prev = sentinel;
        } else {
            sentinel.prev.next = addNote;
            addNote.prev = sentinel.prev;
            sentinel.prev = addNote;
        }
        size = size + 1;
    }
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }
    public int size() {
        return size;
    }
    public void printDeque(){
        Node p = sentinel.next;
        while (p != null) {
            System.out.print(p.item);
            p = p.next;
        }
        System.out.println();
    }
    public T removeFirst() {
        if (sentinel.next != null) {
            Node first = sentinel.next;
            sentinel.next = first.next;
            size = size - 1;
            return first.item;
        }
        return null;

    }
    public T removeLast(){
        if (sentinel.next != null) {
            Node Last = sentinel.prev;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = null;
            Last.prev = null;
            size = size - 1;
            return Last.item;
        }
        return null;
    }
    public T get(int index) {
        return sentinel.get(size);
    }

    public class Node {
        T item;
        Node next;
        Node prev;
        public Node() {
            item = null;
            next = null;
            prev = null;
        }
        public Node(T item) {
            this.item = item;
            next = null;
        }
        private T get(int index) {
            Node n = getNode(index);
            return  n.item;
        }
        private Node getNode(int index) {
            Node temp = this;
            while (index > 0) {
                temp = temp.next;
                index = index - 1;
            }
            return temp;
        }
    }
}
