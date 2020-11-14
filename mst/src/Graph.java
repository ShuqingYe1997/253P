import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: Graph
 * @Description: Undirected graph
 * @Author: SQ
 * @Date: 2020-11-14
 */

class Edge {
    int start;
    int end;
    int weight;

    Edge() {
        start = 0;
        end = 0;
        weight = 0;
    }

    Edge(int start, int end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public String toString() {
        return start + " -> " + end + ", weight = " + weight;
    }
}

public class Graph {
    private int n;
    private List<List<Edge>> edges;

    Graph(){}

    Graph(int n){
        init(n);
    }

    Graph(String filename){
        try {
            FileReader fileReader = new FileReader(new File(filename));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] commands = line.split(",");
                switch (commands.length) {
                    case 1:
                        init(Integer.parseInt(commands[0]));
                        break;
                    case 3:
                        addEdge(Integer.parseInt(commands[0]), Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
                        break;
                    default:
                        System.err.println("Please check your input!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init(int n) {
        this.n = n;
        edges = new ArrayList<>();
        for (int i = 0; i < n ; i++)
            edges.add(new ArrayList<>());
    }


    void addEdge(int a, int b, int weight) {
        if (a < n && b < n) {
            edges.get(a).add(new Edge(a, b, weight));
            edges.get(b).add(new Edge(b, a, weight));
        }
    }

    void addEdge(Edge e) {
        edges.get(e.start).add(e);
        edges.get(e.end).add(new Edge(e.end, e.start, e.weight));
    }

    List<Integer> getPath(int node, int[] parent){
        List<Integer> path = new LinkedList<>();
        while(node != 0) {
            path.add(0, node);
            node = parent[node];
        }
        path.add(0, node);
        return path;
    }

    int getN() {
        return n;
    }

    List<List<Edge>> getEdges() {
        return edges;
    }
}
