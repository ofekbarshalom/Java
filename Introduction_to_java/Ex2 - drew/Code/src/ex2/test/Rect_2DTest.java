package ex2.test;

import ex2.ex2.Ex2_Const;
import ex2.geo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Rect_2DTest {
    Point_2D p1, p2;
    Rect_2D r1;

    @BeforeEach // set rectangle for tests before each test
    void set(){
        p1 = new Point_2D(1,5);
        p2 = new Point_2D(3,2);
        r1 = new Rect_2D(p1,p2);
    }

    @Test
    void get_p1() {
        Point_2D expectedP1 = new Point_2D(1,5);
        assertTrue(r1.get_p1().equals(expectedP1),"The points should be equal");
    }

    @Test
    void get_p2() {
        Point_2D expectedP2 = new Point_2D(3,2);
        assertTrue(r1.get_p2().equals(expectedP2),"The points should be equal");
    }

    @Test
    void get_p3() {
        Point_2D expectedP3 = new Point_2D(1,2);
        assertTrue(r1.get_p3().equals(expectedP3),"The points should be equal");
    }

    @Test
    void get_p4() {
        Point_2D expectedP4 = new Point_2D(3,5);
        assertTrue(r1.get_p4().equals(expectedP4),"The points should be equal");
    }

    @Test
    void contains() {
        Point_2D inRectangle = new Point_2D(2,3);
        assertTrue(r1.contains(inRectangle),"The Point should be in the rectangle");

        Point_2D onRectangle = new Point_2D(1,2);
        assertTrue(r1.contains(onRectangle),"The Point should be on the rectangle");

        Point_2D notInRectangle = new Point_2D(9,9);
        assertFalse(r1.contains(notInRectangle),"The Point shouldn't be on the rectangle");
    }

    @Test
    void area() {
        double r1Area=r1.area();
        double expectedArea=6.0;    // 3*2 = 6.0
        assertEquals(r1Area,expectedArea,"Area should be 6.0");
    }

    @Test
    void perimeter() {
        double r1Perimeter=r1.perimeter();
        double expectedArea=10.0;    // 3*2+2*2 = 10.0
        assertEquals(r1Perimeter,expectedArea,"Perimeter should be 10.0");
    }

    @Test
    void translate() {
        Point_2D expectedP1 = new Point_2D(4,8);
        Point_2D expectedP2 = new Point_2D(6,5);
        Rect_2D expectedR1 = new Rect_2D(expectedP1,expectedP2);
        Point_2D vec = new Point_2D(3,3);

        r1.translate(vec);
        assertTrue(r1.equals(expectedR1),"The rectangles should be equal (test 1)");

        Point_2D vec2 = new Point_2D(-3,-3);
        r1.translate(vec2);
        Point_2D expectedOriginP1 = new Point_2D(1,5);
        Point_2D expectedOriginP2 = new Point_2D(3,2);
        Rect_2D expectedOriginR1 = new Rect_2D(expectedOriginP1,expectedOriginP2);
        assertTrue(r1.equals(expectedOriginR1),"The rectangles should be equal (test 2)");
    }

    @Test
    void copy() {
        Rect_2D r1Copy = (Rect_2D) r1.copy();
        assertTrue(r1.equals(r1Copy),"the rectangles should be equal");
    }

    @Test
    void scale() {
        Point_2D p1 = new Point_2D(1,1);
        Point_2D p2 = new Point_2D(3,3);
        Rect_2D r1 = new Rect_2D(p1,p2);
        Point_2D center = new Point_2D(2,2);

        r1.scale(center,2);         // Scaled the rectangle by 2.
        Point_2D expectedP1 = new Point_2D(0,0);
        Point_2D expectedP2 = new Point_2D(4,4);
        Rect_2D expectedR1 = new Rect_2D(expectedP1,expectedP2);
        assertTrue(r1.equals(expectedR1),"The rectangles should be equal");

        r1.scale(p1,1);            // Scaled the rectangle by 1, the rectangle need to stay in the same size.
        assertTrue(r1.equals(expectedR1),"The rectangles should stay the same");
    }

    @Test
    void rotate() {
        Point_2D p1 = new Point_2D(1.0,2.0);
        Point_2D p2 = new Point_2D(7.0,3.0);
        Rect_2D r1 = new Rect_2D(p1,p2);
        Point_2D center = new Point_2D(3.0,3.0);
        r1.rotate(center,90);

        Point_2D expectedP1 = new Point_2D(4,1);
        assertTrue(r1.get_p1().close2equals(expectedP1,Ex2_Const.EPS),"p1 should be close to equal to expectedP1");

        Point_2D expectedP2 = new Point_2D(3,7);
        assertTrue(r1.get_p2().close2equals(expectedP2,Ex2_Const.EPS),"p2 should be close to equal to expectedP2");

        Point_2D expectedP3 = new Point_2D(3,1);
        assertTrue(r1.get_p3().close2equals(expectedP3,Ex2_Const.EPS),"p3 should be close to equal to expectedP3");

        Point_2D expectedP4 = new Point_2D(4,7);
        assertTrue(r1.get_p4().close2equals(expectedP4,Ex2_Const.EPS),"p4 should be close to equal to expectedP4");
    }

    @Test
    void ToString() {
        assertEquals(1.0+ "," +5.0+ "," +1.0+ "," +2.0+ "," +3.0+ "," +2.0+ "," +3.0+ "," +5.0, r1.toString(),"The Strings should be equal");
    }

    @Test
    void equals(){
       Rect_2D r1 = new Rect_2D(p1,p2);
       Rect_2D r2 = new Rect_2D(p1,p2);
       Rect_2D r3 = new Rect_2D(p2,p1);

        assertTrue(r1.equals(r2),"r1 should be equal to r2");
        assertTrue(r1.equals(r3),"r1 should be equal to r3");

        assertFalse(r3.equals(null),"the object is null");

        Circle_2D c1 = new Circle_2D(p1,4.0);
        assertFalse(r1.equals(c1),"c1 is not a rectangle");
    }
}