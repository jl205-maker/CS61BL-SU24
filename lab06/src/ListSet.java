import java.util.ArrayList;
import java.util.List;

/**
 * Represents a set of ints. A simple implementation of a set using a list.
 */
public class ListSet implements SimpleSet {

    List<Integer> elems;

    public ListSet() {
        elems = new ArrayList<Integer>();
    }

    /** Adds k to the set. */
    @Override
    public void add(int k) {
        if (!elems.contains(k)) {
            elems.add(k);
        }
    }

    /** Removes k from the set. */
    @Override
    public void remove(int k) {
        Integer toRemove = k;
        if (elems.contains(toRemove)) {
            elems.remove(Integer.valueOf(k)); // Remove the Integer object representing k
        }
    }

    /** Return true if k is in this set, false otherwise. */
    @Override
    public boolean contains(int k) {
        return elems.contains(k);
    }

    /** Return true if this set is empty, false otherwise. */
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /** Returns the number of items in the set. */
    @Override
    public int size() {
        return elems.size();
    }

    /** Returns an array containing all of the elements in this collection. */
    @Override
    public int[] toIntArray() {
        int[] arrayFromList = new int[elems.size()];
        for (int i = 0; i < elems.size(); i++) {
            arrayFromList[i] = elems.get(i);
        }
        return arrayFromList;
    }
}
