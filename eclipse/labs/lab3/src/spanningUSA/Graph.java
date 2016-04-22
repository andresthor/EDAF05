package spanningUSA;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
	private static final TreeSet<Vertex> EMPTY_SET = new TreeSet<Vertex>();
	private HashMap<Vertex, TreeSet<Vertex>> neighbors;
	private HashMap<String, Vertex> myVertices;
	private TreeSet<Edge> edges;
	private int myNumVertices;
	private int myNumEdges;
	
	private LinkedHashSet<Edge> msT;
	
	/*
	 * Construct new empty Graph
	 */
	public Graph() {
		neighbors = new HashMap<Vertex, TreeSet<Vertex>>();
		myVertices = new HashMap<String, Vertex>();
		myNumVertices = myNumEdges = 0;
		edges = new TreeSet<Edge>();
		msT = new LinkedHashSet<Edge>();
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
	

	public Vertex getVertex(String name) {
		return myVertices.get(name);
	}
	
	public boolean hasVertex(String name) {
		return myVertices.containsKey(name);
	}
	
	public boolean hasEdge(String from, String to) {

		return false;
	}
	
	public boolean addEdge(String from, String to, int distance) {
		Vertex f, t;
		if ((f = getVertex(from)) == null)
			f = addVertex(from);
		if ((t = getVertex(to)) == null)
			t = addVertex(to);
		
		Edge e = new Edge(f, t, distance);
		if (edges.add(e)) {
			myNumEdges++;
			makeNeighbors(f, t);
			return true;
		}
		
		return false;
	}
	
	public LinkedHashSet<Edge> Kruskal() {
		for (Edge e : edges) {
			System.out.printf("Edge: %s\n", e);
			if (!find(e.getFirst()).equals(find(e.getSecond()))){
				msT.add(e);
				System.out.printf("%s added\n", e);	
				union(e.getFirst(), e.getSecond());
			}
		}
		
		return msT;
	}
	
	private void union(Vertex x, Vertex y) {
		Vertex xRoot = find(x);
		Vertex yRoot = find(y);
		
		if (xRoot.equals(yRoot)) return;
		
		// x and y are not in the same set, merge them
		if (xRoot.rank < yRoot.rank)
			xRoot.parent = yRoot;
		else if (xRoot.rank > yRoot.rank)
			yRoot.parent = xRoot;
		else {
			yRoot.parent = xRoot;
			xRoot.rank++;
		}
	}
	
	private Vertex find(Vertex e) {
		System.out.printf("find(%s) = ", e);
		if (!e.parent.equals(e))
			e.parent = find(e.parent);
		
		System.out.printf("%s\n", e.parent);
		return e.parent;
		
		//		while(!e.equals(e.parent)) {
//			e = e.parent;
//		}
//		return e;
		
		//		if (e.parent.equals(e)){
//			return e;
//		}
//		else {
//			return find(e.parent);
//		}
	}
	
	private void makeNeighbors(Vertex v1, Vertex v2) {
		neighbors.get(v1).add(v2);
		neighbors.get(v2).add(v1);
	}
	
	public int getDistance(String from, String to) {

		return -1;
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
		
		String t = "";
		t += "EDGES\n";
		for (Edge e : edges) {
			t += e.toString() + " : " + e.length + "\n";
		}
		
		
		return s + t;
	}	
}
