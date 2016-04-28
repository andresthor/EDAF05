package pairsOnAPlane;

public class Point implements Comparable<Point> {
	public double x;
	public double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/* 
	 * Overrides compareTo so that only x values is compared.
	 * Orders points by increasing x values.
	 */
	@Override
	public int compareTo(Point other) {
		return (int) ( this.x - other.x );
	}
	
	public double distanceTo(Point other) {
		return Math.sqrt(Math.pow((this.x - other.x), 2) + Math.pow((this.y - other.y), 2));
	}
}
