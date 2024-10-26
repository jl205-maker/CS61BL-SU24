/**
 * Represent a set of nonnegative ints from 0 to maxElement (inclusive) for some initially
 * specified maxElement.
 */
public class BooleanSet implements SimpleSet {

    private boolean[] contains;
    private int size;

    /** Initializes a set of ints from 0 to maxElement. */
    public BooleanSet(int maxElement) {
        contains = new boolean[maxElement + 1];
        size = 0;
    }

    /** Adds k to the set. */
    @Override
    public void add(int k) {
        contains[k] = true;
        return;
    }

    /** Removes k from the set. */
    @Override
    public void remove(int k) {
        contains[k] = false;
        return;
    }

    /** Return true if k is in this set, false otherwise. */
    @Override
    public boolean contains(int k) {
        return contains[k];
    }


    /** Return true if this set is empty, false otherwise. */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /** Returns the number of items in the set. */
    @Override
    public int size() {
        int numOfItems = 0;
        for (boolean value : contains) {
            if (value) {
                numOfItems += 1;
            }
        }
        return numOfItems;
    }

    /** Returns an array containing all of the elements in this collection. */
    @Override
    public int[] toIntArray() {
        int numOfItems = this.size(); // Get the number of true values
        int[] arrayFromBoolSet = new int[numOfItems]; // Create an array of that size
        int index = 0;
        for (int i = 0; i < contains.length; i++) { // Use contains.length, not contains.size()
            if (contains[i]) {
                arrayFromBoolSet[index] = i;
                index++;
            }
        }
        return arrayFromBoolSet;
    }
}
