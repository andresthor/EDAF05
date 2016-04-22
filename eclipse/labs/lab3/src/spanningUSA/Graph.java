package spanningUSA;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
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
		String name2 = "";//name.trim();
		name = name.trim();
		Vertex v;
		v = myVertices.get(name);
		
		if (name2.equals("Ravenna ")) {
			System.out.printf("#%s# vs #%s\n", name, name2);
		}
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
		if (myNumEdges == 104)
			myNumEdges = 104;
		if (edges.add(e)) {
			myNumEdges++;
			makeNeighbors(f, t);
			return true;
		} else {
			Edge culp = null;
			for (Edge tmp : edges) {
				if (tmp.equals(e)) culp = tmp;
			}
			System.out.printf("not added: %s <-> %s\n", e.getFirst(), e.getSecond());
			if (culp != null)
				System.out.printf("culprit: %s <-> %s\n\n", culp.getFirst(), culp.getSecond());
			else
				System.out.println("No culprit found");
		}
		
		return false;
	}
	
	public LinkedHashSet<Edge> Kruskal() {
		for (Edge e : edges) {
			//System.out.printf("Edge: %s - size: %d\n", e, e.length);
			if (!find(e.getFirst()).equals(find(e.getSecond()))){
				msT.add(e);
				//System.out.printf("%s added\n", e);
				//System.out.printf("%s + \n", e.length);
				union(e.getFirst(), e.getSecond());
			}
		}
		return msT;
	}
	
	private void union(Vertex x, Vertex y) {
//		Vertex xRoot = find(x);
//		Vertex yRoot = find(y);
//		
//		if (xRoot.equals(yRoot)) return;
//		
//		// x and y are not in the same set, merge them
//		if (xRoot.rank < yRoot.rank)
//			xRoot.parent = yRoot;
//		else if (xRoot.rank > yRoot.rank)
//			yRoot.parent = xRoot;
//		else {
//			yRoot.parent = xRoot;
//			xRoot.rank++;
//		}
		Vertex xRoot = find(x);
		Vertex yRoot = find(y);
		
		xRoot.parent = yRoot;
	}
	
	private Vertex find(Vertex e) {
		//System.out.printf("find(%s) = ", e);
//		if (!e.parent.equals(e))
//			e.parent = find(e.parent);
//		
//		return e.parent;
		
		if (e.parent.equals(e))
			return e;
		else
			return find(e.parent);
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
	
	public void printVertices() {
//		List<String> vList = new ArrayList<String>();
//		for (Vertex v : myVertices.values()) {
//			vList.add(v.name);
//		}
//		Collections.sort(vList);
//		
//		int n = 1;
//		for (String s : vList) {
//			System.out.printf("%d. \"%s\"\n", n, s);
//			n++;
//		}
		
		List<Vertex> vList2 = new ArrayList<Vertex>();
		for (Vertex v : myVertices.values()) {
			vList2.add(v);
		}
		Collections.sort(vList2);
		
		int n = 1;
		for (Vertex v : vList2) {
			System.out.printf("%d. \"%s\" : %s\n", n, v.name, v.hashCode());
			n++;
		}
	}
}
