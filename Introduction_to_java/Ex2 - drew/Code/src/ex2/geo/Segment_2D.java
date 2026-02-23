package ex2.geo;

import ex2.ex2.Ex2_Const;

/** Segment_2D:
 * This class represents a 2D segment on the plane.
 * @author ofek bar-shalom
 */
public class Segment_2D implements GeoShape{
	private final Point_2D p1;
	private final Point_2D p2;

	/**
	 * Constructs a Segment_2D object
	 * @param p1  the starting point of the segment.
	 * @param p2  the ending point of the segment.
	 */
	public Segment_2D(Point_2D p1, Point_2D p2) {
		this.p1= new Point_2D(p1);
		this.p2= new Point_2D(p2);
	}

	/**
	 * Constructs a new Segment_2D object as a copy of the given Segment_2D object.
	 * @param t1 The Segment_2D object to be copied.
	 */
	public Segment_2D(Segment_2D t1) {
		this(t1.get_p1(),t1.get_p2());
	}

	/** get_p1:
	 * @return the starting point of the segment. */
	public Point_2D get_p1() {return this.p1;}

	/** get_p2:
	 * @return the ending point of the segment. */
	public Point_2D get_p2() {return this.p2;}

	/** contains:
	 * Computes if the point (ot) falls on the segment.
	 * @param ot - a query 2D point
	 * @return true iff the point falls on the segment.
	 */
	@Override
	public boolean contains(Point_2D ot) {
        return this.p1.distance(ot) + this.p2.distance(ot) - this.p1.distance(this.p2) < Ex2_Const.EPS1;
	}

	/** area:
	 * Computes the area of the segment.
	 * @return - the area of the segment.
	 */
	@Override
	public double area() {
		return 0;
	}

	/** perimeter:
	 * Computes the perimeter of the segment.
	 * @return the perimeter of the segment.
	 */
	@Override
	public double perimeter() {
		return this.p1.distance(this.p2)*2;
	}

	/** translate:
	 * Move the segment by the vector 0,0-->vec
	 * @param vec - a vector from the 0,0
	 */
	@Override
	public void translate(Point_2D vec) {
		this.p1.move(vec);
		this.p2.move(vec);
	}

	/** copy:
	 * computes a new (deep) copy of the segment.
	 * @return a deep copy of the segment.
	 */
	@Override
	public GeoShape copy() {
		return new Segment_2D(this);
	}

	/** scale:
	 * Rescales the segment with respect to the given center point.
	 * @param center - center point from which the rescaling is being done.
	 * @param ratio - the ratio of rescaling.
	 */
	@Override
	public void scale(Point_2D center, double ratio) {
		this.p1.scale(center,ratio);
		this.p2.scale(center,ratio);
	}

	/** rotate:
	 * Rotates the segment with respect to the given center point by an angle.
	 * @param center - center point from which the rotation is being done.
	 * @param angleDegrees - the angle (in Degrees) the shape should be rotated by.
	 */
	@Override
	public void rotate(Point_2D center, double angleDegrees) {
		this.p1.rotate(center,angleDegrees);
		this.p2.rotate(center,angleDegrees);
	}

	/** toString:
	 * @return a string representation of the segment (using the toString of Point_2D)
	 */
	@Override
	public String toString() {
		return p1.toString()+","+p2.toString();
	}

	/** equals:
	 * Computes if the object (p) is equal to the segment (if they have the same 2 points the segments are equal)
	 * @param p the object we compare to the segment.
	 * @return true iff the object (p) is equal to the segment.
	 */
	@Override
	public boolean equals(Object p){
		if(p==null || !(p instanceof Segment_2D)) {return false;}
        Segment_2D s2 = (Segment_2D) p;
		if(s2.p1.equals(this.p1) && s2.p2.equals(this.p2)) // checks if the segments are identical
			return true;
		if(s2.p1.equals(this.p2) && s2.p2.equals(this.p1)) // checks if the segments have the same points (symmetric)
			return true;
		return false;
    }
}