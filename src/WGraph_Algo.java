package ex1;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms{
    private weighted_graph graph;

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */

    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     * @return
     */
    @Override
    public weighted_graph copy() {
        if (graph != null) {
            weighted_graph graphCopy = new WGraph_DS(this.graph);
            return graphCopy;
        }
        return null;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node. NOTE: assume ubdirectional graph.
     * @return
     */
    @Override
    public boolean isConnected() {
        if (this.graph.nodeSize() == 0 || this.graph.nodeSize() == 1) {
            return true;
        }

        Iterator<node_info> it = graph.getV().iterator();
        node_info src = it.next();


        HashMap<Integer, Integer> comp = Dijkstra(src.getKey());
        cleanNode();

        if (comp.size() == graph.nodeSize()) {
            return true;
        }
        return false;
    }




    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        {
            if (src == dest)
                return 0;
            if (this.graph.getNode(src) != null && this.graph.getNode(dest) != null) {
                Dijkstra(src);

                double value = graph.getNode(dest).getTag();
                cleanNode();
                return value;
            }
            return -1;
        }
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> shortPath = new LinkedList<node_info>();
        if (src == dest)
            return shortPath;

        if ((graph.getNode(src) == null) || (graph.getNode(dest) == null))
            return shortPath;
        else
        {
            shortPath.add(0,graph.getNode(dest));
            int value = dest;
            HashMap<Integer, Integer> dijkstra = Dijkstra(src);


            while (value != src) {
                shortPath.add(0,graph.getNode(dijkstra.get(value)));
                value = dijkstra.get(value);
            }
            cleanNode();
        }
        return shortPath;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this.graph);
            fileOut.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object objectFromFile = objectIn.readObject();
            objectIn.close();
            if (objectFromFile instanceof weighted_graph) {
                this.graph = (weighted_graph) objectFromFile;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    /**
     *An algorithm for finding the shortest paths between nodes in a graph.
     *the algorithm will assign  initial distance values changes them by distance and will try to improve them step by step
     *and finds the shortest path from src to a node
     */
    private HashMap<Integer, Integer> Dijkstra(int src) {
        double weight;
        PriorityQueue<node_info> priorityQueue = new  PriorityQueue<node_info>(); // Queue of nodes that need to be taken care of
        HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();

        graph.getNode(src).setTag(0); // The first node of the algorithem
        parent.put(src, null); // He has no parent

        priorityQueue.add(graph.getNode(src));

        while (!(priorityQueue.isEmpty())) {
            node_info node = priorityQueue.remove();
            if(node.getInfo().equals("x")) // Not visited
            {
                for (node_info nodeIndex : graph.getV(node.getKey()))
                {
                    if (nodeIndex.getInfo().equals("x"))
                    {
                        weight=node.getTag() + graph.getEdge(node.getKey(), nodeIndex.getKey());
                        if ((nodeIndex.getTag() == -1) || (weight < nodeIndex.getTag()))
                        {
                            nodeIndex.setTag(weight);
                            priorityQueue.add(nodeIndex);
                            parent.put(nodeIndex.getKey(), node.getKey());
                        }
                    }
                }
                node.setInfo("v");
            }
        }
        return parent;
    }


    /**
     * Set to Default node
     */
    private void cleanNode()
    {
        for (node_info n : graph.getV()) {
            n.setInfo("x");
            n.setTag(-1);
        }
    }
}
