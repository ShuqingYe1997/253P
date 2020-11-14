import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: Dijkstra
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-13
 */

class DijkstraImpl{
    Graph graph;
    Set<Integer> visited;
    int[] parent;
    int[] dist;
    PriorityQueue<int[]> pq;
    int n;

    DijkstraImpl(Graph graph) {
        this.graph = graph;
        n = graph.getN();

        dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        parent = new int[n];
        visited = new HashSet<>();

        pq = new PriorityQueue<int[]>((a,b) -> a[1] - b[1]);
    }

    void getShortestPath() {
        dist[0] = 0;
        parent[0] = -1;
        pq.offer(new int[]{0, dist[0]});

        while (visited.size() != n) {
            int[] pair = pq.poll();
            visited.add(pair[0]);
            for (Edge edge : graph.getEdges().get(pair[0])) {
                int u = edge.end;
                if (!visited.contains(u)) {
                    if (dist[u] > pair[1] + edge.weight) {
                        dist[u] = pair[1] + edge.weight;
                        parent[u] = pair[0];
                    }
                    pq.offer(new int[]{u, dist[u]});
                }
            }
        }
    }

    void printShortestPath() {
        for (int i = 0; i < n; i++) {
            System.out.println(i + ": " + dist[i] + ", path: " + graph.getPath(i, parent));
        }
    }
}

public class Dijkstra {
    public static void main(String[] args) {
        Graph graph = new Graph("sample_graph.txt");
        DijkstraImpl dijkstra = new DijkstraImpl(graph);
        dijkstra.getShortestPath();
        dijkstra.printShortestPath();
    }
}
