package spanningUSA;

/**
 * Edge class of undirected graph.
 * @author Anton
 *
 */
public class Edge implements Comparable<Edge> {
	private Vertex from, to;
	private int weight;
	
	public Edge(Vertex from, Vertex to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	/* 
	 * Compare to function
	 * Greater weight -> is greater than return.
	 * @param o : The Edge to compare.
	 */
	@Override
	public int compareTo(Edge o) {
		if (this.getWeight() > o.getWeight()) {
			return 1;
		} else if (this.getWeight() < o.getWeight()) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public Vertex getFrom() {
		return from;
	}
	
	public void setFrom(Vertex from) {
		this.from = from;
	}
	
	public Vertex getTo() {
		return to;
	}
	
	public void setTo(Vertex to) {
		this.to = to;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
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
