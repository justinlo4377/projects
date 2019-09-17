


//*******************************************************************
// Node
//
// Binary search node. In this case, it uses person as its <T>
//*******************************************************************
public class Node<T>{
	
	private T				data;
	private Node<T>			parent;
	private Node<T>			left;	
	private Node<T>			right;	
	

	Node(T data){	
		this.data = data;
	}

	
	void setData(T data){	
		this.data = data;
	}
	
	T getData(){
		return data;
	}
	
	
	Node<T> getLeft(){
		return left;
	}
	
	
	void setLeft(Node<T> Left){
		this.left = Left;
	}
	
	
	Node<T> getRight(){
		return right;
	}
	
	
	void setRight(Node<T> Right){
		this.right = Right;
	}
	Node<T> getParent(){
		return parent;
	}
	
	
	void setParent(Node<T> Parent){
		this.parent = Parent;
	}
	
	boolean equals(Node<T> n) {
		return this.getData().equals(n.getData());
	}
	// Returns data of prev node, this node, and next node. Uses "<" if prev is
	// null, and ">" if next is null.
	
	
	public String toString(){
		String s;
		if (left == null)
			s = "<";
		else s = left.data.toString();
		
		s += data;
		
		if (right == null)
			s += ">";
		else
			s += right.data;
		
		return s;
	}
}
