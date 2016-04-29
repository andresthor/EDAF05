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
	
	public double solveNaiveDist() {
		return solveNaiveDist(entries);
	}
	
	public double solveDivideConquer() {
		ArrayList<Point> srtEntriesX = entries;
		ArrayList<Point> srtEntriesY = entries;
		
		/* Sorts in acsending order */
		Collections.sort(srtEntriesX,new Comparator<Point>() {
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.x, o2.x);
			}
		});
		
		Collections.sort(srtEntriesY,new Comparator<Point>() {
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.y, o2.y);
			}
		});
		
		double minDist = closestPoints(srtEntriesX, srtEntriesY);
		return minDist; 
	}
	
	/**
	 * Recursive method that splits list into two and checks closest points.
	 * @param srtY 
	 */
	private double closestPoints(List<Point> srtX, List<Point> srtY) {
		if (srtX.size() <= 3) {
			return solveNaiveDist(srtX); // Does not matter which one
		} else {
			/* Split each into two parts. Head is smaller if odd size. */
			int middleX = srtX.size() / 2;
			int middleY = srtY.size() / 2;
			List<Point> qX = srtX.subList(0, middleX);
			List<Point> rX = srtX.subList(middleX, srtX.size());
			List<Point> qY = srtY.subList(0, middleY);
			List<Point> rY = srtY.subList(middleY, srtY.size());
			
			double minQ = closestPoints(qX, qY);
			double minR = closestPoints(rX, rY);
			
			//smallest distance so far
			double delta = (minQ < minR) ? minQ : minR;
			
			// Finds points on the dividing line, put into L
			Point pMaxQ = qX.get(qX.size() - 1); //Points closest to line.
			List<Point> L = new ArrayList<Point>(); // List with points on line. 
			L.add(pMaxQ);
			for (int i = qX.size()-2; i >= 0; i--) {
				if (qX.get(i).compareTo(pMaxQ) == 0)
					L.add(qX.get(i));
			}
			
			// Points within delta distance of points on line, put into S.
			List<Point> S = new ArrayList<Point>();
			for (Point pLine : L) {
				int indexY = srtY.indexOf(pLine);
				
				for (int i = indexY; i < srtY.size() -1 ; i++) {
					Point pUp 	= srtY.get(i);
					if (pUp.equals(pLine))
						continue;
					if (pUp.distanceTo(pLine) < delta)
						S.add(pUp);
					else 
						break;
				}
				
				for (int i = indexY-1; i >= 0; i--) {
					Point pDn = srtY.get(i);
					if (pDn.equals(pLine))
						continue;
					if (pDn.distanceTo(pLine) < delta)
						S.add(pDn);
					else 
						break;
				}
			}
			
			// Sort by y coordinte, ascending.
			Collections.sort(S,new Comparator<Point>() {
				public int compare(Point o1, Point o2) {
					return Double.compare(o1.y, o2.y);
				}
			});
			
			// Find distance between points in S, compare max 15. 
			double minDist = inf;
			for (Point s : S) {
				for (int i = S.indexOf(s) + 1; i < S.indexOf(s) + 16; i++) {
					if (i > S.size()-1)
						break;
					Point sPrime = S.get(i);
					double thisDist = s.distanceTo(sPrime);
					minDist = (minDist > thisDist) ? thisDist : minDist;
				}
			}
			
			// Find minimum of right, left and over border. 
			minDist = (minDist > delta) ? delta : minDist;
			return minDist;
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
