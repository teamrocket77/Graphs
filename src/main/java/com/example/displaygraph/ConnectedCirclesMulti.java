package com.example.displaygraph;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.util.stream.*;

import java.util.*;

public class ConnectedCirclesMulti extends Application {
    private int NewCircCounter = -1;
    private List<List<Circle>> DistinctCircleGrouping;
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new CirclePane(), 450, 350);
        primaryStage.setTitle("ConnectedCircles");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    class CirclePane extends Pane {
        public CirclePane() {
            this.setOnMouseClicked(e -> {
                if (!isInsideACircle(new Point2D(e.getX(), e.getY()))) {
                    getChildren().add(new Circle(e.getX(), e.getY(), 20));
                    NewCircCounter++;
                    DistinctCircleGrouping = CountNonOverlappingCircs();
                    colorIfConnected();
                }
            });
        }

        private List<List<Circle>> CountNonOverlappingCircs(){
            int parents[] = new int[getChildren().size()];
            List<List<Circle>> connectedCircs = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                connectedCircs.add(new ArrayList<Circle>());

            for (int i = 0; i < getChildren().size();i++)
                parents[i] = -1;

            if (getChildren().size() < 1) {
                System.out.println("There is only one circ");
                return null;
            }


            return connectedCircs;
        }

        private boolean isInsideACircle(Point2D p) {
            for (Node circle : getChildren())
                if (circle.contains(p))
                    return true;
            return false;
        }

        private Color ColorReturn(int ColorVal){
            if (ColorVal == 1){
                return Color.CADETBLUE;
            }
            else if ( ColorVal == 2)
                return Color.BLANCHEDALMOND;
            else if ( ColorVal == 3)
                return Color.YELLOWGREEN;

            return Color.PEACHPUFF;

        }
        private void colorIfConnected() {
            if (getChildren().size() == 0)
                return;

            java.util.List<Edge> edges = new java.util.ArrayList<>();
            for (int i = 0; i < getChildren().size(); i++)
                for (int j = i + 1; j < getChildren().size(); j++)
                    if (overlaps((Circle) (getChildren().get(i)),
                            (Circle) (getChildren().get(j)))) {
                        edges.add(new Edge(i, j));
                        edges.add(new Edge(j, i));
                    }
            HashMap<Node, List<Integer>> myMap = new HashMap<>();

            Graph<Node> graph = new UnweightedGraph<>((java.util.List<Node>) getChildren(), edges);

            List<Node> Nodes = new ArrayList<>();
            // put nodes into array
            Nodes = graph.getVertices();
            Boolean[] visited = new Boolean[Nodes.size()];
            for ( int i = 0; i < visited.length; i++)
                visited[i] = false;
            Node n;
            UnweightedGraph<Node>.SearchTree tree;
            for (int i = 0; i < visited.length; i++) {
                if(visited[i])
                    continue;
                n = Nodes.get(i);
                int index = getChildren().indexOf(n);
                tree = graph.dfs(index);
                myMap.put(n, tree.getSearchOrder());
                for (Integer nodeIntToRemove : tree.getSearchOrder()){
                    visited[nodeIntToRemove] = true;
                }
            }
            ColorCircs(myMap, graph);
        }

        private void ColorCircs(Map<Node, List<Integer>> map, Graph graph){
            int counter = 0;
            while (true){
                counter = 0;
                for (Map.Entry<Node, List<Integer>> entry : map.entrySet()){
                    counter += 1;
                    for (Integer i : entry.getValue()){
                        Circle node = (Circle)graph.getVertex(i);
                        node = (Circle)getChildren().get(getChildren().indexOf(node));
                        node.setFill(ColorReturn(counter));
                    }
                }
                map.clear();
                counter = 0;
                break;
            }

        }

    }

    public static boolean overlaps(Circle c1, Circle c2) {
        return new Point2D(c1.getCenterX(), c1.getCenterY()).distance(c2.getCenterX(), c2.getCenterY()) <= c1.getRadius() + c2.getRadius();
    }
}
