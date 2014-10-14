import java.util.ArrayList;

public class Board {
	
	private final int N;
	private final int[][] tiles; //change to short to save space
	
    public Board(int[][] blocks) {
    	// construct a board from an N-by-N array of blocks
    	// (where blocks[i][j] = block in row i, column j)
    	N = blocks.length;
    	this.tiles = new int[N][N];
    	for(int i=0; i < N; i++)
    		for(int j=0;j < N; j++)
    			tiles[i][j] = blocks[i][j];
    }
    
//    private Board(short[][] blocks){
//    	//have to find a way to cast int arrays to short to be able to use this
//    	N = blocks.length;
//    	this.tiles = new short[N][N];
//    	for(int i=0; i < N; i++)
//    		for(int j=0;j < N; j++)
//    			tiles[i][j] = (short) blocks[i][j];
//    }
                                           
    public int dimension() {
    	// board dimension N
    	return N;
    }
    public int hamming() {
    	// number of blocks out of place
    	int count = 0;
    	for (int i=0; i<N ; i++)
    		for (int j=0; j<N; j++) {
     			if (tiles[i][j] != 0 && tiles[i][j] != j * N + i + 1)
    				count++;
    		}
    	return count;
    }
    public int manhattan() {
    	// sum of Manhattan distances between blocks and goal
    	int count = 0;
    	for (int i=0; i<N ; i++)
    		for (int j=0; j<N; j++) 
    			if (tiles[i][j] != 0 && tiles[i][j] != j * N + i + 1) {
    				//find index of requested value
    				//then add the difference of the indexes of the requested value
    				//to count
    				int value = tiles[i][j];
    		       	int[] ind = new int[2];
    				ind = findIndices(value);
    				count += Math.abs(ind[0] - i) + Math.abs(ind[1] - j);
    			}
    	return count;
    }
    
    private int[] findIndices(int value) {
       	int[] ind = new int[2];
       	
       	for (int i=0; i<N ; i++)
    		for (int j=0; j<N; j++) 
    			if (tiles[i][j] == value){
    				ind[0] = i;
    				ind[1] = j;
    				return ind;
    			}
       	return ind;
    }
    public boolean isGoal() {
    	// is this board the goal board?
    	for (int i=0; i<N ; i++)
    		for (int j=0; j<N; j++){
    			if( i == N-1 && j == N-1)
    				return (tiles[i][j] == 0);
    			if (tiles[i][j] != i * N + j + 1)
    				return false;
    		}
    	return true;
    }
    public Board twin() {
    	// a board that is obtained by exchanging two adjacent blocks in the same row
    	Board twin = new Board((int[][])tiles);
    	int swap = twin.tiles[0][0];
    	twin.tiles[0][0] = twin.tiles[0][1];
    	twin.tiles[0][1] = swap;
    	return twin;
    }
    public boolean equals(Object y) {
    	// does this board equal y?
    	// here we also need some other statements
    	
    	if (y == this)
    		return true;
    	
    	if (y == null)
    		return false;
    	
    	if (y.getClass() != this.getClass())
    		return false;
    	
    	Board that = (Board) y;
    	
    	if(this.N !=  that.N)
    		return false;

    	for (int i=0; i<N ; i++)
    		for (int j=0; j<N; j++){
    			if (tiles[i][j] != that.tiles[i][j])
    				return false;
    		}
    	return true;
    }
    public Iterable<Board> neighbors() { //could also return list but is against API
    	// all neighboring boards
       	int[] ind = new int[2];
       	ind = findIndices(0);
       	int a = ind[0];
       	int b = ind[1];
       	
//       	put the 2-4 neigbours in iterable list
//       	arraylist will do
       	
       	ArrayList<Board> list = new ArrayList<Board>();
       	
       	Board copy;
       	
       	if (a > 0) {
       		copy = new Board(tiles);
       		list.add(move(copy, a, b, a-1, b)); 
       	}
       	if (a < N-1) {
       		copy = new Board(tiles);
       		list.add(move(copy, a, b, a+1, b));
       	}
       	if (b > 0) {
       		copy = new Board(tiles);
       		list.add(move(copy, a, b, a, b-1));
       	}
       	if (b < N-1) {
       		copy = new Board(tiles);
       		list.add(move(copy, a, b, a, b+1));
       	}
       	return list;
    }
    
    private Board move(Board bord, int xnul, int ynul, int xn, int yn){
    	bord.tiles[xnul][ynul] = bord.tiles[xn][yn];
    	bord.tiles[xn][yn] = 0;
    	return bord;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
    	// unit tests (not graded)
    	
    	
    }
}