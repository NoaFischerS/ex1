# ex1
This project represents an undirected, weighted graph.

there are 2 classes which implements 3 interfaces. The main data structure that was used in this project is a HashMap.

The main idea of the project :
The most basic object in this project is the Node which is implementing the node_info interface and shows up as a inner class in the Graph class. each node contains a HashMap of his neighbors and the distance from them. 

The Graph is an undirected, weighted graph that implements the weighted__graph interface. This object contains a HashMap which holds the key of a node and the node. 

And a class that implements algorithms on the graph. this class implements the weighted_graph_algorithms interface. This class provides Dijkstra and uses it. 


Node info inner class in WGraph_DS 
Contains parameters :
int key - represents a key, every node in the graph has his unique key. 
String info - mainly used for Dijkstra algorithm.
int tag - mainly used for Dijkstra algorithm.
HashMap<node_info, Double> neighbors With Weights - HashMap of neighbors of a node and distances

Methods :
NodeInfo(int key) - NodeInfo class constructor
int getKey() - Return the key associated with this node
String getInfo() - Returns the info of the node
setInfo(String s) - Sets this node_info info parameter to the given one.
double getTag()- Returns this node's tag 
setTag(double t) - Allows setting this node tag parameter to the given value.
boolean nodeEquals(NodeInfo node)-compers nodes.

WGraph_DS
This class represents a weighted undirected graph data structure

Contained parameters :
HashMap<Integer, node_info> keys with nodes - used to contain the nodes in the graph according to their key
int countMc - a parameter that counts the number of changes which was done on the graph
int countEdge - a parameter that counts the number of edges in the graph.

Methods :
WGraph_DS() - Constructor method for WGrpah_DS class 
WGraph_DS(weighted_graph source) - Copy constructor
getNode(int key) - Returns the node_info by the given key
boolean hasEdge(int node1, int node2) - Boolean method which return true iff there is an edge between node1 and node2 
double getEdge(int node1, int node2) - Returns the distance to the given nodes if the edge exist. In case there is no such edge - should return -1. 
void addNode(int key) - Method adds a new node to the graph with the given key.
void connect(int node1, int node2, double w) - Method connects an edge between node1 and node2, with an edge with weight >=0.
Collection<node_info> getV() - This method returns a Collection<node_info> type data structure which returns all the contained nodes in the grpah . 
Collection<node_info> getV(int node_id) - This method returns a Collection containing all the nodes distancesToConnectedNodes to node_id.
node_info removeNode(int key) - Delete the node (with the given ID) from the graph - and removes all edges which start or end at this node. 
void removeEdge(int node1, int node2) - Method deletes an edge between 2 nodes, thanks to removeNode(int key) method from the NodeInfo class the 
int nodeSize() - Returns the size of the graph, how many nodes exists.
int edgeSize() - Method returns countEdge parameter.
int getMC() - Method returns countMc parameter.
boolean equals(Object compared) - Method provides a comparison for this graph and the given graph in the function.

WGraph_Algo
WGraph__Algo class which implements weighted_graph_algorithms interface.

Contains parameters :
weighted_graph graph - a graph.

Methods :
void init(weighted_graph g) - Init the graph on which this set of algorithms operates on.
weighted_graph getGraph() - Method which returns a pointer to the graph.
weighted_graph copy() - Compute a deep copy of this weighted graph.
boolean isConnected() - Returns true iff there is a valid path to all nodes, uses Dijakstra algorithm.
double shortestPathDist(int src, int dest) - Returns the length of the shortest path between src to dest using Dijkstra algorithm.
void Dijkstra(int src) - Method is implementing Dijkstra algorithm from the given node. It starts on the given node, changing it tag parameter to 0 and then adding it to the Priority queue with all of its adjacent nodes, changes tag parameter to the sum of edges between them. uses a HashMap of parents to do so.
List<node_info> shortestPath(int src, int dest) - Returns the shortest path between src to dest - as an ordered list of nodes, uses the Dijkstramethod.
boolean save(String file) - Saves this weighted (undirected) graph to the given file name.
boolean load(String file) - This method load a graph to this graph algorithm. if the file was successfully loaded - the underlying graph of this class will be changed (to the loaded one), in case the graph was not loaded the original graph should remain "as is".  
cleanNode() - resets a node to his default value, as they are considerd in node_info.
