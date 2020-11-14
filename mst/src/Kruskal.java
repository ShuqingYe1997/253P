import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: Kruskal
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-13
 */

// For union-find
class Subset{
    int root;
    int rank;

    Subset(){}

    Subset(int root, int rank){
        this.root = root;
        this.rank = rank;
    }
}

class KruskalImpl{
    private Graph graph;
//    private int[] group;
    private int[] parent;
    private PriorityQueue<Edge> pq;
    private int n;
    private int sum;  // total cost

    private Subset[] subsets;

    KruskalImpl(Graph graph) {
        this.graph = graph;
        n = graph.getN();
        parent = new int[n];
//        group = new int[n];
//        for (int i = 0; i < n; i++)
//            group[i] = i;
        pq = new PriorityQueue<Edge>((a,b) -> a.weight - b.weight);
        sum = 0;
        subsets = new Subset[n];
        for(int i = 0; i < n ;i++) {
            subsets[i] = new Subset(i, 0);
        }
    }

    void getMST() {
        for(List<Edge> list: graph.getEdges()){
            for(Edge e: list)
                pq.offer(e);
        }
        int cnt = 0;
        while(cnt < n - 1) {
            Edge e = pq.poll();
            if(find(e.start) != find(e.end)){
                union(e.start, e.end);
                parent[e.end] = e.start;
                sum += e.weight;
                cnt++;
            }
        }

    }

//    int find(int u) {
//        return group[u];
//    }
//    void union(int u, int v) {
//        int tmp = group[v];  // 搞死老子了
//        for (int i = 0; i < n; i++)
//            if(group[i] == tmp)
//                group[i] = group[u];
//    }

    // path-compression
    int find(int i) {
        if (subsets[i].root != i)  // If is not root
            subsets[i].root = find(subsets[i].root);  // Assign it to root's child
        return subsets[i].root;
    }

    // A function that does union of two sets by rank
    void union(int i, int j) {
        int iRoot = find(i);
        int jRoot = find(j);

        // Attach smaller rank tree under root of high rank tree (Union by Rank)
        if(subsets[iRoot].rank > subsets[jRoot].rank){
            subsets[jRoot].root = subsets[iRoot].root;
        }
        else if (subsets[jRoot].rank > subsets[iRoot].rank){
            subsets[iRoot].root = subsets[jRoot].root;
        }
        else {
            // If ranks are same, then make one as root and increment its rank by one
            subsets[jRoot].root = subsets[iRoot].root;
            subsets[iRoot].rank++;
        }
    }

    void printMST() {
        for (int i = 0; i < n; i++) {
            System.out.println(i + ": " + graph.getPath(i, parent) + ", subset: " + find(i));
        }
        System.out.println("The minimum cost to traverse the tree is " + sum + ".");
    }
}

public class Kruskal {
    public static void main(String[] args) {
        Graph graph = new Graph("sample_graph.txt");
        KruskalImpl kruskal = new KruskalImpl(graph);
        kruskal.getMST();
        kruskal.printMST();
    }
}
