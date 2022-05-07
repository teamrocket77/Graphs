package com.example.displaygraph;
import java.lang.reflect.Array;
import java.util.*;
public class UnweightedGraph <V> implements Graph<V>{
    protected List<V> verticies = new ArrayList<>();
    protected List<List<Edge>> neighbors = new ArrayList<>();

    protected  UnweightedGraph(){

    }

    protected  UnweightedGraph(V[] verticies, int [][] edges){
        for ( int i = 0; i < verticies.length; i++)
            addVertex(verticies[i]);

            createAdjancencyLists(edges, verticies.length);

    }

    protected UnweightedGraph(List<V> verticies, List<Edge> edges){
        for ( int i = 0; i < verticies.size(); i++){
            addVertex((verticies.get(i)));
        }

        createAdjancencyLists(edges, verticies.size());
    }
    protected UnweightedGraph(List<Edge> edges, int numberOfVerticies){
        for ( int i = 0; i < numberOfVerticies; i++)
            addVertex((V)(Integer.valueOf(i)));

        createAdjancencyLists(edges, numberOfVerticies);
    }

    public void createAdjancencyLists(
            int[][] edges, int numberOfVerticies){
        for ( int i = 0; i < edges.length; i++)
            addEdge(edges[i][0], edges[i][1]);
    }
    public void createAdjancencyLists(List<Edge> edges, int numberOfVerticies){
        for ( Edge e: edges)
            addEdge(e.u, e.v);
    }
    @Override
    public int getSize(){
        return verticies.size();
    }

    @Override
    public List<V> getVertices(){
        return  verticies;
    }

    @Override
    public V getVertex(int index){
        return verticies.get(index);
    }

    @Override
    public int getIndex(V v){
        return verticies.indexOf(v);
    }

    @Override
    public List<Integer> getNeighbors(int index){
        List<Integer> result = new ArrayList<>();
        for (Edge e : neighbors.get(index))
            result.add(e.v);

        return result;
    }

    @Override
    public int getDegree(int v){
        return neighbors.get(v).size();
    }

    @Override
    public void printEdges(){
        for ( int u = 0; u < neighbors.size(); u++){
            System.out.println(getVertex(u) + "( " + u + "): ");
            for (Edge e : neighbors.get(u)) {
                System.out.println("(" + getVertex(e.u)+ ", " + getVertex(e.v));
            }
            System.out.println();
        }
    }


    @Override
    public void clear() {
        verticies.clear();
        neighbors.clear();
    }

    @Override
    public boolean addVertex(V vertex){
        if (!verticies.contains(vertex)){
            verticies.add(vertex);
            neighbors.add(new ArrayList<Edge>());
            return true;
        }
        return false;
    }

    @Override
    public boolean addEdge(Edge e){
        if (e.u < 0 || e.u > getSize() - 1)
            throw new IllegalArgumentException("No such index: "+ e.u);
        if ( e.v < 0 || e.v > getSize() - 1)
            throw new IllegalArgumentException("No such index: " + e.v);
        if (!neighbors.get(e.u).contains(e)){
            neighbors.get(e.u).add(e);
            return true;
        }
        return false;
    }

    @Override
    public boolean addEdge(int u, int v){
        return addEdge(new Edge(u, v));
    }


