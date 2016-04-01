/*
Implementation of the Gale-Shapley algorithm.
*/import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class GS {

	static boolean DEBUG = false;
	
	ArrayList<Integer>[] menPref;
	ArrayList<Integer>[] womenPref;
	String[] names;
	LinkedList<Integer> freeMen;
	int[] next;
	int[] proposed;
	int[] partner;
	int n;
	
	/**
	 * Creates and initializes a GS object that can then be used to run
	 * the Gale-Shapley/Stable marriage algorithm on.
	 * @param input is a file with the format laid out in the lab description
	 */
	public GS(Scanner input) {

        // The first zero or more lines start with “#” and are ignored.
		String line = input.nextLine();
		while (line.contains("#")){
			line = input.nextLine();
		}
		
		// Then comes a line of the form “n=int” defining n.
		n = Integer.parseInt(line.replaceAll("[\\D]", ""));
 
        // The following 2n lines describe m1, w1, … mn, wn.
        // We've decided to make the arrays contain NULL elements, for easier
        // indexing. So for example menPref are saved [null, m1, null, m2...]
        names = new String[2*n];			
        menPref = new ArrayList[2*n+1];		
        womenPref = new ArrayList[2*n+1];
        
        next = new int[2*n];
        proposed = new int[2*n+1];
        partner = new int[2*n+1];
        
        freeMen = new LinkedList();
        

        for (int i = 1; i <= 2*n; i+=2) {
        	menPref[i] = new ArrayList<Integer>();
        	womenPref[i+1] = new ArrayList<Integer>();
        }

        for (int i = 0; i < n; i++) {
        	freeMen.addLast(i*2 + 1);
        	next[i] = 0;
        }

        for (int i = 0; i < 2*n; i++) {
        	names[i] = input.nextLine().split(" ")[1];
        	proposed[i+1] = -1;
        	partner[i+1] = 0;
        }

        // Blank line
        input.nextLine();

        // Finally, there is a line for every id (in order) that describes a
        // preference list. The line starts with id, followed by a colon and a
        // single space.
        for (int i = 1; i < 2*n+1; i+=2) {
        	String[] manP = input.nextLine().split(" ");
        	String[] womanP = input.nextLine().split(" ");

        	for (int j = 0; j < n; j++) {
        		menPref[i].add(Integer.parseInt(manP[j+1]));
        		womenPref[i+1].add(Integer.parseInt(womanP[j+1]));
        	}
        }

        input.close();


        if (DEBUG) {
	        // Print out the inputs
	    	System.out.println("\nNames:");
	        for (int i = 0; i < 2*n; i++) {
	        	System.out.println(names[i]);
	        }
	    	System.out.println("\nMen:");
	        for (int i = 1; i < 2*n+1; i+=2) {
	        	System.out.println(menPref[i]);
	        }
	    	System.out.println("\nWomen:");
	        for (int i = 2; i < 2*n+1; i+=2) {
	        	System.out.println(womenPref[i]);
	        }
	    	System.out.println("\nFree Men:");
	        for (int f : freeMen) {
	        	System.out.println(f);
	        }
        }
	}
	
	/**
	 * Prints the results of the Gale-Shapley algorithm.
	 * @pre: Requires that run() has been executed beforehand.
	 * @post: Results printed to System.out
	 */
	public void print(){
		for (int man = 1; man < 2*n; man+=2) {
			int woman = partner[man];
			System.out.println(names[man-1] + " -- " + names[woman-1]);
		}
	}

	/**
	 * Runs the Gale-Shapley/Stable marriage algorithm on the data found in
	 * the GS object 
	 */
	public void run() {
		while (!freeMen.isEmpty()) {
			int m = freeMen.get(0);
			int w = menPref[m].get(next[m]);
			
			if (proposed[w] == -1) {	// w has not been proposed to
				proposed[w] = m;		// (m, w) are now married
				partner[m] = w;
				freeMen.remove(0);
			} else {
				if (womenPref[w].indexOf(m) < womenPref[w].indexOf(proposed[w])) {
					// w prefers m to her old man m'
					// (m, w) get married and m' is now free
					freeMen.remove(0);
					freeMen.addFirst(proposed[w]);					
					proposed[w] = m;
					partner[m] = w;
				}
			}
			next[m]++;
		}
		
		if (DEBUG) {
	    	System.out.println("\nCurrent:");
	        for (int c : proposed) {
	        	System.out.println(c);
	        }
		}
        
        
	}

	/**
	 * Runs the Gale-Shapley/Stable marriage algorithm on an input file
	 * @param args file to be parsed for the GS algorithm
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		GS gs;

	    try {
	        Scanner input = new Scanner(new File(args[0]));
	        gs = new GS(input);
		    gs.run();
		    gs.print();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
}
