package ex2.test;

import ex2.ex2.Ex2_Const;
import ex2.geo.Point_2D;
import ex2.geo.Polygon_2D;
import ex2.geo.Triangle_2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Polygon_2DTest {
    private Point_2D p1, p2, p3, p4, p5;
    private Polygon_2D vertex1;

    @BeforeEach // set polygon for tests before each test
    void setUp() {
        p1 = new Point_2D(1, 2);
        p2 = new Point_2D(3, 5);
        p3 = new Point_2D(9, 1);
        p4 = new Point_2D(4, 1);
        p5 = new Point_2D(1, 0);
        vertex1 = new Polygon_2D();

        vertex1.add(p1); vertex1.add(p2); vertex1.add(p3); vertex1.add(p4); vertex1.add(p5);
    }

    @Test
    void getAllPoints() {
        Point_2D[] expectedPoints = {p1, p2, p3, p4, p5};
        Point_2D[] actualPoints = vertex1.getAllPoints();

        assertArrayEquals(expectedPoints, actualPoints,"The arrays should be equal");
    }

    @Test
    void add() {
        Point_2D p6 = new Point_2D(3,4);
        vertex1.add(p6);
        Point_2D actualPoint= vertex1.getAllPoints()[5];

        assertEquals(p6,actualPoint,"p6 should be in vertex1");
    }

    @Test
    void ToString() {
        assertEquals(vertex1.toString(),"["+1.0+","+2.0+", "+3.0+","+5.0+", "+9.0+","+1.0+", "+4.0+","+1.0+", "+1.0+","+0.0+"]"
                ,"The String should be equal");
    }

    @Test
    void contains() {
        Point_2D p1 = new Point_2D(4,3);
        boolean actual = vertex1.contains(p1);
        assertTrue(actual,"p1 should be in vertex1");

        Point_2D p2 = new Point_2D(9,10);
        boolean actual2 = vertex1.contains(p2);
        assertFalse(actual2,"p2 shouldn't be in vertex1");
    }

    @Test
    void area() {
        double actualArea = vertex1.area();
        double expectedArea = 19.5;

        assertEquals(actualArea,expectedArea,"The area should be 19.5");
    }

    @Test
    void perimeter() {
        double actualPerimeter = vertex1.perimeter();
        double expectedPerimeter = 20.978931486560345;
        assertEquals(actualPerimeter,expectedPerimeter,"The perimeter should be 20.978931486560345");
    }

    @Test
    void translate() {
        Point_2D vec = new Point_2D(2,3);
        vertex1.translate(vec);
        Point_2D exceptedP1=new Point_2D(3,5);
        Point_2D exceptedP2=new Point_2D(5,8);
        Point_2D exceptedP3=new Point_2D(11,4);
        Point_2D exceptedP4=new Point_2D(6,4);
        Point_2D exceptedP5=new Point_2D(3,3);
        Point_2D[] expectedPoints = {exceptedP1,exceptedP2,exceptedP3,exceptedP4,exceptedP5};
        Point_2D[] actualPoints = vertex1.getAllPoints();

        assertArrayEquals(expectedPoints,actualPoints,"The arrays should be equal");
    }

    @Test
    void copy() {
        Polygon_2D vertex1Copy=(Polygon_2D) vertex1.copy();
        assertArrayEquals(vertex1.getAllPoints(),vertex1Copy.getAllPoints(),"The arrays should be equal");
    }

    @Test
    void scale() {
        vertex1.scale(p1,2);     // Scaled the polygon by 2;
        Point_2D exceptedP1 = new Point_2D(1,2);
        Point_2D exceptedP2 = new Point_2D(5,8);
        Point_2D exceptedP3 = new Point_2D(17,0);
        Point_2D exceptedP4 = new Point_2D(7,0);
        Point_2D exceptedP5 = new Point_2D(1,-2);
        Point_2D[] expectedPoints = {exceptedP1,exceptedP2,exceptedP3,exceptedP4,exceptedP5};
        Point_2D[] actualPoints = vertex1.getAllPoints();
        assertArrayEquals(expectedPoints,actualPoints,"The arrays should be equal (test 1)");

        vertex1.scale(p1,1); // Scaled the vertex by 1, the vertex need to stay in the same size.
        Point_2D[] expectedPoints3 = {p1,p2,p3,p4,p5};
        Point_2D[] actualPoints3 = vertex1.getAllPoints();
        assertArrayEquals(expectedPoints3,actualPoints3,"The arrays should be equal (test 2)");
    }

    @Test
    void rotate() {
        vertex1.rotate(p1,90); // rotate the polygon 90 degrees
        Point_2D exceptedP1 = new Point_2D(1,2);
        Point_2D exceptedP2 = new Point_2D(-2,4);
        Point_2D exceptedP3 = new Point_2D(2,10);
        Point_2D exceptedP4 = new Point_2D(2,5);
        Point_2D exceptedP5 = new Point_2D(3,2);
        Point_2D[] expectedPoints = {exceptedP1,exceptedP2,exceptedP3,exceptedP4,exceptedP5};
        Point_2D[] actualPoints = vertex1.getAllPoints();

        for (int i = 0; i < actualPoints.length; i++)
            assertTrue(expectedPoints[i].close2equals(actualPoints[i], Ex2_Const.EPS),"The arrays should be equal");
    }

    @Test
    void equal(){
        Polygon_2D vertex2 = (Polygon_2D) vertex1.copy(); // The polygons are exactly the same.
        assertTrue(vertex1.equals(vertex2),"The arrays should be equal");

        Polygon_2D vertex3 = new Polygon_2D(); // The polygons have the same points in the same order in different index's, there for they equals.
        vertex3.add(p2); vertex3.add(p3); vertex3.add(p4); vertex3.add(p5); vertex3.add(p1);
        assertTrue(vertex1.equals(vertex3),"The arrays should be equal");

        Polygon_2D vertex4 = new Polygon_2D(); // The polygons don't have the same number of points.
        vertex4.add(p3); vertex4.add(p2); vertex4.add(p4); vertex4.add(p1);
        assertFalse(vertex1.equals(vertex4),"The arrays shouldn't be equal");

        Polygon_2D vertex5 = new Polygon_2D(); // The polygons have the same points but not in the same order, but in another direction
        vertex5.add(p1); vertex5.add(p5); vertex5.add(p4); vertex5.add(p3); vertex5.add(p2);
        assertTrue(vertex1.equals(vertex5),"The arrays should be equal");
    }
}