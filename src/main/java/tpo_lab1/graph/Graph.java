package tpo_lab1.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {

    public static final class Edge {
        private final int to;
        private final double weight;

        public Edge(int to, double weight) {
            this.to = to;
            this.weight = weight;
        }

        public int getTo() {
            return to;
        }

        public double getWeight() {
            return weight;
        }
    }

    private final int vertices;
    private final List<List<Edge>> adj;

    public Graph(int vertices) {
        if (vertices <= 0) {
            throw new IllegalArgumentException("vertices must be > 0");
        }
        this.vertices = vertices;
        this.adj = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public int vertices() {
        return vertices;
    }

    public List<Edge> neighbors(int v) {
        validateVertex(v);
        return Collections.unmodifiableList(adj.get(v));
    }

    public void addEdge(int u, int v, double weight) {
        validateVertex(u);
        validateVertex(v);

        if (Double.isNaN(weight) || Double.isInfinite(weight)) {
            throw new IllegalArgumentException("weight must be finite");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Dijkstra requires non-negative weights");
        }
        if (u == v) {
            adj.get(u).add(new Edge(v, weight));
            return;
        }

        adj.get(u).add(new Edge(v, weight));
        adj.get(v).add(new Edge(u, weight));
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException("vertex out of range: " + v);
        }
    }
}