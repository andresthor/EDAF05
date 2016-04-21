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
	private LinkedList<Edge> minRoute;
	private TreeSet<Vertex> visitedVertices;
	
	/*
	 * Construct new empty Graph
	 */
	public Graph() {
		neighbors = new HashMap<Vertex, TreeSet<Vertex>>();
		myVertices = new HashMap<String, Vertex>();
		myNumVertices = myNumEdges = 0;
		edges = new LinkedHashMap<Edge, Integer>();
		visitedVertices = new TreeSet<Vertex>();
		minRoute = new LinkedList<Edge>();
		
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
		neighbors.get(w).add(v);
		//System.out.println(neighbors.get(v).size());
		return true;
	}
	
	public void addEdge(String from, String to, int distance) {
		//System.out.printf("addEdge: %s - %s : %d\n", from, to, distance);
		if (addEdge(from, to)) {
			Edge tmp = new Edge(this.getVertex(from), this.getVertex(to));
			edges.put(tmp, distance);
			
			int tmpDist = edges.get(tmp);
			//System.out.printf("%s - %s : %d vs %d\n", from, to, distance, tmpDist );
		}
	}
	
	public int getDistance(String from, String to) {
		//System.out.printf("\nfrom: %s to:%s\n", from, to);
		Edge tmp = new Edge(this.getVertex(from), this.getVertex(to));
		int tmpDist = 0;
		if(edges.containsKey(tmp))
			tmpDist = edges.get(tmp);
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
	
	public LinkedList<Edge> minimumSpanningTree() {
		Vertex v1 = myVertices.entrySet().iterator().next().getValue(); // Get first Vertex value from myVertices
		System.out.println(v1.name);
		if(visitedVertices != null)
			visitedVertices.clear();
		this.MSTrecursive(v1);
		return minRoute;
	}
	
	
	
	private void MSTrecursive(Vertex v1) {		
		Vertex v2 = this.getClosestNeighboor(v1); 			//Closest Neighbor not in set
		if (v2 != null) {
			System.out.println(v2.name);
			minRoute.add(new Edge(v1, v2));
			MSTrecursive(v2);
		}	
	}

	private boolean alreadyInSet(Vertex v) {
		boolean b;
		try {
			b = visitedVertices.contains(v);
		} catch (NullPointerException e) {
			b = false;
		}
		
		if (b) {
			return true;
		} else {
			visitedVertices.add(v);
			return false;
		}
	}

	private Vertex getClosestNeighboor(Vertex v1) {
		TreeSet<Vertex> ts = neighbors.get(v1);
		int minDistance = -1;
		Vertex closestNeighbor = null;
		if (ts.isEmpty())
			System.out.println("Its empty");
		for (Vertex n : neighbors.get(v1)) {
			if (!alreadyInSet(n)) {
				int newDistance = this.getDistance(n.name, v1.name);
				if (minDistance < 0 || minDistance >= newDistance) {
					minDistance = newDistance;
					closestNeighbor = n;
				}
			} 
		}
		return closestNeighbor;
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
		
//		int n = 0;
//		s += "\nEDGES\n";
//		for (Edge e : edges.keySet()) {
//			s += e.getFirst().name;
//			s += " -- ";
//			s += e.getSecond().name;
//			s += "\n";
//			n++;
//			if (n > 10) break;
//		}
//
//		s += "\n";
		
		return s;
	}	
}
