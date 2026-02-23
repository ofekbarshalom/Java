package ex2.geo;

import java.util.Comparator;

import ex2.ex2.Ex2_Const;
import ex2.gui.GUI_Shape;

/** ShapeComp:
 * This class represents a Comparator over GUI_Shapes - 
 * as a linear order over GUI_Shapes.
 * @author ofek bar-shalom
 */
public class ShapeComp implements Comparator<GUI_Shape>{

	public static final ShapeComp CompByArea = new ShapeComp(Ex2_Const.Sort_By_Area);
	public static final ShapeComp CompByAntiArea = new ShapeComp(Ex2_Const.Sort_By_Anti_Area);
	public static final ShapeComp CompByPerimeter = new ShapeComp(Ex2_Const.Sort_By_Perimeter);
	public static final ShapeComp CompByAntiPerimeter = new ShapeComp(Ex2_Const.Sort_By_Anti_Perimeter);
	public static final ShapeComp CompByToString = new ShapeComp(Ex2_Const.Sort_By_toString);
	public static final ShapeComp CompByAntiToString = new ShapeComp(Ex2_Const.Sort_By_Anti_toString);
	public static final ShapeComp CompByTag = new ShapeComp(Ex2_Const.Sort_By_Tag);
	public static final ShapeComp CompByAntiTag = new ShapeComp(Ex2_Const.Sort_By_Anti_Tag);

	private int _flag;
	public ShapeComp(int flag) {_flag = flag;}  // Sets the flag to the comparison option.

	/** compare:
	 * Compares two GUI_Shape objects based on the specified comparison option.
	 * @param o1 the first object to be compared.
	 * @param o2 the second object to be compared.
	 * @return 1 iff o1 is grater than o1, -1 iff o1 is smaller than o2  and 0 iff o1 and o2 are equals.
	 */
	@Override
	public int compare(GUI_Shape o1, GUI_Shape o2) {
		double a1 =-1, a2 = -1;
		GeoShape s1 = o1.getShape(), s2 = o2.getShape();
		int ans = 0;
		if(_flag == Ex2_Const.Sort_By_Area) { // Sort by the area
			a1 = s1.area();
			a2 = s2.area();
			if(a1>a2) {ans = 1;}
			if(a1<a2) {ans = -1;}
		}
		if(_flag == Ex2_Const.Sort_By_Anti_Area) { // Sort by the anti-area (the lowest area on top)
			a1 = s1.area();
			a2 = s2.area();
			if(a1<a2) {ans = 1;}
			if(a1>a2) {ans = -1;}
		}
		if(_flag == Ex2_Const.Sort_By_Perimeter) { // Sort by the perimeter
			a1 = s1.perimeter();
			a2 = s2.perimeter();
			if(a1>a2) {ans = 1;}
			if(a1<a2) {ans = -1;}
		}
		if(_flag == Ex2_Const.Sort_By_Anti_Perimeter) { // Sort by the anti-perimeter (the lowest perimeter on top)
			a1 = s1.perimeter();
			a2 = s2.perimeter();
			if(a1<a2) {ans = 1;}
			if(a1>a2) {ans = -1;}
		}
		if(_flag == Ex2_Const.Sort_By_toString) { // Sort by the String (lexicographic order)
			ans = o1.toString().compareTo(o2.toString());
		}
		if(_flag == Ex2_Const.Sort_By_Anti_toString) { // Sort by the anti-String (lexicographic order, the lowest String on top)
			ans = o2.toString().compareTo(o1.toString());
		}
		if(_flag == Ex2_Const.Sort_By_Tag){ // Sort by the tag
			a1 = o1.getTag();
			a2 = o2.getTag();
			if(a1>a2) {ans = 1;}
			if(a1<a2) {ans = -1;}
		}
		if(_flag == Ex2_Const.Sort_By_Anti_Tag){ // Sort by the anti-tag (the lowest tag on top)
			a1 = o1.getTag();
			a2 = o2.getTag();
			if(a1<a2) {ans = 1;}
			if(a1>a2) {ans = -1;}
		}
		return ans;
	}
}
