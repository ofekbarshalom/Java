package ex2.ex2;
import ex2.geo.*;
import ex2.gui.GUIShape;
import ex2.gui.GUI_Shape;

import javax.imageio.IIOException;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

/** ShapeCollection:
 * This class represents a collection of GUI_Shape.
 * @author ofek bar-shalom
 *
 */
public class ShapeCollection implements GUI_Shape_Collection {
	private ArrayList<GUI_Shape> _shapes;

	/** Constructs a ShapeCollection object as a ArrayList of GUI_Shape*/
	public ShapeCollection() {
		_shapes = new ArrayList<GUI_Shape>();
	}

	/** get:
	 * return a reference to the i'th element in the collection.
	 * @param i - the index of the element
	 * @return a reference (NOT a copy) for the i'th element in the collection.
	 */
	@Override
	public GUI_Shape get(int i) {
		return _shapes.get(i);
	}

	/** size:
	 * return the size of the collection (if empty return 0).
	 * @return GUI_Shape in index i
	 */
	@Override
	public int size() {
		return _shapes.size();
	}

	/** removeElementAt:
	 * remove (and return) the gui_shape at the i'th location.
	 * After removing the size of this collection decreases (by 1) and the order (of the elements) remains the same - just with out the removed element.
	 * @param i - the index of the element to be removed.
	 * @return the gui_shape which was removed
	 */
	@Override
	public GUI_Shape removeElementAt(int i) {
		GUI_Shape removedShape=_shapes.get(i);
		_shapes.remove(i);
		return removedShape;
	}

	/** addAt:
	 * adds the gui_element s to this collection in the i'th position.
	 * @param s - the gui_shape
	 * @param i - the location (index) in which s should be added
	 */
	@Override
	public void addAt(GUI_Shape s, int i) {
		if(s!=null && s.getShape()!=null) {
			_shapes.add(i,s);
		}
	}

	/** add:
	 * adds the gui_element s to this collection in the last position.
	 * @param s - the gui_shape
	 */
	@Override
	public void add(GUI_Shape s) {
		if(s!=null && s.getShape()!=null) {
			_shapes.add(s);
		}
	}

	/** copy:
	 * This method constructs a deep copy of this collection.
	 */
	@Override
	public GUI_Shape_Collection copy() {
		ShapeCollection copiedShapeCollection=new ShapeCollection();
		for (int i = 0; i < _shapes.size(); i++) {
			// Add each shape from the original shape collection to the copied shape collection
			copiedShapeCollection.add(_shapes.get(i).copy());
		}
		return copiedShapeCollection;
	}

	/** sort:
	 * sorts this gui_shape collection according to the comp Comparator - in increasing order.
	 * @param comp a linear order over gui_shapes as defined in java.util.Comparator
	 */
	@Override
	public void sort(Comparator<GUI_Shape> comp) {
		_shapes.sort(comp);
	}

	/** removeAll:
	 * removes all the elements from this collection. */
	@Override
	public void removeAll() {
		_shapes.clear();
	}

	/** save:
	 * This method saves this gui_shape collection to a text file.
	 * @param file_name - the file name in which this collection will be saved.
	 */
	@Override
	public void save(String file_name) {
		String path = file_name + ".txt";
		ArrayList<String> lines = new ArrayList<>();  // Crate an array of lines, Each line represent a GUI_Shape
		for (int i = 0; i < this._shapes.size(); i++) { // loop over all the shapes in shape collection
			String shape = this._shapes.get(i).toString(); // Crate a String representation of the shape using toString
			lines.add(shape); // add the shape as a string to the line array
		}
		try {
			Files.write(Paths.get(path),lines); // Writes "lines" to a File and saving it
			System.out.println("File saved successfully.");
		} catch (Exception e) {
			System.err.println("Error writing to file: " + e.getMessage());
		}
	}

	/** load:
	 * restore a gui_shape collection from text file (as saved be the save method).
	 * @param file - the name of the text file to create a gui shape file from.
	 */
	@Override
	public void load(String file) {
		try {
			_shapes.clear();
			String path = file; // Store the path of the file
			String content = new String(Files.readAllBytes(Paths.get(path))); // Read the content of the file and store it in "content"
			String[] shapes = content.split("\n"); // Split the content into an array of shapes
			for (int i = 0; i < shapes.length; i++) { // looping over the array of shapes (the String array)
				String shape = shapes[i];
				GUIShape gs = new GUIShape(shape); // Create a GUIShape object from the shape String.
				this._shapes.add(gs); // Add the shape to the shape collection "_shapes"
			}
		}
		catch (IOException e){
			System.err.println("file is not readable" + e.getMessage());
		}
	}

	/** toString:
	 * @return a String representation of the collection by concatenating the string representations of its elements*/
	@Override
	public String toString() {
		String ans = "";
		for(int i=0;i<size();i=i+1) {
			ans += this.get(i);
		}
		return ans;
	}

	/** equals:
	 * Computes if the object (p) is equal to the shape collection.
	 * @param p the object we compare to the shape collection.
	 * @return true iff the object (p) is equal to the shape collection.
	 */
	@Override
	public boolean equals(Object p){
		if(p == null || !(p instanceof ShapeCollection)) {return false;}
		ShapeCollection p2 = (ShapeCollection) p;
		int count = 0;
		if(this._shapes.size() == p2.size()) {
			for (int i = 0; i < this._shapes.size(); i++) {
				if (this._shapes.get(i).getClass() == p2.get(i).getClass() && this._shapes.get(i).getColor() == p2.get(i).getColor()) {
					if (this._shapes.get(i).getTag() == p2.get(i).getTag() && this._shapes.get(i).isSelected() == p2.get(i).isSelected()) {
						if (this._shapes.get(i).isSelected() == p2.get(i).isSelected())
							count++; // if all the quality's are equal add 1 to count
					}
				}
			}
		}
		return (count == this._shapes.size());
	}
}
