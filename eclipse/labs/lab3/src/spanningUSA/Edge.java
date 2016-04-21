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
		Vertex a1 = that.v1;
		Vertex a2 = that.v2;
		Vertex b1 = this.v1;
		Vertex b2 = this.v2;
		
		return 		( a1.equals(b1) && a2.equals(b2) )
				||
					( a1.equals(b2) && a2.equals(b1) );
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
