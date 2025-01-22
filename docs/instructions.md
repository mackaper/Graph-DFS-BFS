### Deadline:
This work should be completed before the exercise **Friday 25th February**.

### Instructions
For instructions on how to do and submit the assignment, please see the
[assignments section of the course instructions](https://gits-15.sys.kth.se/inda-21/course-instructions#assignments).

### Preparation

You must read and answer the questions in the OLI material:

- Read [Module 8: Graphs](https://kth.oli.cmu.edu/jcourse/webui/syllabus/module.do?context=4664ba83ac1f08881edad8194c211034)
  - If you have not done so, goto https://kth.oli.cmu.edu/, signup and register for the course key `dd1338-ht21`

You may also want to read the former course text:
* [Introduction to graph algorithms: definitions and examples](https://yourbasic.org/algorithms/graph/)

### Task 1 - Implement Graph

The first task is to provide an implementation of a Graph. There is some starter
code in [src/Graph.java](src/Graph.java) that you should review.

- The graph is **undirected** and edges are **weighted**
- We use an **adjacency list** implementation, which is implemented using a
  **hashtable**
- The hashtable is an array of `Map<Integer, Integer>` entries
- Java does not like to mix arrays and generic types, so you will see warnings,
 which you can safely ignore

Your task is to **complete the implementation** and ensure it passes the
provided test suite in [src/GraphTest.java](src/GraphTest.java). The following
methods must be implemented:

- `int numVertices()` - return the number of vertices in the graph

-  `int numEdges()` - return the number of edges in the graph

- `int degree(int v)` - return the out degree of vertex v. Note that you must
  handle illegal cases if an invalid vertex is provided, i.e. not in the graph.

> ***Assistants note:*** We need to test for valid edges, i.e. the provided
> vertex v exists in the graph, otherwise we throw an exception. We provide a
> helper function `validEdge(int v)` to use when you have one or two vertices to
> consider. For example:

```java
if (!validEdge(v)) {
    throw new IllegalArgumentException();
}
```

or

```java
if (!validEdge(v) || !validEdge(w)) {
    throw new IllegalArgumentException();
}
```

- `Iterator<Integer> neighbors(int v)` - Returns an iterator of vertices
  adjacent to `v`. Note, referring to the [HashMap API
  documentation](https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html)
  will help here as there are several methods that return iterators, and greatly
  simplify the task

- `boolean hasEdge(int v, int w)` - Returns true if there is an edge from `v` to
  `w`. Again, remember to check for illegal vertices!

- `int cost(int v, int w)` - Returns the edge cost if `v` and `w` are adjacent.

- `void add(int v, int w, int c)` - Inserts an edge with edge cost `c`. Note
  that the graph is undirected, and you should add both an edge from `v` to `w`,
  and an edge from `w` to `v`. The cost will be the same both ways.

- `void remove(int v, int w)` - Removes the edges between `v` and `w`. As
  before, remember to remove both edges.

- `String toString()` - Returns a string representation of this graph. Two tips
  here: (1) Use a StringBuilder to avoid String concatenation costs; (2) To
  avoid printing duplicate edges, think about using an adjacency matrix to model
  which edges have been already printed. This can be a simple 2D array,
  `boolean[][] added`, that lets you test and mark which edges have been added
  to the StringBuilder already, e.g. `if(added[v][w] || added[w][v]) { // skip
  }`

> ***Assistants note:*** We do not make use of weighted edges in this task, but
> it is useful if you want to explore algorithms for finding the shortest path.

### Task 2 - Find Cycles using DFS

Depth first search (DFS) traverses a graph by repeatedly picking a random path
from a given vertex and following it until there are no new vertices to visit.
This algorithm is slightly easier to implement than BFS and is used in many
[algorithms](https://en.wikipedia.org/wiki/Depth-first_search#Applications). Use
a DFS to solve the *decision problem* of whether a graph contains a cycle or
not.

Pseudocode for general DFS:

```
visited <- bool[]
DFS(graph, vertex):
  visited[vertex] := true
  for v in vertex.neighbors:
    if !visited[v]:
      DFS(graph, v)
```

If we consider two graphs, the first contains no cycles:

```
Graph 1

0 - 1 - 2 - 3
         \
          4
```

The second contains a cycle:

```
Graph 2

0 - 1 - 2 - 3
         \ /
          4
```

So the challenge is to use DFS to traverse the graph and detect a cycle. The
important concepts to capture are:

1. We should model the visited vertices using a `boolean[] visited` array so we
know where we have visited.

2. The recursive case is whilst we still have unvisited neighbors to visit

3. If there are no neighbors left, then we are at a base case

4. Perhaps the most subtle concept is that we need to detect **back edges** that
indicate a cycle exists. In `Graph 2` the back edge is between `4` and `2`. This
can be solved by remembering who our parent vertex is (`3` in this case). If we
detect a vertex that is already marked (`2`) and it is not a parent vertex, then
it has already been visited earlier in the traversal and we conclude there is a
cycle in the graph.

> ***Assistants note:*** _Parent_ in this case means the vertex that made the
> recursive call. So whilst 4 _has_ an edge to 2, it was at 3 the DFS(4) call
> was made, making 3 the parent of 4 recursively speaking...oj oj oj...

Try to trace out the call sequence for some simple graphs to get an idea of how
the traversal would operate in principle. Then, complete the implementations in
[src/GraphAlgorithms.java](src/GraphAlgorithms.java) for:

- `boolean hasCycle(Graph g)` - This will iterate across all the vertices,
  maintaining a `boolean[] visited` array to ensure you do not miss any
  components of the graph.

- `boolean DFS(Graph g, int vertex, boolean[] visited, int parent)` - This
  will be the recursive part of the traversal. Ensure you cover the base cases
  (no more neighbors, and, back edge detected) and the recursive case (more
  neighbors to visit).

Whilst there are some tests implemented, you should also create some more tests
for graphs that have cycles and have no cycles, also have graphs that have
single or multiple components.

> ***Assistants note:*** When solving this problem, keep in mind that a graph
> can contain multiple *components* to avoid false negatives.

### Task 3 - Find Paths using BFS

Breadth first search (BFS) traverses a graph by simultaneously following *every*
path from a given vertex until all vertices have been visited. BFS is also
referred to as a flood fill. Use a BFS to solve the *construction problem* of
finding the shortest path (by number of edges) between two vertices. BFS is
particularly suited for solving this problem as it's guaranteed to have taken
the shortest path to any vertex it encounters.

Pseudocode for general BFS:

```
BFS(graph, start):
  visited <- bool[]
  q <- queue()
  q.add(start)
  while !q.empty:
    vertex := q.remove()
    for v in vertex.neighbors:
      if !visited[v]:
        visited[v] := true
        q.add(v)
```

> ***Assistants note:*** Use a
> [`LinkedList`](https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html)
> subtyped as the
> [`Queue`](https://docs.oracle.com/javase/7/docs/api/java/util/Queue.html)
> interface when implementing BFS.

Note that the psuedocode will not solve the problems as is, they simply describe
the general idea of the algorithms.

If we consider the following question, is there a path from 0 to 8, we can
observe what happens in two different graphs, both with 9 vertices, but
differing component structure:

```
Graph 1 (single component)

0 - 1 - 2 - 3
         \
          4 - 5 - 7
               \
                8

True - There exists a path from 0 to 8 -> 0, 1, 2, 4, 5, 8
```

and...

```
Graph 2 (multiple components)

0 - 1 - 2
 \ /
  3

4 - 5 - 7
     \
      8

False - There exists no path from 0 to 8.
```

The main challenge in this task is to **manage the multiple helper data
structures**.

1. We can use a `boolean[] visited` as before to keep track of what we have
already visited.

2. We can use a `Queue<Integer> queue` to keep track of where to go next. You
can choose any implementation of `Queue` as the only operations you need to
_enqueue_ is `add(E e)` and to _dequeue_ is `remove()`.

Your task is to implement the following:

- `boolean hasPath(Graph g, int from, int to)` - An iterative implementation of
  a BFS traversal that confirms if a path exists between vertices `from` and
  `to`.

Finally you should complete the test suite for the algorithms.
[src/GraphAlgorithmsTest.java](src/GraphAlgorithmsTest.java) contains some test
methods to get you started, but you must fix the tests that currently are not
implemented.
