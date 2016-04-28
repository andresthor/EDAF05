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
	
	public double solveNaive() {
		double minDist = Double.MAX_VALUE;
		for (Point p1 : entries) {
			for (Point p2 : entries) {
				if (p1 != p2) {
					double currentDist = p1.distanceTo(p2);
					if (currentDist < minDist) minDist = currentDist;
				}
			}
		}
		
		return minDist;
	}
	
	public double solveDivideConquer() {
		ArrayList<Point> srtEntries = entries;
		
		/* Sorts in acsending order */
		Collections.sort(srtEntries,new Comparator<Point>() {
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.x, o2.x);
			}
		});
		
		double minDist = closestPoints(srtEntries);
		return minDist; 
	}
	
	/**
	 * Recursive method that splits list into two and checks closest points.
	 */
	private double closestPoints(List<Point> input) {
		if (input.size() < 2) {
			return inf;
		}
		if (input.size() == 2) {
			return input.get(0).distanceTo(input.get(1));
		} else {
			/* Split into two parts. Head is smaller if odd size. */
			int middle = input.size() / 2;
			List<Point> head = input.subList(0, middle);
			List<Point> tail = input.subList(middle, input.size());
			
			double minOver = head.get(head.size()-1).distanceTo(tail.get(0));
			double minHead = closestPoints(head);
			double minTail = closestPoints(tail);
			
			double smallest = minOver;
			if (smallest > minHead) smallest = minHead;
			if (smallest > minTail) smallest = minTail;
			return smallest;
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
