package spanningUSA;

public class Edge implements Comparable<Edge> {
	private Vertex v1, v2;
	
	
	public Edge(Vertex v1, Vertex v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public boolean equals(Edge other) {
		if (this == other) return true;
				
		return this.compareTo(other) == 0;
	}
	
	public int compareTo(Edge other) {
		if (this == other) return 0;

		if (v1.equals(other.v1) && v2.equals(other.v2)) {
			return 0;
		} else {
			return v1.compareTo(other.v2) + v2.compareTo(other.v1);
		}
	}
	
	public int hashCode() {
		return (int) v1.name.hashCode() * v2.name.hashCode();
	}
	
	public Vertex getFirst(){
		return v1;
	}
	
	public Vertex getSecond() {
		return v2;
	}
}
