package ex2.geo;

/** Polygon_2D:
 * This class represents a 2D axis parallel rectangle.
 * @author ofek bar_shalom (324161421)
 */
public class Rect_2D implements GeoShape {
	private Point_2D p1, p2, p3, p4;

	 /**
	  * Constructs a Rect_2D object using 2 points and building the other 2 with the data on p1 and p2
	 * @param p1 a corner of the rectangle (Point_2D object)
	 * @param p2 a corner of the rectangle in a opposite direction of p1
	 */
	 public Rect_2D(Point_2D p1, Point_2D p2) {
		this.p1 = new Point_2D(p1);
		this.p2 = new Point_2D(p2);
		this.p3 = new Point_2D(p1.x(),p2.y());
		this.p4 = new Point_2D(p2.x(),p1.y());
	}

	/**
	 * constructs a Rect_2D object using 4 points
	 * @param p1 a corner in the rectangle
	 * @param p2 a corner in the rectangle
	 * @param p3 a corner in the rectangle
	 * @param p4 a corner in the rectangle
	 */
	public Rect_2D(Point_2D p1, Point_2D p2, Point_2D p3, Point_2D p4) {
			this.p1 = new Point_2D(p1);
			this.p2 = new Point_2D(p2);
			this.p3 = new Point_2D(p3);
			this.p4 = new Point_2D(p4);
	}

	/**
	 * Constructs a new Rect_2D object as a copy of the given Rect_2D object.
	 * @param r1 The Rect_2D object to be copied.
	 */
	public Rect_2D(Rect_2D r1) {
		this(r1.p1, r1.p2);
	}

	 /** get_p1:
	  * @return p1 in the Rect_2D (the first corner) */
	public Point_2D get_p1() {return this.p1;}

	/** get_p2:
	 * @return p2 in the Rect_2D (the second corner) */
	public Point_2D get_p2() {return this.p2;}

	/** get_p3:
	 * @return p3 in the Rect_2D (the third corner) */
	public Point_2D get_p3() {return this.p3;}

	/** get_p4:
	 * @return p4 in the Rect_2D (the fourth corner) */
	public Point_2D get_p4() {return this.p4;}

	/** contains:
	 * Computes if the point (ot) falls inside the rectangle.
	 * @param ot - a query 2D point
	 * @return true iff the point falls with in the rectangle.
	 */
	@Override
	public boolean contains(Point_2D ot) {
		if (this.p1.y() < this.p2.y()) {  // checks if p1 one of the bottom corners
			if (this.p1.y() <= ot.y() && ot.y() <= this.p2.y()) { // if so checks if the point (ot) is within the y area of the rectangle
				if (this.p1.x() < this.p2.x()) { // checks if p1 one of the left corners
					if (this.p1.x() <= ot.x() && ot.x() <= this.p2.x()) // if so checks if the point (ot) is within the x area of the rectangle
						return true;
				}
				else if (this.p2.x() <= ot.x() && ot.x() <= this.p1.x()) // p1 one of the right corners, checks if the point (ot) is within the x area of the rectangle
					return true;
			}
		}
		else { //p1 one of the top corners
			if (this.p2.y() <= ot.y() && ot.y() <= this.p1.y()) { // checks if the point (ot) is within the y area of the rectangle
				if (this.p1.x() < this.p2.x()) {    // checks if p1 one of the left corners
					if (this.p1.x() <= ot.x() && ot.x() <= this.p2.x())  // checks if the point (ot) is within the x area of the rectangle
						return true;
				}
				else if (this.p2.x() <= ot.x() && ot.x() <= this.p1.x()){ // p1 one of the right corners, checks if the point (ot) is within the x area of the rectangle
					return true;
				}
			}
		}
		return false;
	}

	/** area:
	 * Computes the area of the rectangle.
	 * @return - the area of the rectangle.
	 */
	@Override
	public double area() {
		return this.p2.distance(p3)* this.p1.distance(p3);
	}

	/** perimeter:
	 * Computes the perimeter of the rectangle.
	 * @return the perimeter of the rectangle.
	 */
	@Override
	public double perimeter() {
		return 2*this.p2.distance(p3)+2*this.p1.distance(p3);
	}

	/** translate:
	 * Move the rectangle by the vector 0,0-->vec
	 * @param vec - a vector from the 0,0
	 */
	@Override
	public void translate(Point_2D vec) {
		this.p1.move(vec);
		this.p2.move(vec);
		this.p3.move(vec);
		this.p4.move(vec);
	}

	/** copy:
	 * computes a new (deep) copy of the rectangle.
	 * @return a deep copy of the rectangle.
	 */
	@Override
	public GeoShape copy() {
		Rect_2D copiedRec = new Rect_2D(new Point_2D(this.p1), new Point_2D(this.p2));
		copiedRec.p3 = new Point_2D(this.p3);
		copiedRec.p4 = new Point_2D(this.p4);
		return copiedRec;
	}

	/** scale:
	 * Rescales the rectangle with respect to the given center point.
	 * @param center - center point from which the rescaling is being done.
	 * @param ratio - the ratio of rescaling.
	 */
	@Override
	public void scale(Point_2D center, double ratio) {
		this.p1.scale(center,ratio);
		this.p2.scale(center,ratio);
		this.p3.scale(center,ratio);
		this.p4.scale(center,ratio);
	}

	/** rotate:
	 * Rotates the rectangle with respect to the given center point by an angle.
	 * @param center - center point from which the rotation is being done.
	 * @param angleDegrees - the angle (in Degrees) the shape should be rotated by.
	 */
	@Override
	public void rotate(Point_2D center, double angleDegrees) {
		if (angleDegrees == 0) {return;}
		this.p1.rotate(center,angleDegrees);
		this.p2.rotate(center,angleDegrees);
		this.p3.rotate(center,angleDegrees);
		this.p4.rotate(center,angleDegrees);
	}

	/** toString:
	 * @return a string representation of the rectangle (using the toString of Point_2D)
	 */
	@Override
	public String toString() {
		return p1.toString()+","+p3.toString()+","+p2.toString()+","+p4.toString();
	}

	/** equals:
	 * Computes if the object (p) is equal to the rectangle (if they have the same 4 points the rectangles are equal)
	 * @param p the object we compare to the rectangle.
	 * @return true iff the object (p) is equal to the rectangle.
	 */
	@Override
	public boolean equals(Object p){
		// checks if the object (p) is null or not have the instance of rectangle.
		if(p==null || !(p instanceof Rect_2D)) {return false;}
		Rect_2D r2 = (Rect_2D) p;
		Point_2D[] actual = new Point_2D[]{p1,p2,p3,p4};
		Point_2D[] excepted = new Point_2D[]{r2.get_p1(),r2.get_p2(),r2.get_p3(),r2.get_p4()};
		int count=0;  // counts the equal points in the rectangle.
		for (int i = 0; i < 4; i++) { // loops over the points of the rectangle and add 1 if the points are equals.
			for (int j = 0; j < 4; j++) {
				if(actual[i].equals(excepted[j])){
					count++;
				}
			}
		}
		return (count==4); // true if the number of equal points in the rectangle is 4.
	}
}