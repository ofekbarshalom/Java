package ex2.geo;

import ex2.ex2.Ex2_Const;

/** Triangle_2D:
 * This class represents a 2D Triangle in the plane.
 * @author ofek bar-shalom
 */
public class Triangle_2D implements GeoShape{
	private Point_2D p1;
	private Point_2D p2;
	private Point_2D p3;

	/**
	 * Constructs a Triangle_2D object
	 * @param p1 a point of the triangle
	 * @param p2 a point of the triangle
	 * @param p3 a point of the triangle
	 */
	public Triangle_2D(Point_2D p1, Point_2D p2, Point_2D p3) {
		this.p1= new Point_2D(p1);
		this.p2= new Point_2D(p2);
		this.p3= new Point_2D(p3);
	}

	/**
	 * Constructs a new Triangle_2D object as a copy of the given Triangle_2D object.
	 * @param t1 The Triangle_2D object to be copied.
	 */
	public Triangle_2D(Triangle_2D t1) {
		this(t1.p1,t1.p2,t1.p3);
	}

	/** getAllPoints:
	 * Create a new point array and fill it with the triangle points.
	 * @return all the points of the triangle in a array of points.
	 */
	public Point_2D[] getAllPoints() {
		return new Point_2D[]{this.p1,this.p2,this.p3};
	}

	/** contains:
	 * Computes if the point (ot) falls inside the triangle by using barycentric coordinates formula.
	 * @param ot - a query 2D point
	 * @return true iff the point falls with in the triangle.
	 */
	@Override
	public boolean contains(Point_2D ot) {
		double area1 = new Triangle_2D(ot,p2,p3).area();
		double area2 = new Triangle_2D(p1,ot,p3).area();
		double area3 = new Triangle_2D(p1,p2,ot).area();
		double barycentric1 = area1 / this.area(); // compute the barycentric coordinates
		double barycentric2 = area2 / this.area();
		double barycentric3 = area3 / this.area();
		return barycentric1 >= 0 && barycentric2 >= 0 && barycentric3 >= 0 &&
				(barycentric1 + barycentric2 + barycentric3) <= 1 + Ex2_Const.EPS;
	}

	/** area:
	 * Computes the area of the triangle by using the Heron's formula.
	 * @return - the area of the triangle.
	 */
	@Override
	public double area() {
		double a=this.p1.distance(p2);
		double b=this.p2.distance(p3);
		double c=this.p3.distance(p1);
		double s=this.perimeter()/2;
		return Math.sqrt(s*(s-a)*(s-b)*(s-c)); // compute the area of the area of the triangle
	}

	/** perimeter:
	 * Computes the perimeter of the triangle.
	 * @return the perimeter of the triangle.
	 */
	@Override
	public double perimeter() {
		return this.p1.distance(p2)+this.p2.distance(p3)+this.p3.distance(p1);
	}

	/** translate:
	 * Move the triangle by the vector 0,0-->vec
	 * @param vec - a vector from the 0,0
	 */
	@Override
	public void translate(Point_2D vec) {
		this.p1.move(vec);
		this.p2.move(vec);
		this.p3.move(vec);
	}

	/** copy:
	 * computes a new (deep) copy of the triangle.
	 * @return a deep copy of the triangle.
	 */
	@Override
	public GeoShape copy() {
		return new Triangle_2D(this);
	}

	/** scale:
	 * Rescales the triangle with respect to the given center point.
	 * @param center - center point from which the rescaling is being done.
	 * @param ratio - the ratio of rescaling.
	 */
	@Override
	public void scale(Point_2D center, double ratio) {
		this.p1.scale(center,ratio);
		this.p2.scale(center,ratio);
		this.p3.scale(center,ratio);
	}

	/** rotate:
	 * Rotates the triangle with respect to the given center point by an angle.
	 * @param center - center point from which the rotation is being done.
	 * @param angleDegrees - the angle (in Degrees) the shape should be rotated by.
	 */
	@Override
	public void rotate(Point_2D center, double angleDegrees) {
		this.p1.rotate(center,angleDegrees);
		this.p2.rotate(center,angleDegrees);
		this.p3.rotate(center,angleDegrees);
	}

	/** toString:
	 * @return a string representation of the triangle (using the toString of Point_2D)
	 */
	@Override
	public String toString() {
		return p1.toString()+","+p2.toString()+","+p3.toString();
	}

	/** equals:
	 * Computes if the object (p) is equal to the triangle (if they have the same 3 points the triangles are equal)
	 * @param p the object we compare to the triangle.
	 * @return true iff the object (p) is equal to the triangle.
	 */
	@Override
	public boolean equals(Object p){
		// checks if the object (p) is null or not have the instance of triangle.
		if(p==null || !(p instanceof Triangle_2D)) {return false;}
		Triangle_2D s2 = (Triangle_2D) p;
		Point_2D[] actual = this.getAllPoints();
		Point_2D[] excepted = s2.getAllPoints();
		int count=0;  // counts the equal points in the triangle
		for (int i = 0; i < 3; i++) {  // loops over the points of the triangle and add 1 if the points are equals.
			for (int j = 0; j < 3; j++) {
				if(actual[i].equals(excepted[j])){
					count++;
				}
			}
		}
		return (count==3); // true if the number of equal points in the triangles is 3.
	}
}
