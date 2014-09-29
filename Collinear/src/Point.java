import java.util.Comparator;

public class Point implements Comparable<Point> {
	private final int x,y;
	
   public static final Comparator<Point> SLOPE_ORDER = new SlopeOrder();
   // compare points by slope to this point
   
   private static class SlopeOrder implements Comparator<Point>{
	   public double compare(Point a,Point b){
		   return this.slopeTo(a) - this.slopeTo(b);
	   }
   }

   public Point(int x, int y){
	   // construct the point (x, y)
	   this.x = x;
	   this.y = y;
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

   public    int compareTo(Point that){
	   // is this point lexicographically smaller than that point?
	   if (that.y < y)	return 1;
	   if (that.y > y)	return -1;
	   if (that.x < x)	return 1;
	   if (that.x > x)	return -1;
	   return 0;
   }
   public double slopeTo(Point that){
	   // the slope between this point and that point
	   if(that.x == x)
		   return Double.MAX_VALUE;
	   if(that.y == y)
		   return 0;
	   return (that.y - y) / (that.x - x);
   }
}