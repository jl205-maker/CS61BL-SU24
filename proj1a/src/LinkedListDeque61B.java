import java.util.Iterator;
import java.util.List;
import java.util.ArrayList; // import the ArrayList class
import java.util.NoSuchElementException;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    //instance variables
    private DLNode sentinel;
    private int size;

    //constructor
    public LinkedListDeque61B() {
        this.sentinel = new DLNode(null);
        this.sentinel.next = sentinel;
        this.sentinel.prev = sentinel;
        this.size = 0;
    }

    @Override
    public void addFirst(T x) {
        DLNode newNode = new DLNode(x);
        newNode.prev = sentinel;
        newNode.next = sentinel.next;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    @Override
    public void addLast(T x) {
        DLNode newNode = new DLNode(x);
        newNode.prev = sentinel.prev;
        newNode.next = sentinel;
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        DLNode current = sentinel.next;
        while (!current.equals(sentinel)) {
            returnList.add(current.item);
            current = current.next;
        }
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
        if (this.isEmpty()) {
            return null;
        }
        DLNode first = sentinel.next;
        sentinel.next = first.next;
        first.next.prev = sentinel;
        size--;
        return first.item;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        DLNode last = sentinel.prev;
        sentinel.prev = last.prev;
        last.prev.next = sentinel;
        size--;
        return last.item;
    }

    @Override
    public T get(int index) {
        if (this.isEmpty() || index >= size || index < 0) {
            return null;
        }
        DLNode current = sentinel.next;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public T getRecursive(int index) {
        if (this.isEmpty() || index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(DLNode node, int index) {
        if (index == 0) {
            return node.item;
        } else {
            return getRecursiveHelper(node.next, index - 1);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LLDequeIterator();
    }

    private class LLDequeIterator implements Iterator<T> {
        private DLNode currentNode;
        public LLDequeIterator() {
            currentNode = sentinel.next;
        }
        public boolean hasNext() {
            return currentNode != sentinel;
        }
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T item = currentNode.item;
            currentNode = currentNode.next;
            return item;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LinkedListDeque61B)) {
            return false;
        }
        LinkedListDeque61B<?> other = (LinkedListDeque61B<?>) obj;
        if (this.size() != other.size()) {
            return false;
        }
        Iterator<T> thisIterator = this.iterator();
        Iterator<?> otherIterator = other.iterator();
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            T thisItem = thisIterator.next();
            Object otherItem = otherIterator.next();
            if (!(thisItem == null ? otherItem == null : thisItem.equals(otherItem))) {
                return false;
            }
        }
        return !thisIterator.hasNext() && !otherIterator.hasNext();
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
     //Node class: a doubly-linked node
    private class DLNode {
        private T item;
        private DLNode next;
        private DLNode prev;

        public DLNode(T i) {
            item = i;
            prev = null;
            next = null;
        }
    }
}
