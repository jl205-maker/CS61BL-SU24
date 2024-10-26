public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /** Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /** Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /** Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /** Creates a RedBlackTree from a given 2-3 TREE. */
    public RedBlackTree(TwoThreeTree<T> tree) {
        Node<T> ttTreeRoot = tree.root;
        root = buildRedBlackTree(ttTreeRoot);
    }

    /** Builds a RedBlackTree that has isometry with given 2-3 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }

        if (r.getItemCount() == 1) {
            RBTreeNode<T> newNode = new RBTreeNode<T>(true, r.getItemAt(0));
            newNode.left = buildRedBlackTree(r.getChildAt(0));
            newNode.right = buildRedBlackTree(r.getChildAt(1));
            return newNode;
        } else {
            RBTreeNode<T> newNode = new RBTreeNode<T>(true, r.getItemAt(1));
            RBTreeNode<T> newRedSub = new RBTreeNode<T>(false, r.getItemAt(0));
            RBTreeNode<T> leftBranch = buildRedBlackTree(r.getChildAt(0));
            RBTreeNode<T> midBranch = buildRedBlackTree(r.getChildAt(1));
            RBTreeNode<T> rightBranch = buildRedBlackTree(r.getChildAt(2));
            newRedSub.left = leftBranch;
            newRedSub.right = midBranch;
            newNode.left = newRedSub;
            newNode.right = rightBranch;
            return newNode;
        }
    }

    /** Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        node.isBlack = !(node.isBlack);
        node.left.isBlack = !(node.left.isBlack);
        node.right.isBlack = !(node.right.isBlack);
    }

    /** Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> midBranch = node.left.right;
        RBTreeNode<T> newRoot = node.left;
        node.isBlack = false;
        newRoot.right = node;
        node.left = midBranch;
        newRoot.left.isBlack = false;
        newRoot.isBlack = true;
        return newRoot;
    }

    /** Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> newRoot = node.right;
        RBTreeNode<T> midBranch = newRoot.left;
        newRoot.left = node;
        node.right = midBranch;
        newRoot.isBlack = node.isBlack;
        node.isBlack = false;
        return newRoot;
    }

    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /** Inserts the given node into this Red Black Tree*/
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode<>(false, item);
        }

        // Handle normal binary search tree insertion.
        int comp = item.compareTo(node.item);
        if (comp == 0) {
            return node; // do nothing.
        } else if (comp < 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }

        // handle "middle of three" and "right-leaning red" structures
        if ((isRed(node.left) && isRed(node.left.right)) || (isRed(node.right) && !(isRed(node.left))))  {
            node = rotateLeft(node);
        } else if (isRed(node.left) && isRed(node.left.left)) { // handle "smallest of three" structure
            node = rotateRight(node);
            flipColors(node);
        } else if (isRed(node.left) && isRed(node.right)) { // handle "largest of three" structure
            flipColors(node);
        }
        return node; //fix this return statement
    }

    /** Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

}
