package TestingFiles;
import com.example.displaygraph.*;

public class TestMST {
    public static void main(String[] args) {
        WeightedGraph<Character> graph = new WeightedGraph<>();
        graph.addVertex('U');
        graph.addVertex('V');
        graph.addVertex('X');
        int indexForU = graph.getIndex('U');
        int indexForV = graph.getIndex('V');
        int indexForX = graph.getIndex('X');
        System.out.println("indexForU is " + indexForU);
        System.out.println("indexForV is " + indexForV);
        System.out.println("indexForX is " + indexForV);
        graph.addEdge(indexForU, indexForV, 3.5);
        graph.addEdge(indexForV, indexForU, 3.5);
        graph.addEdge(indexForU, indexForX, 2.1);
        graph.addEdge(indexForX, indexForU, 2.1);
        graph.addEdge(indexForV, indexForX, 3.1);
        graph.addEdge(indexForX, indexForV, 3.1);
        WeightedGraph<Character>.MST mst
                = graph.getMinimumSpanningTree();
        graph.printWeightedEdges();
        System.out.println(mst.getTotalWeight());
        mst.printTree();
    }
}
