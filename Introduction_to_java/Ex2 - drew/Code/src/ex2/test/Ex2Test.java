package ex2.test;

import ex2.ex2.GUI_Shape_Collection;
import ex2.ex2.ShapeCollection;
import ex2.geo.Circle_2D;
import ex2.geo.Point_2D;
import ex2.gui.Ex2;
import ex2.gui.GUIShape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

class Ex2Test {
    private Ex2 ex2;
    GUI_Shape_Collection collection;
    Point_2D p1;
    Circle_2D c1;
    GUIShape gs1;

    @BeforeEach // set Ex2 object for tests before each test
    void setUp(){
        ex2 = Ex2.getInstance();
        collection = new ShapeCollection();

        p1 = new Point_2D(4,5);
        c1 = new Circle_2D(p1,4);
        gs1 = new GUIShape(c1,true,Color.BLACK,1);
    }

    @Test
    void init() {
        ex2.init(null);      // test with null
        assertTrue(ex2.getShape_Collection() instanceof ShapeCollection,"ex2 should have the instance of shape collection");

        ex2.init(collection);   // test with shape collection
        assertTrue(collection.equals(ex2.getShape_Collection()),"The collections should be equal");
    }

    @Test
    void getShape_Collection() {
        collection.add(gs1);
        ex2.init(collection);
        GUI_Shape_Collection actual = ex2.getShape_Collection();
        assertTrue(collection.equals(actual),"The collections should be equal");
    }

    @Test
    void getInfo() {
        String actual = ex2.getInfo();
        String excepted = "GUIShape,-16777216,true,1,Circle_2D,4.0,5.0, 4.0\n";
        assertEquals(excepted,actual,"The Strings should be equal");
    }
}