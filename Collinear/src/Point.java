import java.util.Comparator;

public class Point implements Comparable<Point> {
	private final int x,y;
	
   public final Comparator<Point> SLOPE_ORDER;
   // compare points by slope to this point
   
   private class SlopeOrder implements Comparator<Point>{
	   public int compare(Point a,Point b){
		   double aSlope = slopeTo(a);
		   double bSlope = slopeTo(b);
		   if(aSlope < bSlope)
			   return -1;
		   if(aSlope > bSlope)
			   return 1;
		   return 0;
				  
		   
//		   return (int) (slopeTo(a) - slopeTo(b));
	   }
   }

   public Point(int x, int y){
	   // construct the point (x, y)
	   this.x = x;
	   this.y = y;
	   SLOPE_ORDER = new SlopeOrder(); //moved initialization to the constructor
   }

   public   void draw(){
	   // draw this point
       StdDraw.point(x, y);
   }
   public   void drawTo(Point that) {
	   // draw the line segment from this point to that point
	   StdDraw.line(this.x, this.y, that.x, that.y);
   }
   public String toString(){
	   // string representation
	   return "(" + x + ", " + y + ")";
   }

   public int compareTo(Point that){
	   // is this point lexicographically smaller than that point?
	   if (that.y < y)	return 1;
	   if (that.y > y)	return -1;
	   if (that.x < x)	return 1;
	   if (that.x > x)	return -1;
	   return 0;
   }
   
   public boolean equals(Point that){
	   // is this point lexicographically smaller than that point?
	   if (that.y == y && that.x == x)	return true;
	   return false;
   }
   
   public double slopeTo(Point that){
	   // the slope between this point and that point
	   if(that.x == x && that.y == y)
		   return Double.NEGATIVE_INFINITY;
	   if(that.x == x)
		   return Double.POSITIVE_INFINITY;
	   if(that.y == y)
		   return 0;
	   double deltaY = (that.y - y);
	   double deltaX = (that.x - x);
	   return deltaY / deltaX;
   }
}