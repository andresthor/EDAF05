/**
 * 
 */
package world_ladder;

/**
 * @author Anton Friberg, Lund University
 *	
 *	Find paths between given words among the five-letter
 *  words of English.
 */
public class WordLadder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Graph G = new Graph();
		G.addEdge("A", "B");
		G.addEdge("A", "C");
		G.addEdge("C", "D");
		G.addEdge("D", "E");
		G.addEdge("D", "G");
		G.addEdge("E", "G");
		G.addVertex("H");
		System.out.println(G.toString());
	}

}
