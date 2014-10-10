import java.util.ArrayList;
import java.util.Arrays;

public class Fast {

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
        Point[] copy = points.clone();
        ArrayList<Point[]> solution = new ArrayList<Point[]>();
    	
        for (int p = 0; p < N; p++) { 
        	Point origin = points[p];
        	
        	copy = points.clone(); //slightly faster dan double sorting (N vs NlogN)
 //       	Arrays.sort(copy); 
        	Arrays.sort(copy,origin.SLOPE_ORDER); 
        	
        	int count=2; //every 2 points make a line
        	double blockedSlope = Double.NEGATIVE_INFINITY;

        	for(int i=0;i<N-1;i++){ 
        		double slope = origin.slopeTo(copy[i]); 
        		double nextSlope = origin.slopeTo(copy[i+1]);
        		
        		if (slope == blockedSlope)
        			continue;
        		
        		if (origin.compareTo(copy[i]) >= 0){ // only interested in lines going up (or horizontal)
        			blockedSlope = slope;
        			continue; // skip all with same slope, not just this one point
        		}
        		
        		if(slope == nextSlope)
        			count++;
        		
        		else {
        			if (count >= 4){ 
        				//store solution
        				Point[] sol = new Point[count];
        				sol[0] = origin;
        				for(int r=1;r<count;r++){ 
        					sol[count-r] = copy[i+1-r];
        				}
        				solution.add(sol);       					
        			}
        			count=2;
        		}
        	}
        	  // here i = N -1
        	if (count >= 4){
    			Point[] sol = new Point[count];
    			sol[0] = origin; 
    			for(int r=1;r<count;r++){ 
    				sol[count-r] = copy[N-r];
    			}
    			solution.add(sol);   
        	}
        }
        output(solution);
        StdDraw.show(0);
	}

	private static void output(ArrayList<Point[]> solution){ 
		for(Point[] sol : solution){
			int length = sol.length;
			for(int i=0;i<length-1;i++){
				System.out.print(sol[i] + " -> ");

			}
			System.out.println(sol[length-1]);
			sol[0].drawTo(sol[length-1]);
		}
	}
}
