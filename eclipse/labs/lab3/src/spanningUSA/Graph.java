package spanningUSA;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.TreeSet;

import javafx.util.Pair;

/**  
 * Initializes a graph from an input stream.
 * The format is the number of vertices V,
 * followed by the number of edges E,
 * followed by E pairs of vertices, with each entry separated by whitespace.
 *
 * @param  in the input stream
 * @throws IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
 * @throws IllegalArgumentException if the number of vertices or edges is negaVertex*/
public class Graph {
	private HashMap<Vertex, TreeSet<Vertex>> neighbors;
	private LinkedHashMap<Edge, Integer> edges;
	private HashMap<String, Vertex> myVertices;
	private static final TreeSet<Vertex> EMPTY_SET = new TreeSet<Vertex>();
	private int myNumVertices;
	private int myNumEdges;
	
	/*
	 * Construct new empty Graph
	 */
	public Graph() {
		neighbors = new HashMap<Vertex, TreeSet<Vertex>>();
		myVertices = new HashMap<String, Vertex>();
		myNumVertices = myNumEdges = 0;
		edges = new LinkedHashMap<Edge, Integer>();
		
	}
	
	/*
	 * Add a new vertex with no neighbors.
	 * @param name
	 * 				vertex to be added
	 */
	public Vertex addVertex(String name) {
		Vertex v;
		v = myVertices.get(name);
		if (v == null) {
			v = new Vertex(name);
			myVertices.put(name, v);
			neighbors.put(v, new TreeSet<Vertex>());
			myNumVertices++;
		}
		return v;
	}
	
	/*
	 * Returns the Vertex matching name
	 */
	public Vertex getVertex(String name) {
		return myVertices.get(name);
	}
	
	/*
	 * Returns true if name is in this Graph, false otherwise.
	 */
	public boolean hasVertex(String name) {
		return myVertices.containsKey(name);
	}
	
	/*
	 * Is from-to, and edge in this Graph? The graph
	 * is currently undirected so the order does not matter
	 */
	public boolean hasEdge(String from, String to) {
		if (!hasVertex(from) || !hasVertex(to))
			return false;
		return neighbors.get(myVertices.get(from)).contains(myVertices.get(to));
	}
	
	/*
	 * Add Edge between from and to, i.e. add to to from's 
	 * set of neighbors, and from to to's set of neighbors.
	 * Does not add if already an edge.
	 */
	private boolean addEdge(String from, String to) {
		Vertex v, w;
		if (hasEdge(from, to))
			return false;
		myNumEdges++;
		if ((v = getVertex(from)) == null)
			v = addVertex(from);
		if ((w = getVertex(to)) == null)
			w = addVertex(to);
		neighbors.get(v).add(w);
		return true;
	}
	
	public void addEdge(String from, String to, int distance) {
		//System.out.printf("addEdge: %s - %s : %d\n", from, to, distance);
		if (addEdge(from, to)) {
			Edge tmp = new Edge(this.getVertex(from), this.getVertex(to));
			edges.put(tmp, distance);
			
			int tmpDist = edges.get(tmp);
			System.out.printf("%s - %s : %d vs %d\n", from, to, distance, tmpDist );
		}
	}
	
	public int getDistance(String from, String to) {
		//System.out.printf("\nfrom: %s to:%s\n", from, to);
		Edge tmp = new Edge(this.getVertex(from), this.getVertex(to));
		int tmpDist = edges.get(tmp);
		return tmpDist;
	}
	
	/**
	 * Returns an Iterator over Vertices in Graph.
	 * @param v
	 * @return an Iterator over all Vertices in Graph
	 */
	public Iterable<Vertex> adjacentTo(Vertex v) {
		if (!neighbors.containsKey(v))
			return EMPTY_SET;
		return neighbors.get(v);
	}
	
	public Iterable<Vertex> getVertices() {
		return myVertices.values();
	}
	
	public int numVertices() {
		return myNumVertices;
	}
	
	public int numEdges() {
		return myNumEdges;
	}
	
	public String toString() {
		String s = "";
		s += "VERTICES:\n";
		for (Vertex v : myVertices.values()) {
			s += v + ": ";
			for (Vertex w : neighbors.get(v)) {
				s += w + " ";
			}
			s += "\n";
		}
		
		int n = 0;
		s += "\nEDGES\n";
		for (Edge e : edges.keySet()) {
			s += e.getFirst().name;
			s += " -- ";
			s += e.getSecond().name;
			s += "\n";
			n++;
			if (n > 10) break;
		}
		
		s += "\n";
		
		return s;
	}	
}
