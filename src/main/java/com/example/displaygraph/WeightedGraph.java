package com.example.displaygraph;

import java.util.*;

public class WeightedGraph<V> extends UnweightedGraph<V> {
    public WeightedGraph() {

    }

    public WeightedGraph(V[] vertices, int[][] edges) {
        List<V> verts = Arrays.asList(vertices);

        createWeightedGraph(verts,edges );
    }

    public WeightedGraph(int[][] edges, int numberofVerticies) {
        List<V> verticies = new ArrayList<>();
        for (int i = 0; i < numberofVerticies; i++)
            verticies .add((V) (Integer.valueOf(i)));

        createWeightedGraph(verticies, edges);
    }

    public void createWeightedGraph(List<V> vertices, int[][] edges) {
        this.verticies = vertices;

        for (int i = 0; i < edges.length; i++)
            neighbors.add(new ArrayList<Edge>());

        for (int i = 0; i < edges.length; i++) {
            neighbors.get(edges[i][0]).add(new WeightedEdge(edges[i][0], edges[i][1], edges[i][2]));
        }
    }

    private void createWeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
        this.verticies = vertices;

        for (int i = 0; i < edges.size(); i++) {
            neighbors.add(new ArrayList<Edge>());

        }
        for (WeightedEdge e : edges) {
            neighbors.get(e.u).add(e);
        }
    }

    public double getWeight(int u, int v) throws Exception {
        for (Edge edge : neighbors.get(u)) {
            if (edge.v == v)
                return ((WeightedEdge) edge).weight;
        }

        throw new Exception("Edge does not exist");
    }

    public void printWeightedEdges() {
        for (int i = 0; i < getSize(); i++) {
            System.out.print(getVertex(i) + " (" + i + "): ");
            for (Edge edge : neighbors.get(i)) {
                System.out.print("(" + edge.u + ", " + edge.v + ((WeightedEdge) edge).weight + ") ");
            }
            System.out.println();
        }
    }

    public boolean addEdge(int u, int v, double weight) {
        return addEdge(new WeightedEdge(u, v, weight));
    }

    //Get minimum spanning tree
    public MST getMinimumSpanningTree(){
        return getMinimumSpanningTree(0);
    }

    public MST getMinimumSpanningTree(int startingVertex){
        double[] cost = new double[getSize()];
        for ( int i = 0; i < cost.length; i++){
            cost[i] = Double.POSITIVE_INFINITY;
        }
        cost[startingVertex] = 0;

        int [] parent = new int[getSize()];
        parent[startingVertex] = -1;
        double totalWeight = 0;

        List<Integer> T = new ArrayList<>();

        while (T.size() < getSize()){
            int u = -1;
            double currentMinCost = Double.POSITIVE_INFINITY;
            for ( int i = 0; i < getSize(); i++){
                if (!T.contains(i) && cost[i] < currentMinCost){
                    currentMinCost = cost[i];
                    u = i;
                }
            }
            if ( u == -1) break; else T.add(u);

            totalWeight += cost[u];

            for (Edge e: neighbors.get(u)){
                if ( !T.contains(e.v) && cost[e.v] > ((WeightedEdge)e).weight){
                    cost[e.v] = ((WeightedEdge)e).weight;
                    parent[e.v] = u;
                }
            }
        }
        return new MST(startingVertex, parent, T, totalWeight);
    }


    public class MST extends SearchTree {
        private double totalWeight;

        public MST(int root, int[] parent, List<Integer> SearchOrder, double totalWeight) {
            super(root, parent, SearchOrder);
            this.totalWeight = totalWeight;
        }

        public double getTotalWeight() {
            return totalWeight;
        }
    }

    public class ShortestPathTree extends SearchTree {
        private double[] cost;

        public ShortestPathTree(int source, int[] parent, List<Integer> searchOrder, double[] cost) {
            super(source, parent, searchOrder);
            this.cost = cost;
        }

        public double getCost(int v) {
            return cost[v];
        }

        public void printAllPaths() {
            System.out.println("All shortest paths from " + verticies.get(getRoot()) + " are: ");
            for (int i = 0; i < cost.length; i++) {
                printPath(i);
                System.out.println("(cost: " + cost[i] + ")");
            }
        }
    }

    public ShortestPathTree getShortestPath(int sourceVertex) {
        double[] cost = new double[getSize()];
        for (int i = 0; i < cost.length; i++) {
            cost[i] = Double.POSITIVE_INFINITY;
        }
        cost[sourceVertex] = 0;

        int[] parent = new int[getSize()];
        parent[sourceVertex] = -1;

        List<Integer> T = new ArrayList<>();

        while (T.size() < getSize()) {
            int u = -1;
            double currentMinCost = Double.POSITIVE_INFINITY;
            for (int i = 0; i < getSize(); i++) {
                if (!T.contains(i) && cost[i] < currentMinCost) {
                    currentMinCost = cost[i];
                    u = i;
                }
            }

            if (u == -1) break;
            else T.add(u);


            for (Edge e : neighbors.get(u)) {
                if (!T.contains(e.v) && cost[e.v] > cost[u] + ((WeightedEdge) e).weight) {
                    cost[e.v] = cost[u] + ((WeightedEdge) e).weight;
                    parent[e.v] = u;
                }
            }
        }

        return new ShortestPathTree(sourceVertex, parent, T, cost);
    }
}
