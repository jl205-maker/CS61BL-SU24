import java.util.*;

public class UnionFind {

    private List<Node> elements;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        elements = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            Node n = new Node(i);
            elements.add(n);
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        Node root = findRootNode(elements.get(v));
        return root.size;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        Node node = elements.get(v);
        if (node == node.parent) {
            return -node.size;
        }
        return node.parent.value;
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v >= elements.size()) {
            throw new IllegalArgumentException();
        }
        Node n = elements.get(v);
        Node root = findRootNode(n);
        return root.value;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        Node root1 = findRootNode(elements.get(v1));
        Node root2 = findRootNode(elements.get(v2));
        if (root1 == root2 || connected(v1, v2)) {
            return;
        }
        if (root1.size > root2.size) {
            root2.parent = root1;
            root1.size += root2.size;
        } else {
            root1.parent = root2;
            root2.size += root1.size;
        }
    }

    private Node findRootNode(Node n) {
        if (n.parent != n) {
            n.parent = findRootNode(n.parent);
        }
        return n.parent;
    }

    private class Node {
        int value;
        Node parent;
        int size;

        public Node (int i) {
            this.value = i;
            this.parent = this;
            this.size = 1;
        }

        public Node getParent() {
            return parent;
        }

        public int getValue() {
            return value;
        }
    }
}
