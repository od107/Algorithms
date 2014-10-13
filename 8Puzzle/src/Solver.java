import java.util.ArrayList;
import java.util.Iterator;



public class Solver {
	
	private class Node {
		public int moves;
		public Board board;
		public Node prevNode;
		
		public Node(){
			moves = 0;
			//initialise board & prevNode?
		}
	}
	
    public Solver(Board initial) {
    	// find a solution to the initial board (using the A* algorithm)
    	MinPQ<Node> que = new MinPQ<Node>(initial.manhattan());
    	
		Node actual = que.delMin();   	
		while (!actual.board.isGoal()) {
			ArrayList<Board> neighbours = new ArrayList<Board>();
			neighbours = (ArrayList<Board>) actual.board.neighbors();
			Iterator<Board> iter = neighbours.iterator();
			while(iter.hasnext()){
				que.insert(iter.next());
			}
//			que.insert(actual.neighbors());
    		
    	
    		 }
    }
    public boolean isSolvable() {
    	// is the initial board solvable?
    	
    }
    public int moves()  {
    	// min number of moves to solve initial board; -1 if unsolvable
    	
    }
    public Iterable<Board> solution() {
    	// sequence of boards in a shortest solution; null if unsolvable
    	
    }
    
    public static void main(String[] args) {

    	// create initial board from file
    	In in = new In(args[0]);
    	int N = in.readInt();
    	int[][] blocks = new int[N][N];
    	for (int i = 0; i < N; i++)
    		for (int j = 0; j < N; j++)
    			blocks[i][j] = in.readInt();
    	Board initial = new Board(blocks);

    	// solve the puzzle
    	Solver solver = new Solver(initial);

    	// print solution to standard output
    	if (!solver.isSolvable())
    		StdOut.println("No solution possible");
    	else {
    		StdOut.println("Minimum number of moves = " + solver.moves());
    		for (Board board : solver.solution())
    			StdOut.println(board);
    	}
    }
}