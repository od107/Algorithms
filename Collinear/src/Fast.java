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
        
//        Arrays.sort(points); //not sure if this step adds anything
        Point[] copy = points;
        ArrayList<Point[]> solution = new ArrayList<Point[]>();
        
        for (int p = 0; p < N-3; p++) { 
        	Point origin = points[p];
        	Arrays.sort(copy,origin.SLOPE_ORDER);
        	int count=2; //every 2 points make a line
        	double slope = origin.slopeTo(copy[0]);
        	
        	for(int i=1;i<N;i++){ //no need to cover first point: always equal to p
        		double prevSlope = slope;
        		slope = origin.slopeTo(copy[i]);
        		if (slope == Double.NEGATIVE_INFINITY)
        			continue;

        		if(slope == prevSlope )
        			count++;

        		if(slope != prevSlope || i == N-1){ 

        			if(count >= 4){ 

        				//store solution
        				Point[] sol = new Point[count];
        				sol[0] = origin;
        				
        				if(i == N-1) //correction if stopped at last element
        					i++;
        				for(int r=1;r<count;r++){
        					sol[r] = copy[i-r];
        				}
        				Arrays.sort(sol);
        				if(!containsArray(solution, sol)){
            				solution.add(sol);       					
        				}
        			}
        			count=2;
        		}
        	}
        }
        output(solution);
        StdDraw.show(0);
	}
	
	private static void output(ArrayList<Point[]> solution){ //nog wat foutjes
		for(Point[] sol : solution){
			for(int i=0;i<sol.length-1;i++){
				System.out.print(sol[i] + " -> ");
				sol[i].drawTo(sol[i+1]);
			}
			System.out.println(sol[sol.length-1]);
		}
	}
	
	private static boolean containsArray(ArrayList<Point[]> solution, Point[] newEntry){
		for(Point[] sol : solution){
			if(Arrays.equals(sol, newEntry))
				return true;
		}
		return false;
	}

}
