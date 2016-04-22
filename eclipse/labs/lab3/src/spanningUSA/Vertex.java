package spanningUSA;

public class Vertex implements Comparable<Vertex>{
	/**
	 * label for Vertex
	 */
	public String name;
	/**
	 * length of shortest path from source
	 */
	public int distance;
	/**
	 * previous vertex on path from source
	 */
	public Vertex parent;
	
	public int index;
	
	/**
	 * Infinite distance indicates that there is no path
	 * from the source to this vertex
	 */
	
	public Vertex(String v) {
		name = v.trim();
		distance = -1;	// -1 represents infinity
		parent = null;
	}
	
	/**
	 * The name of the Vertex is assumed to be unique, so it
	 * is used as a HashCode
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return name.hashCode();
	}
	
	public String toString()
	{
		return name;
	}
	
	/**
	 * Compare to the basis of distance from source first and
	 * then lexicographically
	 */
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
		String s1 = this.name;
		String s2 = that.name;
		return (s1.equals(s2));
	}

	public Object name() {
		// TODO Auto-generated method stub
		return null;
	}

	public int index() {
		return index;
	}

}
