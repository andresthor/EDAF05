package spanningUSA;

public class Edge implements Comparable<Edge> {
	private Vertex from, to;
	private int weight;
	
	
	/**
	 * Initializes an edge between vertices from and to with
	 * the given weight. 
	 * 
	 * @param from		One Vertex
	 * @param to		Another Vertex		
	 * @param weight	The weight of this edge (distance)
	 */
	public Edge(Vertex from, Vertex to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	/**
	 * Returns the weight of this edge.
	 * 
	 * @return the weight
	 */
	public int weight() {
		return weight;
	}
	
	/**
	 * Returns one of the points of the edge.
	 * 
	 * @return either point of this edge
	 */
	public Vertex either() {
		return from;
	}
	
	/**
	 * Returns the endpoint of the edge i.e. the point different from the given.
	 * 
	 * @param the starting vertex on this edge.
	 * @return the other vertex on this edge
	 * @throws IllegalArgumentException if the vertex is not one of the endpoints
	 */
	public Vertex other(Vertex start) {
		if 		(start.equals(to))		return from;
		else if (start.equals(from))	return to;
		else throw new IllegalArgumentException("Illegal input point");
	}
	
	/* 
	 * Compares the edges by weight.
	 */
	@Override
	public int compareTo(Edge that) {
		if 			(this.weight() < that.weight()) return -1;
		else if 	(this.weight() > that.weight()) return +1;
		else 										return 	0;
	}
	
	public String toString() {
		return String.format("%s -- %s [%d]", from.name(), to.name(), weight);
	}
	
	
	
//	public boolean equals(Object other) {
//		if (this == other) return true;
//		
//		if (other == null || this.getClass() != other.getClass())
//			return false;
//		
//		Edge that = (Edge) other;
//		Vertex a1 = that.v1;
//		Vertex a2 = that.v2;
//		Vertex b1 = this.v1;
//		Vertex b2 = this.v2;
//		
//		return 		( a1.equals(b1) && a2.equals(b2) )
//				||
//					( a1.equals(b2) && a2.equals(b1) );
//	}
//	
//	public int compareTo(Object other) {
//		if (getClass() != other.getClass()){
//			return -1;
//		}
//		
//		Edge that = (Edge) other;
//		if (v1.equals(that.v1) && v2.equals(that.v2)) {
//			return 0;
//		} else {
//			return v1.compareTo(that.v2) + v2.compareTo(that.v1);
//		}
//		
//	}
//	
//	public int hashCode() {
//		return (int) v1.name.hashCode() * v2.name.hashCode();
//	}
//	
//	public Vertex getFirst(){
//		return v1;
//	}
//	
//	public Vertex getSecond() {
//		return v2;
//	}

	
}
