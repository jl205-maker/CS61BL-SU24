package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] deque;
    private int front;
    private int back;
    private int size;
    private int capacity;
    private double usageRatio;
    public static final int DEFAULT_CAPACITY = 1;
    private static final int RFACTOR = 2;

    public ArrayDeque61B() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayDeque61B(int initialCapacity) {
        this.capacity = initialCapacity;
        deque = (T[]) new Object[capacity];
        front = -1;
        back = -1;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        if (size == capacity) {
            resize();
        }
        if (size == 0) { //if the array is empty
            front = 0;
            back = 0;
        } else {
            front = Math.floorMod(front - 1, capacity);
        }
        deque[front] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == capacity) {
            resize();
        }
        if (size == 0) {
            front = 0;
            back = 0;
        } else {
            back = Math.floorMod(back + 1, capacity);
        }
        deque[back] = x;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = front; i != back; i = (i + 1) % capacity) {
            returnList.add(deque[i]);
        }
        // Add the last element
        returnList.add(deque[back]);
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T toRemove = deque[front];
        deque[front] = null;
        front = Math.floorMod(front + 1, capacity);
        size--;
        if (size == 0) {
            front = 0;
            back = 0;
        }
        resize();
        return toRemove;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T toRemove = deque[back];
        deque[back] = null;
        back = Math.floorMod(back - 1, capacity);
        size--;
        if (size == 0) {
            front = 0;
            back = 0;
        }
        resize();
        return toRemove;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        } else {
            return deque[Math.floorMod(index + front, deque.length)];
        }
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    private void resize() {
        setUsageRatio();
        if (usageRatio < 0.25 && capacity > 8) {
            int newCapacity = capacity / RFACTOR;
            T[] newArray = (T[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newArray[i] = deque[Math.floorMod(front + i, capacity)];
            }
            deque = newArray;
            front = 0;
            back = size - 1;
            capacity = newCapacity;
        } else if (size == capacity) {
            int newCapacity = capacity * RFACTOR;
            T[] newArray = (T[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newArray[i] = deque[Math.floorMod(front + i, capacity)];
            }
            deque = newArray;
            front = 0;
            back = size - 1;
            capacity = newCapacity;
        }
    }

    public void setUsageRatio() {
        this.usageRatio = (double) size / deque.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int current = front;
        private int count = 0;

        @Override
        public boolean hasNext() {
            return count < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            T item = deque[current];
            current = (current + 1) % capacity;
            count++;
            return item;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deque61B)) {
            return false;
        }
        Deque61B<?> other = (Deque61B<?>) o;
        if (this.size() != other.size()) {
            return false;
        }
        Iterator<T> thisIterator = this.iterator();
        Iterator<?> otherIterator = other.iterator();
        while (thisIterator.hasNext()) {
            T thisItem = thisIterator.next();
            Object otherItem = otherIterator.next();
            if (!thisItem.equals(otherItem)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}


