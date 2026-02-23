package ex2.gui;

import ex2.ex2.Ex2_Const;
import ex2.ex2.GUI_Shape_Collection;
import ex2.ex2.ShapeCollection;
import ex2.geo.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;

/** ReadMe:
 * The goal of the Ex2 assignment is to complete an interface capable of drawing shape and performing actions on them.
 * Some of these actions include sorting shapes by certain criteria, remove shapes, saving and loading them from a file,
 * as well as selecting shapes and performing action on the selected ones.
 * After completing these actions, we were asked to crate tests for them using JUnit Test class
 * and checking for their correctness.
 * we also needed to find bugs and complete different sections in the code that were missing.
 * Written for 101 java course it uses simple static functions to allow a
 * "Singleton-like" implementation.
 *
 * Bibliography:
 * wikipedia - the shoelace formula (https://en.wikipedia.org/wiki/Shoelace_formula)
 * wikipedia - the barycentric coordinates formula (https://en.wikipedia.org/wiki/Barycentric_coordinate_system)
 * wikipedia - Heron's formula (https://en.wikipedia.org/wiki/Heron%27s_formula)
 * chatGPT and wikipedia - ray casing formula (https://en.wikipedia.org/wiki/Point_in_polygon)
 *
 * @author boaz.ben-moshe + ofek bar-shalom (ID: 324161421)
 */


/** Ex2 Class:
 * This class is a simple "interlayer" connecting (aka simplifying) the
 * StdDraw with the Map class.
 * Ex2 is a function that setup drew window environment, it can handel mouse movement and clicks
 * and operate accordingly.
 * by handling this clicks it can change the mode of operation.
 */
public class Ex2 implements Ex2_GUI {
    private GUI_Shape_Collection _shapes = new ShapeCollection();
    private GUI_Shape _gs;
    private Polygon_2D _pp;
    private Color _color = Color.blue;
    private boolean _fill = false;
    private String _mode = "";
    private Point_2D _p1;

    private static Ex2 _winEx2 = null;

    private Ex2() {init(null);}

    /** init:
     * a constructor that Initialize Ex2 object (window) with a given GUI shape collection, or with a new empty collection if none is provided.
     * This method is also responsible for setting up the GUI_Shape, polygon, color, fill, mode and point.
     * @param s a GUI collection of shapes
     */
    public void init(GUI_Shape_Collection s) {
        if (s == null) {
            _shapes = new ShapeCollection();
        } else {
            _shapes = s;
        }
        GUI_Shape _gs = null;
        Polygon_2D _pp = null;
        _color = Color.blue;
        _fill = false;
        _mode = "";
        Point_2D _p1 = null;
    }

    /** show:
     *  a method that sets up the display window with a specified size and draws the shapes stored in the collection.
     * @param d the size of the window
     */
    public void show(double d) {
        StdDraw_Ex2.setScale(0, d);
        StdDraw_Ex2.show();
        drawShapes();
    }

    /** getInstance:
     * Return the instance of Ex2.
     * if the instance does not exist it creates one and returns it.
     * @return the Ex2 instance
     */
    public static Ex2 getInstance() {
        if (_winEx2 == null) {
            _winEx2 = new Ex2();
        }
        return _winEx2;
    }

    /* private static void drawGrid(int x, int y, int delta) {
         for(int i=0;i<x;i+=delta) {
             StdDraw_Ex2.line(i, 0, i, y);
         }
         for(int i=0;i<y;i+=delta) {
             StdDraw_Ex2.line(0, i, x, i);
         }
    } */

