import java.lang.reflect.Array;

public class LinkedList 
{
	private Node<Person >			head;	// head.prev is always null
	private Node<Person >			tail;	// tail.next is always null
	public int size=0;
	
	//*******************************************************************
	// Linked List Class
	//
	// added size global vairable to check the size of this list
	//*******************************************************************
	public LinkedList(){
		
	}
	
	
	public int getSize() {
		return size;
	}
	public boolean isEmpty() {
		
		if (size<=0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Converts arg to nodes which are appended to end of this list.
	// Creates a node for ch and appends it to the linked list.
	// "Append" always means "at the end".
	public void append(Person  f){
		
			Node <Person> N = new Node <Person >(f);
			append( N);
			
		
		
	}
	public void insertHead(Person  f){
		Node <Person> N = new Node <Person >(f);
		insertHead(N);
	
	}
	
	//inserts a node to the head of this list
	public void insertHead(Node<Person >n) {
		if (head == null){
			
			n.setPrev(null);
			n.setNext(null);
			head = n;
			tail = n;
			size++;
		}
		else{
			
			head.setPrev(n);
			n.setNext(head);
			head = n;
			size++;
			
		}
	}
	
	
	// Appends n to tail of this list.
	public void append(Node<Person > n){
		// Corner case: empty list.
		if (tail == null){
			
			n.setPrev(null);
			n.setNext(null);
			head = n;
			tail = n;
			size++;
		}
		
		// Vanilla case.
		else{
			
			tail.setNext(n);
			n.setPrev(tail);
			tail = n;
			size++;
			
		}
	}
	
	
	public String toString(){
		
		String s = "";
		if (head == null)
			s += "You have no friends";
		else{
			
			Node<Person > n = head;
			
			while (n != null){
				
				s += n.getData()+",";
				n = n.getNext();
			}
		}
		return s;
	}
	
	
	// Returns true if the node starting at startNode matches the target string.
	private boolean matches(Node<Person > startNode, String target){
		if (startNode.getData().toString().equals(target)) {
		
			return true;
		}
	else {
	
		return false;
	}

		
	}
	
	
	// If this list contains a chain of nodes whose data is the target, returns
	// the nodes at the start of that chain. If the target appears multiple times
	// in this list, returns the first occurrence. If the target is not in this list,
	// returns null.
	
	public Node<Person > find(String target){
		
		if(head==null||tail==null) {
			
			return null;
		}
		Node<Person > n = head;
		
		boolean done=false;
		while(!done) {

			if(n==null) {
				done=true;
				break;
			}
			
			if(matches(n,target)) {
				return n;
				
			}
			if(!matches(n,target)) {
				n=n.getNext();

			}
		
		}
		return n;
		

	}
	
	
	
	//removes the first instance of Person 
	public boolean remove(Person  f) {

		Node<Person> n = head;
	
		
		for(int i =0;i<size;i++) {
			
			if(matches(n,f.toString())){
				
				removeAt(i);
				return true;
				
			}
			n=n.getNext();
			
		}
		
		return false;
		
	}
	
	//removes a node from inputed index.
	public void removeAt(int index) {
		if(size==0) {
			System.out.println("there is nothing to remove");
		}
		else if(index>=size) {
			System.out.println("this is too high of a value");
		}
		else if(size==1) {
			head = null;
			tail = null;
			size--;
		}
		else if(index==0) {
			 head = head.getNext();
			 head.setPrev(null);
			 size--;
		}
		else if(index==size-1) {
			tail= tail.getPrev();
			tail.setNext(null);
			 size--;
		}
		else {

			Node<Person > n = head;
			for(int i =1;i<=index;i++) {
				
				 n = n.getNext();
				
			}
			n.getNext().setPrev(n.getPrev());
			n.getPrev().setNext(n.getNext());
			
			size--;
		}
	}

	public String getTail() {
		return tail.toString();
	}
	public String getHead() {
		return head.toString();
	}
	
}
