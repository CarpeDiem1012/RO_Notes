import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various graph traversal algorithms.
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you should use is the adjacency
     * list from graph. DO NOT create new instances of Map for BFS
     * (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin the bfs on.
     * @param graph The graph to search through.
     * @return List of vertices in visited order.
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<Vertex<T>> visitedList = new ArrayList<Vertex<T>>();
        Queue<Vertex<T>> candidateQueue = new ArrayDeque<Vertex<T>>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjListMap = graph.getAdjList();
        visitedList.add(start);
        candidateQueue.add(start);
        while (!candidateQueue.isEmpty()) {
            Vertex<T> curr = candidateQueue.remove();
            List<VertexDistance<T>> adjList = adjListMap.get(curr);
            for (VertexDistance<T> vertDistPair : adjList) {
                Vertex<T> vert = vertDistPair.getVertex();
                if (!visitedList.contains(vert)) {
                    visitedList.add(vert);
                    candidateQueue.add(vert);
                }
            }
        }
        return visitedList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: This method should be implemented recursively. You may need to
     * create a helper method.
     *
     * You may import/use java.util.Set, java.util.List, and any classes that
     * implement the aforementioned interfaces, as long as they are efficient.
     *
     * The only instance of java.util.Map that you may use is the adjacency list
     * from graph. DO NOT create new instances of Map for DFS
     * (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * You may assume that the passed in start vertex and graph will not be null.
     * You may assume that the start vertex exists in the graph.
     *
     * @param <T>   The generic typing of the data.
     * @param start The vertex to begin the dfs on.
     * @param graph The graph to search through.
     * @return List of vertices in visited order.
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        List<Vertex<T>> visitedList = new ArrayList<>();
        rDFS(start, graph, visitedList);
        return visitedList;
    }
    private static <T> void rDFS(Vertex<T> start, Graph<T> graph, List<Vertex<T>> visitedList) {
        visitedList.add(start);
        Map<Vertex<T>, List<VertexDistance<T>>> adjListMap = graph.getAdjList();
        List<VertexDistance<T>> adjList = adjListMap.get(start);
        // base case: end of depth
        if (adjList.isEmpty()) {
            return;
        }

        for (VertexDistance<T> vertDistPair : adjList) {
            Vertex<T> vert = vertDistPair.getVertex();
            if (!visitedList.contains(vert)) {
                rDFS(vert, graph, visitedList);
            }
        }
    }

}