package spanningUSA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;

public class spanningUSA {

	public static void main(String[] args) {
		/*
		 *	Build the graph
		 */
		BufferedReader br = readFile(args[0]);
		Graph G = new Graph();
		
		try {
			addCities(br, G);
			addPaths(br, G);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		
		
		/*
		 *	Run Kruskal's msT and print out
		 */
		LinkedHashSet<Edge> msT = G.Kruskal();
		
		print(msT);
		System.out.println(G);
		
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void print(LinkedHashSet<Edge> msT) {
		String t = "";
		t += "EDGES\n";
		for (Edge e : msT) {
			t += e.toString();
		}
		System.out.println(t);
	}

	/* 	Reads from input file and add paths according to the scheme
	 *  			City1--City2 [pathLength]
	 *  where the cities can be multiple words enclosed in ""
	 */
	private static void addPaths(BufferedReader br, Graph G) {
		String line = null;
		while (true) {
			try {
				line = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (line == null)
				break;
			
			String[] cities = line.split("--");
			String[] dist = cities[1].split(" \\[");
			dist[1] = dist[1].substring(0, dist[1].length() - 1);
			cities[1] = dist[0];
			
			cities[0] = removeQuote(cities[0]);
			cities[1] = removeQuote(cities[1]);
			
			int distance = 0;
			try {
				distance = Integer.parseInt(dist[1]);
			} catch (NumberFormatException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			G.addEdge(cities[0],  cities[1], distance);
		}
	}
	
	/*	Helper function that removes "" from the start and end of strings */
	private static String removeQuote(String input) {
		if (input.startsWith("\"")){
			int i = input.indexOf('\"');
			int k = input.lastIndexOf('\"');
			input = input.substring(i + 1, k);	// removes quotes and trailing spaces
		}
		
		return input;
	}

	/*
	 * Read word on every line on input file and add to graph.
	 */
	private static void addCities(BufferedReader br, Graph G) throws IOException {
		String word = null;
		br.mark(100);
		while (true) {
			try {
				word = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (word == null){
				break;
			} else if (word.endsWith("]")) {
				// we've reached the start of the distance part of the file
				br.reset();
				return;
			} else {
				word = removeQuote(word);
			}
			
			br.mark(0);
			G.addVertex(word);
		}
	}

	/* Uses a bufferedReader to try to open a file @ fileName */
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
