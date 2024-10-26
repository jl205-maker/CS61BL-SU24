import javax.annotation.processing.SupportedAnnotationTypes;
import java.util.*;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. That is, adds an edge
       in ONE directions, from v1 to v2. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT.
       Hint: you may want to use isAdjacent in this method. */
    public void addEdge(int v1, int v2, int weight) {
        if (isAdjacent(v1, v2)) {
            for (Edge e : adjLists[v1]) {
                if (e.to == v2 && !(e.weight == weight)) {
                    //modify weight
                    e.setWeight(weight);
                } return;
            }
        } else {
            Edge newEdge = new Edge(v1, v2, weight);
            adjLists[v1].add(newEdge);
        }

    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        //add/override edge v1-v2
        addEdge(v1, v2, weight);
        //add/override edge v2-v1
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        for (Edge e : adjLists[from]) {
            if (e.to == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        List<Integer> neighbors = new ArrayList<>();
        for (Edge e : adjLists[v]) {
            neighbors.add(e.to);
        }
        return neighbors;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        int inDegree = 0;
        for (int i = 0; i < vertexCount; i++) {
            for (Edge e : adjLists[i]) {
                if (e.to == v) {
                    inDegree++;
                }
            }
        }
        return inDegree;
    }


    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /**
     *  A class that iterates through the vertices of this graph,
     *  starting with a given vertex. Does not necessarily iterate
     *  through all vertices in the graph: if the iteration starts
     *  at a vertex v, and there is no path from v to a vertex w,
     *  then the iteration will not include w.
     */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                while (visited.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            int curr = fringe.pop();
            ArrayList<Integer> lst = new ArrayList<>();
            for (int i : neighbors(curr)) {
                lst.add(i);
            }
            lst.sort((Integer i1, Integer i2) -> -(i1 - i2));
            for (Integer e : lst) {
                fringe.push(e);
            }
            visited.add(curr);
            return curr;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        if (start == stop) {
            return true;
        }
        List<Integer> result = dfs(start);
        return result.contains(stop);
    }



    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        List<Integer> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        Iterator<Integer> iter = new DFSIterator(start);

        // If no path exists, return an empty list
        if (!pathExists(start, stop)) {
            return path;
        }

        if (start == stop) {
            path.add(start);
            return path;
        }

        //find all nodes that's along the paths from start to end
        while (iter.hasNext()) {
            int curr = iter.next();
            if (curr == stop) {
                break;
            }
            result.add(curr);
        }

        path.add(stop);
        int parent = stop;

        while (parent != start) {
            for (int node : result) {
                if (isAdjacent(node, parent) && ! path.contains(node)) {
                    parent = node;
                    break;
                }
            }
            path.add(parent);
        }

        Collections.reverse(path);

        return path;
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private int[] currentInDegree;
        private HashSet<Integer> result;

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            currentInDegree = new int[vertexCount];
            result = new HashSet<>();

            for (int i = 0; i < vertexCount; i++) {
                currentInDegree[i] = inDegree(i);
                if (currentInDegree[i] == 0) {
                    fringe.push(i);
                }
            }
        }

        public boolean hasNext() {
            while (!fringe.isEmpty()) {
                int i = fringe.pop();
                for (int neighbor : neighbors(i)) {
                    currentInDegree[neighbor] -= 1;
                }
                while (result.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int curr = fringe.pop();
            result.add(curr);

            for (int neighbor : neighbors(curr)) {
                currentInDegree[neighbor]--;
                if (currentInDegree[neighbor] == 0) {
                    fringe.push(neighbor);
                }
            }
            return curr;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        private void setWeight(int newWeight) {
            this.weight = newWeight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

    }

    private void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();
        System.out.println(g1.pathExists(0, 1));
        System.out.println(g1.path(0, 1));
        System.out.println(g1.pathExists(0, 2));
        System.out.println(g1.path(0, 2));
        System.out.println(g1.pathExists(3, 1));
        Graph g4 = new Graph(5);
        g4.generateG4();
        System.out.println(g4.pathExists(0, 3));
        g4.printPath(0, 3);
    }
}