package tpo_lab1.graph;

import java.util.*;

public class Dijkstra {

    public static final class PathResult {
        private final double distance;
        private final List<Integer> path;

        public PathResult(double distance, List<Integer> path) {
            this.distance = distance;
            this.path = List.copyOf(path);
        }

        public double getDistance() {
            return distance;
        }

        public List<Integer> getPath() {
            return path;
        }
    }

    /**
     * Возвращает кратчайший путь и его длину.
     * Если пути нет — Optional.empty().
     */
    public static Optional<PathResult> shortestPath(Graph graph, int start, int end) {
        Objects.requireNonNull(graph, "graph");

        if (start < 0 || start >= graph.vertices() || end < 0 || end >= graph.vertices()) {
            throw new IllegalArgumentException("start/end out of range");
        }

        if (start == end) {
            return Optional.of(new PathResult(0.0, List.of(start)));
        }

        int n = graph.vertices();
        double[] dist = new double[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(prev, -1);
        dist[start] = 0.0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> dist[a[0]]));
        pq.add(new int[]{start});

        while (!pq.isEmpty()) {
            int u = pq.poll()[0];

            if (visited[u]) {
                continue;
            }
            visited[u] = true;

            if (u == end) {
                break;
            }

            for (Graph.Edge e : graph.neighbors(u)) {
                int v = e.getTo();
                if (visited[v]) {
                    continue;
                }
                double w = e.getWeight();

                // веса уже проверяются в Graph.addEdge, но оставим защиту
                if (w < 0) {
                    throw new IllegalArgumentException("negative edge weight");
                }

                double newDist = dist[u] + w;
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    prev[v] = u;
                    pq.add(new int[]{v});
                }
            }
        }

        if (Double.isInfinite(dist[end])) {
            return Optional.empty();
        }

        List<Integer> path = reconstructPath(prev, start, end);
        return Optional.of(new PathResult(dist[end], path));
    }

    private static List<Integer> reconstructPath(int[] prev, int start, int end) {
        LinkedList<Integer> path = new LinkedList<>();
        int cur = end;
        while (cur != -1) {
            path.addFirst(cur);
            if (cur == start) {
                break;
            }
            cur = prev[cur];
        }
        if (path.isEmpty() || path.getFirst() != start) {
            return List.of(); // теоретически не должно случиться
        }
        return path;
    }
}