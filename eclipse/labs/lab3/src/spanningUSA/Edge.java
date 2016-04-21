package spanningUSA;

public class Edge implements Comparable {
	private Vertex v1, v2;
	
	
	public Edge(Vertex v1, Vertex v2) {
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public boolean equals(Object other) {
		if (this == other) return true;
		
		if (other == null || this.getClass() != other.getClass())
			return false;
		
		Edge that = (Edge) other;
		return 		( that.getFirst().equals(this.v1) && that.getSecond().equals(this.v2) )
				||
					( that.getFirst().equals(this.v2) && that.getSecond().equals(this.v1) );
	}
	
	public int compareTo(Object other) {
		if (getClass() != other.getClass()){
			return -1;
		}
		
		Edge that = (Edge) other;
		if (v1.equals(that.v1) && v2.equals(that.v2)) {
			return 0;
		} else {
			return v1.compareTo(that.v2) + v2.compareTo(that.v1);
		}
		
	}
	
	public Vertex getFirst(){
		return v1;
	}
	
	public Vertex getSecond() {
		return v2;
	}
}
