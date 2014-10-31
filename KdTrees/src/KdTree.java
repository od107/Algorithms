import java.util.ArrayList;
import java.util.TreeSet;


public class KdTree {
	private Node root;
	private int size;

	private static class Node {
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node lb;        // the left/bottom subtree
		private Node rt;        // the right/top subtree

		private Node(Point2D p) {
			this.p = p;
		}
		
		private Node(Point2D p, RectHV rect) {
			this.p = p;
			this.rect = rect;
		}
	}

	public KdTree() {
		// construct an empty set of points 
		root = null;
		size = 0;
	}
	public boolean isEmpty() {
		// is the set empty? 
		return root == null;
	}
	public int size()  {
		// number of points in the set 
		return size;//subsize(root);
	}

	private int subsize(Node n) {
		int count = 0;
		if (n != null) {
			if(n.lb != null) {
				count = count + subsize(n.lb);
			}
			if(n.rt != null) {
				count = count + subsize(n.rt);
			}
			count++;
		}
		return count;
	}

	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		assert !contains(p);
		if (contains(p))
			return;
		
		if (root == null) {
			root = new Node(p, new RectHV(0,0,1,1));
		}
		else {
			Node n = root;
			boolean lr = false; //true = left/right, false = top / bottom
			boolean islb = false;//true = left half of search tree, false = right half
			Node parent = null;

			//this could be done more elegantly using again recursion
			while (n != null) {
				lr = !lr; //every time we go a level deeper we change the lr/tb division
				if(lr) {
					if (p.x() < n.p.x()) { 
						islb = true;
						parent = n;
						n = n.lb;
					}
					else {
						islb = false;
						parent = n;
						n = n.rt;
					}
				}
				else {
					if (p.y() < n.p.y()) {
						islb = true;
						parent = n;
						n = n.lb;
					}
					else {
						islb = false;
						parent = n;
						n = n.rt;
					}
				}
			}
			
			Node newNode = new Node(p, calcRect(parent, islb, lr));			
			if(islb) {
				parent.lb = newNode;
			}
			else
				parent.rt = newNode;
		}
		size++;
		assert contains(p);
	}
	
	private RectHV calcRect(Node node, boolean islb, boolean lr) {
		RectHV rect;
		if(lr){
			if(islb)
				rect = new RectHV(node.rect.xmin(),node.rect.ymin(),node.p.x(),node.rect.ymax());
			else
				rect = new RectHV(node.p.x(),node.rect.ymin(),node.rect.xmax(),node.rect.ymax());
		}
		else { // : top/bottom
			if(islb)
				rect = new RectHV(node.rect.xmin(),node.rect.ymin(),node.rect.xmax(),node.p.y());
			else
				rect = new RectHV(node.rect.xmin(),node.p.y(),node.rect.xmax(),node.rect.ymax());
		}
		return rect;
	}

	public  boolean contains(Point2D p) {
		// does the set contain point p? 
		return search(root, p, true);
	}

	private boolean search(Node n, Point2D p, boolean lr) {
		if (n == null)
			return false;
		if (n.p.equals(p))
			return true;
		if (lr) {
			if (p.x() < n.p.x())
				return search (n.lb, p, !lr);
			else
				return search (n.rt, p, !lr); 
		}
		else {
			if (p.y() < n.p.y())
				return search (n.lb, p, !lr);
			else
				return search (n.rt, p, !lr); 
		}
	}

	public void draw() {
		// draw all points to standard draw 
		draw(root, true);
        StdDraw.setPenColor(StdDraw.BLACK);
        root.rect.draw();
	}
	
	private void draw(Node n, boolean lr) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
		n.p.draw();
		StdDraw.setPenRadius(.001);
		
		if(n.lb != null) 
			draw(n.lb, !lr);

		if(n.rt != null) {
			draw(n.rt, !lr);
		}	

		if(n.lb == null && n.rt == null) {
			if(lr){
				//draw the final lines (bottom nodes do not have child rect)
				StdDraw.setPenColor(StdDraw.RED);
				n.p.drawTo(new Point2D(n.p.x(),n.rect.ymax()));
				n.p.drawTo(new Point2D(n.p.x(),n.rect.ymin()));
			}
			else {
				StdDraw.setPenColor(StdDraw.BLUE);
				n.p.drawTo(new Point2D(n.rect.xmax(), n.p.y()));
				n.p.drawTo(new Point2D(n.rect.xmin(), n.p.y()));
			}
		}
		if(!lr)
			StdDraw.setPenColor(StdDraw.RED);
		else
			StdDraw.setPenColor(StdDraw.BLUE);
		n.rect.draw();	
	}
	
	public Iterable<Point2D> range(RectHV rect) { // more correct to use a set
		// all points that are inside the rectangle 
		if(isEmpty())
			return null;
		
		ArrayList<Point2D> list = new ArrayList<Point2D>();
		list = contains(list, rect, root, true);
		
		return list;
	}
	
	private ArrayList<Point2D> contains(ArrayList<Point2D> list, RectHV rect, Node n, boolean lr){
		if (rect.contains(n.p))
			list.add(n.p);
		
		if(lr) {
			if (n.lb != null && n.p.x() >= rect.xmin()) //rect to the left, search left searchtree
				list = contains(list, rect, n.lb, !lr);
			if (n.rt != null && n.p.x() <= rect.xmax()) // search right subtree
				list = contains(list, rect, n.rt, !lr);
		}
		else {
			if (n.lb != null && n.p.y() >= rect.ymin())
				list = contains(list, rect, n.lb, !lr);
			if (n.rt != null && n.p.y() <= rect.ymax())
				list = contains(list, rect, n.rt, !lr);
		}
		return list;
	}
	
	
	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty 
		if(isEmpty())
			return null;
		
		return nearest(root, p, root.p);
	}
	
	private Point2D nearest(Node node, Point2D p , Point2D nearest) {
		if(node.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))	
			//If the distance from the node point is smaller than nearest found
			nearest = node.p;
		
		if(node.lb != null && node.lb.rect.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))	
			//If the distance from the nodes left child rect is smaller
    		nearest = nearest(node.lb, p, nearest);
    	
    	if(node.rt != null && node.rt.rect.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))	
    		// if the distance from the nodes right child rect is smaller
    		nearest = nearest(node.rt, p , nearest);
    	
    	return nearest;
	}
	
	private static Point2D randomPoint() {
		double x = StdRandom.uniform();
		double y = StdRandom.uniform();
		return new Point2D(x, y);
	}
	
	private static RectHV randomRect() {
		double x1 = StdRandom.uniform();
		double x2 = StdRandom.uniform(); 
		if(x1 > x2){
			double swap = x1;
			x1 = x2;
			x2 = swap;
		}
		double y1 = StdRandom.uniform();
		double y2 = StdRandom.uniform();
		if(y1 > y2){
			double swap = y1;
			y1 = y2;
			y2 = swap;
		}		
		return new RectHV(x1, y1, x2, y2);
	}

	public static void main(String[] args) {
		// unit testing of the methods (optional) 
		boolean fileinput = false;
        String filename = "testing\\input5.txt";
		int nbr = 100000;
       	
		KdTree kd = new KdTree();
		PointSET set = new PointSET();
		TreeSet<Point2D> reference = new TreeSet<Point2D>();
		
		if(!set.isEmpty() || !kd.isEmpty())
			System.out.println("Problem in the empty() method");
        
        if(fileinput) {
            In in = new In(filename);
        	while (!in.isEmpty()) {
        		double x = in.readDouble();
        		double y = in.readDouble();
        		Point2D p = new Point2D(x, y);
        		kd.insert(p);
        		set.insert(p);
        		reference.add(p);
        	}
        }
        else { // add a number of random points
        	for (int i=0;i<nbr;i++) {
        		Point2D point = randomPoint();
        		kd.insert(point);
        		set.insert(point);
        		reference.add(point);
        	}
        }
		
		if(kd.size() == set.size())
			System.out.println("sizes match");
		else {
			System.out.println("size kd: " + kd.size());
			System.out.println("size set: " + set.size());
		}
		
		for (Point2D p : reference) { 
			if (!set.contains(p))
				System.out.println("point: " + p + " is not contained in the set");
			if(!kd.contains(p))
				System.out.println("point: " + p + " is not contained in the kdtree");
		}
		
//		kd.draw();
		
//		kd.nearest(new Point2D(.81,.30));
		
		Stopwatch nearesttime = new Stopwatch();
		
		for (int i=0 ; i<100 ; i++) {
			Point2D p = randomPoint();
			if(!set.nearest(p).equals(kd.nearest(p)))
				System.out.println("problem in the nearest() method");
//			p.draw();
		}
		System.out.println("Time to verify 100 random nearest points = " + nearesttime.elapsedTime());
		
		Stopwatch rangetime = new Stopwatch();
		
		for (int i=0 ; i<100 ; i++) {
			RectHV rect = randomRect();
			Iterable<Point2D> setContained = set.range(rect);
			Iterable<Point2D> kdContained = kd.range(rect);
//			rect.draw();
			for (Point2D point : setContained)
				if(!kd.contains(point))
					System.out.println("point: " + point + " exist in set but not in kd");
			for (Point2D point : kdContained)
				if(!set.contains(point))
					System.out.println("point: " + point + " exist in kd but not in set");			
		}
		
		System.out.println("Time to verify contents of 100 random rectangles = " + rangetime.elapsedTime());
	}
}
