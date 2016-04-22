package spanningUSA;

public class Edge implements Comparable {
	private Vertex v1, v2;
	public int length;
	public Edge parent;
	public int rank;
	
	
	public Edge(Vertex v1, Vertex v2, int length) {
		this.v1 = v1;
		this.v2 = v2;
		this.length = length;
		parent = this;
		rank = 0;
	}
	
	public boolean equals(Object other) {
		if (this == other) return true;
		
		if (other == null || other.getClass() != this.getClass()) return false;
		
		Edge that = (Edge) other;

		/*
		 * 		return (x1 = x2 && y1 = y2) || (x1 = y2 && y1 = x2)
		 */
		return (   (getFirst().equals(that.getFirst()) && getSecond().equals(that.getSecond()) 	)
			     ||
			       (getFirst().equals(that.getSecond()) && getSecond().equals(that.getFirst()))  );
	}
	
	public int compareTo(Object other) {
		Edge that = (Edge) other;
		int diff = this.length - that.length;
		if (diff == 0)
			return this.hashCode() - that.hashCode();
		return diff;
		//return this.length - that.length;
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
	
	public String toString() {
		return v1 + " <-> " + v2;
	}
}
