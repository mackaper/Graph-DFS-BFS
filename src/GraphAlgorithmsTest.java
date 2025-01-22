import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for GraphAlgorithms.
 *
 * @author [Name]
 * @version [Date]
 */

public class GraphAlgorithmsTest {
    @Before
    public void setUp() {}

    @Test
    public void hasCycleTrueMultipleComponents() {
        // Arrange
        // Three components:
        // 0 - 1 - 5
        // 2 - 3
        //  \ /
        //   9
        // 4 - 6 - 7 - 8
        Graph g = new Graph(10);
        g.add(0, 1, 0);
        g.add(1, 5, 0);
        g.add(9, 2, 0);
        g.add(2, 3, 0);
        g.add(3, 9, 0);
        g.add(4, 6, 0);
        g.add(6, 7, 0);
        g.add(7, 8, 0);

        // Act
        boolean hasCycle = GraphAlgorithms.hasCycle(g);

        // Assert
        assertTrue(hasCycle);
    }

    @Test
    public void hasPathTrueTreeGraph() {
        // Arrange
        //            0
        //           / \
        //          1   2
        //         / \   \
        //        3   4   5
        //       / \   \
        //      6   7   8
        //               \
        //                9
        Graph g = new Graph(10);
        g.add(0, 1, 0);
        g.add(0, 2, 0);
        g.add(1, 3, 0);
        g.add(1, 4, 0);
        g.add(2, 5, 0);
        g.add(3, 6, 0);
        g.add(3, 7, 0);
        g.add(4, 8, 0);
        g.add(8, 9, 0);

        // Act
        boolean hasPath = GraphAlgorithms.hasPath(g, 0, 9);

        // Assert
        assertTrue(hasPath);
    }

    @Test
    public void hasCycleFalseSingleComponent() {
        // 0 - 1 - 2
        //        / 
        //   5 - 3 - 4
        //    \ 
        //     6
      Graph g = new Graph(7);
      g.add(0, 1, 0);
      g.add(1, 2, 0);
      g.add(2, 3, 0);
      g.add(3, 4, 0);
      g.add(3, 5, 0);
      g.add(5, 6, 0);
      // Assert
      assertFalse(GraphAlgorithms.hasCycle(g));

    }

  @Test
  public void hasCycleTrueSingleComponent() {
    Graph g = new Graph(3);
    g.add(0, 1, 0);
    g.add(1, 2, 0);
    g.add(2, 0, 0);
    // Act
    assertTrue(GraphAlgorithms.hasCycle(g));
  }

  @Test
  public void hasCycleFalseMultipleComponents() {
    Graph g = new Graph(4);
    g.add(0, 1, 0);
    g.add(3, 2, 0);
    // Act
    assertFalse(GraphAlgorithms.hasCycle(g));
  }

    @Test
    public void graphWithCycleHasPathToSelf() {
    Graph g = new Graph(3);
    g.add(0, 1, 0);
    g.add(1, 2, 0);
    g.add(2, 0, 0);
    // Act
    assertTrue(GraphAlgorithms.hasPath(g, 0, 1));
    }

    @Test
    public void verteciesOnDifferentComponentsHasPathIsFalse() {
    Graph g = new Graph(4);
    g.add(0, 1, 0);
    g.add(3, 2, 0);
    // Act
    assertFalse(GraphAlgorithms.hasPath(g, 0, 2));
    }

    @Test
    public void graphWithVerteciesWithoutEdgesHasPathIsFalse() {
    Graph g = new Graph(4);
    assertFalse(GraphAlgorithms.hasPath(g, 0, 1));
    }
}
