/**
 * 
 */
package world_ladder;

import java.util.HashMap;
import java.util.TreeSet;

/**  
 * Initializes a graph from an input stream.
 * The format is the number of vertices V,
 * followed by the number of edges E,
 * followed by E pairs of vertices, with each entry separated by whitespace.
 *
 * @param  in the input stream
 * @throws IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
 * @throws IllegalArgumentException if the number of vertices or edges is negative
 */
public class Graph {
	private HashMap<Vertex, TreeSet<Vertex>> neighbors;
	private HashMap<String, Vertex> myVertices;
	private static final TreeSet<Vertex> EMPTY_SET = new TreeSet<Vertex>();
	private int myNumVertices;
	private int myNumEdges;
	
	/*
	 * Construct new empty Graph
	 */
	public Graph() {
		neighbors = new HashMap<Vertex, TreeSet<Vertex>>();
		myVertices
		myNum
	}
	
}
