import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for solving graph problems.
 *
 * @author [Name]
 * @version [Date]
 */

public class GraphAlgorithms {
    private static boolean[] visited;

    /**
    * Determines if a component has a cycle.
    *
    * @param g the graph to search
    * @param v the node to start at
    * @param visited boolean array keeping track of visited nodes
    * @param parent parent node of v
    * @return true if component has cycle, false otherwise
    */
    private static boolean DFS(Graph g, int v, boolean[] visited, int parent) {
        visited[v] = true;
        Integer i;
        Iterator<Integer> it = g.neighbors(v);
        while(it.hasNext())
        {
            i = it.next();
            if(!visited[i])
            {
                if(DFS(g, i, visited, v))
                {
                    return true;
                }
            }
            else if(i != parent)
            {
                return true;
            }
        }
        return false;

        //first draft(doesnt inclue parent):
        /*
        for(Iterator<Integer> it = g.neighbors(v); it.hasNext();)
        {          
            if(!visited[v])
            {
                visited[v] = true;              
                return DFS(g, it.next(), visited, v); 
            }
            else
            {
                return true;   
            }            
        }
        return false;
        */
        
    }

    /**
    * Determine if there is a cycle in the graph. Implement using DFS.
    *
    * @param g the graph to search
    * @return true if there exists at least one cycle in the graph, false otherwise
    */
    public static boolean hasCycle(Graph g) {
        visited = new boolean[g.numVertices()];
        
        for(int i = 0; i < g.numVertices(); i++)
        {
            visited[i] = true;
            Iterator<Integer> it = g.neighbors(i);
            //saves the next iteration
            int next = it.next();
            if(!visited[next])
            {
                if(DFS(g, next, visited, i))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
    * Determine if there is a path between two vertices. Implement using
    * BFS.
    *
    * @param v vertex
    * @param w vertex
    * @param g the graph to search
    * @return true if there is a path between v and w, false otherwise
    */
    public static boolean hasPath(Graph g, int v, int w) {
        visited = new boolean[g.numVertices()];
        visited[v] = true;
        Queue<Integer> queue = new LinkedList();
        queue.add(v);
        Integer i;
        Iterator<Integer> it;
        //runs untill queue is empty
        while(!queue.isEmpty())
        {
            //removes the first
            v = queue.remove();
            //assigns the iterator
            it = g.neighbors(v);
            //runs until the iterator has no elements left,
            while(it.hasNext())
            {
                //save the integer
                i=it.next();
                //checks that the current vertex is the same as the end vertex
                if(i == w)
                {
                    return true;
                }
                else if(!visited[i])
                {
                    visited[i] = true;
                    queue.add(i);
                }
            }
        }
        return false;
    }
}
