package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<T> implements Iterable<T> {
    T[] items;
    int size;
    int head;
    int tail;
    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        head = 3;
        tail = 4;
    }
    private void grow() {
        int length = items.length * 2;
        T[] array = (T[]) new Object[length];
        for(int i = 0; i < items.length; i++) {
            int start = ((items.length / 2 + i) + items.length * 2) % (items.length * 2);
            array[start] = items[((head + 1 + i) + items.length) % items.length];
        }
        head = items.length / 2 - 1;
        tail = head + items.length;
        items = array;
    }
    public void addFirst(T item) {
        if (head == tail){
            grow();
        }
        items[head] = item;
        head = (head - 1 + items.length) % items.length;
        size = size + 1;
    }

    public void addLast(T item) {
        if (head == tail){
            grow();
        }
        items[tail] = item;
        tail = (tail + 1 + items.length) % items.length;
        size = size + 1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(items[(head + 1 + i + items.length) % items.length]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) return null;
        head = (head + 1) % items.length;
        T first = items[head];
        items[head] = null;
        size = size - 1;
        return first;
    }

    public T removeLast() {
        if (size == 0) return null;
        tail = (tail - 1 + items.length) % items.length;
        T last = items[tail];
        items[tail] = null;
        size = size - 1;
        return last;
    }

    public T get(int index) {
        if (index > size || size == 0) {
            return null;
        }
        return items[(head + index + 1) % items.length];
    }

    public class AdIterator implements Iterator<T> {
        int pos;

        public AdIterator() {
            pos = 0;
        }
        @Override
        public boolean hasNext() {
            return size > pos;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            pos = pos + 1;
            return items[(pos + head) % items.length];
        }
    }

    public Iterator<T> iterator() {
        return new AdIterator();
    }
}
