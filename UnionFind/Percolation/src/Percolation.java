//Resolving backwash has not been covered


public class Percolation {
	private boolean[][] grid;
	private int N;
	private int top;
	private int bottom;
	private WeightedQuickUnionUF wuf;
		
	   public Percolation(int N)  {
		   // create N-by-N grid, with all sites blocked
		   if (N<=0)
			   throw new IllegalArgumentException("N <= 0");
		   this.N=N;
		   grid = new boolean[N][N];
		   top = 0;
		   bottom = N * N + 1;
		   wuf = new WeightedQuickUnionUF(N * N + 2);
	   }
	   
	   public void open(int i, int j) {
		   if (i <= 0 || i > N || j <= 0 || j > N) 
			   throw new IndexOutOfBoundsException("index i or j out of bounds");
		   // open site (row i, column j) if it is not already
		   grid[i-1][j-1]= true;
		   
		   if(i==1)
			   wuf.union(getNbr(i,j), top);
		   if(i==N)
			   wuf.union(getNbr(i,j), bottom);
		   
		   //connect to neighbours if they're open
		   if(i>1 && isOpen(i-1,j))
			   wuf.union(getNbr(i,j), getNbr(i-1,j));
		   if(i<N && isOpen(i+1,j))
			   wuf.union(getNbr(i,j), getNbr(i+1,j));
		   if(j>1 && isOpen(i,j-1))
			   wuf.union(getNbr(i,j), getNbr(i,j-1));
		   if(j<N && isOpen(i, j+1))
			   wuf.union(getNbr(i,j), getNbr(i,j+1));
		 		   
	   }
	   public boolean isOpen(int i, int j){
		   if (i <= 0 || i > N || j <= 0 || j > N) 
			   throw new IndexOutOfBoundsException("index i or j out of bounds");
		   // is site (row i, column j) open?
		   return grid[i-1][j-1];
	   }
	   public boolean isFull(int i, int j)  {
		   if (i <= 0 || i > N || j <= 0 || j > N) 
			   throw new IndexOutOfBoundsException("index i or j out of bounds");
		   // is site (row i, column j) full?
		   return wuf.connected(top,getNbr(i,j));
	   }
	   public boolean percolates()    {
		   // does the system percolate?
		   return wuf.connected(top, bottom);
	   }
	   public static void main(String[] args) {
		   // test client, optional
	   }

	   private int getNbr(int i, int j){
		   return (N * (i-1) + j);
	   }
	}