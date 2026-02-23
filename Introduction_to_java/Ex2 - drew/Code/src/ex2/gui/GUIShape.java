package ex2.gui;

import ex2.ex2.ShapeCollection;
import ex2.geo.*;
import java.awt.*;

/** GUIShape:
 * This class implements the GUI_shape.
 * @author ofek bar-shalom
 */
public class GUIShape implements GUI_Shape{
	private GeoShape _g = null;
	private boolean _fill;
	private Color _color;
	private int _tag;
	private boolean _isSelected;

	/**
	 * Constructs a GUIShape object with the specified parameters and sets the shape as not selected.
	 * @param g the geometric shape to be represented by the GUIShape.
	 * @param f true if the shape is filled, false otherwise.
	 * @param c the color of the shape.
	 * @param t the tag of the shape.
	 */
	public GUIShape(GeoShape g, boolean f, Color c, int t) {
		_g = null;
		if (g!=null) {_g = g.copy();}
		_fill= f;
		_color = c;
		_tag = t;
		_isSelected = false;
	}

	/**
	 * Constructs a new GUIShape object as a copy of the given GUIShape object.
	 * @param ot The GUIShape object to be copied.
	 */
	public GUIShape(GUIShape ot) {
		this(ot._g, ot._fill, ot._color, ot._tag);
	}

	/**
	 * Constructs a new GUIShape object based on the given string representation.
	 * @param shape the string representation of the shape to be converted into a GUIShape object.
	 */
	public GUIShape(String shape) {
		String[] quality = shape.split(","); // Split the quality's of the GUIShape as cells in a String array
		if("Rect_2D".equals(quality[4])) {  // Checks if the GUIShape is a rectangle
			double x1 = Double.parseDouble(quality[5]);
			double y1 = Double.parseDouble(quality[6]);
			double x3 = Double.parseDouble(quality[7]);
			double y3 = Double.parseDouble(quality[8]);
			double x2 = Double.parseDouble(quality[9]);
			double y2 = Double.parseDouble(quality[10]);
			double x4 = Double.parseDouble(quality[11]);
			double y4 = Double.parseDouble(quality[12]);
			Point_2D p1 = new Point_2D(x1,y1);
			Point_2D p2 = new Point_2D(x2,y2);
			Point_2D p3 = new Point_2D(x3,y3);
			Point_2D p4 = new Point_2D(x4,y4);
			Rect_2D r = new Rect_2D(p1, p2, p3, p4);
            boolean isFilled = Boolean.parseBoolean(quality[2]);
			Color color = Color.decode(quality[1]);
			int tag = Integer.parseInt(quality[3]);
			// Constructs a new GUIShape object based on the quality's of the String array.
			_g = r; _fill= isFilled; _color = color; _tag = tag; _isSelected = false;
		}
		if("Triangle_2D".equals(quality[4])){ // Checks if the GUIShape is a triangle
			double x1 = Double.parseDouble(quality[5]);
			double y1 = Double.parseDouble(quality[6]);
			double x2 = Double.parseDouble(quality[7]);
			double y2 = Double.parseDouble(quality[8]);
			double x3 = Double.parseDouble(quality[9]);
			double y3 = Double.parseDouble(quality[10]);
			Point_2D p1 = new Point_2D(x1,y1);
			Point_2D p2 = new Point_2D(x2,y2);
			Point_2D p3 = new Point_2D(x3,y3);
			Triangle_2D t =new Triangle_2D(p1,p2,p3);
			boolean isFilled = Boolean.parseBoolean(quality[2]);
			Color color = Color.decode(quality[1]);
			int tag = Integer.parseInt(quality[3]);
			// Constructs a new GUIShape object based on the quality's of the String array.
			_g = t; _fill= isFilled; _color = color; _tag = tag; _isSelected=false;
		}
		if("Circle_2D".equals(quality[4])){ // Checks if the GUIShape is a circle
			double x1 = Double.parseDouble(quality[5]);
			double y1 = Double.parseDouble(quality[6]);
			double rad = Double.parseDouble(quality[7]);
			Point_2D cen = new Point_2D(x1,y1);
			Circle_2D c = new Circle_2D(cen,rad);
			boolean isFilled = Boolean.parseBoolean(quality[2]);
			Color color = Color.decode(quality[1]);
			int tag = Integer.parseInt(quality[3]);
			// Constructs a new GUIShape object based on the quality's of the String array.
			_g = c; _fill= isFilled; _color = color; _tag = tag; _isSelected=false;
		}
		if("Segment_2D".equals(quality[4])){ // Checks if the GUIShape is a segment
			double x1 = Double.parseDouble(quality[5]);
			double y1 = Double.parseDouble(quality[6]);
			double x2 = Double.parseDouble(quality[7]);
			double y2 = Double.parseDouble(quality[8]);
			Point_2D p1 = new Point_2D(x1,y1);
			Point_2D p2 = new Point_2D(x2,y2);
			Segment_2D s = new Segment_2D(p1,p2);
			boolean isFilled = Boolean.parseBoolean(quality[2]);
			Color color = Color.decode(quality[1]);
			int tag = Integer.parseInt(quality[3]);
			// Constructs a new GUIShape object based on the quality's of the String array.
			_g = s; _fill= isFilled; _color = color; _tag = tag; _isSelected=false;
		}
		if("Polygon_2D".equals(quality[4])){ // Checks if the GUIShape is a polygon
			Polygon_2D p = new Polygon_2D();
			for (int j = 5; j < quality.length-1; j+=2) {
				double x1 = Double.parseDouble(quality[j]);
				double y1 = Double.parseDouble(quality[j+1]);
				p.add(new Point_2D(x1,y1));
			}
			boolean isFilled = Boolean.parseBoolean(quality[2]);
			Color color = Color.decode(quality[1]);
			int tag = Integer.parseInt(quality[3]);
			// Constructs a new GUIShape object based on the quality's of the String array.
			_g = p; _fill= isFilled; _color = color; _tag = tag; _isSelected=false;
		}
	}

