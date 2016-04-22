package spanningUSA;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import javafx.util.Pair;

public class spanningUSA {

	public static void main(String[] args) {
		BufferedReader br = readFile(args[0]);
		Graph G = new Graph();
		
		try {
			addCities(br, G);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		addPaths(br, G);
		
		//System.out.println(G);
		LinkedHashSet<Edge> msT = G.Kruskal();
		
		int distance = 0;
		for (Edge e : msT) {
			//System.out.printf("%s - %s\n", e.getFirst(), e.getSecond());
			distance += e.length;
		}
		
		//G.printVertices();
		
		System.out.printf("\nmsT distance: %d\n", distance);
		System.out.printf("msT size: %d\n", msT.size());
		System.out.printf("G numVert: %d\n", G.numVertices());
		System.out.printf("G numEdges: %d\n", G.numEdges());
		
		
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
			//System.out.printf("%s - %s : %d\n", cities[0], cities[1], distance);
			//cities[0] = cities[0].replace(' ', '#');
			//cities[1] = cities[1].replace(' ', '#');
			G.addEdge(cities[0],  cities[1], distance);
		}
	}
	
	private static String removeQuote(String input) {
		if (input.startsWith("\"")){
			int i = input.indexOf('\"');
			int k = input.lastIndexOf('\"');
			input = input.substring(i + 1, k);			// removes quotes and trailing spaces
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
			//System.out.println(word);
			//word.replace(' ', '#');
			G.addVertex(word);
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
	
	private static boolean printDistance(String c1, String c2, Graph G) {
		System.out.printf("%s -> %s = %d\n", c1, c2, G.getDistance(c1,  c2));
		return true;
	}
}
