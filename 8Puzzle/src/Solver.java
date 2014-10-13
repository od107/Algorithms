import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;



public class Solver {
	
//	ArrayList<Node> solution = new ArrayList<Node>();
	private boolean solveable = false;
//	ArrayList<Board> bsol = new ArrayList<Board>();
	Node solution = null;

	
	private class Node implements Comparable<Node>{
		public int moves;
		public Board board;
		public Node prevNode;
		public int priority;
		
		public Node(Board board) { 
			// constructor for initial board
			moves = 0;
			this.board = board;
			prevNode = null;
			priority = board.manhattan() + moves;
		}
		
		public Node(Board board, Node previous){
			moves = previous.moves++;
			this.board = board;
			prevNode = previous;
			priority = board.manhattan() + moves;
		}
		
		public int compareTo(Node that){
			if(this.priority < that.priority)
				return -1;
			if(this.priority > that.priority)
				return 1;
			else
				return 0;
		}
	}
	
    public Solver(Board initial) {
    	// find a solution to the initial board (using the A* algorithm)
    	MinPQ<Node> que = new MinPQ<Node>();
    	
    	Node actual = new Node(initial);
    	que.insert(actual);
     	
    	while (!que.isEmpty()) {
        	actual = que.delMin(); 
    		
    		Iterable<Board> neighbours = new ArrayList<Board>();
    		neighbours = actual.board.neighbors();

    		for(Board neighbour : neighbours) {
    			if(neighbour.equals(actual.prevNode))
    				continue;
    			Node node = new Node(neighbour, actual);
    			que.insert(node);
    		}
  
//    		bsol.add(actual.board);
    		
    		
    		if(actual.board.isGoal()){
    			solveable = true;
    			solution = actual;
    			break;
    		}
    	} 
    }

    public boolean isSolvable() {
    	// is the initial board solvable?
    	return solveable;
    }
    public int moves()  {
    	// min number of moves to solve initial board; -1 if unsolvable
    	if (solveable){
    		int count = 0;
    		Node actual = solution;
        	while(actual.prevNode != null) {
            	count++;
            	actual = actual.prevNode;
        	}
        	return count;
    	}
//    		return solution.size();
    	else
    		return -1;
    }
    public Iterable<Board> solution() {
    	// sequence of boards in a shortest solution; null if unsolvable
    	LinkedList<Board> sequence = new LinkedList<Board>();
    	
    	Node actual = solution;
    	
    	while(actual.prevNode != null) {
        	sequence.add(actual.board);
        	actual = actual.prevNode;
    	}

    	return sequence;
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