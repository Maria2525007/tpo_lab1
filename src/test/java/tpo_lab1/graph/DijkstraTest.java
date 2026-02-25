package tpo_lab1.graph;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {

    private static final double DELTA = 1e-9;

    @Test
    void singleVertex_startEqualsEnd() {
        Graph g = new Graph(1);
        Optional<Dijkstra.PathResult> res = Dijkstra.shortestPath(g, 0, 0);

        assertTrue(res.isPresent());
        assertEquals(0.0, res.get().getDistance(), DELTA);
        assertEquals(List.of(0), res.get().getPath());
    }

    @Test
    void twoVertices_withEdge() {
        Graph g = new Graph(2);
        g.addEdge(0, 1, 5.0);

        var res = Dijkstra.shortestPath(g, 0, 1);
        assertTrue(res.isPresent());
        assertEquals(5.0, res.get().getDistance(), DELTA);
        assertEquals(List.of(0, 1), res.get().getPath());
    }

    @Test
    void twoVertices_withoutEdge_noPath() {
        Graph g = new Graph(2);

        var res = Dijkstra.shortestPath(g, 0, 1);
        assertTrue(res.isEmpty());
    }

    @Test
    void choosesShorterAlternativePath() {
        // 0 --(10)--> 1
        // 0 --(1)--> 2 --(1)--> 1
        Graph g = new Graph(3);
        g.addEdge(0, 1, 10.0);
        g.addEdge(0, 2, 1.0);
        g.addEdge(2, 1, 1.0);

        var res = Dijkstra.shortestPath(g, 0, 1);
        assertTrue(res.isPresent());
        assertEquals(2.0, res.get().getDistance(), DELTA);
        assertEquals(List.of(0, 2, 1), res.get().getPath());
    }

    @Test
    void graphUndirected_symmetryDistance() {
        Graph g = new Graph(3);
        g.addEdge(0, 1, 2.0);
        g.addEdge(1, 2, 3.0);

        var res01 = Dijkstra.shortestPath(g, 0, 2);
        var res10 = Dijkstra.shortestPath(g, 2, 0);

        assertTrue(res01.isPresent());
        assertTrue(res10.isPresent());

        assertEquals(res01.get().getDistance(), res10.get().getDistance(), DELTA);

        // пути могут быть зеркальными: [0,1,2] и [2,1,0]
        assertEquals(List.of(0, 1, 2), res01.get().getPath());
        assertEquals(List.of(2, 1, 0), res10.get().getPath());
    }

    @Test
    void multipleShortestPaths_distanceCorrect() {
        // Два одинаковых по длине пути до 3:
        // 0-1-3 (2 + 2)
        // 0-2-3 (2 + 2)
        Graph g = new Graph(4);
        g.addEdge(0, 1, 2.0);
        g.addEdge(1, 3, 2.0);
        g.addEdge(0, 2, 2.0);
        g.addEdge(2, 3, 2.0);

        var res = Dijkstra.shortestPath(g, 0, 3);
        assertTrue(res.isPresent());
        assertEquals(4.0, res.get().getDistance(), DELTA);

        // Путь может быть любой из двух — проверяем только корректность:
        List<Integer> p = res.get().getPath();
        assertEquals(0, p.get(0));
        assertEquals(3, p.get(p.size() - 1));
        assertEquals(3, p.size());
    }

    @Test
    void disconnectedGraph_noPath() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1.0);
        g.addEdge(2, 3, 1.0);

        var res = Dijkstra.shortestPath(g, 0, 3);
        assertTrue(res.isEmpty());
    }

    @Test
    void negativeWeight_rejectedInGraph() {
        Graph g = new Graph(2);
        assertThrows(IllegalArgumentException.class, () -> g.addEdge(0, 1, -1.0));
    }

    @Test
    void invalidVertex_throws() {
        Graph g = new Graph(2);
        assertThrows(IllegalArgumentException.class, () -> Dijkstra.shortestPath(g, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> Dijkstra.shortestPath(g, 0, 2));
    }

    @Test
    void graphWithZeroVertices_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Graph(0));
    }

    @Test
    void addEdge_withNaNWeight_shouldThrow() {
        Graph g = new Graph(2);
        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge(0, 1, Double.NaN));
    }

    @Test
    void selfLoop_shouldBeHandled() {
        Graph g = new Graph(1);
        g.addEdge(0, 0, 3.0);

        var res = Dijkstra.shortestPath(g, 0, 0);
        assertTrue(res.isPresent());
        assertEquals(0.0, res.get().getDistance(), 1e-9);
    }
}
