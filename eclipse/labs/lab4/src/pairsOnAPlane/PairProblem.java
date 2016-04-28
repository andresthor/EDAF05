package pairsOnAPlane;

import java.util.ArrayList;

import javafx.util.Pair;

public class PairProblem {
	protected String 	file;
	protected String 	name;
	protected String 	comment;
	protected int		dim;
	
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
