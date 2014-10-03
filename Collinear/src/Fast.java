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
        
        //now we don't want the natural ordering
        //but the ordering according to slope
        Point[] copy = points;
        
        for (int p = 0; p < N; p++) { 
        	Point origin = points[p];
            Arrays.sort(copy,origin.SLOPE_ORDER);
            int count=2; //every 2 points make a line
        	for(int i=2;i<N;i++){ //no need to cover first point: always equal to p
        		double slope = origin.slopeTo(copy[i]);
        		double prevSlope = origin.slopeTo(copy[i-1]);
        		if(slope == prevSlope)
        			count++;
        		else 
        			count = 2;
//        		{
        			if(count >= 4){ //here still too much segments drawn
        				// no permutations allowed, and no more than 1 segment per line, 
        				// no matter how many points
        				
        				//sorteer de gevonden punten
//        				//
//        				System.out.print(origin + " -> ");
//        				for(int k=1;k<count;k++){
//        					System.out.print(copy[i-k] + " -> ");
//        					origin.drawTo(copy[i-k]);
//        				}
        				System.out.println(origin + " -> " +
        						points[i] + " -> " +
        						points[i-1] + " -> " +
        						points[i-2]);
        				origin.drawTo(points[i]);
        				origin.drawTo(points[i-1]);
        				origin.drawTo(points[i-2]);
        			
//        			System.out.println("");
//        			}
//        			count=2;
        		}
        	}
        }
        StdDraw.show(0);
	}

}
