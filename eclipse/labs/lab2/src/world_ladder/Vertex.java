package world_ladder;

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
	
	/**
	 * Infinite distance indicates that there is no path
	 * from the source to this vertex
	 */
	
	public Vertex(String v) {
		name = v;
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
		int diff = distance - other.distance;
		if (diff != 0)
			return diff;
		else
			return name.compareTo(other.name);
	}
	
	public String lastFourLetters() {
		return  name.substring(name.length() - 4);
	}

	public boolean matches(Vertex w) {
		String word = this.name;
		boolean b = true;
		String letters = w.lastFourLetters();
		for (int i = 0; i < letters.length(); i++) {
			int charIndx = word.indexOf(letters.charAt(i));
			
			if (b &= charIndx != -1)
				word = word.substring(0, charIndx) + word.substring(charIndx + 1); 
		}
		return b;
	}
}
