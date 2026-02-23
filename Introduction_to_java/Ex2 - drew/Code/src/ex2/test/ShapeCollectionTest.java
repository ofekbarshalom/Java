package ex2.test;

import ex2.ex2.ShapeCollection;
import ex2.geo.*;
import ex2.gui.GUIShape;
import ex2.gui.GUI_Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ShapeCollectionTest {
    private Point_2D p1, p2, p3, p4, p5;
    private Polygon_2D v1; private GUIShape gs1;
    private Circle_2D c1; private GUIShape gs2;
    private Rect_2D r1; private GUIShape gs3;
    private Triangle_2D t1; private GUIShape gs4;
    private Segment_2D s1; private GUIShape gs5;
    private ShapeCollection collection1;

    @BeforeEach // set a shape collection for tests before each test
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

        collection1 = new ShapeCollection();
        collection1.add(gs1); collection1.add(gs2); collection1.add(gs3); collection1.add(gs4); collection1.add(gs5);
    }

    @Test
    void equals(){
        ShapeCollection copiedCollection1 = (ShapeCollection) collection1.copy();
        assertTrue(collection1.equals(copiedCollection1),"The collection should be equal");

        copiedCollection1.removeAll();
        assertFalse(collection1.equals(copiedCollection1),"The collection shouldn't be equal");
    }

    @Test
    void get() {
        GUIShape actual = (GUIShape) collection1.get(2);
        GUI_Shape excepted = gs3;
        assertEquals(excepted.getTag(),actual.getTag(),"The tags should be equal");
        assertEquals(excepted.getShape(),actual.getShape(),"The shapes should be equal");
        assertEquals(excepted.getColor(),actual.getColor(),"The color should be equal");
        assertTrue(actual.isFilled(),"isFilled should be true");
        assertFalse(actual.isSelected(),"isSelected should be true");
    }

    @Test
    void size() {assertEquals(5,collection1.size(),"the size should be 5");}

    @Test
    void removeElementAt() {
        collection1.removeElementAt(0);
        assertNotEquals(collection1.get(0).toString(),gs1.toString(),"The Strings shouldn't be equal");

        assertEquals(collection1.get(0).getTag(),gs2.getTag(),"The tags should be equal");
        assertEquals(collection1.get(0).getShape(),gs2.getShape(),"The shapes should be equal");
        assertEquals(collection1.get(0).getColor(),gs2.getColor(),"The color should be equal");
        assertFalse(collection1.get(0).isSelected(),"isFilled should be false");
        assertFalse(collection1.get(0).isFilled(),"isSelected should be false");
    }

    @Test
    void addAt() {
        collection1.addAt(gs1,2);
        assertEquals(collection1.get(2).getTag(),gs1.getTag(),"The tags should be equal");
        assertEquals(collection1.get(2).getShape(),gs1.getShape(),"The shapes should be equal");
        assertEquals(collection1.get(2).getColor(),gs1.getColor(),"The colors should be equal");
        assertFalse(collection1.get(2).isSelected(),"isFilled should be false");
        assertTrue(collection1.get(2).isFilled(),"isSelected should be true");

        assertEquals(collection1.get(3).getTag(),gs3.getTag(),"The tags should be equal");
        assertEquals(collection1.get(3).getShape(),gs3.getShape(),"The shapes should be equal");
        assertEquals(collection1.get(3).getColor(),gs3.getColor(),"The colors should be equal");
        assertFalse(collection1.get(3).isSelected(),"isFilled should be false");
        assertTrue(collection1.get(3).isFilled(),"isSelected should be true");
    }

    @Test
    void add() {
        collection1.add(gs1);
        assertEquals(collection1.get(5).getTag(),gs1.getTag(),"The tags should be equal");
        assertEquals(collection1.get(5).getShape(),gs1.getShape(),"The shapes should be equal");
        assertEquals(collection1.get(5).getColor(),gs1.getColor(),"The colors should be equal");
        assertFalse(collection1.get(5).isSelected(),"isFilled should be false");
        assertTrue(collection1.get(5).isFilled(),"isSelected should be true");
    }

    @Test
    void copy() {
        ShapeCollection copiedCollection1 = (ShapeCollection) collection1.copy();
        assertTrue(collection1.equals(copiedCollection1),"The collections should be equal");
    }

    @Test
    void sort() {
        ShapeComp comp = new ShapeComp(0); // Sort by Tag
        collection1.sort(comp);
        for (int i = 0; i < collection1.size()-1; i++)
            assertTrue(collection1.get(i).getTag()<collection1.get(i+1).getTag(),"The sort should be by tag");

        ShapeComp comp2 = new ShapeComp(1); // Sort by anti-Tag
        collection1.sort(comp2);
        for (int i = 0; i < collection1.size()-1; i++)
            assertTrue(collection1.get(i).getTag()>collection1.get(i+1).getTag(),"The sort should be by anti tag");

        ShapeComp comp3 = new ShapeComp(2); // Sort by area
        collection1.sort(comp3);
        for (int i = 0; i < collection1.size()-1; i++)
            assertTrue(collection1.get(i).getShape().area()<collection1.get(i+1).getShape().area(),"The sort should be by area");

        ShapeComp comp4 = new ShapeComp(3); // Sort by anti-area
        collection1.sort(comp4);
        for (int i = 0; i < collection1.size()-1; i++)
            assertTrue(collection1.get(i).getShape().area()>collection1.get(i+1).getShape().area(),"The sort should be by anti-area");

        ShapeComp comp5 = new ShapeComp(4); // Sort by perimeter
        collection1.sort(comp5);
        for (int i = 0; i < collection1.size()-1; i++)
            assertTrue(collection1.get(i).getShape().perimeter()<collection1.get(i+1).getShape().perimeter(),"The sort should be by perimeter");

        ShapeComp comp6 = new ShapeComp(5); // Sort by anti-perimeter
        collection1.sort(comp6);
        for (int i = 0; i < collection1.size()-1; i++)
            assertTrue(collection1.get(i).getShape().perimeter()>collection1.get(i+1).getShape().perimeter(),"The sort should be by anti-perimeter");

        ShapeComp comp7 = new ShapeComp(6); // Sort by toString
        collection1.sort(comp7);
        for (int i = 0; i < collection1.size()-1; i++)
            assertTrue(collection1.get(i).toString().compareTo(collection1.get(i+1).toString())<=0,"The sort should be by toString");

        ShapeComp comp8 = new ShapeComp(7); // Sort by anti-toString
        collection1.sort(comp8);
        for (int i = 0; i < collection1.size()-1; i++)
            assertTrue(collection1.get(i).toString().compareTo(collection1.get(i+1).toString())>=0,"The sort should be by anti-toString");
    }

    @Test
    void removeAll() {
        collection1.removeAll();
        assertEquals(collection1.size(),0,"The collection should be empty");
    }

    @Test
    void save() {
        ShapeCollection collection2 = new ShapeCollection();
        collection2.add(gs1); collection2.add(gs2);
        collection2.save("testCollection");
        assertTrue(Files.exists(Paths.get("testCollection.txt")), "File should be saved successfully.");
    }

    @Test
    void load() throws Exception{
        ShapeCollection collection2 = new ShapeCollection();
        String expectedContent = "GUIShape,-16776961,true,1,Polygon_2D,1.0,2.0, 3.0,5.0, 9.0,1.0, 4.0,1.0, 1.0,0.0, \n" +
                "GUIShape,-16777216,false,2,Circle_2D,1.0,2.0, 3.0";
        Path path1 = Paths.get("textShape.txt");

        Files.writeString(path1,expectedContent);
        collection2.load(path1.toString());
        assertEquals(collection2.size(),2,"The collections should be the same length");
    }

    @Test
    void ToString() {
        ShapeCollection collection2 = new ShapeCollection();
        collection2.add(gs1); collection2.add(gs2);
        String actual = collection2.toString();
        String expected = "GUIShape,-16776961,true,1,Polygon_2D,[1.0,2.0, 3.0,5.0, 9.0,1.0, 4.0,1.0, 1.0,0.0]GUIShape,-16777216,false,2,Circle_2D,1.0,2.0, 3.0";
        assertEquals(actual,expected,"The Strings should be equal");
    }
}