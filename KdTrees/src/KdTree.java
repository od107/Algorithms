
public class KdTree {
	private Node root;

	private static class Node {
		private Point2D p;      // the point
		private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		private Node lb;        // the left/bottom subtree
		private Node rt;        // the right/top subtree

		private Node(Point2D p) {
			this.p = p;
			lb = null;
			rt = null;
		}
	}

	public KdTree() {
		// construct an empty set of points 
		//this will not work for multiple instances
		root = null;
	}
	public boolean isEmpty() {
		// is the set empty? 
		return root == null;
	}
	public int size()  {
		// number of points in the set 
		int count = 0;
		return subsize(root, count);
	}

	private int subsize(Node n, int count) {
		while (n != null) {
			if(n.lb != null) {
				count = count + subsize(n.lb, count);
			}
			if(n.rt != null) {
				count = count + subsize(n.rt, count);
			}
		}
		return count;
	}

	public void insert(Point2D p) {
		// add the point to the set (if it is not already in the set)
		assert !contains(p);

		Node n = root;
		boolean lr = false;
		boolean islb = false;
		Node parent = null;

		while (n != null) {
			lr = !lr;
			if(lr) {
				if (p.x() < n.p.x()) { 
					islb = true;
					parent = n;
					n = n.lb;
				}
				else {
					islb = false
							;parent = n;
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

		Node newNode = new Node(p);
		if(islb)
			parent.lb = newNode;
		else
			parent.rt = newNode;

		assert contains(p);
	}

	public  boolean contains(Point2D p) {
		// does the set contain point p? 
		Node n = root;
		boolean lr = true;
		return search(n, p, lr);
	}

	private boolean search(Node n, Point2D p, boolean lr) {
		if (n == null)
			return false;
		if (n.p.equals(p))
			return true;
		if (lr) {
			if (p.x() < n.p.x())
				search (n.lb, p, !lr);
			else
				search (n.rt, p, !lr); 
		}
		else {
			if (p.y() < n.p.y())
				search (n.lb, p, !lr);
			else
				search (n.rt, p, !lr); 
		}
		return false;
	}

	public void draw() {
		// draw all points to standard draw 

	}
	public Iterable<Point2D> range(RectHV rect) {
		// all points that are inside the rectangle 

	}
	public Point2D nearest(Point2D p) {
		// a nearest neighbor in the set to point p; null if the set is empty 

	}

	public static void main(String[] args) {
		// unit testing of the methods (optional) 

	}
}
