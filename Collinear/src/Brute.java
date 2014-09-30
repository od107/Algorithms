//Brute force solution to find all lines which have at least 4 collinear points
import java.util.Arrays;
//import java.lang.*;

public class Brute {

	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01); 
		
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }
        StdDraw.show(0);
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        
        Arrays.sort(points);
        
        for (int p = 0; p < N - 3; p++) {
        	for(int q=p+1;q < N - 2;q++){
        		double pqSlope = points[p].slopeTo(points[q]);
        		for(int r = q+1;r < N - 1;r++){
        			double prSlope = points[p].slopeTo(points[r]);
        			if(pqSlope == prSlope){
        				for(int s = r+1; s < N; s++){
        					double psSlope = points[p].slopeTo(points[s]);
        					if(prSlope == psSlope){
        						System.out.println(points[p] + " -> " +
        								points[q] + " -> " +
        								points[r] + " -> " +
        								points[s]);
        					points[p].drawTo(points[s]);
        					}
        				}
        			}
        		}
        	}
        	
        }
        
        StdDraw.show(0);

	}

}
