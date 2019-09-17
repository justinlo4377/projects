import java.util.ArrayList;

public class BinarySearchTree {

	public Node<Person> root=null;

	public BinarySearchTree() {

	}
	public void Insert(Person p) {
		TreeInsert(new Node<Person>(p));
	}
	public void Delete(Person p) {
		Node<Person> y = new Node<Person>(p);
		TreeDelete(y);
	}
	public void SetRoot(Node<Person> p) {
		root = p;

	}
	//*******************************************************************
	// InOrderTreeWalk
	//
	// Returns a List of People in InOrder traversal for the binary tree
	//*******************************************************************
	public ArrayList<Person> InOrderTreeWalk(Node<Person> x,ArrayList<Person> PersonList) {
		
		if (x != null) {
			
			InOrderTreeWalk(x.getLeft(),PersonList);
			PersonList.add(PersonList.size(), x.getData());
			InOrderTreeWalk(x.getRight(),PersonList);
		}
		return PersonList;
	}
	//*******************************************************************
	// IterativeTreeSearch
	//
	// Searches for a person. Returns resulting node if person is found, or if it hits a leaf node
	//*******************************************************************
	public Node<Person> IterativeTreeSearch(Node<Person> x, Person k) {
		while (x != null && k.getValue() != x.getData().getValue()) {
			if (k.getValue() < x.getData().getValue()) {
				x = x.getLeft();
			} else {
				x = x.getRight();
			}
		}
		
		return x;
	}
	//*******************************************************************
	// TreeMin
	//
	// returns the minimunm value for the bst
	//*******************************************************************
	public Node<Person> TreeMin(Node<Person> x) {
		while (x.getLeft() != null) {
			x = x.getLeft();
		}
		return x;
	}
	//*******************************************************************
	// getRoot
	//
	// returns the root of the tree
	//*******************************************************************
	public Node<Person> getRoot() {
		return this.root;
	}
	//*******************************************************************
	// TreeMax
	//
	// returns the Maximum value for the bst
	//*******************************************************************
	public Node<Person> TreeMax(Node<Person> x) {
		while (x.getRight() != null) {
			x = x.getRight();
		}
		return x;
	}
	//*******************************************************************
	// TreeSuccessor
	//
	// returns the minimum value that is greater then the node inserted
	//*******************************************************************
	public Node<Person> TreeSuccessor(Node<Person> x) {
		if(x.getRight()!=null) {
			return TreeMin(x.getRight());
		}
		Node<Person> y =  x.getParent();
		while(y!=null&&x==y.getRight()) {
			x=y;
			y=y.getParent();
		}
		return y;
	}
	//*******************************************************************
	// TreeInsert
	//
	// Inserts a node into the BST
	//*******************************************************************	
	public void TreeInsert(Node<Person> z) {
		Node<Person> y = null;
		Node<Person>x = this.root;
		while(x!=null) {
			y=x;

			if(z.getData().getValue()<x.getData().getValue()) {
				x=x.getLeft();
			}
			else {
				x=x.getRight();
			}
			
		}
		z.setParent(y);
		if(y==null) {
			root=z;
		}
		else if(z.getData().getValue()<y.getData().getValue()) {
			y.setLeft(z);
			
		}
		else {
			y.setRight(z);
			
		}
		
	}
	//*******************************************************************
	// Transplant
	//
	// Fixer algorithem for when deletion occurs in the BST
	//*******************************************************************
	public void Transplant(Node<Person> u,Node<Person> v){
		if (u.getParent()==null){
			root=v;
			
		}
		else if (u.getParent().getLeft()!=null){
			
			if(u.equals(u.getParent().getLeft())){
				u.getParent().setLeft(v);
			}
			else {
				u.getParent().setRight(v);
			}
				
			
		}
		else {
			u.getParent().setRight(v);
		}
		if(v!=null) {
			v.setParent(u.getParent());
		}
		
	}
	//*******************************************************************
	// Tree Delete
	//
	// Deletes a node in the BST
	//*******************************************************************
	public void TreeDelete(Node<Person> z) {
		if (z.getLeft()==null) {
			Transplant(z,z.getRight());
		}
		else if (z.getRight()==null) {
			Transplant(z,z.getLeft());
		}
		else {
			Node<Person>y = TreeMin(z.getRight());
			if(!y.getParent().equals(z)) {
				Transplant(y,y.getRight());
				y.setRight(z.getRight());
				y.getRight().setParent(y);
			}
			Transplant(z,y);
			y.setLeft(z.getLeft());
			y.getLeft().setParent(y);
		}
	}
	public Person TreeSearch(Person p) {

		Node<Person> n = IterativeTreeSearch(root, p);
		if(n==null) {
			return null;
		}
		else {
			return n.getData();
		}
		
	}
	public void TreeDelete(Person clicked) {

		Node<Person> n = IterativeTreeSearch(root, clicked);
		if(n!=null) {
			TreeDelete(n);
		}
	}
	
	
	
	
	
	
	
	
	
}
