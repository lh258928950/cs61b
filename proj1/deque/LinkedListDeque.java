package deque;

import java.util.Iterator;


public class LinkedListDeque<T> implements Iterable<T>{
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
            first.next = sentinel.next;
            first.prev = sentinel;
            sentinel.next.prev = first;
            sentinel.next = first;
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
        if (sentinel.next != null && sentinel.next != sentinel) {
            Node first = sentinel.next;
            T item = first.item;
            if (size == 1){
                sentinel.next = sentinel;
                sentinel.prev = sentinel;
            }else {
                sentinel.next = first.next;
                first.next.prev = sentinel;
            }
            first.next = null;
            first.prev = null;
            first.item = null;
            size = size - 1;
            return item;
        }
        return null;
    }
    public T removeLast(){
        if (sentinel.prev != null && sentinel.prev != sentinel) {
            Node last = sentinel.prev;
            T item = last.item;
            if (size == 1) {
                sentinel.next = sentinel;
                sentinel.prev = sentinel;
            } else {
                sentinel.prev = last.prev;
                sentinel.prev.next = sentinel;
            }
            last.item = null;
            last.next = null;
            last.prev = null;
            size = size - 1;
            return item;
        }
        return null;
    }
    public T get(int index) {
        return sentinel.get(index);
    }

    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }
    public T getRecursiveHelper(Node current, int index) {
        if (index == 0) {
            return current.item;
        } else {
            return getRecursiveHelper(current.next, index - 1);
        }
    }
    @Override
    public boolean equals(Object o){
        if (o instanceof LinkedListDeque){
            if (size != ((LinkedListDeque<T>) o).size) {
                return false;
            }
            Iterator<T> iterator = iterator();
            for (T item : (LinkedListDeque<T>) o){
                if (item != iterator.next()){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public Iterator<T> iterator(){
        return new LLDIterator();
    }

    public class LLDIterator implements Iterator<T> {
        int pos;
        Node nextNode;

        public LLDIterator() {
            pos = 0;
            nextNode = sentinel.next;
        }
        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T item = nextNode.item;
            nextNode = nextNode.next;
            pos = pos + 1;
            return item;
        }
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
            return temp.next;
        }
    }
}
