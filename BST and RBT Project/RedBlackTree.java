import java.awt.Color;
import java.util.ArrayList;

public class RedBlackTree {
	Person p = new Person("Nil", 0);
	private Node<Person> Nil = new Node<Person>(p);
	public Node<Person> root = Nil;

	public RedBlackTree() {
		root.setLeft(Nil);
		root.setRight(Nil);
		root.setParent(Nil);
	}

	public Node<Person> getRoot() {
		return this.root;
	}

	public void Insert(Person p) {

		RBInsert(new Node<Person>(p));
	}
	//*******************************************************************
	// RBOrderTreeWalk
	//
	// Returns a List of People in InOrder traversal for the Red Black Tree
	//*******************************************************************
	public ArrayList<Person> RBOrderTreeWalk(Node<Person> x, ArrayList<Person> PersonList) {

		if (x != null) {
			if (!x.equals(Nil)) {

				RBOrderTreeWalk(x.getLeft(), PersonList);
				PersonList.add(PersonList.size(), x.getData());
				RBOrderTreeWalk(x.getRight(), PersonList);
			}
		}
		return PersonList;
	}
	//*******************************************************************
	// Left Rotate
	//
	// Rotates the nodes so that the parent node is shifted to the left of the child node on the right.
	//*******************************************************************
	public void LeftRotate(Node<Person> x) {
		Node<Person> y = x.getRight();
		x.setRight(y.getLeft());

		if (!y.getLeft().equals(Nil)) {
			y.getLeft().setParent(x);
		}
		y.setParent(x.getParent());
		if (x.getParent().equals(Nil)) {
			root = y;
		} else if (x.equals(x.getParent().getLeft())) {
			x.getParent().setLeft(y);
		} else {
			x.getParent().setRight(y);
		}
		y.setLeft(x);
		x.setParent(y);
	}
	//*******************************************************************
	// Right Rotate
	//
	// Rotates the nodes so that the parent node is shifted to the right of the child node on the left.
	//*******************************************************************
	public void RightRotate(Node<Person> x) {

		Node<Person> y = x.getLeft();

		x.setLeft(y.getRight());

		if (!y.getRight().equals(Nil)) {
			y.getRight().setParent(x);
		}
		y.setParent(x.getParent());

		if (x.getParent().equals(Nil)) {
			root = y;

		} else if (x.equals(x.getParent().getRight())) {
			x.getParent().setRight(y);
		} else {
			x.getParent().setLeft(y);
		}
		y.setRight(x);
		x.setParent(y);
	}
	//*******************************************************************
	// RBInsert
	//
	// Inserts a node into the RB Tree. Then calls RBInsertFixup to fix the tree
	//*******************************************************************
	public void RBInsert(Node<Person> z) {

		Node<Person> y = Nil;
		Node<Person> x = root;

		while (!x.equals(Nil)) {

			y = x;

			if (z.getData().getValue() < x.getData().getValue()) {
				x = x.getLeft();

			} else {
				x = x.getRight();
			}
		}
		z.setParent(y);
		if (y.equals(Nil)) {
			root = z;

		} else if (z.getData().getValue() < y.getData().getValue()) {
			y.setLeft(z);
		} else {
			y.setRight(z);

		}
		z.setLeft(Nil);
		z.setRight(Nil);
		z.getData().setColor(Color.RED);

		RBInsertFixup(z);

	}
	//*******************************************************************
	// RBInsertFixup
	//
	// Fixes the RB Tree if the tree is violating the 5 properties
	//*******************************************************************
	private void RBInsertFixup(Node<Person> z) {
		Node<Person> y = Nil;
		while (z.getParent().getData().getColor().equals(Color.RED)) {

			if (z.getParent().equals(z.getParent().getParent().getLeft())) {

				y = z.getParent().getParent().getRight();

				if (y.getData().getColor().equals(Color.RED)) {
					z.getParent().getData().setColor(Color.BLACK);
					y.getData().setColor(Color.BLACK);
					z.getParent().getParent().getData().setColor(Color.RED);
					z = z.getParent().getParent();
				}

				else if (z.equals(z.getParent().getRight())) {
					z = z.getParent(); 
					LeftRotate(z); 
				} else {

					z.getParent().getData().setColor(Color.BLACK);
					z.getParent().getParent().getData().setColor(Color.RED);
					RightRotate(z.getParent().getParent());
				}

			} else {
				y = z.getParent().getParent().getLeft();

				// Case 1
				if (y.getData().getColor().equals(Color.RED)) {
					z.getParent().getData().setColor(Color.BLACK);
					y.getData().setColor(Color.BLACK);
					z.getParent().getParent().getData().setColor(Color.RED);
					z = z.getParent().getParent();
				}

				// Case 2: if y is black and z is a left child

				else if (z.equals(z.getParent().getLeft())) {
					z = z.getParent(); // case 2
					RightRotate(z); // case 2
				}

				// Case 3: if y is black and z is a right child
				else {

					z.getParent().getData().setColor(Color.BLACK);
					z.getParent().getParent().getData().setColor(Color.RED);
					LeftRotate(z.getParent().getParent());

				}

			}

		}
		root.getData().setColor(Color.BLACK);
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

	public Person TreeSearch(Person p) {
		Node<Person> n = IterativeTreeSearch(root, p);
		if (n == null) {
			return null;
		} else {
			return n.getData();
		}

	}

	public ArrayList<Person> InOrderTreeWalk(Node<Person> x, ArrayList<Person> PersonList) {

		if (x != Nil) {

			RBOrderTreeWalk(x.getLeft(), PersonList);

			PersonList.add(PersonList.size(), x.getData());
			RBOrderTreeWalk(x.getRight(), PersonList);
		}
		return PersonList;
	}

}
