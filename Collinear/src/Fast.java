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
        	
        	Arrays.sort(copy); //why is this needed? shouldn't it be stable without?
        	// can test if making a new copy is faster; should be N instead of N log N
        	Arrays.sort(copy,origin.SLOPE_ORDER);
        	
        	int count=2; //every 2 points make a line
        	double blockedSlope = Double.NEGATIVE_INFINITY;


        	for(int i=0;i<N-1;i++){ //no need to cover first point: always equal to p
        		double slope = origin.slopeTo(copy[i]); 
        		double nextSlope = origin.slopeTo(copy[i+1]);
        		
        		if (slope == blockedSlope)
        			continue;
        		
        		if (origin.compareTo(copy[i]) >= 0){ // enkel geinteresseerd in lijnen die naar boven gaan
        			blockedSlope = slope;
        			continue; // skip all with same slope, not just this one point
        		}

        		if(slope == nextSlope)
        			count++;

        		//        		if(slope != nextSlope || i == N-1){ 
        		else {
        			if (count >= 4){ 
        				//store solution
        				Point[] sol = new Point[count];
        				sol[0] = origin;
        				//        				if(i == N-1 && slope == prevSlope) //correction if stopped at last element which was part of the line
        				//        					i++;
        				for(int r=1;r<count;r++){ //not in right order
        					sol[count-r] = copy[i+1-r];
        				}
  //      				Arrays.sort(sol); // still necessary because order gets messed up
        				solution.add(sol);       					
        			}
        			count=2;
        		}
        	}
        	  // here i = N -1, al die stappen op dit moment uitvoeren
        	if (count >= 4){
    			Point[] sol = new Point[count];
    			sol[0] = origin; 
    			for(int r=1;r<count;r++){ //not in right order
    				sol[count-r] = copy[N-r];
    			}
//    			Arrays.sort(sol); 
    			solution.add(sol);   
        	}
        }
        output(solution);
        StdDraw.show(0);
	}

	private static void store(ArrayList<Point[]> solution, Point origin, Point[] copy){
		
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
	
	private static boolean containsArray(ArrayList<Point[]> solution, Point[] newEntry){
		int length = newEntry.length;
		for(Point[] sol : solution){
			if(sol.length == length)
				if(sol[0] == newEntry[0] && sol[length-1] == newEntry[length-1])
					return true;
		}
		return false;
	}

}
