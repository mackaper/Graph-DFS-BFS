import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.swing.text.html.HTMLDocument.RunElement;

/**
 * A graph with a fixed number of vertices implemented using adjacency maps.
 * Space complexity is &Theta;(n + m) where n is the number of vertices and m
 * the number of edges.
 *
 * @author [Name]
 * @version [Date]
 */
public class Graph {
    /**
     * The map edges[v] contains the key-value pair (w, c) if there is an edge from
     * v to w; c is the cost assigned to this edge.
     */
    private final Map<Integer, Integer>[] edges;

    /** Number of edges in the graph. */
    private int numEdges;

    /**
     * Constructs a HashGraph with n vertices and no edges. Time complexity: O(n)
     *
     * @throws IllegalArgumentException if n < 0
     */
    public Graph(int n) {
        if (n < 0)
            throw new IllegalArgumentException("n = " + n);

        // The array will contain only Map<Integer, Integer> instances created
        // in addEdge(). This is sufficient to ensure type safety.
        @SuppressWarnings("unchecked")
        Map<Integer, Integer>[] a = new HashMap[n];
        for (int i = 0; i < a.length; i++) {
            a[i] = new HashMap<>();
        }
        edges = a;
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the number of vertices in this graph
     */
    public int numVertices() {
        return edges.length;
    }

    /**
     * Returns the number of edges in this graph.
     *
     * @return the number of edges in this graph
     */
    public int numEdges() {
        return numEdges;
    }

    /**
    * Helper function that determines if v is a valid vertex in the graph.
    *
    * @param v vertex
    * @return boolean result for presence of vertex v
    */
    private boolean validEdge(int v) {
        return v >= 0 && v < edges.length;
    }

    /**
     * Returns the outdegree of vertex v.
     *
     * @param v vertex
     * @return the outdegree of vertex v
     * @throws IllegalArgumentException if v is out of range
     */
    public int degree(int v) throws IllegalArgumentException {
        //check if v is valid
        if (!validEdge(v))
        {
            throw new IllegalArgumentException();
        }
        return edges[v].size();
        /*
        //creats a map and checks if its null, otherwise returns the amount.
        Map<Integer,Integer> elements = edges[v];
        if (elements == null)
        {
            return 0;
        }
        else
        {
            return elements.size();
        }
        */
    }

    /**
     * Returns an iterator of vertices adjacent to v.
     *
     * @param v vertex
     * @return an iterator of vertices adjacent to v
     * @throws IllegalArgumentException if v is out of range
     */
    public Iterator<Integer> neighbors(int v) {
        if (!validEdge(v))
        {
            throw new IllegalArgumentException();
        }
        return edges[v].keySet().iterator();
    }

    /**
     * Returns true if there is an edge (from, to).
     *
     * @param v vertex
     * @param w vertex
     * @return true if there is an edge (from, to).
     * @throws IllegalArgumentException if from or to are out of range
     */
    public boolean hasEdge(int v, int w) {
        //checks the validity of the integers
        if (!validEdge(v))
        {
            throw new IllegalArgumentException();
        }
        if (!validEdge(w))
        {
            throw new IllegalArgumentException();
        }
        return edges[v].containsKey(w);
    

    }

    /**
     * Returns the edge cost if from and to are adjacent, otherwise -1.
     *
     * @param v vertex
     * @param w vertex
     * @return edge cost if available, -1 otherwise
     * @throws IllegalArgumentException if from or to are out of range
     */
    public int cost(int v, int w) throws IllegalArgumentException {
        //checks the validity of the integer
        if (!validEdge(v))
        {
            throw new IllegalArgumentException();
        }
        if (!validEdge(w))
        {
            throw new IllegalArgumentException();
        }
        //Creates a map of the given edge
        Map<Integer,Integer> element = edges[v];
        //Create the integer to return, starting at zero
        int cost = -1;
        //checks if there is a connection (path) and then sets cost to that value
        if (element != null && element.containsKey(w))
        {
            cost = element.get(w);
        }
        return cost;
    }

    /**
     * Inserts an edge with edge cost c.
     *
     * @param c edge cost, c >= 0
     * @param v vertex
     * @param w vertex
     * @throws IllegalArgumentException if from or to are out of range
     */
    public void add(int begin, int to, int cost) {
        //checks the validity of the integers
        if (!validEdge(begin))
        {
            throw new IllegalArgumentException();
        }
        if (!validEdge(to))
        {
            throw new IllegalArgumentException();
        }
        //Creates a map of the given edge
        Map<Integer,Integer> elementBegin = edges[begin];
        Map<Integer,Integer> elementTo = edges[to];
        if (elementBegin == null)
        {
            elementBegin = new HashMap<Integer,Integer>();
            elementBegin.put(to, cost);
                   
            //increment the size
            numEdges++;
        }
        else if (elementTo == null)
        {
            elementTo = new HashMap<Integer,Integer>();
            elementTo.put(begin, cost);
            numEdges++;
        }
        else if (hasEdge(begin, to))
        {
            elementBegin.put(to, cost);   
            elementTo.put(begin, cost);       
        }
        else if (elementBegin.containsKey(to))
        {
            elementBegin.put(to, elementBegin.get(to) + cost==0 ? 0:cost);
        }
        else if (elementTo.containsKey(begin))
        {
            elementTo.put(begin, elementTo.get(begin) + cost==0 ? 0:cost);
        }
        else
        {
            //adds
            elementBegin.put(to, cost);
            elementTo.put(begin, cost);
            //increments
            numEdges++;
        }
        edges[to] = elementTo;
        edges[begin] = elementBegin;
        
    }

    /**
     * Removes the edges between v and w.
     *
     * @param v vertex
     * @param w vertex
     * @throws IllegalArgumentException if v or w are out of range
     */
    public void remove(int v, int w) {
        //checks the validity of the integers
        if (!validEdge(v))
        {
            throw new IllegalArgumentException();
        }
        if (!validEdge(w))
        {
            throw new IllegalArgumentException();
        }
        //Creates a map of the given edge
        Map<Integer,Integer> elementV = edges[v];
        Map<Integer,Integer> elementW = edges[w];
        if (elementV != null && elementV.containsKey(w) && elementW != null && elementW.containsKey(v))
        {
            //removes
            elementW.remove(v);
            elementV.remove(w);
            //decrement
            numEdges--;
        }
    }

    /**
     * Returns a string representation of this graph.
     *
     * Should represent the graph in terms of edges (order does not matter). For
     * example, if a graph has edges (2,3,0) and (1,0,0), the
     * representaiton should be:
     *
     * "{(1,0,0), (2,3,0)}" or "{(2,3,0), (1,0,0)}"
     *
     * An empty graph is just braces:
     *
     * "{}"
     *
     * @return a String representation of this graph
     */
    @Override
    public String toString() {

        boolean[][] connected = new boolean[numVertices()][numVertices()];
        StringBuilder build = new StringBuilder();
        build.append("{");
        //matrix tostring, checks first column, then all the elements in that column, then the next and so on
        for(int i = 0; i < numVertices(); i++)
        {
            for(int j = 0; j < numVertices(); j++)
            {   
                if(!(connected[i][j] || connected[j][i]))
                {
                    if(hasEdge(i, j) == true)
                    {
                        connected[i][j] = true;
                        connected[j][i] = true;
                        build.append("(" + i + "," + j + "," + cost(i, j) + "), ");
                    }
                }
            }
        }
        if(build.length() == 1)
        {
            return build.append("}").toString();
        }
        else
        {
            return build.replace(build.length()-2, build.length(), "}").toString(); 
        }
    }
}
