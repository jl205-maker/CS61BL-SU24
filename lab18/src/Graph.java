import java.util.*;

public class Graph {

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
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        if (isAdjacent(v1, v2)) {
            for (Edge e : adjLists[v1]) {
                if (e.to == v2 && e.weight != weight) {
                    //modify weight
                    e.setWeight(weight);
                }
                return;
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
    public List<Integer> neighbors(int n) {
        List<Integer> neighbors = new ArrayList<>();
        for (Edge e : adjLists[n]) {
            neighbors.add(e.to);
        }
        return neighbors;
    }

    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int n) {
        int inDegree = 0;
        for (int i = 0; i < vertexCount; i++) {
            for (Edge e : adjLists[i]) {
                if (e.to == n) {
                    inDegree++;
                }
            }
        }
        return inDegree;
    }

    /* Returns a list of the vertices that lie on the shortest path from start to stop.
       If no such path exists, you should return an empty list. If START == STOP, returns a List with START. */
    public ArrayList<Integer> shortestPath(int startValue, int stopValue) {
        // Distance map to store the shortest distance to each node
        HashMap<Integer, Integer> distanceMap = new HashMap<>();
        // Predecessor map to store the predecessor of each node
        HashMap<Integer, Integer> predecessorMap = new HashMap<>();
        // Priority queue for the fringe
        PriorityQueue<Node> fringe = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));

        Node start = new Node(startValue, null);
        Node stop = new Node(stopValue, null);

        // Initialize the start node distance
        distanceMap.put(start.value, 0);
        start.distance = 0;
        fringe.add(start);

        while (!fringe.isEmpty()) {
            Node curr = fringe.poll();

            if (curr.value == stop.value) {
                break;
            }

            for (int neighborValue : neighbors(curr.value)) {
                Edge edge = getEdge(curr.value, neighborValue);
                if (edge != null) {
                    int newDist = distanceMap.get(curr.value) + edge.getWeight();
                    if (newDist < distanceMap.getOrDefault(neighborValue, Integer.MAX_VALUE)) {
                        distanceMap.put(neighborValue, newDist);
                        predecessorMap.put(neighborValue, curr.value);
                        Node neighborNode = new Node(neighborValue, curr);
                        neighborNode.distance = newDist;
                        fringe.add(neighborNode);
                    }
                }
            }
        }

        // Construct the shortest path
        ArrayList<Integer> shortestPath = new ArrayList<>();
        if (!predecessorMap.containsKey(stop.value)) {
            return shortestPath; // No path found
        }

        for (int at = stop.value; at != start.value; at = predecessorMap.get(at)) {
            shortestPath.add(at);
        }
        shortestPath.add(start.value);
        Collections.reverse(shortestPath);
        return shortestPath;
    }

    private Edge getEdge(int from, int to) {
        for (Edge e : adjLists[from]) {
            if (e.to == to) {
                return e;
            }
        }
        return null;
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

        private int getWeight() {
            return weight;
        }

        private void setWeight(int newWeight) {
            this.weight = newWeight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }
    }

    private class Node {
        int value;
        Node predecessor;
        int distance;

        Node(int value, Node predecessor) {
            this.value = value;
            this.predecessor = predecessor;
            this.distance = Integer.MAX_VALUE; // initialize distance to infinity
        }
    }

    public static void main(String[] args) {
        Graph graph = new Graph(5); // Example with 5 vertices

        // Adding edges to the graph
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 10);
        graph.addEdge(2, 4, 3);
        graph.addEdge(4, 3, 4);

        // Finding shortest path from node 0 to node 3
        List<Integer> path = graph.shortestPath(0, 3);
        System.out.println("Shortest path from 0 to 3: " + path);
    }
}
