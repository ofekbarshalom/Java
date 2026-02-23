package ex2.test;

import ex2.geo.Circle_2D;
import ex2.geo.Point_2D;
import ex2.geo.Rect_2D;
import ex2.geo.Segment_2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Segment_2DTest {
    private Point_2D p1, p2, p3;
    private Segment_2D s1, s2;

    @BeforeEach // set segment for tests before each test
    void set(){
        p1 = new Point_2D(1,1);
        p2 = new Point_2D(3,3);
        p3 = new Point_2D(1,3);
        s1 = new Segment_2D(p1,p2);
        s2 = new Segment_2D(p1,p3);

    }

    @Test
    void get_p1() {
        Point_2D p1 = new Point_2D(1,6);
        Point_2D p2 = new Point_2D(2,2);
        Segment_2D s1 = new Segment_2D(p1,p2);

        assertEquals(s1.get_p1().x(),1,"p1 X should be 1.0");
        assertEquals(s1.get_p1().y(),6,"p1 Y should be 6.0");
    }

    @Test
    void get_p2() {
        Point_2D p1 = new Point_2D(1,1);
        Point_2D p2 = new Point_2D(2,4);
        Segment_2D s1 = new Segment_2D(p1,p2);
        assertEquals(s1.get_p2().x(),2,"p2 X should be 2.0");
        assertEquals(s1.get_p2().y(),4,"p2 Y should be 4.0");
    }

    @Test
    void contains() {
        Point_2D onSegemnet = new Point_2D(2,2);
        assertTrue(s1.contains(onSegemnet),"The Point should be on the segment");

        Point_2D notOnSegment = new Point_2D(7,6);
        assertFalse(s1.contains(notOnSegment),"The Point should not be on the segment");
    }

    @Test
    void area() {
        double actual = s1.area();
        assertEquals(0,actual,"area should be 0");
    }

    @Test
    void perimeter() {
        double actual = s2.perimeter();
        double expected = 4.0;        // (3-1)*2 = 4.0
        assertEquals(expected,actual,"perimeter should be 4.0");
    }

    @Test
    void translate() {
        Point_2D vec = new Point_2D(2,2);
        s2.translate(vec);
        Point_2D expectedP1 = new Point_2D(3,3);
        assertTrue(s2.get_p1().equals(expectedP1),"p1 should be equal to expectedP1");
        Point_2D expectedP2 = new Point_2D(3,5);
        assertTrue(s2.get_p2().equals(expectedP2),"p2 should be equal to expectedP2");

        Point_2D vec2 = new Point_2D(-2,-2);
        s2.translate(vec2);
        Point_2D expectedOriginP1 = new Point_2D(1,1);
        assertTrue(s2.get_p1().equals(expectedOriginP1),"p1 should be equal to expectedOriginP1");
        Point_2D expectedOriginP2 = new Point_2D(1,3);
        assertTrue(s2.get_p2().equals(expectedOriginP2),"p2 should be equal to expectedOriginP2");
    }

    @Test
    void copy() {
        Segment_2D s1Copy=(Segment_2D) s1.copy();
        assertTrue(s1.equals(s1Copy),"the segments should be equal");
    }

    @Test
    void scale() {
        Segment_2D s1 = new Segment_2D(p3,p1);
        Point_2D center=new Point_2D(1,2);
        Segment_2D originS1 = (Segment_2D)s1.copy();

        s1.scale(p1,1);  // Scaled the segment by 1, the segment need to stay in the same size
        assertTrue(s1.equals(originS1),"The segments should be equal");

        s1.scale(center,2);         // Scaled the segment by 2;
        assertEquals(s1.get_p1().x(),1.0,"p1 X should be 1.0");
        assertEquals(s1.get_p1().y(),4.0,"p1 Y should be 4.0");
        assertEquals(s1.get_p2().x(),1.0,"p2 X should be 1.0");
        assertEquals(s1.get_p2().y(),0.0,"p2 Y should be 0.0");
    }

    @Test
    void rotate() {
        Segment_2D s1 = new Segment_2D(p3,p1);
        Point_2D center=new Point_2D(1,2);

        s1.rotate(center,90);

        assertEquals(s1.get_p1().x(),0.0,"p1 X should be 0.0");
        assertEquals(s1.get_p1().y(),2.0,"p1 Y should be 2.0");
        assertEquals(s1.get_p2().x(),2.0,"p2 X should be 2.0");
        assertEquals(s1.get_p2().y(),2.0,"p2 Y should be 2.0");
    }

    @Test
    void ToString() {
        String s1String = s1.toString();
        assertEquals(s1String,1.0+","+1.0+","+3.0+","+3.0,"the Strings should be equal");
    }

    @Test
    void equal(){
        Point_2D p1 = new Point_2D(1,3);
        Point_2D p2 = new Point_2D(0,5);
        Segment_2D s1 = new Segment_2D(p1,p2);
        Segment_2D s2 = new Segment_2D(p1,p2);
        Segment_2D s3 = new Segment_2D(p2,p1);

        assertTrue(s1.equals(s2),"s1 should be equal to s2");
        assertTrue(s1.equals(s3),"s1 should be equal to s3");

        assertFalse(s3.equals(null),"the object is null");

        Rect_2D r1=new Rect_2D(p1,p2);
        assertFalse(s1.equals(r1),"r1 is not a segment");
    }
}