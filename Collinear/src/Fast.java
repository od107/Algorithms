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

//        double[] slopes = new double[N];
        Point[] copy = points;
        
        for (int p = 0; p < N; p++) { //up to N here?
//        	Point origin = points[p];
            Arrays.sort(copy,points[p].SLOPE_ORDER);
            int count=0;
        	for(int i=1;i<N;i++){
        		double slope = copy[i].slopeTo(points[p]);
        		double prevSlope = copy[i-1].slopeTo(points[p]);
        		if(slope == prevSlope)
        			count++;
        		else
        			count=1;
        		if(count == 4){
        			System.out.println(points[p] + " -> " +
							points[i] + " -> " +
							points[i-1] + " -> " +
							points[i-2]);
        			points[p].drawTo(points[i]);
        		}
        	}
        }
        StdDraw.show(0);
	}

}
