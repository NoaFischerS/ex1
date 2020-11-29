package ex1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class test {
    weighted_graph graphForTest() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.addNode(4);
        g.addNode(5);
        g.addNode(8);
        g.addNode(12);
        g.addNode(17);
        g.addNode(45);
        g.addNode(79);
        g.addNode(704);
        g.connect(0, 5, 1);
        g.connect(0, 2, 4.7);
        g.connect(0, 4, 1.75);
        g.connect(4, 17, 0);
        g.connect(5, 704, 2);
        g.connect(2, 5, 17);
        g.connect(1, 2, 0.825);
        g.connect(1, 17, 0.5);
        g.connect(2, 12, 7.05);
        g.connect(2, 45, 3);
        g.connect(45, 12, 4.05);
        g.connect(45, 8, 4.02);
        g.connect(12, 8, 2.08);
        g.connect(8, 79, 1.08);
        g.connect(12, 79, 1.05);
        g.connect(45, 704, 2.07);
        g.connect(2, 17, 1.7);
        return g;
    }

    @Test
    void createAndRemoveSingleNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(5);
        assertEquals(1, g.nodeSize());
        g.addNode(5);
        assertEquals(1, g.nodeSize());
        g.removeNode(5);
        assertEquals(0, g.nodeSize());
        g.addNode(5);
        assertEquals(1, g.nodeSize());
        assertEquals(3, g.getMC());
    }

    @Test
    void createEdge() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        assertEquals(0, g.edgeSize());
        g.connect(0, 1, 5);
        g.connect(0, 1, 47.0521);
        assertEquals(47.0521, g.getEdge(0, 1));
        assertFalse(g.hasEdge(1, 2));
        assertFalse(g.hasEdge(1, 7));
    }

    @Test
    void removeSingleNode() {
        weighted_graph g = new WGraph_DS();
        assertEquals(0, g.nodeSize());
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        assertEquals(3, g.nodeSize());
        g.removeNode(1);
        assertEquals(2, g.nodeSize());
    }

    @Test
    void removeConnectedNode() {
        weighted_graph g = new WGraph_DS();
        g.addNode(0);
        g.addNode(1);
        g.addNode(2);
        g.connect(0, 1, 5);
        g.connect(0, 2, 5);
        g.connect(1, 2, 5);
        assertEquals(3, g.edgeSize());
        assertEquals(6, g.getMC());
        g.removeNode(0);
        assertEquals(9, g.getMC());
        assertTrue(!g.hasEdge(0, 1));
        g.removeEdge(1, 2);
        assertEquals(-1, g.getEdge(1, 2));
    }

    @Test
    void shortestPathDist() {
        weighted_graph g = graphForTest();
        weighted_graph_algorithms ga1 = new WGraph_Algo();
        ga1.init(g);
        assertEquals(3.075, ga1.shortestPathDist(0, 2));
        assertEquals(10.17, ga1.shortestPathDist(0, 79));
        assertEquals(5.07, ga1.shortestPathDist(0, 45));
        assertEquals(1.325, ga1.shortestPathDist(2, 17));
        ga1.getGraph().connect(4, 17, 70215);
        assertEquals(5.07, ga1.shortestPathDist(0, 45));
    }

    @Test
    void connectivity() {
        weighted_graph g = graphForTest();
        weighted_graph_algorithms ga1 = new WGraph_Algo();
        ga1.init(g);
        assertTrue(ga1.isConnected());
        g.removeEdge(1, 2);
        g.removeEdge(1, 17);
        assertTrue(!ga1.isConnected());
        g.removeNode(1);
        assertTrue(ga1.isConnected());
        g.removeNode(45);
        assertTrue(ga1.isConnected());
        g.connect(8, 704, 597);
        g.removeNode(5);
        assertTrue(ga1.isConnected());
    }
}