    @Override
    public SearchTree dfs(int v){
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[verticies.size()];
        for ( int i = 0; i < parent.length; i++){
            parent[i] = -1;
        }
        boolean[] isVisited = new boolean[verticies.size()];

        dfs(v, parent, searchOrder, isVisited);

        return new SearchTree(v, parent, searchOrder);
    }
    //This is my implementation
    public SearchTree DFS(int startingV){
        boolean[] visited = new boolean[verticies.size()];
        List<Integer> SearchOrder = new ArrayList<>();
        int[] parents = new int[verticies.size()];
        for (int i = 0; i < visited.length; i++){
            parents[i] = -1;
        }
        // This is heading in the direciton of BFS b/c of the DS used try to figure out a way to use a Stack
        Stack<Integer> S = new Stack<>();
        S.add(startingV);
        visited[startingV] = true;
        int current;
        while (!S.isEmpty()){
            current = S.pop();
            SearchOrder.add(current);
            for( Edge e : neighbors.get(current)){
                if (!visited[e.v]){
                    //Visited is just controlling what gets added to the SeachOrder obj
                    visited[e.v] = true;
                    S.add(e.v);
                    parents[e.v] = current;
                }
            }
        }
        return new UnweightedGraph.SearchTree(startingV, parents, SearchOrder);
    }
    //This is my implementation
    public SearchTree BFS(int startingV){
        boolean[] visited = new boolean[verticies.size()];
        List<Integer> SearchOrder = new ArrayList<>();
        int[] parents = new int[verticies.size()];
        for (int i = 0; i < visited.length; i++){
            parents[i] = -1;
        }
        // This is heading in the direciton of BFS b/c of the DS used try to figure out a way to use a Stack
        Queue<Integer> S = new ArrayDeque<>();
        S.add(startingV);
        visited[startingV] = true;
        int current;
        while (!S.isEmpty()){
            current = S.poll();
            SearchOrder.add(current);
            for( Edge e : neighbors.get(current)){
                if (!visited[e.v]){
                    //Visited is just controlling what gets added to the SeachOrder obj
                    visited[e.v] = true;
                    S.offer(e.v);
                    parents[e.v] = current;
                }
            }
        }
        return new UnweightedGraph.SearchTree(startingV, parents, SearchOrder);
    }


    private void dfs(int v, int[] parent, List<Integer> searchOrder, boolean[] isVisited){
        searchOrder.add(v);
        isVisited[v] = true;

        for ( Edge e : neighbors.get(v)){
            int w = e.v;
            if (!isVisited[w]){
                parent[w] = v;
                dfs(w, parent, searchOrder, isVisited);
            }
        }
    }

    @Override
    public SearchTree bfs(int v){
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[verticies.size()];
        for ( int i = 0; i < parent.length; i++){
            parent[i] = -1;
        }


        //offer method is an add method that does not through an exception
        LinkedList<Integer> queue = new LinkedList<>();
        boolean[] isVisited = new boolean[verticies.size()];
        queue.offer(v);
        isVisited[v] = true;

        while (!queue.isEmpty()){
            int u = queue.poll();
             searchOrder.add(u);
             for( Edge e : neighbors.get(u)){
                 int w = e.v;
                 if (!isVisited[w]){
                     queue.offer(w);
                     parent[w] = u;
                     isVisited[w] = true;
                 }
             }
        }

        return new SearchTree(v, parent, searchOrder);

    }

    public class SearchTree{
        private int root;
        private int parent[];
        private List<Integer> searchOrder;

        public SearchTree(int root, int[] parent, List<Integer> searchOrder) {
            this.root = root;
            this.parent = parent;
            this.searchOrder = searchOrder;
        }

        public int getRoot(){
            return root;
        }

        public int getParent(int index){
            return parent[index];
        }

        public List<Integer> getSearchOrder(){
            return searchOrder;
        }

        public int getNumberOfVerticiesFound(){
            return searchOrder.size();
        }

        public List<V> getPath(int index){
            ArrayList<V> path = new ArrayList<>();

            do {
                path.add(verticies.get(index));
                index = parent[index];
            }
            while (index != -1);

            return path;
        }

        public void printPath(int index){
            List<V> path = getPath(index);
            System.out.print("A path from "+ verticies.get(root) + " to "+ verticies.get(index)+ ": ");
            for ( int i = path.size() - 1; i >= 0; i--)
                System.out.print(path.get(i) + " ");
        }


        public void printTree(){
            System.out.println("Root is: " + verticies.get(root));
            System.out.print("Edges: ");
            for (int i = 0; i < parent.length; i++){
                if (parent[i] != -1){
                    System.out.print("(" + verticies.get(parent[i]) + ", " + verticies.get(i) + ") ");
                }
            }
            System.out.println();
        }
    }

    //implement these two
    @Override
    public boolean remove(V v){
        return true;
    }

    @Override
    public boolean remove(int u, int v){
        return true;
    }
}
