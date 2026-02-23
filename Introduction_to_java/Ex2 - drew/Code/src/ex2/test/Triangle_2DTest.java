package ex2.test;

import ex2.ex2.Ex2_Const;
import ex2.geo.Rect_2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ex2.geo.Point_2D;
import ex2.geo.Triangle_2D;

import static org.junit.jupiter.api.Assertions.*;

class Triangle_2DTest {

    Point_2D p1, p2, p3;
    Triangle_2D t1;
    Point_2D[] allPoints;

    @BeforeEach // set triangle for tests before each test
    void set(){
        p1 = new Point_2D(1,5);
        p2 = new Point_2D(1,3);
        p3 = new Point_2D(5,3);
        t1=new Triangle_2D(p1,p2,p3);
        allPoints =t1.getAllPoints();
    }

    @Test
    void getAllPoints() {
        assertEquals(allPoints[0].x(),1.0,"p1 X should be 1.0");
        assertEquals(allPoints[0].y(),5.0,"p1 Y should be 5.0");
        assertEquals(allPoints[1].x(),1.0,"p2 X should be 1.0");
        assertEquals(allPoints[1].y(),3.0,"p2 Y should be 3.0");
        assertEquals(allPoints[2].x(),5.0,"p3 X should be 5.0");
        assertEquals(allPoints[2].y(),3.0,"p3 Y should be 3.0");
    }

    @Test
    void contains() {
        Point_2D inTriangle = new Point_2D(2,4);
        assertTrue(t1.contains(inTriangle),"The Point should be in the triangle");

        Point_2D onTriangle = new Point_2D(1,3);
        assertTrue(t1.contains(onTriangle),"The Point should be on the triangle");

        Point_2D notOnTriangle = new Point_2D(7,7);
        assertFalse(t1.contains(notOnTriangle),"The Point should not be in the triangle");
    }

    @Test
    void area() {
        double t1Area=t1.area();
        double expectedArea=4.0;    // (2*4)/2 = 4.0
        assertEquals(t1Area,expectedArea,"area should be 4.0");
    }

    @Test
    void perimeter() {
        double t1Perimeter=t1.perimeter();
        double expectedArea=10.47213595499958;
        assertEquals(t1Perimeter,expectedArea,"perimeter should be 10.47213595499958");
    }

    @Test
    void translate() {
        Point_2D p1 = new Point_2D(1,5);
        Point_2D p2 = new Point_2D(1,3);
        Point_2D p3 = new Point_2D(5,3);
        Triangle_2D t1=new Triangle_2D(p1,p2,p3);
        Point_2D vec = new Point_2D(2,3);
        Point_2D[] allPoints = t1.getAllPoints();

        t1.translate(vec);
        assertEquals(allPoints[0].x(),3.0,"p1 X should be 3.0");
        assertEquals(allPoints[0].y(),8.0,"p1 Y should be 8.0");
        assertEquals(allPoints[1].x(),3.0,"p2 X should be 3.0");
        assertEquals(allPoints[1].y(),6.0,"p2 Y should be 6.0");
        assertEquals(allPoints[2].x(),7.0,"p3 X should be 7.0");
        assertEquals(allPoints[2].y(),6.0,"p3 Y should be 6.0");
    }

    @Test
    void copy() {
        Triangle_2D t1Copy=(Triangle_2D) t1.copy();
        assertTrue(t1.equals(t1Copy),"the triangle should be equal");
    }

    @Test
    void scale() {
        Point_2D center = new Point_2D(1,3);
        Triangle_2D originT1 = (Triangle_2D)t1.copy();

        t1.scale(center,1);  // Scaled the rectangle by 1, the rectangle need to stay in the same size
        assertTrue(t1.equals(originT1),"the triangle should be equal (test 1)");


        t1.scale(center,2); // Scaled the Triangle by 2;
        Point_2D expectedP1 = new Point_2D(1,7);
        Point_2D expectedP2 = new Point_2D(1,3);
        Point_2D expectedP3 = new Point_2D(9,3);
        Triangle_2D expectedT1 = new Triangle_2D(expectedP1, expectedP2, expectedP3);
        assertTrue(t1.equals(expectedT1),"the triangle should be equal (test 2)");
    }

    @Test
    void rotate() {
        Point_2D center = new Point_2D(5.0,3.0);
        t1.rotate(center,90);

        Point_2D expectedP1 = new Point_2D(3,-1);
        assertTrue(allPoints[0].close2equals(expectedP1, Ex2_Const.EPS),"the points should be equal (p1) ");

        Point_2D expectedP2 = new Point_2D(5,-1);
        assertTrue(allPoints[1].close2equals(expectedP2,Ex2_Const.EPS),"the points should be equal (p2) ");

        Point_2D expectedP3 = new Point_2D(5,3);
        assertTrue(allPoints[2].close2equals(expectedP3,Ex2_Const.EPS),"the points should be equal (p3) ");
    }

    @Test
    void ToString() {
        assertEquals(t1.toString(),1.0+","+5.0+","+1.0+","+3.0+","+5.0+","+3.0,"The Strings should be equal");
    }

    @Test
    void equals() {
        Triangle_2D t1 = new Triangle_2D(p1,p2,p3);
        Triangle_2D t2 = new Triangle_2D(p1,p2,p3);
        Triangle_2D t3 = new Triangle_2D(p2,p3,p1);

        assertTrue(t1.equals(t2),"t1 should be equal to t2");
        assertTrue(t1.equals(t3),"t1 should be equal to t3");

        assertFalse(t3.equals(null),"the object is null");

        Rect_2D r1=new Rect_2D(p1,p2);
        assertFalse(t1.equals(r1),"r1 is not a triangle");
    }
}