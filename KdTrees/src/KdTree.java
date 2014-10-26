import java.util.ArrayList;
import java.util.TreeSet;


public class KdTree {
	private Node root;

	private static class Node {
		// should we store also the lr <> tb in the node? 
		
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node lb;        // the left/bottom subtree
		private Node rt;        // the right/top subtree
//		private boolean lr; // true = left/right, false  = top/down

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
		//this will not work for multiple instances
		root = null; //new Node();
	}
	public boolean isEmpty() {
		// is the set empty? 
		return root == null;
	}
	public int size()  {
		// number of points in the set 
		return subsize(root);
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
		Node n = root;
		draw(n);
	}
	
	private void draw(Node n) {
		n.p.draw();
		n.rect.draw();
		if(n.lb != null) {
			draw(n.lb);
		}
		if(n.rt != null) {
			draw(n.rt);
		}				
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
			if (n.lb != null && n.p.x() > rect.xmin()) //rect to the left, search left searchtree
				list = contains(list, rect, n.lb, !lr);
			if (n.rt != null && n.p.x() < rect.xmax()) // search right subtree
				list = contains(list, rect, n.rt, !lr);
		}
		else {
			if (n.rt != null && n.p.y() > rect.ymin())
				list = contains(list, rect, n.rt, !lr);
			if (n.lb != null && n.p.y() < rect.ymax())
				list = contains(list, rect, n.lb, !lr);
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
		//TODO in order for this to work the rectangle needs to work
		
		if(node.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))	
			//If the distance from the node point is smaller than nearest found
			nearest = node.p;
		
		if(node.lb != null && node.lb.rect.distanceSquaredTo(p) < node.p.distanceSquaredTo(p)){	
			//If the distance from the nodes left child rect is smaller
    		nearest = nearest(node.lb, p, nearest);
    	}
    	if(node.rt != null && node.rt.rect.distanceSquaredTo(p) < node.p.distanceSquaredTo(p))	
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
		double x2 = StdRandom.uniform(x1,1);
		double y1 = StdRandom.uniform();
		double y2 = StdRandom.uniform(y1,1);
		
		return new RectHV(x1, y1, x2, y2);
	}
	

	public static void main(String[] args) {
		// unit testing of the methods (optional) 
		int nbr = 1000;
		
		KdTree kd = new KdTree();
		PointSET set = new PointSET();
		TreeSet<Point2D> reference = new TreeSet<Point2D>();
		
		if(!set.isEmpty() || !kd.isEmpty())
			System.out.println("Problem in the empty() method");
		
		for (int i=0;i<nbr;i++) {
			Point2D point = randomPoint();
			kd.insert(point);
			set.insert(point);
			reference.add(point);
		}
		
		if(kd.size() == set.size())
			System.out.println("sizes match");
		else {
			System.out.println("size kd: " + kd.size());
			System.out.println("size set: " + set.size());
		}
		
		for (Point2D p : reference) { // problem in insert or in contains?
			if (!set.contains(p))
				System.out.println("point: " + p + " is not contained in the set");
			if(!kd.contains(p))
				System.out.println("point: " + p + " is not contained in the kdtree");
		}
		
		for (int i=0 ; i<100 ; i++) {
			Point2D p = randomPoint();
			if(set.nearest(p) != kd.nearest(p))
				System.out.println("problem in the nearest() method");
		}
		
		for (int i=0 ; i<100 ; i++) {
			RectHV rect = randomRect();
			Iterable<Point2D> setContained = set.range(rect);
			Iterable<Point2D> kdContained = kd.range(rect);
			for (Point2D point : setContained)
				if(!kd.contains(point))
					System.out.println("point: " + point + " exist in set but not in kd");
			for (Point2D point : kdContained)
				if(!set.contains(point))
					System.out.println("point: " + point + " exist in kd but not in set");			
		}
	}
}
