package myCode;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

public class GraphDemo {

    public static void main(String[] args) {
        DirectedGraph<Integer, DefaultEdge> graph = 
            new DefaultDirectedGraph <Integer, DefaultEdge>(DefaultEdge.class);

        graph.addVertex(7);
        graph.addVertex(4);
        graph.addVertex(9);
        graph.addVertex(3);
        graph.addVertex(2);
        graph.addVertex(5);


        graph.addEdge(7, 4);
        graph.addEdge(7, 9);
        graph.addEdge(9, 3);
        graph.addEdge(3, 2);
        graph.addEdge(3, 5);

        GraphIterator<Integer, DefaultEdge> iterator = 
                new BreadthFirstIterator<Integer, DefaultEdge>(graph);
        while (iterator.hasNext()) {
            System.out.println( iterator.next() );
        }
    }
}