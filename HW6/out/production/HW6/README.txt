// compile
```Bash
javac KNearestNeighborClusters.java
java KNearestNeighborClusters
```

// run
Input filenames. For example, input `sample_input.txt` in command line.

// exit
Input `exit` in command line.

// sample output
K=2
Cluster 1: A, B, C, D, E, F, G, H, I, J, K, L
Cluster 2: M, N, O
K=3
Cluster 1: A, B, C, D
Cluster 2: E, F, G, H, I, J, K, L
Cluster 3: M, N, O
K=4
Cluster 1: A, B, C, D
Cluster 2: E, F, G, H
Cluster 3: I, J, K, L
Cluster 4: M, N, O

// main algorithm
Kruskal with (M - K) union-find loops, M is the number of vertices and K is the number of clusters.

// error detection
1. invalid filename
2. invalid M
3. invalid K
