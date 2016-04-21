package spanningUSA;

import java.util.Comparator;

public class Vertex implements Comparator<Vertex>{
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
	
	
	public Vertex(String v) {
		name = v;
		distance = -1;	// -1 represents infinity
		parent = null;
	}
	
	public int hashCode()
	{
		return name.hashCode();
	}
	
	public String toString()
	{
		return name;
	}
	
	public boolean equals(Object other) {
		if (this == other) return true;
		
		if (this.getClass() != other.getClass() || other == null)
			return false;
		
		Vertex that = (Vertex) other;
		return this.name.equals(that.name);
	}
	
	public int compare(Vertex v1, Vertex v2) {
		return v1.name.compareTo(v2.name);
	}
	
//	public int compareTo(Object other)
//	{
//		if (this == other) return 0;
//		
//		Vertex that = (Vertex) other;
//		return name.compareTo(that.name);
//	}

}
