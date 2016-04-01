/*
Implementation of the Gale-Shapley algorithm.
*/import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;


public class GS {

	static boolean DEBUG = true;

	public GS(Scanner input) {
		ArrayList<Integer>[] menPref;
		ArrayList<Integer>[] womenPref;
		String[] names;
		int[] partner;
		LinkedList<Integer> freeMen;

        // The first zero or more lines start with “#” and are ignored.
        while (input.hasNext("#*")) {
        	input.nextLine();
        }

        // Then comes a line of the form “n=int” defining n.
        input = input.skip("n=");
        int n = input.nextInt();
        input.nextLine();

        // The following 2n lines describe m1, w1, … mn, wn.
        names = new String[2*n];
        menPref = new ArrayList[n];
        womenPref = new ArrayList[n];
        freeMen = new LinkedList();

        for (int i = 0; i < n; i++) {
        	menPref[i] = new ArrayList<Integer>();
        	womenPref[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < n; i++) {
        	freeMen.addLast(i*2 + 1);
        }

        for (int i = 0; i < 2*n; i++) {
        	names[i] = input.nextLine().split(" ")[1];
        	//System.out.println(names[i]);
        }

        // Blank line
        input.nextLine();

        // Finally, there is a line for every id (in order) that describes a
        // preference list. The line starts with id, followed by a colon and a
        // single space.
        for (int i = 0; i < n; i ++) {
        	String[] manP = input.nextLine().split(" ");
        	String[] womanP = input.nextLine().split(" ");

        	for (int j = 0; j < n; j++) {
        		menPref[i].add(Integer.parseInt(manP[j+1]));
        		womenPref[i].add(Integer.parseInt(womanP[j+1]));
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
	        for (int i = 0; i < n; i++) {
	        	System.out.println(menPref[i]);
	        }
	    	System.out.println("\nWomen:");
	        for (int i = 0; i < n; i++) {
	        	System.out.println(womenPref[i]);
	        }
	    	System.out.println("\nFree Men:");
	        for (int f : freeMen) {
	        	System.out.println(f);
	        }
        }
	}

	public void run() {
		// do the algorithm here
	}


	public static void main(String[] args) throws IOException {
		GS gs;

	    try {
	        //Scanner input = new Scanner(System.in);
	        Scanner input = new Scanner(new File(args[0]));
	        gs = new GS(input);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
}