	/** getShape:
	 * @return the class of the GUIShape */
	@Override
	public GeoShape getShape() {
		return _g;
	}

	/** setShape:
	 * set the class of the GUIShape
	 * @param g the class to set the GUIShape to
	 */
	@Override
	public void setShape(GeoShape g) {
		_g = g;
	}

	/** isFilled:
	 * @return if the GUIShape is filled or not. */
	@Override
	public boolean isFilled() {
		return _fill;
	}

	/** setFilled:
	 * Sets whether the GUIShape is filled or not.
	 * @param filled the boolean value indicating whether the GUIShape should be filled.
	 */
	@Override
	public void setFilled(boolean filled) {
		_fill = filled;
	}

	/** getColor:
	 * @return the color of the GUIShape */
	@Override
	public Color getColor() {
		return _color;
	}

	/** setColor:
	 * set the color of the GUIShape
	 * @param cl the color to set the GUIShape to.
	 */
	@Override
	public void setColor(Color cl) {
		_color = cl;
	}

	/** getTag:
	 * @return the tag of the GUIShape */
	@Override
	public int getTag() {
		return _tag;
	}

	/** setTag:
	 * set the tag of the GUIShape
	 * @param tag the tag to set the GUIShape to.
	 */
	@Override
	public void setTag(int tag) {
		_tag = tag;
	}

	/** copy:
	 * computes a new (deep) copy of the GUIShape.
	 * @return a deep copy of the GUIShape.
	 */
	@Override
	public GUI_Shape copy() {
		GUI_Shape cp = new GUIShape(this);
		return cp;
	}

	/** toString:
	 * @return a string representation of the GUIShape */
	@Override
	public String toString() {
		String ans = ""+this.getClass().getSimpleName()+","+_color.getRGB()+","+_fill+","+_tag+","+this._g.getClass().getSimpleName()+","+_g.toString();
		return ans;
	}

	/** isSelected:
	 * @return if the GUIShape is selected or not. */
	@Override
	public boolean isSelected() {
		return this._isSelected;
	}

	/** setFilled:
	 * Sets whether the GUIShape is selected or not.
	 * @param s the boolean value indicating whether the GUIShape should be selected.
	 */
	@Override
	public void setSelected(boolean s) {
		this._isSelected = s;
	}

	/** equals:
	 * Computes if the object (p) is equal to the GUI_Shape.
	 * @param p the object we compare to the GUI_Shape.
	 * @return true iff the object (p) is equal to the GUI_Shape.
	 */
	@Override
	public boolean equals(Object p){
		if(p == null || !(p instanceof GUIShape)) {return false;}
		GUIShape p2 = (GUIShape) p;
		return  (p2.getShape().equals(this._g) && p2.isFilled() == this._fill && p2.getColor() == this._color && p2.getTag() == this._tag && p2.isSelected() == this._isSelected);
	}
}
