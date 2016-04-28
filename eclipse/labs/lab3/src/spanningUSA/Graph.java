package spanningUSA;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.TreeSet;

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
	private HashMap<String, Vertex> myVertices;
	private TreeSet<Edge> edges;
	private int myNumVertices = 0;
	private int myNumEdges = 0;
	private int msTDistance = 0;
	
	private LinkedHashSet<Edge> msT;
	
	/*
	 * Construct new empty Graph
	 */
	public Graph() {
		myVertices = new HashMap<String, Vertex>();
		edges = new TreeSet<Edge>();
		msT = new LinkedHashSet<Edge>();
	}
	
	/*
	 * Add a new vertex with no neighbors.
	 * @param name
	 * 				vertex to be added
	 */
	
	public Vertex addVertex(String name) {
		name = name.trim();
		
		Vertex v;
		v = myVertices.get(name);
		if (v == null) {
			v = new Vertex(name);
			myVertices.put(name, v);
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
	
	public boolean addEdge(String from, String to, int distance) {
		Vertex f, t;
		if ((f = getVertex(from)) == null)
			f = addVertex(from);
		if ((t = getVertex(to)) == null)
			t = addVertex(to);
		
		Edge e = new Edge(f, t, distance);
		if (myNumEdges == 104)
			myNumEdges = 104;
		if (edges.add(e)) {
			myNumEdges++;
			return true;
		}
		
		return false;
	}
	
	public LinkedHashSet<Edge> Kruskal() {
		for (Edge e : edges) {
			if (!find(e.getFirst()).equals(find(e.getSecond()))){
				msT.add(e);
				msTDistance += e.length;
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
		if (!e.parent.equals(e))
			e.parent = find(e.parent);
		
		return e.parent;
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
//		s += "VERTICES:\n";
//		for (Vertex v : myVertices.values()) {
//			s += v + ": ";
//			for (Vertex w : neighbors.get(v)) {
//				s += w + " ";
//			}
//			s += "\n";
//		}
//		
		String t = "";
//		t += "EDGES\n";
//		for (Edge e : edges) {
//			t += e.toString() + " : " + e.length + "\n";
//		}
		
		try {
			String info = "\n";
			info += "INFO\n";
			info += "nbr. vertices: " + numVertices() + "\n";
			info += "nbr. edges   : " + numEdges() + "\n";
			info += "msT distance : " + msTDistance;
			
			return s + t + info;
		} catch (Exception e) {
			return s + t;
		}
	}
}
