public class Solver {
	
	private boolean solveable = false;
	private Node solution = null;
	
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
			moves = previous.moves + 1;
			this.board = board;
			prevNode = previous;
			priority = board.manhattan() + moves;
			assert priority >= previous.priority;
		}
		
		public int compareTo(Node that){
//			return (this.priority - that.priority);
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
    	MinPQ<Node> altque = new MinPQ<Node>();
    	
    	Node actual = new Node(initial);
    	Node twin = new Node(initial.twin());
    	que.insert(actual);
    	altque.insert(twin);
     	
    	while (true) { 
        	actual = que.delMin(); 
        	twin = altque.delMin();
    		
    		Iterable<Board> neighbours = actual.board.neighbors();
    		Iterable<Board> altneighbours = twin.board.neighbors();

    		for(Board neighbour : neighbours) { 
    			if(actual.prevNode == null || !neighbour.equals(actual.prevNode.board)) {
    				Node node = new Node(neighbour, actual);
    				que.insert(node);
    			}
    		}
    		for(Board neighbour : altneighbours) { 
    			if(twin.prevNode == null || !neighbour.equals(twin.prevNode.board)) {
    				Node node = new Node(neighbour, twin);
    				altque.insert(node);
    			}
    		}
    		
    		if(actual.board.isGoal()){
    			solveable = true;
    			solution = actual;
    			break;
    		}
    		if(twin.board.isGoal()){ 
    			solveable = false; //redundant
    			solution = null;
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
    	else
    		return -1;
    }
    public Iterable<Board> solution() {
    	// sequence of boards in a shortest solution; null if unsolvable
    	
    	if (!solveable)
    		return null;
    	
    	Stack<Board> sequence = new Stack<Board>();
    	
    	Node actual = solution;
       	sequence.push(actual.board);
    	
       	while(actual.prevNode != null)  {
        	actual = actual.prevNode;
        	sequence.push(actual.board);
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