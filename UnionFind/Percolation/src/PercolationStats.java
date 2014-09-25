
public class PercolationStats {
	private int T;
	private double[] fraction;
	
   public PercolationStats(int N, int T){
	   // perform T independent computational experiments on an N-by-N grid
	   if(N<=0 || T<=0)
		   throw new IllegalArgumentException("T or N was equal or smaller than 0");
	   this.T=T;
	   fraction = new double[T];

	   
	   for(int i=0; i<T;i++){
		   int opened=0;
		   Percolation perc = new Percolation(N);
	       while (!perc.percolates()) {
	    	   int x = StdRandom.uniform(N)+1;
	    	   int y = StdRandom.uniform(N)+1;
	    	   if(!perc.isOpen(y, x)){
	    		   perc.open(y,x);
	    		   opened++;
	    	   }
	       }
	       fraction[i] = ((double)opened) / (N*N);
	   }	   
   }
   public double mean() {
	   // sample mean of percolation threshold
	   return StdStats.mean(fraction);
   }
   public double stddev(){
	   // sample standard deviation of percolation threshold
	   return StdStats.stddev(fraction);
   }
   public double confidenceLo() {
	   // returns lower bound of the 95% confidence interval
	   return mean() - (1.96 * stddev() / Math.sqrt(T));
	   
   }
   public double confidenceHi(){
	   // returns upper bound of the 95% confidence interval
	    return mean() + (1.96 * stddev() / Math.sqrt(T));
   }
   public static void main(String[] args) {
	   // test client, described below
	   PercolationStats pstats = new PercolationStats(200,100);
	   System.out.println("percolation finished with");
	   System.out.println("mean: " + pstats.mean());
	   System.out.println("stddev: " + pstats.stddev());
	   System.out.println("95% confidence lower bound: " + pstats.confidenceLo());
	   System.out.println("95% confidence upper bound: " + pstats.confidenceHi());
	   
   }
}
