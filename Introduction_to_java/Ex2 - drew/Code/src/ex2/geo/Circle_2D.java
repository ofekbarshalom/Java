package ex2.geo;

/** Circle_2D:
 * This class represents a 2D circle in the plane.
 * @author ofek bar-shalom (324161421)
 */
public class Circle_2D implements GeoShape{
	private Point_2D _center;
	private double _radius;

	/**
	 * Constructs a Circle_2D object
	 * @param cen the center of the circle
	 * @param rad the radios of the circle
	 */
	public Circle_2D(Point_2D cen, double rad) {
		this._center = new Point_2D(cen);
		this._radius = rad;
	}

	/**
	 * Constructs a new Circle_2D object as a copy of the given Circle_2D object.
	 * @param c The Circle_2D object to be copied.
	 */
	public Circle_2D(Circle_2D c) {this(c.getCenter(), c.getRadius());}

	/** getRadios:
	 * @return the radios of the circle. */
	public double getRadius() {return this._radius;}

	/** getCenter:
	 * @return the center of the circle */
	public Point_2D getCenter(){ return _center;}

	/** toString:
	 * @return a string representation of the circle (center x,y and radios)
	 */
	@Override
	public String toString() { return _center.toString() + ", " + _radius; }

	/** contains:
	 * Computes if the point (ot) falls inside the circle.
	 * @param ot - a query 2D point
	 * @return true iff the point falls with in the circle.
	 */
	@Override
	public boolean contains(Point_2D ot) {
		if(this._radius >= this._center.distance(ot))
			return true;
		return false;
	}

	/** area:
	 * Computes the area of the circle.
	 * @return - the area of the circle.
	 */
	@Override
	public double area() {
		return Math.PI*Math.pow(this._radius,2);
	}

	/** perimeter:
	 * Computes the perimeter of the circle.
	 * @return the perimeter of the circle.
	 */
	@Override
	public double perimeter() {
		return 2*Math.PI*this._radius;
	}

	/** translate:
	 * Move the circle by the vector 0,0-->vec
	 * @param vec - a vector from the 0,0
	 */
	@Override
	public void translate(Point_2D vec) {
		this._center.move(vec);
	}

	/** copy:
	 * computes a new (deep) copy of the circle.
	 * @return a deep copy of the circle.
	 */
	@Override
	public GeoShape copy() { return new Circle_2D(this); }

	/** scale:
	 * Rescales the circle with respect to the given center point.
	 * @param center - center point from which the rescaling is being done.
	 * @param ratio - the ratio of rescaling.
	 */
	@Override
	public void scale(Point_2D center, double ratio) {
		_center.scale(center,ratio);
		_radius*=ratio;
	}

	/** rotate:
	 * Rotates the circle with respect to the given center point by an angle.
	 * @param center - center point from which the rotation is being done.
	 * @param angleDegrees - the angle (in Degrees) the shape should be rotated by.
	 */
	@Override
	public void rotate(Point_2D center, double angleDegrees) {
		this._center.rotate(center,angleDegrees);
	}

	/** equals:
	 * Computes if the object (p) is equal to the circle.
	 * @param p the object we compare to the circle.
	 * @return true iff the object (p) is equal to the circle.
	 */
	@Override
	public boolean equals(Object p){
		// checks if the object (p) is null or not have the instance of circle.
		if(p == null || !(p instanceof Circle_2D)) {return false;}
		Circle_2D c2 = (Circle_2D) p;
		if(c2.getCenter().equals(this._center) && this._radius == c2.getRadius()) // checks if the center and radios are equal
			return true;
		return false;
	}
}