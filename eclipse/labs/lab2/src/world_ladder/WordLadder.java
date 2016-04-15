/**
 * 
 */
package world_ladder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * @author Anton Friberg, Andres Saemundsson, Lund University
 *	
 *	Find paths between given words among the five-letter
 *  words of English.
 */
public class WordLadder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader br1 = null;
		BufferedReader br2 = null;
		BufferedReader br3 = null;
		Graph G = new Graph();
		
		br1 = readFile(args[0]);		// should be the file with all vertices
		br2 = readFile(args[1]);		// should be the file we want to test
		
		buildGraph(br1, G);
		
		int[] distances = BFS(br2, G);	// get the distances between words in test file
		
		if (args.length == 2) {
			for (int i : distances)
				System.out.println(i);
		} else if (args.length > 2) {
			br3 = readFile(args[1]);
			printBFSPath(br3, G);
		} else {
			System.out.println("Wrong number of inputs");
			System.exit(1);
		}
	}
	
	private static int[] BFS(BufferedReader br, Graph G) {
		String line = null;
		LinkedList<String[]> words = new LinkedList<String[]>();
		
		try {
			while ((line = br.readLine()) != null) {
				words.add(line.split(" "));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int[] distances = new int[words.size()];
		int i = 0;
		while (!words.isEmpty()) {
			String[] twoWords = words.pop();
			distances[i] = G.BFS(G.getVertex(twoWords[0]), G.getVertex(twoWords[1]));
			i++;
		}
		
		return distances;
	}
	
	private static void printBFSPath(BufferedReader br, Graph G) {
		String line = null;
		LinkedList<String[]> words = new LinkedList<String[]>();
		
		try {
			while ((line = br.readLine()) != null) {
				words.add(line.split(" "));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!words.isEmpty()) {
			String[] twoWords = words.pop();
			LinkedList<Vertex> path = G.BFSPath(G.getVertex(twoWords[0]), G.getVertex(twoWords[1]));
			for (Vertex v : path) {
				System.out.printf(v.name);
				if (v != path.getLast()) {
					System.out.printf(" ,");
				}
			}
			System.out.println();
		}
	}
	
	/*
	 * Read word on every line on input file and add to graph. 
	 */
	private static void buildGraph(BufferedReader br, Graph G) {
		String word = null;
		while (true) {
			try {
				word = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (word == null)
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
	}
	
	private static BufferedReader readFile(String fileName) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			System.out.println("File not found.\n");
			e1.printStackTrace();
			System.exit(0);
		}
		
		return br;
	}
	
	

}
