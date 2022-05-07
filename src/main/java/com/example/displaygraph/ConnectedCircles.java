package com.example.displaygraph;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ConnectedCircles extends Application {
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
                    colorIfConnected();
                }
            });
        }


        private boolean isInsideACircle(Point2D p) {
            for (Node circle : getChildren())
                if (circle.contains(p))
                    return true;
            return false;
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
            Graph<Node> graph = new UnweightedGraph<>((java.util.List<Node>) getChildren(), edges);
            UnweightedGraph<Node>.SearchTree tree = graph.dfs(0);
            boolean isAllCirclesConnected = getChildren().size() == tree.getNumberOfVerticiesFound();
            for (Node circle : getChildren()) {
                if (isAllCirclesConnected)
                    ((Circle) circle).setFill(Color.GREEN);
                else {
                    ((Circle) circle).setStroke(Color.BLACK);
                    ((Circle) circle).setFill(Color.WHITE);
                }
            }
        }

    }


    public static boolean overlaps(Circle c1, Circle c2) {
        return new Point2D(c1.getCenterX(), c1.getCenterY()).distance(c2.getCenterX(), c2.getCenterY()) <= c1.getRadius() + c2.getRadius();
    }
}
