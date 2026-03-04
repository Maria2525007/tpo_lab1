package tpo_lab1.graph;

import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Feature("Алгоритмы")
@Story("Алгоритм Дейкстры — кратчайший путь в графе")
@Link(name = "Требование: Dijkstra", url = "https://se.ifmo.ru/courses/testing")
@Severity(SeverityLevel.CRITICAL)
class DijkstraTest {

    private static final double DELTA = 1e-9;

    @Test
    @Story("Одна вершина: старт равен концу")
    void singleVertex_startEqualsEnd() {
        Graph g = new Graph(1);
        Optional<Dijkstra.PathResult> res = Dijkstra.shortestPath(g, 0, 0);

        assertTrue(res.isPresent());
        assertEquals(0.0, res.get().getDistance(), DELTA);
        assertEquals(List.of(0), res.get().getPath());
    }

    @Test
    @Story("Две вершины с ребром")
    void twoVertices_withEdge() {
        Graph g = new Graph(2);
        g.addEdge(0, 1, 5.0);

        var res = Dijkstra.shortestPath(g, 0, 1);
        assertTrue(res.isPresent());
        assertEquals(5.0, res.get().getDistance(), DELTA);
        assertEquals(List.of(0, 1), res.get().getPath());
    }

    @Test
    @Story("Две вершины без ребра — нет пути")
    void twoVertices_withoutEdge_noPath() {
        Graph g = new Graph(2);

        var res = Dijkstra.shortestPath(g, 0, 1);
        assertTrue(res.isEmpty());
    }

    @Test
    @Story("Выбор более короткого альтернативного пути")
    void choosesShorterAlternativePath() {
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
    @Story("Неориентированный граф — симметрия расстояний")
    void graphUndirected_symmetryDistance() {
        Graph g = new Graph(3);
        g.addEdge(0, 1, 2.0);
        g.addEdge(1, 2, 3.0);

        var res01 = Dijkstra.shortestPath(g, 0, 2);
        var res10 = Dijkstra.shortestPath(g, 2, 0);

        assertTrue(res01.isPresent());
        assertTrue(res10.isPresent());

        assertEquals(res01.get().getDistance(), res10.get().getDistance(), DELTA);

        assertEquals(List.of(0, 1, 2), res01.get().getPath());
        assertEquals(List.of(2, 1, 0), res10.get().getPath());
    }

    @Test
    @Story("Несколько кратчайших путей — корректная длина")
    void multipleShortestPaths_distanceCorrect() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 2.0);
        g.addEdge(1, 3, 2.0);
        g.addEdge(0, 2, 2.0);
        g.addEdge(2, 3, 2.0);

        var res = Dijkstra.shortestPath(g, 0, 3);
        assertTrue(res.isPresent());
        assertEquals(4.0, res.get().getDistance(), DELTA);

        List<Integer> p = res.get().getPath();
        assertEquals(0, p.get(0));
        assertEquals(3, p.get(p.size() - 1));
        assertEquals(3, p.size());
    }

    @Test
    @Story("Несвязный граф — нет пути")
    void disconnectedGraph_noPath() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1.0);
        g.addEdge(2, 3, 1.0);

        var res = Dijkstra.shortestPath(g, 0, 3);
        assertTrue(res.isEmpty());
    }

    @Test
    @Story("Отрицательный вес — отклонение")
    @Severity(SeverityLevel.BLOCKER)
    void negativeWeight_rejectedInGraph() {
        Graph g = new Graph(2);
        assertThrows(IllegalArgumentException.class, () -> g.addEdge(0, 1, -1.0));
    }

    @Test
    @Story("Невалидная вершина — исключение")
    void invalidVertex_throws() {
        Graph g = new Graph(2);
        assertThrows(IllegalArgumentException.class, () -> Dijkstra.shortestPath(g, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> Dijkstra.shortestPath(g, 0, 2));
    }

    @Test
    @Story("Граф с нулём вершин — исключение")
    void graphWithZeroVertices_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new Graph(0));
    }

    @Test
    @Story("Ребро с NaN весом — исключение")
    void addEdge_withNaNWeight_shouldThrow() {
        Graph g = new Graph(2);
        assertThrows(IllegalArgumentException.class,
                () -> g.addEdge(0, 1, Double.NaN));
    }

    @Test
    @Story("Петля на одной вершине")
    void selfLoop_shouldBeHandled() {
        Graph g = new Graph(1);
        g.addEdge(0, 0, 3.0);

        var res = Dijkstra.shortestPath(g, 0, 0);
        assertTrue(res.isPresent());
        assertEquals(0.0, res.get().getDistance(), DELTA);
    }
}