    /** drawShapes:
     * Clears the window and drew all the shapes from the shape collection.
     * After that, the function checks if there is a shape in _gs, if so, is draws it.
     * Finally, the function will display the shapes.
     */
    public void drawShapes() {
        StdDraw_Ex2.clear();
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape sh = _shapes.get(i);

            drawShape(sh);
        }
        if (_gs != null) {
            drawShape(_gs);
        }
        StdDraw_Ex2.show();
    }

    /** drawShape:
     * this function Checks if a GUI_Shape contains an instance of a specific shape,
     * then crates a deep copy of the shape and checks if it is filled before drawing it.
     * @param g The Gui_Shape to draw.
     */
    private static void drawShape(GUI_Shape g) {
        StdDraw_Ex2.setPenColor(g.getColor());
        if (g.isSelected()) {
            StdDraw_Ex2.setPenColor(Color.gray);
        }
        GeoShape gs = g.getShape();
        boolean isFill = g.isFilled();
        if (gs instanceof Circle_2D) {
            Circle_2D c = (Circle_2D) gs;
            Point_2D cen = c.getCenter();
            double rad = c.getRadius();
            if (isFill) {
                StdDraw_Ex2.filledCircle(cen.x(), cen.y(), rad);
            } else {
                StdDraw_Ex2.circle(cen.x(), cen.y(), rad);
            }
        }
        if (gs instanceof Segment_2D) {
            Segment_2D c = (Segment_2D) gs;
            Point_2D m0 = c.get_p1();
            Point_2D m1 = c.get_p2();
            StdDraw_Ex2.line(m0.x(), m0.y(), m1.x(), m1.y());
        }
        Point_2D[] ps = null;
        if (gs instanceof Polygon_2D) {
            ps = ((Polygon_2D) gs).getAllPoints();
        }
        if (gs instanceof Triangle_2D) {
            ps = ((Triangle_2D) gs).getAllPoints();
        }
        if(gs instanceof Rect_2D){
            Rect_2D r = (Rect_2D) gs;
            ps = new Point_2D[]{r.get_p1(),r.get_p3(),r.get_p2(),r.get_p4()};
        }
        if (ps != null) {
            double[] xx = new double[ps.length];
            double[] yy = new double[ps.length];
            for (int i = 0; i < xx.length; i++) {
                xx[i] = ps[i].x();
                yy[i] = ps[i].y();
            }
            if (isFill) {
                StdDraw_Ex2.filledPolygon(xx, yy);
            } else {
                StdDraw_Ex2.polygon(xx, yy);
            }
        }
    }

    /** setColor:
     * sets the color of selected GUI_Shapes in the "_shapes" collection to the specified color.
     * the method loops over all GUI_Shapes in the collection and if a shape is selected, it changes its color to the provided color.
     * @param c The Color to set to the selected shapes.
     */
    private void setColor(Color c) {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            if (s.isSelected()) {
                s.setColor(c);
            }
        }
    }

    /** setFill:
     * loops over all GUI_Shapes in the collection and if a shape is selected, sets whether they should be filled or not.
     */
    private void setFill() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            if (s.isSelected()) {
                s.setFilled(_fill);
            }
        }
    }

    /** remove:
     * loops over all GUI_Shapes in the collection and if a shape is selected, it removes it from "_shapes".
     */
    private void remove() {
        for (int i = _shapes.size() - 1; i >= 0; i--) {
            GUI_Shape s = _shapes.get(i);
            if (s.isSelected()) {
                _shapes.removeElementAt(i);
            }
        }
    }

    /** actionPerformed:
     * performs various actions based on a given String.
     * It updates the mode
     * then based on the String we got the function can sets color, fills shapes,
     * removes shapes, saves, loads, sorts shapes, clears the collection,
     * selects or deselects shapes, or displays information depending on the command.
     * @param p a String that represent The command to be executed.
     */
    public void actionPerformed(String p) {
        _mode = p;
        if (p.equals("Blue")) {
            _color = Color.BLUE;
            setColor(_color);
        }
        if (p.equals("Red")) {
            _color = Color.RED;
            setColor(_color);
        }
        if (p.equals("Green")) {
            _color = Color.GREEN;
            setColor(_color);
        }
        if (p.equals("White")) {
            _color = Color.WHITE;
            setColor(_color);
        }
        if (p.equals("Black")) {
            _color = Color.BLACK;
            setColor(_color);
        }
        if (p.equals("Yellow")) {
            _color = Color.YELLOW;
            setColor(_color);
        }
        if (p.equals("Fill")) {
            _fill = true;
            setFill();
        }
        if (p.equals("Empty")) {
            _fill = false;
            setFill();
        }
        if (p.equals("Remove")) {
            remove();
        }
        if (p.equals("Save")) {
            save();
        }
        if (p.equals("Load")) {
            load();
        }
        if (p.equals("ByArea")) {
            _shapes.sort(ShapeComp.CompByArea);
        }
        if (p.equals("ByAntiArea")) {
            _shapes.sort(ShapeComp.CompByAntiArea);
        }
        if (p.equals("ByPerimeter")) {
            _shapes.sort(ShapeComp.CompByPerimeter);
        }
        if (p.equals("ByAntiPerimeter")) {
            _shapes.sort(ShapeComp.CompByAntiPerimeter);
        }
        if (p.equals("ByToString")) {
            _shapes.sort(ShapeComp.CompByToString);
        }
        if (p.equals("ByAntiToString")) {
            _shapes.sort(ShapeComp.CompByAntiToString);
        }
        if (p.equals("ByTag")) {
            _shapes.sort(ShapeComp.CompByTag);
        }
        if (p.equals("ByAntiTag")) {
            _shapes.sort(ShapeComp.CompByAntiTag);
        }
        if (p.equals("Clear")) {
            _shapes.removeAll();
        }
        if (p.equals("None")) {
            selectNone();
        }
        if (p.equals("All")) {
            selectAll();
        }
        if (p.equals("Anti")) {
            selectAnti();
        }
        if (p.equals("Info")) {
            System.out.println(getInfo());
        }

        drawShapes();

    }

    /** save:
     * prompts the user to select a location to save the current state of the shape collection to a text file.
     * It displays a file dialog window with the title "Save to Text file", allowing the user to choose a directory and specify a filename.
     * If a filename is selected and confirmed by the user, the shape collection is saved to the specified location.
     */
    private void save() {
        FileDialog chooser = new FileDialog(StdDraw_Ex2.getFrame(), "Save to Text file", FileDialog.SAVE);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            _shapes.save(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    /** load:
     * prompts the user to select a text file to load data from.
     * It displays a file dialog window with the title "Load from Text file", allowing the user to choose a file to load.
     * If a file is selected and confirmed by the user, the shape collection is loaded from the specified file location.
     */
    private void load() {
        FileDialog chooser = new FileDialog(StdDraw_Ex2.getFrame(), "Load from Text file", FileDialog.LOAD);
        chooser.setVisible(true);
        String filename = chooser.getFile();
        if (filename != null) {
            _shapes.load(chooser.getDirectory() + File.separator + chooser.getFile());
        }
    }

    /** mouseClicked:
     * this functions handles mouse click events.
     * It performs different actions based on the current "_mode" and the position of the mouse click (p).
     * the actions are creating shapes, moving, copying, rotating, adding points to polygons or triangles,
     * selecting points, scaling shapes, and updating the display of shapes.
     * @param p the Point where the mouse was clicked on.
     */
    public void mouseClicked(Point_2D p) {
        System.out.println("Mode: " + _mode + "  " + p);
        if (_mode.equals("Rect") || _mode.equals("Circle") || _mode.equals("Segment")) {
            if (_gs == null) {
                _p1 = new Point_2D(p);
            } else {
                _gs.setColor(_color);
                _gs.setFilled(_fill);
                _shapes.add(_gs);
                _gs = null;
                _p1 = null;
            }
        }
        if (_mode.equals("Move")) {
            if (_p1 == null) {
                _p1 = new Point_2D(p);
            } else {
                _p1 = new Point_2D(p.x() - _p1.x(), p.y() - _p1.y());
                move();
                _p1 = null;
            }
        }
        if (_mode.equals("Copy")) {
            if (_p1 == null) {
                _p1 = new Point_2D(p);
            } else {
                _p1 = new Point_2D(p.x() - _p1.x(), p.y() - _p1.y());
                copy();
                _p1 = null;
            }
        }
        if (_mode.equals("Rotate")) {
            if (_p1 == null) {
                _p1 = new Point_2D(p);
            } else {
                rotate(p);
                _p1 = null;
            }
        }


        if (_mode.equals("Polygon")) {
            if (_pp == null) {
                _pp = new Polygon_2D();
            }
            _p1 = new Point_2D(p);
            _pp.add(p);
        }

        if (_mode.equals("Triangle")) {
            if (_pp == null) {
                _pp = new Polygon_2D();
            }
            _p1 = new Point_2D(p);
            _pp.add(p);
            if (_pp.getAllPoints().length == 3) {
                Point_2D[] pp = _pp.getAllPoints();
                Triangle_2D tt = new Triangle_2D(pp[0], pp[1], pp[2]);
                GUI_Shape s = new GUIShape(tt, _fill, _color, 0);
                _shapes.add(s);
                _pp = null;
                _p1 = null;
                _gs = null;
            }
        }
        if (_mode.equals("Point")) {
            select(p);
        }
        //
        if (_mode.equals("Scale_90%")) {
            scale(p, 0.9);
        }
        if (_mode.equals("Scale_110%")) {
            scale(p, 1.10);
        }
        drawShapes();
    }

    /** scale:
     * loops over all GUI_Shapes in the collection "_shapes" and if a shape is selected,
     * it scales it around a specified point by a given ratio.
     * @param p The center point of scaling.
     * @param ratio The ratio of scaling.
     */
    private void scale(Point_2D p, double ratio) {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            GeoShape g = s.getShape();
            if (s.isSelected() && g != null) {
                g.scale(p, ratio);
            }
        }
    }

    /** select:
     * select or deselect a shape if the point is contained in the shape.
     * @param p The point to check for containment in the shape.
     */
    private void select(Point_2D p) {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            GeoShape g = s.getShape();
            if (g != null && g.contains(p)) {
                s.setSelected(!s.isSelected());
            }
        }
    }

    /** move:
     * loops over all GUI_Shapes in the collection "_shapes" and if a shape is selected,
     * it moves it by a specified vector.
     */
    private void move() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            GeoShape g = s.getShape();
            if (s.isSelected() && g != null) {
                g.translate(_p1);
            }
        }
    }
    /** copy:
     * loops over all GUI_Shapes in the collection "_shapes" and if a shape is selected,
     * it copies and translates it by a specified vector.
     */
    private void copy() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            if (s.isSelected()) {
                GUI_Shape s1 = s.copy();
                GeoShape g = s1.getShape();
                g.translate(_p1);
                _shapes.add(s1);
            }
        }
    }

    /** rotate:
     * loops over all GUI_Shapes in the collection "_shapes" and if a shape is selected,
     * it rotates it around a specified point by a given angle.
     * @param ang The Point to rotate the shape around
     */
    private void rotate(Point_2D ang) {
        double dx = ang.x() - _p1.x();
        double dy = ang.y() - _p1.y();
        double da = Math.atan2(dy, dx);
        da = Math.toDegrees(da);
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            GeoShape g = s.getShape();
            if (s.isSelected()) {
                g.rotate(_p1, da);
            }
        }
    }

    /** selectAll:
     * loops over all GUI_Shapes in the collection "_shapes" and selects all shapes.
     */
    private void selectAll() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            s.setSelected(true);
        }
    }

    /** printsInfo:
     * loops over all GUI_Shapes in the collection "_shapes" and if a shape is selected, it prints information about it.
     */
    private void printInfo() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            if (s.isSelected()) {
                System.out.println(i + ") " + s.toString());
            }
        }
    }

    /** selectNone:
     * loops over all GUI_Shapes in the collection "_shapes" and deselects all shapes.
     */
    private void selectNone() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            s.setSelected(false);
        }
    }

    /** selectAnti:
     * loops over all GUI_Shapes in the collection "_shapes",
     * if a shape is selected it deselected and the other way around.
     */
    private void selectAnti() {
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            s.setSelected(!s.isSelected());
        }
    }

    /** mouseRightClicked:
     * this function handles right-click events.
     * If the current mode is "Polygon" and there is a Polygon_2D object(_pp) in not null,
     * it creates a new GUIShape from the constructed Polygon_2D and adds it to the shape collection.
     * Finally, it resets all relevant variables (_pp, _gs, _p1) and updates the display of shapes.
     * @param p The Point where the right-click happened.
     */
    public void mouseRightClicked(Point_2D p) {
        if (_mode.equals("Polygon") && _pp != null) {
            GUIShape s = new GUIShape(_pp, _fill, _color, 0);
            _shapes.add(s);
        }
        _pp = null;
        _gs = null;
        _p1 = null;
        drawShapes();
    }

    /** mouseMoved:
     * this function handles mouse movement by updating the shape being drawn if in drawing mode(_mode).
     * @param e The mouse event
     */
    public void mouseMoved(MouseEvent e) {
        if (_p1 != null) {
            double x1 = StdDraw_Ex2.mouseX();
            double y1 = StdDraw_Ex2.mouseY();
            GeoShape gs = null;
            // System.out.println("M: "+x1+","+y1);
            Point_2D p = new Point_2D(x1, y1);
            if (_mode.equals("Circle")) {
                double r = _p1.distance(p);
                gs = new Circle_2D(_p1, r);
            }
            if (_mode.equals("Rect")) {
                gs = new Rect_2D(_p1, p);
            }
            if (_mode.equals("Segment")) {
                gs = new Segment_2D(_p1, p);
            }

            if (_mode.equals("Polygon") || _mode.equals("Triangle")) {
                if (_pp == null) {
                    _pp = new Polygon_2D();
                    _pp.add(_p1);
                }
                Polygon_2D gg = new Polygon_2D(_pp);
                gg.add(p);
                gs = gg;
            }
            _gs = new GUIShape(gs, false, Color.pink, 0);
            drawShapes();
        }
    }

    /** getShape_Collection:
     * this function return the GUI_Shape_Collection
     * @return the GUI_Shape_Collection "_shapes"
     */
    @Override
    public GUI_Shape_Collection getShape_Collection() {
        // TODO Auto-generated method stub
        return this._shapes;
    }

    /** show:
     * displays the graphical user interface with the default dimension size from the Ex2_Const class.
     */
    @Override
    public void show() {
        show(Ex2_Const.DIM_SIZE);
    }

    /** getInfo:
     * return information about the GUI_Shape_Collection "_shapes"
     * @return Information about the shapes.
     */
    @Override
    public String getInfo() {
        // TODO Auto-generated method stub
        String ans = "";
        for (int i = 0; i < _shapes.size(); i++) {
            GUI_Shape s = _shapes.get(i);
            ans += s.toString() + "\n";
            //	ans +=s.toString()+"  area: "+s.getShape().area()+"  per: "+s.getShape().perimeter()+"\n";
        }
        return ans;
    }
}