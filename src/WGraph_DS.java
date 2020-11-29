package ex1;

import java.util.*;

class WGraph_DS implements weighted_graph {

    HashMap<Integer, node_info> keysWithNodes = new HashMap<>();

    int countEdge = 0;
    int countMC = 0;

    static class NodeInfo implements node_info, Comparable<node_info> {

        HashMap<node_info, Double> neighborsWithWeights = new HashMap<>();

        int key;
        String info;
        double tag;


        /**
         * Constructor
         */
        public NodeInfo(int key) {
            this.key = key;

            this.info = "x";
            this.tag = -1;
        }

        /**
         * Copy constructor
         */
          public NodeInfo(NodeInfo node) {
            this.key = node.getKey();
            this.info = node.getInfo();
            this.tag = node.getTag();
            this.neighborsWithWeights = new HashMap<node_info, Double>();
        }

        public boolean nodeEquals (NodeInfo node){
            return this.key == node.getKey() && this.info == node.getInfo() && this.tag == node.getTag();
        }

        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         * @return
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * return the remark (meta data) associated with this node.
         * @return
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         * @param s
         */
        @Override
        public void setInfo(String s) {
            info = s;
        }

        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         * @return
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            tag = t;
        }

        @Override
        public int compareTo(node_info node) {
            if (this.key == node.getKey() && this.info == node.getInfo() && this.tag == node.getTag())
                return 1;
            return 0;
        }
    }


    /**
     * Constructor
     */
    public WGraph_DS(){
        this.countEdge = 0;
        this.countMC = 0;
        keysWithNodes = new HashMap<Integer, node_info>();


    }

    /**
     * Copy constructor
     */
    public WGraph_DS(weighted_graph source) {
        keysWithNodes = new HashMap<Integer, node_info>();
        for (node_info node : source.getV()) {
            //copy the nodes
            node_info nodeCopy = new NodeInfo((NodeInfo)node);
            keysWithNodes.put(nodeCopy.getKey(), nodeCopy);
            //connect the nodes
            for (node_info neighbor : source.getV(node.getKey())) {
                double weight = source.getEdge(node.getKey(), neighbor.getKey());
                connect(node.getKey(), neighbor.getKey(), weight);
            }
        }
    }


    /**
     * Compers object from graph type
     */

    public boolean equals(Object obj) {
        if (obj instanceof WGraph_DS) {
            return graphEquals((WGraph_DS)obj);
        } else {
            return super.equals(obj);
        }
    }

    private boolean graphEquals (WGraph_DS other){

        if (getV().size() != other.getV().size()) {
            return false;
        }

        for (node_info node : getV()) {
            node_info otherNode = other.keysWithNodes.get(node.getKey());
            if (!((NodeInfo)node).nodeEquals((NodeInfo)otherNode)) {
                return false;
            }
        }

        for (node_info node : getV()) {
            for (node_info neighbor : this.getV(node.getKey())) {
                if (!other.hasEdge(node.getKey(), neighbor.getKey()) ||
                        other.getEdge(node.getKey(), neighbor.getKey()) != this.getEdge(node.getKey(), neighbor.getKey())) {
                    return false;
                }
            }
        }
        for (node_info otherNode : other.getV()) {
            for (node_info otherNeighbor : this.getV(otherNode.getKey())) {
                if (!this.hasEdge(otherNode.getKey(), otherNeighbor.getKey()) ||
                        other.getEdge(otherNode.getKey(), otherNeighbor.getKey()) != this.getEdge(otherNode.getKey(), otherNeighbor.getKey())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * return the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */

    @Override
    public node_info getNode(int key) {
        return keysWithNodes.get(key);
    }


    /**
     * return true iff (if and only if) there is an edge between node1 and node2
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!(keysWithNodes.containsKey(node1)) || !(keysWithNodes.containsKey(node2))) {
            return false;
        }
        NodeInfo nodeA = (NodeInfo) getNode(node1);
        NodeInfo nodeB = (NodeInfo) getNode(node2);
        return (nodeA.neighborsWithWeights.containsKey(nodeB));
    }

    /**
     * return the weight if the edge (node1, node1). In case
     * there is no such edge - should return -1
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        NodeInfo nodeA = (NodeInfo) getNode(node1);
        NodeInfo nodeB = (NodeInfo) getNode(node2);

        if (nodeA == null || nodeB == null) {
            return -1;
        }

        if (nodeA.neighborsWithWeights.containsKey(nodeB))
            return nodeA.neighborsWithWeights.get(nodeB);
        else
            return -1;
    }

    /**
     * add a new node to the graph with the given key.
     * Note: this method should run in O(1) time.
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
    @Override
    public void addNode(int key) {
        node_info newNode = new NodeInfo(key);
        if (keysWithNodes.containsKey(key))
            return;
        keysWithNodes.put(key, newNode);
        countMC++;
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method should run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 == node2)
            return;
        NodeInfo nodeA = (NodeInfo) getNode(node1);
        NodeInfo nodeB = (NodeInfo) getNode(node2);

        if (!hasEdge(node1, node2)) {
            countEdge++;

        }
        nodeA.neighborsWithWeights.put(nodeB, w);
        nodeB.neighborsWithWeights.put(nodeA, w);
        countMC++;
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method should run in O(1) tim
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return keysWithNodes.values();
    }

    /**
     *
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * Note: this method can run in O(k) time, k - being the degree of node_id.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        NodeInfo node = (NodeInfo) getNode(node_id);
        return node.neighborsWithWeights.keySet();
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(n), |V|=n, as all the edges should be removed.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_info removeNode(int key) {
        NodeInfo node = (NodeInfo) getNode(key);
        if (node == null) {
            return null;
        }

        Set<node_info> neighbors = new HashSet<>(getV(node.key));
        for (node_info edge : neighbors) {

            removeEdge(node.getKey(), edge.getKey());
//            if (node.neighborsWithWeights.containsKey(edge)) {
//                node.neighborsWithWeights.remove(edge);
//                ((NodeInfo)edge).neighborsWithWeights.remove(node);
//                countEdge--;
//                countMC++;
        }
        countMC++;
        return keysWithNodes.remove(key);
    }

    /**
     * Delete the edge from the graph,
     * Note: this method should run in O(1) time.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        NodeInfo nodeA = (NodeInfo) getNode(node1);
        NodeInfo nodeB = (NodeInfo) getNode(node2);

        if (nodeA.neighborsWithWeights.containsKey(nodeB)) {
            nodeA.neighborsWithWeights.remove(nodeB);
            nodeB.neighborsWithWeights.remove(nodeA);
            countEdge--;
            countMC++;
        }
    }

    /** return the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return keysWithNodes.size();
    }

    /**
     * return the number of edges (undirectional graph).
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int edgeSize() {
        return countEdge;
    }


    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
    @Override
    public int getMC() {
        return countMC;
    }
}
