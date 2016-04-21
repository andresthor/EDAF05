package spanningUSA;

public class Edge implements Comparable<Edge> {
	private Vertex v1, v2;
	int length;
	
	
	public Edge(Vertex v1, Vertex v2, int length) {
		this.v1 = v1;
		this.v2 = v2;
		this.length = length;
	}
	
	public boolean equals(Edge other) {
		if (this == other) return true;

		/*
		 * 		return (x1 = x2 && y1 = y2) || (x1 = y2 && y1 = x2)
		 */
		return (   (getFirst().equals(other.getFirst()) && getSecond().equals(other.getSecond()) 	)
			     ||
			       (getFirst().equals(other.getSecond()) && getSecond().equals(other.getFirst()))  );
	}
	
	public int compareTo(Edge other) {
		return this.length - other.length;
	}
	
	public int hashCode() {
		return (int) this.v1.hashCode() * this.v2.hashCode();
	}
	
	public Vertex getFirst(){
		return v1;
	}
	
	public Vertex getSecond() {
		return v2;
	}
}
