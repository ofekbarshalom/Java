package ex2.ex2;

import ex2.geo.*;
import ex2.gui.Ex2;
import ex2.gui.GUIShape;
import ex2.gui.GUI_Shape;

import java.awt.*;

/** Ex2_main:
 * This class is a very simple main for starting with Ex2
 */
public class Ex2_main {

	public static void main(String[] args) {
		simpleShapes();
		//loadDemo();
	}

	public static void simpleShapes() {
		Ex2 ex2 = Ex2.getInstance();
		GUI_Shape_Collection shapes = ex2.getShape_Collection();
		Point_2D p1 = new Point_2D(3,4);
		Circle_2D c1 = new Circle_2D(p1,3);
		GUI_Shape gs1 = new GUIShape(c1, true, Color.blue, 1);
		shapes.add(gs1);
		ex2.init(shapes);
		ex2.show();
	}
	public static void loadDemo() {
		Ex2 ex2 = Ex2.getInstance();
		GUI_Shape_Collection shapes = ex2.getShape_Collection();
		String file = "a0.txt";
		shapes.load(file);
		ex2.init(shapes);
		ex2.show();
	}
}