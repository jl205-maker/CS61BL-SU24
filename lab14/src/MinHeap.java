import java.util.ArrayList;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private int size;
    // TODO: YOUR CODE HERE (no code should be needed here if not implementing the more optimized version)

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        contents.add(null);
        size = 0;
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size() || index < 1) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        if (2 * index <= contents.size()) {
            return 2 * index;
        }
        return 0;
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        if (2 * index + 1 <= contents.size()) {
            return 2 * index + 1;
        }
        return 0;
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        return index / 2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        E elem1 = this.getElement(index1);
        E elem2 = this.getElement(index2);
        if (elem1 == null) {
            return index2;
        }
        if (elem2 == null) {
            return index1;
        }
        if (elem1.compareTo(elem2) <= 0) {
            return index1;
        } return index2;
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        if(size == 0) {
            return null;
        }
        //return the root value of the min heap
        return this.getElement(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    private void bubbleUp(int index) {
        while (index > 1 && getElement(index).compareTo(getElement(getParentOf(index))) < 0) {
            swap(index, getParentOf(index));
            index = getParentOf(index);
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        int smallest = index;
        int left = getLeftOf(index);
        int right = getRightOf(index);

        if (left <= size && getElement(left) != null && getElement(smallest) != null && getElement(left).compareTo(getElement(smallest)) < 0) {
            smallest = left;
        }
        if (right <= size && getElement(right) != null && getElement(smallest) != null && getElement(right).compareTo(getElement(smallest)) < 0) {
            smallest = right;
        }

        if (smallest != index) {
            swap(index, smallest);
            bubbleDown(smallest);
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if (contents.contains(element)) {
            throw new IllegalArgumentException("Element already exists.");
        }
        size += 1;
        contents.add(size, element);
        int index = size;
        if (index / 2 > 0 && getElement(index).compareTo(getElement(index / 2)) < 0) {
            bubbleUp(index);
        }
    }

    /* Returns and removes the smallest element in the MinHeap, or null if there are none. */
    public E removeMin() {
        if (size == 0) {
            return null;
        }
        E minElement = findMin();
        swap(1, size);
        setElement(size, null);
        size -= 1;
        if (size > 0) {
            bubbleDown(1);
        }
        return minElement;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        if (!contains((element))) {
            throw new NoSuchElementException("Element does not exist");
        }
        for (int i = 1; i <= size; i++) {
            if (getElement(i).equals(element)){
                bubbleUp(i);
                bubbleDown(i);
                break;
            }
        }
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        for (int i = 1; i <= size;  i++) {
            try {
                if (getElement(i).equals(element)) {
                    return true;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }
}
