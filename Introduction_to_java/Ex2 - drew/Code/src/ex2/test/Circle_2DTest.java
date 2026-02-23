package ex2.test;

import ex2.ex2.Ex2_Const;
import ex2.geo.Circle_2D;
import ex2.geo.Point_2D;
import ex2.geo.Rect_2D;
import ex2.gui.GUIShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Circle_2DTest {
    private Point_2D p1;
    private Circle_2D c1;

    @BeforeEach // set circle for tests before each test
    void set(){
        p1 = new Point_2D(3,6);
        c1 = new Circle_2D(p1, 5);
    }

    @Test
    void getRadius() {
        double c1Radios = c1.getRadius();
        assertEquals(c1Radios,5,"Radios should be 5.0");
    }

    @Test
    void getCenter() {
        Point_2D actualCenter = c1.getCenter();
        Point_2D expectedCenter = new Point_2D(3,6);

        assertTrue(actualCenter.equals(expectedCenter),"the centers should be equal");
    }

    @Test
    void ToString() {
        String c1String = c1.toString();
        assertEquals(c1String,3.0+","+6.0+", "+5.0,"the Strings should be equal");
    }

    @Test
    void contains() {
        Point_2D inCircle = new Point_2D(4,6);                        // point in circle
        boolean checkContains = c1.contains(inCircle);                      // True
        assertTrue(checkContains,"Point should be in circle");

        Point_2D onCircle = new Point_2D(3,11);                       // point on circle perimeter
        checkContains = c1.contains(onCircle);                              // True
        assertTrue(checkContains,"Point should be in circle");

        Point_2D notInCircle = new Point_2D(12,12);                    // point not in the circle
        checkContains = c1.contains(notInCircle);                            // False
        assertFalse(checkContains,"Point should not be in circle");
    }

    @Test
    void area() {
        double c1Area = c1.area();
        double expectedArea = Math.PI*Math.pow(5.0,2);
        assertEquals(c1Area,expectedArea,"The area should be Equal");
    }

    @Test
    void perimeter() {
        double c1Perimeter = c1.perimeter();
        double expectedPerimeter = 2*Math.PI*5.0;
        assertEquals(c1Perimeter,expectedPerimeter,"The perimeter should be Equal");
    }

    @Test
    void translate() {
        Point_2D vec = new Point_2D(2,3);
        c1.translate(vec);       // Moved the circle by the vector

        Point_2D expectedCenter = new Point_2D(5,9);
        assertTrue(c1.getCenter().equals(expectedCenter),"The centers should be equal");
    }

    @Test
    void copy() {
        Circle_2D c1Copy = (Circle_2D) c1.copy();
        assertTrue(c1.equals(c1Copy),"the circles should be equal");
    }

    @Test
    void scale() {
        c1.scale(p1,2);        // Scaled the circle by 2;
        assertEquals(c1.getRadius(),10.0,"Radios should be 10.0");

        c1.scale(p1,1);        // Scaled the circle by 1, the circle need to stay in the same size
        assertEquals(c1.getRadius(),10.0,"Radios should stay the same [10.0]");
    }

    @Test
    void rotate() {
        Point_2D p2 = new Point_2D(1,1);
        Circle_2D c2 = new Circle_2D(p2, 5);
        Point_2D center = new Point_2D(2,2);
        c2.rotate(center,90);

        Point_2D expectedCenter = new Point_2D(3,1);
        assertTrue(c2.getCenter().equals(expectedCenter),"The centers should be equal");
    }

    @Test
    void equals(){
        Circle_2D c2= new Circle_2D(p1, 5.0);
        assertTrue(c1.equals(c2),"the circles should be equal");

        Point_2D p2 = new Point_2D(4,3);
        Circle_2D c3 = new Circle_2D(p2, 6.0);
        assertFalse(c3.equals(c2),"the circles shouldn't be equal");
    }
}