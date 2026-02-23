package ex2.test;

import ex2.ex2.Ex2_Const;
import ex2.geo.Point_2D;
import ex2.geo.Rect_2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point_2DTest {
    private Point_2D p1;

    @BeforeEach
    void set(){ p1 = new Point_2D(3.0,0.5); } // set point for tests before each test

    @Test
    void x() {
        double ActualX = p1.x();
        assertEquals(ActualX,3.0,"p1 X should be 3.0");
    }

    @Test
    void y() {
        double ActualY = p1.y();
        assertEquals(ActualY,0.5,"p1 Y should be 0.5");
    }

    @Test
    void ix() {
        Point_2D p1 = new Point_2D(3.77,0.3);
        int ActualX = p1.ix();
        assertEquals(ActualX,3,"p1 X in int should be 3");
    }

    @Test
    void iy() {
        Point_2D p1 = new Point_2D(3.77,0.3);
        int ActualY = p1.iy();
        assertEquals(ActualY,0,"p1 Y in int should be 0");
    }

    @Test
    void add() {
        Point_2D p1 = new Point_2D(3.0,0.5);
        Point_2D p2 = new Point_2D(2.3,1.5);
        Point_2D a = p1.add(p2);
        assertEquals(a.x(),5.3,"p1 X should be 5.3");
        assertEquals(a.y(),2.0,"p1 Y should be 2.0");
    }

    @Test
    void ToString() {
        assertEquals(p1.toString(),3.0+","+0.5,"The Strings should be equal");
    }

    @Test
    void distance() {
        Point_2D p1 = new Point_2D(3.0,4.0);
        double originDistance = p1.distance();
        assertEquals(originDistance,5.0,"the distance from origin should be 5.0");
    }

    @Test
    void Distance() {
        Point_2D p1 = new Point_2D(3.0,4.0);
        Point_2D p2 = new Point_2D(6.0,8.0);
        double distance = p1.distance(p2);
        assertEquals(distance,5.0,"the distance should be 5.0");
    }

    @Test
    void equals() {
        Point_2D p2 = new Point_2D(6.0,8.0);
        assertFalse(p1.equals(p2),"p1 should not be equal to p2");

        assertFalse(p1.equals(null),"p2 is null");

        Rect_2D r1 = new Rect_2D(p1,p2);
        assertFalse(p1.equals(r1),"p2 is not a point");

        Point_2D p3 = new Point_2D(3.0,0.5);
        assertTrue(p1.equals(p3),"p1 should be equal to p2");
    }

    @Test
    void close2equals() {
        Point_2D p2 = new Point_2D(6.0,8.0);
        assertFalse(p1.close2equals(p2,0.1),"p1 should not be close to equal to p2");

        Point_2D p3 = new Point_2D(3.01,0.501);
        assertTrue(p1.close2equals(p3,0.1),"p1 should be close to equal to p2");
    }

    @Test
    void vector() {
        Point_2D p1 = new Point_2D(1.0,2.0);
        Point_2D p2 = new Point_2D(7.0,3.0);
        Point_2D expectedVector = new Point_2D(6.0,1.0);
        assertEquals(p1.vector(p2),expectedVector,"the vector should be (6.0,1.0)");
    }

    @Test
    void move() {
        Point_2D p1 = new Point_2D(1.0,2.0);
        Point_2D p2 = new Point_2D(7.0,3.0);
        Point_2D vector = new Point_2D(6.0,1.0);

        p1.move(vector);
        assertTrue(p1.equals(p2),"p1 should be equal to p2");
    }

    @Test
    void scale() {
        Point_2D p1 = new Point_2D(1.0,2.0);

        p1.scale(p1,2);   // Scaled the point by 2 with a center which is p1, the point should stay the same.
        assertEquals(p1.x(),1.0,"X should stay the same");
        assertEquals(p1.y(),2.0,"Y should stay the same");

        Point_2D center2 = new Point_2D(0.0,1.0);
        p1.scale(center2,2); // Scaled the point by 2 with other center
        assertEquals(p1.x(),2.0,"X should be 2.0");
        assertEquals(p1.y(),3.0,"Y should be 3.0");
    }

    @Test
    void rotate() {
        Point_2D p1 = new Point_2D(3.0,6.0);
        Point_2D center = new Point_2D(1.5,3.0);

        p1.rotate(p1,90);
        Point_2D expectedP1 = new Point_2D(3,6);
        assertTrue(p1.close2equals(expectedP1, Ex2_Const.EPS));

        p1.rotate(center,90);
        Point_2D expectedP2 = new Point_2D(-1.5,4.5);
        assertTrue(p1.close2equals(expectedP2,Ex2_Const.EPS));
    }
}