package com.example.displaygraph;
import java.util.*;

public class TestDFS {
    public static void main(String[] args) {

        String[] vertices = {
                "Seattle", "San Fran", "Los Angeles",
                "Denver", "Kansas City", "Chicago", "Boston", "New York",
                "Atlanta", "Miami", "Dallas", "houston"};
        int[][] edges = {
                {0, 1}, {0, 3}, {0, 5}, {1, 0}, {1, 2}, {1, 3},
                {2, 1}, {2, 3}, {2, 4}, {2, 10},
                {3, 0}, {3, 1}, {3, 2}, {3, 4}, {3, 5},
                {4, 2}, {4, 3}, {4, 5}, {4, 7}, {4, 8}, {4, 10},
                {5, 0}, {5, 3}, {5, 4}, {5, 6}, {5, 7},
                {6, 5}, {6, 7}, {7, 4}, {7, 5}, {7, 6}, {7, 8},
                {8, 4}, {8, 7}, {8, 9}, {8, 10}, {8, 11},
                {9, 8}, {9, 11}, {10, 2}, {10, 4}, {10, 8}, {10, 11},
                {11, 8}, {11, 9}, {11, 10}
        };
        UnweightedGraph<String> graph = new UnweightedGraph<>(vertices, edges);

        UnweightedGraph<String>.SearchTree dfs = graph.DFS(graph.getIndex("Chicago"));
        UnweightedGraph<String>.SearchTree dfs1 = graph.dfs(graph.getIndex("Chicago"));
        System.out.println(dfs.getNumberOfVerticiesFound() + " vertices are searched in DFS Order:");

        java.util.List<Integer> searchOrders = dfs.getSearchOrder();
        java.util.List<Integer> searchOrders1 = dfs1.getSearchOrder();

        for ( int i = 0; i < searchOrders.size(); i++){
            System.out.print(graph.getVertex(searchOrders.get(i)) + " ");
        }
        System.out.println();
        for ( int i = 0; i < searchOrders1.size(); i++){
            System.out.print(graph.getVertex(searchOrders.get(i)) + " ");
        }
        System.out.println();

        UnweightedGraph<String>.SearchTree bfs = graph.BFS(graph.getIndex("Chicago"));
        UnweightedGraph<String>.SearchTree bfs1 = graph.bfs(graph.getIndex("Chicago"));
        System.out.println(dfs.getNumberOfVerticiesFound() + " vertices are searched in BFS Order:");

        java.util.List<Integer> searchOrdersb = bfs.getSearchOrder();
        java.util.List<Integer> searchOrders1b = bfs1.getSearchOrder();

        for ( int i = 0; i < searchOrders.size(); i++){
            System.out.print(graph.getVertex(searchOrders.get(i)) + " ");
        }
        System.out.println();
        for ( int i = 0; i < searchOrders1.size(); i++){
            System.out.print(graph.getVertex(searchOrders.get(i)) + " ");
        }
        System.out.println();

    }
}
