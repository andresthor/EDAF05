/**
 * 
 */
package world_ladder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
		BufferedReader br = null;
		Graph G = new Graph();
		String word = null;
		
		try {
			br = new BufferedReader(new FileReader(args[0]));
		} catch (FileNotFoundException e1) {
			System.out.println("File not found.\n");
			e1.printStackTrace();
			System.exit(0);
		}
		
		/*
		 * Read word on every line on input file and add to graph. 
		 */
		while (true) {
			try {
				word = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (word==null)
				break;
			else if (word != "") {
				G.addVertex(word);
			}
		}
		
		for (Vertex v : G.getVertices()) {
			for (Vertex w : G.getVertices()) {
				if (v.name == w.name) {
					break;
				} else {
					if (v.matches(w))
						G.addEdge(w.name, v.name);
					if (w.matches(v))
						G.addEdge(v.name, w.name);
				}
			}	
		}
		
		System.out.println(G.toString());
		
		
	}
	
	

}
