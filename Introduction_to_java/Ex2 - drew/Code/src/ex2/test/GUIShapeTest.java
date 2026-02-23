package ex2.test;

import ex2.ex2.ShapeCollection;
import ex2.geo.*;
import ex2.gui.GUIShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GUIShapeTest {
    private Point_2D p1, p2, p3 ,p4, p5;
    private GUIShape gs1, gs2, gs3, gs4, gs5;
    private Polygon_2D v1;
    private Circle_2D c1;
    private Rect_2D r1;
    private Triangle_2D t1;
    private Segment_2D s1;

    @BeforeEach
    void setUp(){
        p1 = new Point_2D(1, 2);
        p2 = new Point_2D(3, 5);
        p3 = new Point_2D(9, 1);
        p4 = new Point_2D(4, 1);
        p5 = new Point_2D(1, 0);

        v1 = new Polygon_2D();
        v1.add(p1); v1.add(p2); v1.add(p3); v1.add(p4); v1.add(p5);
        gs1 = new GUIShape(v1, true, Color.blue, 1);

        c1 = new Circle_2D(p1,3);
        gs2 = new GUIShape(c1, false, Color.black, 2);

        r1 = new Rect_2D(p1,p2);
        gs3 = new GUIShape(r1, true, Color.white, 3);

        t1 = new Triangle_2D(p1,p2,p3);
        gs4 = new GUIShape(t1, false, Color.yellow, 4);

        s1 = new Segment_2D(p1,p2);
        gs5 = new GUIShape(s1, true, Color.green, 5);
    }

    @Test
    void getShape() {
        GUIShape s1 = new GUIShape(gs1.getShape(),true,Color.BLUE,6);  // getShape test for polygon
        assertEquals(s1.getShape(),gs1.getShape(),"The shapes should be equal (polygon)");

        GUIShape s2 = new GUIShape(gs2.getShape(),true,Color.BLUE,6);  // getShape test for circle
        assertEquals(s2.getShape(),gs2.getShape(),"The shapes should be equal (circle)");

        GUIShape s3 = new GUIShape(gs3.getShape(),true,Color.BLUE,6);  // getShape test for rectangle
        assertEquals(s3.getShape(),gs3.getShape(),"The shapes should be equal (rectangle)");

        GUIShape s4 = new GUIShape(gs4.getShape(),true,Color.BLUE,6);  // getShape test for triangle
        assertEquals(s4.getShape(),gs4.getShape(),"The shapes should be equal (triangle)");

        GUIShape s5 = new GUIShape(gs5.getShape(),true,Color.BLUE,6);  // getShape test for segment
        assertEquals(s5.getShape(),gs5.getShape(),"The shapes should be equal (segment)");
    }

    @Test
    void setShape() {
        GUIShape actual = new GUIShape(gs5.getShape(),true,Color.BLUE,6);
        actual.setShape(v1);    // set shape to for polygon
        assertEquals(actual.getShape(),gs1.getShape(),"The shapes should be equal (polygon)");

        actual.setShape(c1);   // set shape to for circle
        assertEquals(actual.getShape(),gs2.getShape(),"The shapes should be equal (circle)");

        actual.setShape(r1);   // set shape to for rectangle
        assertEquals(actual.getShape(),gs3.getShape(),"The shapes should be equal (rectangle)");

        actual.setShape(t1);   // set shape to for triangle
        assertEquals(actual.getShape(),gs4.getShape(),"The shapes should be equal (triangle)");

        actual.setShape(s1);   // set shape to for segment
        assertEquals(actual.getShape(),gs5.getShape(),"The shapes should be equal (segment)");
    }

    @Test
    void isFilled() {
        assertTrue(gs1.isFilled(),"isFilled should be true");
        assertFalse(gs2.isFilled(),"isFilled should be false");
    }

    @Test
    void setFilled() {
        gs1.setFilled(false);  // set isFilled to false
        assertFalse(gs1.isFilled(),"isFilled should be false");

        gs2.setFilled(true);   // set isFilled to true
        assertTrue(gs2.isFilled(),"isFilled should be true");
    }

    @Test
    void getColor() {
        Color actual = gs1.getColor();
        Color expected = Color.BLUE;
        assertEquals(expected,actual,"The colors should be equal");

        assertNotEquals(Color.BLACK,actual,"The colors shouldn't be equal");
    }

    @Test
    void setColor() {
        gs1.setColor(Color.BLACK);
        Color actual = gs1.getColor();
        Color expected = Color.BLACK;
        assertEquals(actual,expected,"The colors should be equal");
    }

    @Test
    void getTag() {
        int actual = gs1.getTag();
        int expected = 1;
        assertEquals(expected,actual,"The tags should be equal");

        assertNotEquals(2,actual,"The tags shouldn't be equal");
    }

    @Test
    void setTag() {
        gs1.setTag(2);
        int actual = gs1.getTag();
        int expected = 2;
        assertEquals(actual,expected,"The tags should be equal");
    }

    @Test
    void copy() {
        GUIShape expected = new GUIShape(v1, true, Color.blue, 1);
        GUIShape copiedShape = (GUIShape) gs1.copy();

        assertTrue(expected.equals(copiedShape), "The GUI_Shapes should be equal");
    }

    @Test
    void ToString() {
        String actual = gs1.toString();
        String expected = "GUIShape,-16776961,true,1,Polygon_2D,[1.0,2.0, 3.0,5.0, 9.0,1.0, 4.0,1.0, 1.0,0.0]";
        assertEquals(actual,expected,"The Strings should be equal");
    }

    @Test
    void isSelected() {
        assertFalse(gs1.isSelected(),"isSelected should be false");
    }

    @Test
    void setSelected() {
        gs1.setSelected(true);
        assertTrue(gs1.isSelected(),"isSelected should be true");
    }

    @Test
    void equals(){
        GUIShape gs2 = (GUIShape)gs1.copy();
        assertTrue(gs1.equals(gs2),"The GUI_Shapes should be equals");

        GUIShape gs3 = (GUIShape)gs4.copy();
        assertFalse(gs1.equals(gs3),"The GUI_Shapes shouldn't be equals");
    }
}