package ex2.geo;
import java.lang.reflect.Array;
import java.util.ArrayList;

/** Polygon_2D:
 * This class represents a Polygon in the plane.
 * @author ofek bar-shalom (324161421)
 */
public class Polygon_2D implements GeoShape{
	private ArrayList<Point_2D> vertex;

	/** Constructs a Polygon_2D object as a ArrayList */
	public Polygon_2D() {
		this.vertex = new ArrayList<>();
	}

	/**
	 * Constructs a new Polygon_2D object as a copy of the given Polygon_2D object.
	 * @param po The Polygon_2D object to be copied.
	 */
	public Polygon_2D(Polygon_2D po) { this.vertex = new ArrayList<>(po.vertex); }

	/** getAllPoints:
	 * Create a new point array and fill it with the polygon's points using a loop.
	 * @return all the points of the polygon in a array of points.
	 */
	public Point_2D[] getAllPoints() {
		Point_2D[] allPoints = new Point_2D[this.vertex.size()];
		for (int i = 0; i < this.vertex.size(); i++)
			allPoints[i]= this.vertex.get(i);
		return allPoints;
	}

	/** add:
	 * Add a new point to the polygon if the point it not already exist in it.
	 * @param p the point to add to the polygon.
	 */
	public void add(Point_2D p) {
		if(!(this.vertex.contains(p)))
			this.vertex.add(p);
	}

	/** toString:
	 * @return a string representation of the polygon using the toString function of array list.
	 */
	@Override
	public String toString() {
		return vertex.toString();
	}

	/** contains:
	 * Computes if the point (ot) falls inside the polygon by using ray casting formula.
	 * @param ot - a query 2D point
	 * @return true iff the point falls with in the polygon.
	 */
	@Override
	public boolean contains(Point_2D ot) {
		int i, j;
		boolean result = false;
		for (i = 0, j = vertex.size() - 1; i < vertex.size(); j = i++) {
			if ((vertex.get(i).y() > ot.y()) != (vertex.get(j).y() > ot.y()) &&
					(ot.x() < (vertex.get(j).x() - vertex.get(i).x()) * (ot.y() - vertex.get(i).y()) / (vertex.get(j).y() - vertex.get(i).y()) + vertex.get(i).x())) {
				result = !result;
			}
		}
		return result;
	}

	/** area:
	 * Computes the area of the polygon by using the shoelace formula.
	 * @return - the area of the polygon.
	 */
	@Override
	public double area() {
		double area=0;
		for (int i = 0; i < this.vertex.size()-1; i++) {
			double x1= this.vertex.get(i).x();
			double y1= this.vertex.get(i).y();
			double x2= this.vertex.get(i+1).x();
			double y2= this.vertex.get(i+1).y();
			area+=(x1*y2)-(x2*y1);
		}
		return Math.abs(area/2.0);
	}

	/** perimeter:
	 * Computes the perimeter of the polygon.
	 * @return the perimeter of the polygon.
	 */
	@Override
	public double perimeter() {
		double preimeter=0;
		for (int i = 0; i < this.vertex.size()-1; i++)
			preimeter+=this.vertex.get(i).distance(this.vertex.get(i+1));
		preimeter+=this.vertex.get(this.vertex.size()-1).distance(this.vertex.get(0));
		return preimeter;
	}

	/** translate:
	 * Move the polygon by the vector 0,0-->vec
	 * @param vec - a vector from the 0,0
	 */
	@Override
	public void translate(Point_2D vec) {
		for (int i = 0; i < this.vertex.size(); i++)
			this.vertex.get(i).move(vec);
	}

	/** copy:
	 * computes a new (deep) copy of the polygon.
	 * @return a deep copy of the polygon.
	 */
	@Override
	public GeoShape copy() {
		Polygon_2D copiedPolygon = new Polygon_2D();
		for (int i=0;i<this.vertex.size();i++)
			copiedPolygon.add(new Point_2D(this.vertex.get(i)));
		return copiedPolygon;
	}

