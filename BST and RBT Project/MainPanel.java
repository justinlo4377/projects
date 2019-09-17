import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

public class MainPanel extends JPanel {
	int WIDTH;
	int HEIGHT;
	JPanel listOfSSNpanel;	// panel which handles all list related methods and inputs on the left
	JPanel drawnPicture;	// panel which handles the displayed nodes and tree on the right, 
							// will also be referenced as display pane;

	BinarySearchTree BST = null;
	RedBlackTree RBT = null;
	DefaultListModel<Person> listModel = new DefaultListModel<Person>();
	ArrayList <Person> insertedOrder;
	NodePanel n;
	Person clicked;
	JScrollPane js;
	Boolean SSNNameToggle=false;
 
	String names[] = { "Mike", "Jane", "James", "Marry", "Ben", "Mason", "Elijah", "Oliver", "Jacob", "Lucas",
			"Michael", "Sofia", "Ethan", "Daniel", "Matthew", "Ella", "Henry", "Victoria", "Jackson", "Ken" };

	//*******************************************************************
	// MainPanel
	//
	// constructor which creates the main panel that holds listOfSSNpanel, and drawnPicture
	//*******************************************************************	
	public MainPanel(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.BLACK);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		Dimension d1 = new Dimension(((5 * (WIDTH - 15) / 11)), HEIGHT - 30);
		listOfSSNpanel = new JPanel();
		listOfSSNpanel.setPreferredSize(d1);
		this.add(listOfSSNpanel);

		Dimension d2 = new Dimension(((6 * (WIDTH - 15) / 11)), HEIGHT - 30);
		drawnPicture = new JPanel();
		drawnPicture.setPreferredSize(d2);
		this.add(drawnPicture);

