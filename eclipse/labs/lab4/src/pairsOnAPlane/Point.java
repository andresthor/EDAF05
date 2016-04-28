package pairsOnAPlane;

public class Point implements Comparable<Point> {
	public int x;
	public int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/* 
	 * Overrides compareTo so that only x values is compared.
	 * Orders points by increasing x values.
	 */
	@Override
	public int compareTo(Point that) {
		return this.x - that.x;
	}
}
