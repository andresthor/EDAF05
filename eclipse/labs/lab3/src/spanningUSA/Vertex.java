package spanningUSA;

public class Vertex implements Comparable<Vertex>{
	/**
	 * label for Vertex
	 */
	public String name;
	/**
	 * previous vertex on path from source
	 */
	public Vertex parent;
	public int rank;
	
	public Vertex(String v) {
		name = v.trim();
		parent = this;
		rank = 0;
	}
	
	public int hashCode()
	{
		return name.hashCode();
	}
	
	public String toString()
	{
		return name;
	}
	
	public int compareTo(Vertex other)
	{
		return name.compareTo(other.name);
	}
	
	public boolean equals (Object other) {
		if (this == other) return true;
				
		if (other == null || other.getClass() != getClass()){
			return false;
		}
			
		Vertex that = (Vertex) other;
		return (this.name.equals(that.name));
	}

}
