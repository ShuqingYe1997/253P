import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName: Graph
 * @Description: Undirected graph
 * @Author: SQ
 * @Date: 2020-11-14
 */

class Vertex{
    int x;
    int y;
    int index;
    String name;

    Vertex(){};

    Vertex(String name, int index, int x, int y) {
        this.name = name;
        this.index = index;
        this.x = x;
        this.y = y;
    }

}
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
    List<Vertex> vertices;
    List<Integer> kList;

    Graph(){}

    Graph(int n){
        init(n);
    }

    Graph(String filename){
        try {
            File file = new File("./" + filename);
            if (!file.exists())
                return;
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().length() == 0)
                    continue;
                String[] commands = line.split("\\s+");
                switch (commands.length) {
                    case 1:
                        if(n == 0) {
                            if (!init(Integer.parseInt(commands[0]))) {
                                System.err.println("Invalid M = " + commands[0]);
                                return;
                            }
                        }
                        else  // when n is set
                            addK(Integer.parseInt(commands[0]));
                        break;
                    case 3:
                        Vertex v = new Vertex(commands[0], vertices.size(), Integer.parseInt(commands[1]), Integer.parseInt(commands[2]));
                        addEdge(v);  // first, create edges with all other nodes
                        addVertex(v);  // second, add node
                        break;
                    default:
                        System.err.println("Please check your input!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean init(int n) {
        if(n <= 0)
            return false;
        this.n = n;
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 0; i < n ; i++)
            edges.add(new ArrayList<>());
        kList = new ArrayList<>();
        return true;
    }

    void addK(int k) {
        if (k <= n)
            kList.add(k);
        else
            System.out.println("Invalid k = " + k);
    }


    void addVertex(String name, int index, int x, int y) {
        vertices.add(new Vertex(name, index, x, y));
    }

    void addVertex(Vertex v) {
        vertices.add(v);
    }


    void addEdge(Vertex vertex) {
        int i = vertex.index;
        for(int j = 0; j < vertices.size(); j++) {
            Vertex v = vertices.get(j);
            // If store dist as double, it would be a mess in lambda
            int dist = (int)Math.sqrt((vertex.x - v.x) * (vertex.x - v.x) + (vertex.y - v.y) * (vertex.y - v.y));
            edges.get(i).add(new Edge(i, j, dist));
            edges.get(j).add(new Edge(j, i, dist));
        }
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