	/** scale:
	 * Rescales the polygon with respect to the given center point.
	 * @param center - center point from which the rescaling is being done.
	 * @param ratio - the ratio of rescaling.
	 */
	@Override
	public void scale(Point_2D center, double ratio) {
		for (int i = 0; i < this.vertex.size(); i++)
			this.vertex.get(i).scale(center,ratio);
	}

	/** rotate:
	 * Rotates the polygon with respect to the given center point by an angle.
	 * @param center - center point from which the rotation is being done.
	 * @param angleDegrees - the angle (in Degrees) the shape should be rotated by.
	 */
	@Override
	public void rotate(Point_2D center, double angleDegrees) {
		for (int i = 0; i < this.vertex.size(); i++)
			this.vertex.get(i).rotate(center,angleDegrees);
	}

	/** equals:
	 * Computes if the object (p) is equal to the polygon.
	 * @param p the object we compare to the polygon.
	 * @return true iff the object (p) is equal to the polygon.
	 */
	@Override
	public boolean equals(Object p){
		// checks if the object (p) is null or not have the instance of polygon.
		if(p == null || !(p instanceof Polygon_2D)) {return false;}
		Polygon_2D p2 = (Polygon_2D) p;
		Point_2D[] actual = this.getAllPoints();
		Point_2D[] excepted = p2.getAllPoints();
		if(actual.length != excepted.length) {return false;} //checks if the polygons as the same number of points
		int count = 0; // counts the equal points in the polygons in clockwise
		int equalsAtActual = -1;   // The index were the actual polygon point is equal to the compered polygon point.
		int equalsAtExpected = -1; // The index were the compered polygon point is equal to the actual polygon point.
		int equalsAtActual2, equalsAtExpected2;

		for (int i = 0; i < actual.length; i++) {// loops over the points of the polygons and finds the index were there equal.
			for (int j = 0; j < actual.length; j++) {
				if (actual[i].equals(excepted[j])){ // if the points in the polygons are equal, save the index in witch there are equal
					equalsAtActual = i;
					equalsAtExpected = j;
				}
				if(equalsAtExpected != -1 || equalsAtActual != -1){break;} // we find the index were the points are equal, no need to continue
			}
			if(equalsAtExpected != -1 || equalsAtActual != -1){break;} // we find the index were the points are equal, no need to continue
		}

		if(equalsAtExpected == -1 && equalsAtActual == -1) {return false;} // if the loop didn't find a equal point in the polygons return false
		else{equalsAtExpected2 = equalsAtExpected; equalsAtActual2 = equalsAtActual;}

		for (int i = 0; i < actual.length; i++) { // loops over the points of the polygons and add 1 to count if the points are equals.
			if (actual[equalsAtActual].equals(excepted[equalsAtExpected])){
				count++;
				if(equalsAtActual == actual.length-1) {equalsAtActual = 0;} // if the point in the actual array is the lest point of the array reset equalsAtActual to 0.
				else {equalsAtActual++;} // else move over to the next point in the array
				if(equalsAtExpected == actual.length-1) {equalsAtExpected =0;}  // if the point in the expected array is the lest point of the array reset equalsAtActual to 0.
				else {equalsAtExpected++;}  // else move over to the next point in the array
			}
		}

		if(count == actual.length){ return true;} // if the polygons are equal return true
		else {count = 0;} // else resent the count to check if they equal clockwise

		for (int i = 0; i < actual.length; i++) {
				if (actual[equalsAtActual2].equals(excepted[equalsAtExpected2])){
					count++;
				if(equalsAtActual2 == actual.length-1) {equalsAtActual2 = 0;} // if the point in the actual array is the lest point move to the first point.
				else {equalsAtActual2++;} // else move over to the next point in the array
				if(equalsAtExpected2 == 0) {equalsAtExpected2 = actual.length-1;}  // if the point in the expected array is the first point move the the last point.
				else {equalsAtExpected2--;}  // else move back to the previous point in the array
			}
		}
		return(count == actual.length); // true if the number of equal points in the polygons equals the total number of points.
	}
}