		setupList();
		setupDrawn();

	}
	//*******************************************************************
	// SetupList
	//
	// container method which creates the list of people in the BST and RBT
	// Also creates the panel which holds the displayable list
	// contains the 8 buttons Insert,Delete,Search,Enlarge,Show SSN/Name,
	// Convert to RB Tree, Generate new BST, Inserted Order
	//*******************************************************************
	private void setupList() {
		Color DarkBlue = new Color(66, 103, 178);
		
		//creates array of random SSN's 
		int y[] = generateRandomSSNArray();

		BST = new BinarySearchTree();
		
		//creates people and inserts them into the binary tree
		insertedOrder =new ArrayList<Person>();
		for (int i = 0; i < y.length; i++) {
			Person p = new Person(names[i], y[i]);
			BST.Insert(p);
			insertedOrder.add(insertedOrder.size(),p);
		}

		ArrayList<Person> container = new ArrayList<Person>();
		container = BST.InOrderTreeWalk(BST.root, container);

		for (Person p : container) {
			listModel.addElement(p);
		}

		listOfSSNpanel.setLayout(new BorderLayout());

		// adding the buttons
		JPanel TopBar = new JPanel();
		TopBar.setLayout(new BorderLayout());
		
		JPanel OptionBarTop = new JPanel();
		OptionBarTop.setLayout(new GridLayout(1, 4));
		OptionBarTop.setBackground(DarkBlue);
		
		JButton Insert = new JButton("Insert");
		JButton Delete = new JButton("Delete");
		JButton Search = new JButton("Search");
		JButton Enlarge = new JButton("Enlarge");
		
		
		JLabel Test = new JLabel("List below represents tree in InOrder sorted order");
		Test.setFont(new Font("Test", Font.PLAIN, 18));
		Test.setForeground(Color.WHITE);
		TopBar.setBackground(DarkBlue);
		
		OptionBarTop.add(Insert);
		OptionBarTop.add(Delete);
		OptionBarTop.add(Search);
		OptionBarTop.add(Enlarge);
		OptionBarTop.add(Test);

		JPanel OptionBarBottom = new JPanel();
		OptionBarBottom.setLayout(new GridLayout(1, 4));
		OptionBarBottom.setBackground(DarkBlue);
		
		JButton UnsortList = new JButton("Inserted Order");
		JButton SSNName = new JButton("Display Name");
		JButton ConvertRB = new JButton("Convert To RBT");
		JButton NewList = new JButton("Create New BST");

		OptionBarBottom.add(UnsortList);
		OptionBarBottom.add(SSNName);
		OptionBarBottom.add(ConvertRB);
		OptionBarBottom.add(NewList);
		
		TopBar.add(OptionBarTop, BorderLayout.NORTH);
		TopBar.add(Test, BorderLayout.SOUTH);
		
		listOfSSNpanel.add(TopBar, BorderLayout.NORTH);
		listOfSSNpanel.add(OptionBarBottom, BorderLayout.SOUTH);

		// creating the list

		JList list = new JList(listModel);	
		PeopleRenderer Render = new PeopleRenderer();
		list.setCellRenderer(Render);
		list.setFixedCellHeight(100);
		list.setFixedCellWidth(400);
		
		/* The Button Action Listeners are declared below */
	
		//*******************************************************************
		// Button Inserted Order
		//
		// Displays the order of people when they are inserted into the Tree
		//*******************************************************************
		UnsortList.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] g= new String [insertedOrder.size()];
				
				for (int i= 0; i<insertedOrder.size();i++) {
					g[i]= insertedOrder.get(i).getName()+" "+ insertedOrder.get(i).getListString();
				}
				
				JOptionPane.showInputDialog( null,"People are inserted into the tree in the order below", " ",	
						JOptionPane.PLAIN_MESSAGE, null,g , g[0]);
			}

		});
		//*******************************************************************
		// Button NewList
		//
		// generates a new Binary Search Tree when pressed
		//*******************************************************************
		NewList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				listModel.clear();
				int y[] = generateRandomSSNArray();
				RBT = null;

				BST = new BinarySearchTree();
				insertedOrder.clear();
				for (int i = 0; i < y.length; i++) {
					Person p = new Person(names[i], y[i]);
					BST.Insert(p);
					insertedOrder.add(insertedOrder.size(),p);
				}
				ArrayList<Person> container = new ArrayList<Person>();
				container = BST.InOrderTreeWalk(BST.root, container);
				
				for (Person p : container) {
					listModel.addElement(p);
					
				}

				
				repaintPanel();	//repaints the display panel;
			}

		});
		
		//*******************************************************************
		// Button Enlarge
		//
		// Creates a Node Display frame and then inputs the values of the RBT or BST based on the display panel
		// similar to repaint, but it is updating a newly created frame, not the displayed panel.
		//*******************************************************************		
		Enlarge.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Dot> dots = new ArrayList<>();

				Dot save = null;
				int x = 1500;
				int y = 100;

				if (BST.getRoot() != null) {
					if (RBT == null) {
						recursion(x, y, 300, save, BST.root, dots);	
					} else {

						RBrecursion(x, y, 300, save, RBT.root, dots);
					}
				}
				NodeDisplay ND = new NodeDisplay(n.getToggle(),dots);
				ND.repaint();
				ND.setVisible(true);
			}

		});
		//*******************************************************************
		// Button Insert
		//
		// Prompts user to enter in a ssn and a name. Then creates a person and inserts it into the BST or RBT
		//*******************************************************************			
		Insert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JTextField sField = new JTextField(10);
				JTextField vField = new JTextField(10);

				JPanel myPanel = new JPanel();

				myPanel.add(new JLabel("Name:"));
				myPanel.add(sField);
				myPanel.add(Box.createHorizontalStrut(10));
				myPanel.add(new JLabel("SSN Number ***-**-****:"));
				myPanel.add(vField);
				myPanel.add(Box.createHorizontalStrut(10));

				int result = JOptionPane.showConfirmDialog(null, myPanel,
						"Please enter the name and the SSN of the person you would like to add.",
						JOptionPane.OK_CANCEL_OPTION);

				if (result == JOptionPane.OK_OPTION) {
					String s = vField.getText();
					if (s.contains("-")) {
						s = s.substring(0, 3) + s.substring(4, 6) + s.substring(7, 11);
					} else if (s.length() >= 9) {
						s = s.substring(0, 9);
					}

					Person p = new Person(sField.getText(), Integer.parseInt(s));
					if (RBT == null) {
						listModel.clear();
						BST.Insert(p);

						ArrayList<Person> holder = new ArrayList<Person>();
						holder = BST.InOrderTreeWalk(BST.root, holder);

						for (Person j : holder) {

							listModel.addElement(j);

						}

					} 
					else {
						listModel.clear();
						RBT.Insert(p);

						ArrayList<Person> holder = new ArrayList<Person>();
						holder = RBT.InOrderTreeWalk(RBT.root, holder);

						for (Person j : holder) {

							listModel.addElement(j);

						}
					}

				}

				repaintPanel();
			}

		});
		
		//*******************************************************************
		// Button Delete
		//
		// Prompts user to click on a person.
		// Deletes that person from the BST.
		// If RBT is selected, it lets the user know that RBT deletion is not part of the project requirements
		//*******************************************************************
		Delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (RBT != null) {
					JOptionPane.showMessageDialog(null,
							"The project requirements state that only the binary search tree, and not the red black tree needs to implement delete");
				} else {
					if (clicked != null) {
						listModel.clear();

						BST.TreeDelete(clicked);

						ArrayList<Person> holder = new ArrayList<Person>();
						holder = BST.InOrderTreeWalk(BST.root, holder);
						repaintPanel();
						for (Person p : holder) {

							listModel.addElement(p);

						}
					} else if (clicked == null) {
						JOptionPane.showMessageDialog(null, "Select a person to delete");
						repaintPanel();
					}

				}
			}

		});
		//*******************************************************************
		// Button SSNName
		//
		// Will prompt the display panel to switch between the person's name and the SSN
		//*******************************************************************
		SSNName.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				n.Toggle();
				if(n.getToggle()) {
					SSNName.setText("Display SSN");
					n.repaint();
				}
				else {
					SSNName.setText("Display Name");
				}
				repaintPanel();
			}

		});
		//*******************************************************************
		// Button Search
		//
		// Prompts user to enter in a ssn. If the SSN is not valid, it lets the user know
		// If the SSN is valid, it searches the RBT or BST for the value, then it displays the person and 
		// The person's color in a J option Panel
		//*******************************************************************
		Search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s;
				int V = Integer.MAX_VALUE;
				JTextField sField = new JTextField(10);

				JPanel myPanel = new JPanel();

				myPanel.add(new JLabel("SSN"));
				myPanel.add(sField);
				myPanel.add(Box.createHorizontalStrut(10));

				int result = JOptionPane.showConfirmDialog(null, myPanel, "Enter the Person's SSN",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {

					s = sField.getText();
					if (s.contains("-")) {
						s = s.substring(0, 3) + s.substring(4, 6) + s.substring(7, 11);
					} 
					if (s.length() > 9) {
						s = s.substring(0, 9);
					}

					V = Integer.parseInt(s);
					Person p = null;

					if (RBT != null) {

						p = RBT.TreeSearch(new Person("search", V));

					} 
					else {
						p = BST.TreeSearch(new Person("search", V));
					}
					if (p == null||p.getValue()==0) {
						JOptionPane.showMessageDialog(null, "There is no person with that SSN");
					} else {
						String v = String.format("%09d", p.getValue());
						v = v.substring(0, 3) + "-" + v.substring(3, 5) + "-" + v.substring(5, 9);
						JOptionPane.showMessageDialog(null, p.getName() + " has a hashcode of " + v, "Found a Person",
								0, p.getIcon());

					}

				}
			}

		});
		//*******************************************************************
		// Button ConvertRB
		//
		// Puts all the nodes of the BST into a List in InOrder format. Then inserts all the nodes
		// to the global RBT
		//*******************************************************************
		ConvertRB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (RBT == null) {
					listModel.clear();

					ArrayList<Person> holder = new ArrayList<Person>();
					holder = BST.InOrderTreeWalk(BST.root, holder);
					insertedOrder.clear();

					RBT = new RedBlackTree();
					for (Person p : holder) {

						RBT.Insert(p);
						insertedOrder.add(insertedOrder.size(),p);
					}
					ArrayList<Person> container2 = new ArrayList<Person>();
					container2 = RBT.RBOrderTreeWalk(RBT.root, container2);

					listModel.removeAllElements();

					for (Person p : container2) {

						listModel.addElement(p);

					}
					repaintPanel();
				} else {
					// Lets user know they are already looking at an RBT
					JOptionPane.showMessageDialog(null, "You already have a Red Black Tree");	
				}
			}

		});
		//*******************************************************************
		// Mouse Listener
		//
		// returns the person selected in the list
		//*******************************************************************
		MouseListener mouseListener = new MouseAdapter() {

			public void mousePressed(MouseEvent mouseEvent) {

				JList theList = (JList) mouseEvent.getSource();

				int index = theList.locationToIndex(mouseEvent.getPoint());
				clicked = listModel.getElementAt(index);

			}
		};

		list.addMouseListener(mouseListener);

		JScrollPane jp = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JScrollBar bar = jp.getVerticalScrollBar();
		bar.setSize(10, 10);
		listOfSSNpanel.add(jp, BorderLayout.CENTER);

	}
	//*******************************************************************
	// setupDrawn
	//
	// Everything related to drawing the nodes is set up in here.
	// Graphics, panels, scroll panes, dot class, nodepanel, ect.
	//*******************************************************************
	private void setupDrawn() {
		ArrayList<Dot> dots = new ArrayList<>();

		Dot save = null;
		int x = 1500;
		int y = 100;
		if (RBT == null) {
			recursion(x, y, 500, save, BST.root, dots);
		} else {

			RBrecursion(x, y, 500, save, RBT.root, dots);
		}

		n = new NodePanel(dots);

		js = new JScrollPane(n, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		js.setPreferredSize(new Dimension(1000, 700));

		JViewport view = js.getViewport();
		view.setViewPosition(new Point(1200, 0));
		view.setSize(new Dimension(10, 10));
		js.setViewport(view);

		drawnPicture.setLayout(new BorderLayout());
		drawnPicture.add(js, BorderLayout.CENTER);

	}
	//*******************************************************************
	// repaintPanel
	//
	// when called upon, repaints/updates the panel that is displaying the tree nodes.
	//*******************************************************************
	public void repaintPanel() {
		ArrayList<Dot> dots = new ArrayList<>();

		Dot save = null;
		int x = 1500;
		int y = 100;

		if (BST.getRoot() != null) {
			if (RBT == null) {
				recursion(x, y, 500, save, BST.root, dots);
			} else {

				RBrecursion(x, y, 500, save, RBT.root, dots);
			}
		}
		
		n.replaceList(dots);

	}
	//*******************************************************************
	// recursion
	//
	// method is create to display the dots/nodes. 
	// x: x-location to put the root node
	// y: y-location to put the root node
	// c: integer representing the spacing of each node from their parent. 
	// save: node which represents the root node.
	// Btnode: nodes that are changing throughout the traversal of the BST
	// dots: Arraylist which holds the dots being painted in the BST
	//*******************************************************************
	private static void recursion(int x, int y, int c, Dot save, Node<Person> btnode, ArrayList<Dot> dots) {
		Dot Legal = new Dot(x, y, 25, btnode.getData());

		if (save != null) {
			Legal.setHead(save);
		}
		dots.add(Legal);
		save = Legal;

		// if left is empty, ignore left
		// if right is empty, ignore right
		if (btnode.getLeft() != null) {
			recursion(x - c, y + 100, c / 2, save, btnode.getLeft(), dots);
		}

		if (btnode.getRight() != null) {
			recursion(x + c, y + 100, c / 2, save, btnode.getRight(), dots);
		}

	}
	//*******************************************************************
	// RBrecursion
	//
	// Almost the exact same as recursion method above.
	// The only difference is it stops when the method hits a Nil node rather then a null 
	//*******************************************************************
	private static void RBrecursion(int x, int y, int c, Dot save, Node<Person> btnode, ArrayList<Dot> dots) {
		Person p = new Person("Nil", 0);

		if (!btnode.getData().equals(p)) {

			Dot Legal = new Dot(x, y, 25, btnode.getData());

			if (save != null) {
				Legal.setHead(save);
			}
			dots.add(Legal);
			save = Legal;

			// if left is empty, ignore left
			// if right is empty, ignore right
			if (btnode.getLeft() != null) {
				RBrecursion(x - c, y + 100, c / 2, save, btnode.getLeft(), dots);
			}

			if (btnode.getRight() != null) {
				RBrecursion(x + c, y + 100, c / 2, save, btnode.getRight(), dots);
			}

		}

	}
	// *******************************************************************
	// generateRandomSSNArray
	//
	// Creates a hashset, and puts random integers inside until the size is 20
	// Because an int's hashcode is just the int, uses an iterator to put random
	// ints into an array
	// Then returns the array
	// *******************************************************************

	private static int[] generateRandomSSNArray() {
		HashSet<Integer> h = new HashSet<Integer>(); // uses hash set to make sure every ssn is unique

		while (h.size() < 20) {
			int x = randomWithRange(1000000, 999999999); // area code numbers cant start with 000,
			h.add(x); // so range is from 001-00-0000 to 999-99-9999
		}
		Iterator<Integer> it = h.iterator(); // iterates through the hashset to get random ints
		int y = 0;
		int[] randomIntArray = new int[20];

		while (it.hasNext()) {

			randomIntArray[y] = it.next().intValue();
			y++;

		}

		return randomIntArray;
	}
	//*******************************************************************
	// randomWithRange
	//
	// returns a random integer with a preset minimum and maximum
	//*******************************************************************		
	
	static int randomWithRange(int min, int max) {

		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	// *******************************************************************
	// printArray
	//
	// Prints an array's contents with a given array. Tester method
	// *******************************************************************
	private static void PrintArray(int A[]) {
		for (int x : A) {
			if (x != Integer.MAX_VALUE) {
				System.out.print(x + " ");
			}

		}
		System.out.println(" ");

	}

}
