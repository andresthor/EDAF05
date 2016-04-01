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
	int[] current;
	int[] partner;
	int n;
	
	
	public GS(Scanner input) {

        // The first zero or more lines start with “#” and are ignored.
        while (input.hasNext("#[.]") || input.hasNext("#")) {
        	//System.out.println("debug: " + input.nextLine());
        	input.nextLine();
        }
//
//        System.out.println(input.nextLine());
//		int k = 0;
//		String line;
//		String[] array = new String[2048];
//		while (input.hasNextLine()) {
//			line = input.nextLine(); 
//			if (!line.startsWith("#")) {
//				array[k] = line;
//				System.out.println(array[k]);
//				k++;
//			}
//		}

        
        // Then comes a line of the form “n=int” defining n.
        input = input.skip("n=");
        n = input.nextInt();
        input.nextLine();

        // The following 2n lines describe m1, w1, … mn, wn.
        names = new String[2*n];
        menPref = new ArrayList[2*n+1]; // 2n+1 elements, since we shift arrays "up one"
        womenPref = new ArrayList[2*n+1];
        freeMen = new LinkedList();
        next = new int[2*n];
        current = new int[2*n+1];
        partner = new int[2*n+1];
        

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
        	//System.out.println(names[i]);
        }
        
        for (int i = 1; i <= 2*n; i++){
        	current[i] = -1;
        	partner[i] = 0;
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
	
	public void print(){
		for (int man = 1; man < 2*n; man+=2) {
			int woman = partner[man];
			System.out.println(names[man-1] + " -- " + names[woman-1]);
		}
	}

	public void run() {
		// do the algorithm here
		while (!freeMen.isEmpty()) {
			int m = freeMen.get(0);

			int w = menPref[m].get(next[m]);
			
			if (current[w] == -1) {
				current[w] = m;
				partner[m] = w;
				freeMen.remove(0);
			} else {
				if (womenPref[w].indexOf(m) > womenPref[w].indexOf(current[w]) && current[w] >= 0) {

				} else {
					freeMen.remove(0);
					freeMen.addFirst(current[w]);					
					current[w] = m;
					partner[m] = w;
				}
			}
			next[m]++;
		}
		
		if (DEBUG) {
	    	System.out.println("\nCurrent:");
	        for (int c : current) {
	        	System.out.println(c);
	        }
		}
        
        
	}


	public static void main(String[] args) throws IOException {
		GS gs;

	    try {
	        //Scanner input = new Scanner(System.in);
	        Scanner input = new Scanner(new File(args[0]));
	        gs = new GS(input);
		    gs.run();
		    gs.print();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    
	    
	}
}
