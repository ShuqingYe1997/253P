import java.util.*;

/**
 * @ClassName: KNearestNeighborClusters
 * @Description:
 * @Author: SQ
 * @Date: 2020-11-13
 */

class Subset{
    int root;
    int rank;

    Subset(){}

    Subset(int root, int rank){
        this.root = root;
        this.rank = rank;
    }
}

public class KNearestNeighborClusters {

    private Graph graph;
    private PriorityQueue<Edge> pq;
    private int n;
    private Subset[] subsets;
    private int k;


    KNearestNeighborClusters(Graph graph, int k) {
        this.graph = graph;
        n = graph.getN();
        pq = new PriorityQueue<Edge>((a,b) -> a.weight - b.weight);
        subsets = new Subset[n];
        for(int i = 0; i < n ;i++) {
            subsets[i] = new Subset(i, 0);
        }
        this.k = k;
    }

    void getMST() {
        for(List<Edge> list: graph.getEdges()){
            for(Edge e: list)
                pq.offer(e);
        }
        int cnt = 0;
        while(cnt < n - k) {
            Edge e = pq.poll();
            if(find(e.start) != find(e.end)){
                union(e.start, e.end);
                cnt++;
            }
        }
    }

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

    // 这个print也蛮麻烦的
    void printClusters() {
        Map<Integer, Integer> map = new HashMap<>();
        int cnt = 0;
        List<List<Integer>> res = new ArrayList<>();
        for (int j = 0; j < k; j++)
            res.add(new ArrayList<>());

        for (int j = 0; j < n; j++) {
            int label = find(j);
            if(map.containsKey(label)) {
                res.get(map.get(label)).add(j);
            }
            else {
                res.get(cnt).add(j);
                map.put(label, cnt++);
            }
        }
        System.out.println("K = " + k);
        for (int i = 1; i <= k ; i++) {
            System.out.print("\tCluster " + i + ": ");
            int j = 0;
            for (; j < res.get(i - 1).size() - 1; j++) {
                System.out.print(graph.vertices.get(res.get(i - 1).get(j)).name + ", ");
            }
            System.out.println(graph.vertices.get(res.get(i - 1).get(j)).name);
        }


    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String filename = "";
        while (!(filename = scanner.nextLine()).equals("exit")) {
            Graph graph = new Graph(filename);
            if (graph.vertices == null) {
                System.err.println("Invalid file or graph!");
                continue;
            }
            for (int k : graph.kList) {
                KNearestNeighborClusters clusters = new KNearestNeighborClusters(graph, k);
                clusters.getMST();
                clusters.printClusters();
            }
        }
    }
}
