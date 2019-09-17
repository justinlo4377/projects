


//*******************************************************************
// Node
//
// A class customized for any type of node. In this case, it uses person as its <T>
//*******************************************************************
public class Node<T>{
	
	private T				data;
	private Node<T>			prev;	
	private Node<T>			next;	
	
	
	Node(T data){	
		this.data = data;
	}
	
	
	T getData(){
		return data;
	}
	
	
	Node<T> getNext(){
		return next;
	}
	
	
	void setNext(Node<T> next){
		this.next = next;
	}
	
	
	Node<T> getPrev(){
		return prev;
	}
	
	
	void setPrev(Node<T> prev){
		this.prev = prev;
	}
	
	
	// Returns data of prev node, this node, and next node. Uses "<" if prev is
	// null, and ">" if next is null.
	
	
	public String toString(){
		String s;
		if (prev == null)
			s = "<";
		else s = prev.data.toString();
		
		s += data;
		
		if (next == null)
			s += ">";
		else
			s += next.data;
		
		return s;
	}
}
