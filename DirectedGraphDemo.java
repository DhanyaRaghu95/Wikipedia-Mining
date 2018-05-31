package myCode;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

/**
 * This class demonstrates some of the operations that can be performed on
 * directed graphs. After constructing a basic directed graph, it computes all
 * the strongly connected components of this graph. It then finds the shortest
 * path from one vertex to another using Dijkstra's shortest path algorithm.
 * The sample code should help to clarify to users of JGraphT that the class
 * org.jgrapht.alg.DijkstraShortestPath can be used to find shortest paths
 * within directed graphs.
 *
 * @author Minh Van Nguyen
 * @since 2008-01-17
 */
public class DirectedGraphDemo {
    public static void main(String args[]) throws MalformedURLException {
    	String article="Lists_of_occupations";
    	DirectedGraphDemo d=new DirectedGraphDemo();
        // constructs a directed graph with the specified vertices and edges
        DirectedGraph<URL, DefaultEdge> directedGraph =
            new DefaultDirectedGraph<URL, DefaultEdge>
            (DefaultEdge.class);
        URL root=new URL("http://en.wikipedia.org/wiki/"+article);
        List<URL> children=new ArrayList<URL>();
        children.add(new URL("http://en.wikipedia.org/wiki/"+"Computer"));
        children.add(new URL("http://en.wikipedia.org/wiki/"+"Music"));
        children.add(new URL("http://en.wikipedia.org/wiki/"+"chef"));
        children.add(new URL("http://en.wikipedia.org/wiki/"+"black"));
        children.add(new URL("http://en.wikipedia.org/wiki/"+"quiscient"));
        directedGraph.addVertex(root);
        for(int i=0;i<5;i++)
        	directedGraph.addVertex(children.get(i));
        for(int i=0;i<5;i++)
        	directedGraph.addEdge(root,children.get(i));
        d.dfsOfGraph(directedGraph);
        
        Set<DefaultEdge> setOfEdges=directedGraph.getAllEdges( root,children.get(1));
        Iterator printEdges=setOfEdges.iterator();
     //   System.out.println("empty or not "+printEdges.hasNext());
        while(printEdges.hasNext())
     	   System.out.println("1 " + printEdges.next());
        

        // computes all the strongly connected components of the directed graph
   /*     StrongConnectivityInspector sci =
            new StrongConnectivityInspector(directedGraph);
        List stronglyConnectedSubgraphs = sci.stronglyConnectedSubgraphs();

        // prints the strongly connected components
        System.out.println("Strongly connected components:");
        for (int i = 0; i < stronglyConnectedSubgraphs.size(); i++) {
            System.out.println(stronglyConnectedSubgraphs.get(i));
        }
        System.out.println();

        // Prints the shortest path from vertex i to vertex c. This certainly
        // exists for our particular directed graph.
        System.out.println("Shortest path from i to c:");
        List path =
            DijkstraShortestPath.findPathBetween(directedGraph, "i", "c");
        System.out.println(path + "\n");

        // Prints the shortest path from vertex c to vertex i. This path does
        // NOT exist for our particular directed graph. Hence the path is
        // empty and the variable "path"; must be null.
        System.out.println("Shortest path from c to i:");
        path = DijkstraShortestPath.findPathBetween(directedGraph, "c", "i");
        System.out.println(path); */
    }
    
    public void dfsOfGraph(DirectedGraph<URL, DefaultEdge> directedGraph)
    {
    	GraphIterator<URL, DefaultEdge> iterator = 
                new DepthFirstIterator<URL, DefaultEdge>(directedGraph);
        while (iterator.hasNext()) {
            System.out.println( iterator.next() );
    }
}
}