package pairsOnAPlane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.util.Pair;

public class PairProblem {
	protected String 	file;
	protected String 	name;
	protected String 	comment;
	protected int		dim;
	private static double inf = Double.POSITIVE_INFINITY;
	private static int bW = 8; //bandWidth
	
	ArrayList<Point> entries;
	
	public PairProblem(String fileName) {
		file	= fileName;
		name 	= "";
		comment = "";
		dim = 0;
		
		entries = new ArrayList<Point>();
	}
	
	protected void appendComment(String c) {
		if (comment.equals("")) {
			comment = c;
		} else {
			comment += "\n" + c;
		}
	}
	
	protected void addEntry(String v, double x, double y) {
		// Not doing anything with v atm
		entries.add(new Point(x, y));
	}
	
	public double solveNaiveDist(List<Point> input) {
		Pair<Point, Point> pt = solveNaive(input);
		return pt.getKey().distanceTo(pt.getValue());
	}
	
	public Pair<Point, Point> solveNaive(List<Point> input) {
		double minDist = Double.MAX_VALUE;
		Pair<Point, Point> closest = null;
		for (Point p1 : input) {
			for (Point p2 : input) {
				if (p1 != p2) {
					double currentDist = p1.distanceTo(p2);
					if (currentDist < minDist) {
						minDist = currentDist;
						closest = new Pair<Point, Point>(p1, p2);
					}
				}
			}
		}
		
		return closest;
	}
	
	public int size() {
		return entries.size();
	}
	
	public double solveNaiveDist() {
		return solveNaiveDist(entries);
	}
	
	public Pair<Point, Point> solveDivideConquer() {
		ArrayList<Point> srtEntriesX = entries;
		ArrayList<Point> srtEntriesY = entries;
		
		/* Sorts in ascending order based on x-coordinates */
		Collections.sort(srtEntriesX,new Comparator<Point>() {
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.x, o2.x);
			}
		});
		
		/* Sorts in ascending order based on y-coordinates */
		Collections.sort(srtEntriesY,new Comparator<Point>() {
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.y, o2.y);
			}
		});
		
		return closestPoints(srtEntriesX, srtEntriesY);
	}
	
	public double solveDivideConquerDist() {
		Pair<Point, Point> minPair = solveDivideConquer();
		return minPair.getKey().distanceTo(minPair.getValue()); 
	}
	
	/**
	 * Recursive method that splits list into two and checks closest points.
	 * @param srtY 
	 */
	private Pair<Point, Point> closestPoints(List<Point> Px, List<Point> Py) {
		if (Px.size() <= 3) {
			return solveNaive(Px); // Does not matter which one (Px or Py)
		} else {
			/*  Split each into two parts. Head is smaller if odd size.
			 *  q is the front half, r is the back half
			 */
			int midX = Px.size() / 2;
			int midY = Py.size() / 2;
			List<Point> qX = Px.subList(0, midX);
			List<Point> rX = Px.subList(midX, Px.size());
			List<Point> qY = Py.subList(0, midY);
			List<Point> rY = Py.subList(midY, Py.size());
			
			/* Recursively divide further */
			Pair<Point, Point> qRes = closestPoints(qX, qY);
			Pair<Point, Point> rRes = closestPoints(rX, rY);
			
			/* Get the shortest paths from the divided subproblems */
			double minQ = qRes.getKey().distanceTo(qRes.getValue());
			double minR = rRes.getKey().distanceTo(rRes.getValue());
			
			// The smallest distance so far is the smallest of the two above...
			double delta = (minQ < minR) ? minQ : minR;
			// ...this is then the closest pair so far
			Pair<Point, Point> closePair = (minQ < minR) ? qRes : rRes;
			
			/*  Now we need to check "across the lines". So we use the "grid
			 *  approach" and check the closest points
			 */
			double minDist = inf;
			Pair<Point, Point> minPair = null;
			Point mX = Px.get(midX);
			Point mY = Py.get(midY); 
			
			// for all p in P
			for (int i = 0; i < Py.size(); i++) {
				Point current = Py.get(i);
				if ((current.x - mX.x) < delta) {	// if closer to line than delta
					
					int jMin = (i - bW > 0) ? (i - bW) : 0;
					int jMax = (i + bW < Py.size()) ? (i + bW) : Py.size();
					
					for (int j = jMin; j < jMax; j++) {		// ...on the y-axis
						if (j == i) continue;	// do not check current node
						
						Point closePoint = Py.get(j);
						double tmpDist = current.distanceTo(closePoint);
						if (tmpDist < minDist) {			// are they the closest?
							minDist = tmpDist;
							minPair = new Pair<Point, Point>(current, closePoint);
						}
					}
				}
			}
			return (delta < minDist) ? closePair : minPair;
		}
	}

	public String toString() {
		return 	"File: " 		+ file 		+ "\n" + 
				"Name: " 		+ name 		+ "\n" +
				"Comment: " 	+ comment 	+ "\n" +
				"Dimension: "	+ dim;
	}
	
	public String print() {
		StringBuilder sb = new StringBuilder();
		for (Point p : entries) {
			sb.append(p.x);
			sb.append(" : ");
			sb.append(p.y);
			sb.append("\n");
		}
		return sb.toString();
	}
}
