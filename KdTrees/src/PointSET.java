//This is the brute-force implementation:
//should use SET or java.util.TreeSet

import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
	private TreeSet<Point2D> tree;
	
   public PointSET() {
	   // construct an empty set of points 
	   tree = new TreeSet<Point2D>();
	   
   }
   public boolean isEmpty() {
	   // is the set empty? 
	   return tree.isEmpty();
   }
   public int size()  {
	   // number of points in the set 
	   return tree.size();
   }
   public void insert(Point2D p) {
	   // add the point to the set (if it is not already in the set)
	   tree.add(p);
   }
   public  boolean contains(Point2D p) {
	   // does the set contain point p? 
	   return tree.contains(p);
   }
   public void draw() {
	   // draw all points to standard draw 
	   for (Point2D p : tree) {
		   p.draw();
	   }
   }
   public Iterable<Point2D> range(RectHV rect) {
	   // all points that are inside the rectangle 
	   ArrayList<Point2D> list = new ArrayList<Point2D>();
	   for (Point2D p : tree) {
		   if (rect.contains(p))
			   list.add(p);
	   }
	   return list;
   }
   public Point2D nearest(Point2D p) {
	   // a nearest neighbor in the set to point p; null if the set is empty 
	   double min = Double.MAX_VALUE;
	   Point2D nearest = null;
	   for (Point2D q : tree) {
		   if(p != q && p.distanceTo(q) < min) {
			   min = p.distanceTo(q);
			   nearest = q;
		   }
	   }
	   return nearest;
   }

   public static void main(String[] args) {
	   // unit testing of the methods (optional) 
	   
   }
}