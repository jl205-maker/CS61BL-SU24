import java.util.*;
import java.util.Queue;
import java.util.ArrayDeque;


/* A mutable and finite Graph object. Edge labels are stored via a HashMap
   where labels are mapped to a key calculated by the following. The graph is
   undirected (whenever an Edge is added, the dual Edge is also added). Vertices
   are numbered starting from 0. */
public class Graph {

    /* Maps vertices to a list of its neighboring vertices. */
    private HashMap<Integer, Set<Integer>> neighbors = new HashMap<>();
    /* Maps vertices to a list of its connected edges. */
    private HashMap<Integer, Set<Edge>> edges = new HashMap<>();
    /* A sorted set of all edges. */
    private TreeSet<Edge> allEdges = new TreeSet<>();

    /* Returns the vertices that neighbor V. */
    public TreeSet<Integer> getNeighbors(int v) {
        return new TreeSet<Integer>(neighbors.get(v));
    }

    /* Returns all edges adjacent to V. */
    public TreeSet<Edge> getEdges(int v) {
        return new TreeSet<Edge>(edges.get(v));
    }

    /* Returns a sorted list of all vertices. */
    public TreeSet<Integer> getAllVertices() {
        return new TreeSet<Integer>(neighbors.keySet());
    }

    /* Returns a sorted list of all edges. */
    public TreeSet<Edge> getAllEdges() {
        return new TreeSet<Edge>(allEdges);
    }

    /* Adds vertex V to the graph. */
    public void addVertex(Integer v) {
        if (neighbors.get(v) == null) {
            neighbors.put(v, new HashSet<Integer>());
            edges.put(v, new HashSet<Edge>());
        }
    }

    /* Adds Edge E to the graph. */
    public void addEdge(Edge e) {
        addEdgeHelper(e.getSource(), e.getDest(), e.getWeight());
    }

    /* Creates an Edge between V1 and V2 with no weight. */
    public void addEdge(int v1, int v2) {
        addEdgeHelper(v1, v2, 0);
    }

    /* Creates an Edge between V1 and V2 with weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        addEdgeHelper(v1, v2, weight);
    }

    /* Returns true if V1 and V2 are connected by an edge. */
    public boolean isNeighbor(int v1, int v2) {
        return neighbors.get(v1).contains(v2) && neighbors.get(v2).contains(v1);
    }

    /* Returns true if the graph contains V as a vertex. */
    public boolean containsVertex(int v) {
        return neighbors.get(v) != null;
    }

    /* Returns true if the graph contains the edge E. */
    public boolean containsEdge(Edge e) {
        return allEdges.contains(e);
    }

    /* Returns if this graph spans G. */
    public boolean spans(Graph g) {
        TreeSet<Integer> all = getAllVertices();
        if (all.size() != g.getAllVertices().size()) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> vertices = new ArrayDeque<>();
        Integer curr;

        vertices.add(all.first());
        while ((curr = vertices.poll()) != null) {
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int n : getNeighbors(curr)) {
                    vertices.add(n);
                }
            }
        }
        return visited.size() == g.getAllVertices().size();
    }

    /* Overrides objects equals method. */
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return neighbors.equals(other.neighbors) && edges.equals(other.edges);
    }

    /* A helper function that adds a new edge from V1 to V2 with WEIGHT as the
       label. */
    private void addEdgeHelper(int v1, int v2, int weight) {
        addVertex(v1);
        addVertex(v2);

        neighbors.get(v1).add(v2);
        neighbors.get(v2).add(v1);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        edges.get(v1).add(e1);
        edges.get(v2).add(e2);
        allEdges.add(e1);
    }

    public Graph prims(int start) {
        Graph MST = new Graph();

        PriorityQueue<Edge> fringe = new PriorityQueue<>(Comparator.comparingInt(n -> n.weight));
        TreeSet<Integer> visitedVertices = new TreeSet<>();

        visitedVertices.add(start);

        while (visitedVertices.size() < this.getAllVertices().size()) {
            for (int v : visitedVertices) {
                for (Edge incidentEdge : this.getEdges(v)) {
                    if (!visitedVertices.contains(incidentEdge.getDest())) {
                        fringe.add(incidentEdge);
                    }
                }
            }

            Edge e;
            do {
                e = fringe.poll();
                if (e == null) {
                    return null;
                }
            } while (visitedVertices.contains(e.getDest()));

            MST.addEdge(e);
            MST.addVertex(e.getDest());
            visitedVertices.add(e.getDest());
        }
        return MST;
    }

    public Graph kruskals() {
        Graph MST = new Graph();

        for (int v : this.getAllVertices()) {
            MST.addVertex(v);
        }

        //use disjoint set to store and connect vertices
        UnionFind vSet = new UnionFind(this.getAllVertices().size());

        //maintain a sorted list of all edges in the current graph by weight
        PriorityQueue<Edge> fringe = new PriorityQueue<>(Comparator.comparingInt(n -> n.weight));
        fringe.addAll(this.getAllEdges());

        while (MST.getAllEdges().size() < this.getAllVertices().size() - 1 && !fringe.isEmpty()) {
            Edge e = fringe.poll();
            if (!vSet.connected(e.getSource(), e.getDest())) {
                vSet.union(e.getSource(), e.getDest());
                MST.addEdge(e);
            }
        }

        if (MST.getAllEdges().size() != this.getAllVertices().size() - 1) {
            return null;
        }

        return MST;
    }
}
