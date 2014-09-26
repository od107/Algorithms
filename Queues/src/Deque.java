import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	
	
   public Deque(){
	   // construct an empty deque
	   
   }
   public boolean isEmpty(){
	   // is the deque empty?
	   
   }
   public int size(){
	   // return the number of items on the deque
	   
   }
   public void addFirst(Item item){
	   // insert the item at the front
	   if(item == null)
		   throw new NullPointerException("Added item was null");
   }
   public void addLast(Item item){
	   // insert the item at the end
	   if(item == null)
		   throw new NullPointerException("Added item was null");
   }
   public Item removeFirst(){
	   // delete and return the item at the front
//	   if empty list
//	   throw new java.util.NoSuchElementException("List was empty");
   }
   public Item removeLast(){
	   // delete and return the item at the end
//	   if empty list
//	   throw new java.util.NoSuchElementException("List was empty");
   }
   public Iterator<Item> iterator()  {
	   // return an iterator over items in order from front to end
	   
   }       
   public static void main(String[] args){
	   // unit testing
   }
}
