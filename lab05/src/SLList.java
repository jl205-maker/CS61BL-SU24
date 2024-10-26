/**
 * An SLList is a list of integers, which encapsulates the
 * naked linked list structure.
 */
public class SLList {

    /**
     * IntListNode is a nested class that represents a single node in the
     * SLList, storing an item and a reference to the next IntListNode.
     */
    private static class IntListNode {
        /*
         * The access modifiers inside a private nested class are irrelevant:
         * both the inner class and the outer class can access these instance
         * variables and methods.
         */
        private int item;
        private IntListNode next;

        public IntListNode(int item, IntListNode next) {
            this.item = item;
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            IntListNode that = (IntListNode) o;
            return item == that.item;
        }

        @Override
        public String toString() {
            return item + "";
        }

    }

    /* The first item (if it exists) is at sentinel.next. */
    private IntListNode sentinel;
    private int size;
    public static final int SENTINEL_VAL = 42;

    /** Creates an empty SLList. */
    public SLList() {
        sentinel = new IntListNode(SENTINEL_VAL, null);
        //in an empty SLList, the sentinel points towards itself
        sentinel.next = sentinel;
        size = 0;
    }
    /** Creates an SLList with a size of 1*/
    public SLList(int x) {
        sentinel = new IntListNode(SENTINEL_VAL, null);
        sentinel.next = new IntListNode(x, null);
        sentinel.next.next = sentinel;
        size = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SLList slList = (SLList) o;
        if (size != slList.size) {
            return false;
        }

        IntListNode l1 = sentinel.next;
        IntListNode l2 = slList.sentinel.next;

        while (l1 != sentinel && l2 != slList.sentinel) {
            if (!l1.equals(l2)) {
                return false;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        if (l1 != sentinel || l2 != slList.sentinel) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        IntListNode l = sentinel.next;
        String result = "";

        while (l != sentinel) {
            result += l + " ";
            l = l.next;
        }

        return result.trim();
    }

    /** Returns an SLList consisting of the given values. */
    public static SLList of(int... values) {
        SLList list = new SLList();
        for (int i = values.length - 1; i >= 0; i -= 1) {
            list.addFirst(values[i]);
        }
        return list;
    }

    /** Returns the size of the list. */
    public int size() {
        return size;
    }

    /** Adds x to the front of the list. */
    public void addFirst(int x) {
        sentinel.next = new IntListNode(x, sentinel.next);
        size += 1;
    }

    /** Return the value at the given index. */
    public int get(int index) {
        IntListNode p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    /** Adds x to the list at the specified index. */
    public void add(int index, int x) {
        //add at the beginning
        if (index == 0) {
            this.addFirst(x);
            return;
        } else if (index >= size) { //add at the end
            size += 1;
            IntListNode currentSentinel = sentinel;
            while (currentSentinel.next != sentinel) {
                currentSentinel = currentSentinel.next;
            }
            currentSentinel.next = new IntListNode(x, sentinel);
        } else {
            size += 1;
            IntListNode currentNode = sentinel;
            int currentIndex = 0;
            while (currentIndex < index) {
                currentNode = currentNode.next;
                currentIndex++;
            }
            currentNode.next = new IntListNode(x, currentNode.next);
        }
    }

    /** Destructively reverses this list. */
    public void reverse() {
        if (sentinel.next == sentinel || sentinel.next.next == sentinel) {
            return; // Base case: list is empty or has one element
        } else {
            sentinel.next = reverseHelper(sentinel.next);
        }
    }

    // Helper method to reverse the linked list
    private IntListNode reverseHelper(IntListNode headNode) {
        if (headNode.next == sentinel) {
            return headNode;
        } else {
            IntListNode rest = reverseHelper(headNode.next);
            headNode.next.next = headNode;
            headNode.next = sentinel;
            return rest;
        }
    }
}
