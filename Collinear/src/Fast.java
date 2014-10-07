import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
//        StdDraw.show(0);
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        
        Arrays.sort(points); //necessary?
//        Point[] points = points.clone();
        ArrayList<Point[]> solution = new ArrayList<Point[]>();
    	
        for (int p = 0; p < N-3; p++) { 
        	Point origin = points[p];
        	
        	Arrays.sort(points,p+1,N,origin.SLOPE_ORDER);
        	int count=2; //every 2 points make a line
        	double slope = origin.slopeTo(points[p]);
        	
        	for(int i=p+1;i<N;i++){ //no need to cover first point: always equal to p
        		double prevSlope = slope;
        		slope = origin.slopeTo(points[i]);
        		if (slope == Double.NEGATIVE_INFINITY)
        			continue;

        		if(slope == prevSlope )
        			count++;

        		if(slope != prevSlope || i == N-1){ 

        			if(count >= 4){ 

        				//store solution
        				Point[] sol = new Point[count];
        				sol[0] = origin;
        				
        				if(i == N-1 && slope == prevSlope) //correction if stopped at last element which was part of the line
        					i++;
        				for(int r=1;r<count;r++){
        					sol[r] = points[i-r];
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
	
	private static boolean contains(Point[] small, Point[] big){
		//4 points of small need to occur in big
		int sLength = small.length;
		int bLength = big.length;
		int count = 0;
		for (int i=0;i<bLength;i++){
			int j;
			for(j=0;j<sLength;j++){
				if(small[j]==big[i])
					if(++count >= 4)
						return true;
			}
		}
		return false;
	}

	
	private static boolean containsArray(ArrayList<Point[]> solution, Point[] newEntry){
		int Nlength = newEntry.length;
		for(Point[] sol : solution){
			int Olength = sol.length;
			if(Olength == Nlength){
				if(sol[0] == newEntry[0] && sol[Olength-1] == newEntry[Nlength-1])
					return true;
			}
			else if (Nlength < Olength){
				if (contains(newEntry, sol))
						//Collections.indexOfSubList(Arrays.asList(sol), Arrays.asList(newEntry)) != -1)
					return true;
			}
			//the next scenario should never be accessed
			else if (Olength < Nlength){
				if (contains(sol, newEntry))
						//Collections.indexOfSubList(Arrays.asList(newEntry), Arrays.asList(sol)) != -1)
					// in this case remove original line from solution before adding new one
					solution.remove(sol);
					//continue searching rest of the solution
			}
		}
		return false;
	}

}
