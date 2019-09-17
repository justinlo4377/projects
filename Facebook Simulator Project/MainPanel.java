import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainPanel extends JPanel {

	int width = 1000;
	int height;
	HashTable Globalht = new HashTable(); //global hashtable containing 50 names

	String names[] = {
			"Liam", "Noah","William","James","Logan","Benjamin","Mason","Elijah",
			"Oliver","Jacob","Jackson","Lucas","Michael","Alexander","Ethan","Daniel","Matthew",
			"Aiden","Henry","Joseph","Samuel","Sebastian","David","Carter","Wyatt", 
			"Emma","Olivia","Ava","Isabella","Sophia","Mia","Charlotte","Amelia","Evelyn","Abigail",
			"Harper","Emily","Elizabeth","Avery","Sofia","Ella","Madison","Scarlett","Victoria","Aria",
			"Grace","Chloe","Camila","Penelope","Riley"};
	
	String Typing = "";	//holds the input whenever the user enters a letter
	Person lmq;			//person refrenced in different methods in this class
	Boolean displayHash=false;	//holds boolean value for when user wants to change between showing hash values
	FriendsPanel fp;			//friends panel(details in friends panel class)
	DefaultListModel<Person> listModel = new DefaultListModel<Person>();	//A list model which displays the people in list
	

	public MainPanel(int w, int h) { 
		
		for(int i =0;i<names.length;i++) {
			Person P = new Person(names[i]);

			
			Globalht.chainedHashInsert(P);

		}
		names=Globalht.HashTabletoStringArr();
		for(int i =0;i<names.length;i++) {
		
			//*******************************************************************
			// Creates a person and then adds randomly 0 to 15 friends to that person.
			//*******************************************************************
			
			Person nil = Globalht.chainedHashSearch(names[i]);  
			 if(nil!=null) {
				 for(int j =0;j<randomWithRange(0,15);j++) {
					
					 	Person p = Globalht.chainedHashSearch(names[randomWithRange(0,49)]);
					 	if(!nil.equals(p)||!nil.isFriend(p)) {
					 		nil.addFriend(p);
					 		p.addFriend(nil);
					 	}
						
					}
				 
				
		   			listModel.addElement(nil);
		        }
			
		}
					

		 height = h;
		 Color DarkBlue = new Color(66, 103, 178);
		setPreferredSize(new Dimension(width, height));// Otherwise, the frame starts off small
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		 fp = new FriendsPanel(this.getWidth(),this.getHeight(),Globalht);
		
		
		fp.setPreferredSize(new Dimension(200,height));
		
		//*******************************************************************
		// Center,Top,Bottom
		//
		// Top: Only has the searchbar
		// Center: Contains the list of people displayed using the list model
		// Bottom: Contains the buttons to interact with the list and friendspanel
		//*******************************************************************

		
		JPanel Center = new JPanel();
		Center.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(1000,500));
		this.add(Center,BorderLayout.CENTER);
		
		
		JPanel Top = new JPanel();
		Top.setPreferredSize(new Dimension(width,70));
		JTextField Search = new JTextField();
		
		Search.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {

				if(Search.getText().contains("Search...")) { //Used to create faded gray search message
					Search.setText("");
				Color c = Color.BLACK;
				Search.setForeground(c);
				
				}
			}

			@Override
			public void focusLost(FocusEvent e) {	//deletes the search message when user clicks

				if(Search.getText().isEmpty()) {
				Color c = Color.GRAY;
				Search.setForeground(c);
				Search.setText("Search...");
				}
				
			}
			
		});
		
		Search.addKeyListener(new KeyListener() { //Checks the multiple options a person might do when searching

			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println(e.getKeyChar());
				
				if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE||e.getKeyChar() == KeyEvent.VK_DELETE ) {
					if(!Typing.contentEquals("")) {	//deletes part of the string if the user presses delete
					Typing = Typing.substring(0, Typing.length()-1);
					}
				if(Typing.contentEquals("")) {	//if the string has nothing, the list returns back to showing everyone
					listModel.removeAllElements();
						for(int i =0;i<names.length;i++) {
							Person nil = Globalht.chainedHashSearch(names[i]);  
							 if(nil!=null) {
						   			listModel.addElement(nil);
						        }
							
						}
					}
					
					
				}
				else if(e.getKeyChar()>='a' && e.getKeyChar()<='z'||e.getKeyChar()==KeyEvent.VK_SPACE) {
					Typing+=e.getKeyChar();	//adds the letter to the string if it is within the values a-z
				}
				else if(e.getKeyChar()>='A' && e.getKeyChar()<='Z') {
					Typing+=e.getKeyChar(); //adds the letter to the string if it is within the values A-Z
				}

				listModel.removeAllElements();

				for(int i =0;i<names.length;i++) {
					if(names[i].toLowerCase().contains(Typing.toLowerCase())) {

						Person nil = Globalht.chainedHashSearch(names[i]);   

						if(nil!=null) {					//adds a person to the list model to be displayed 
							listModel.addElement(nil);	//if that person's name contains part of the string entered
						}

					}
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
		});


		JList list = new JList(listModel); 
		
		PeopleRenderer Render = new PeopleRenderer();

		list.setCellRenderer(Render);

		list.setFixedCellHeight(80);

	    list.setFixedCellWidth(200);
	    
	    MouseListener mouseListener = new MouseAdapter() {
	        public void mouseClicked(MouseEvent mouseEvent) {
	      	
	          JList theList = (JList) mouseEvent.getSource();

	          
	            int index = theList.locationToIndex(mouseEvent.getPoint());
	            if (mouseEvent.getClickCount()>=2) {	//only receieves user input if user clicks twice on selected object
	            
	    		if(!listModel.isEmpty()) {
	    			lmq= listModel.getElementAt(index);
	    			String Friendslist[];
	    		
	    			
	    			if(lmq.getFriendsList().length<20) {
	    				Friendslist = new String[20];
	    				for(int i =0; i<lmq.getFriendsList().length;i++) {
		    				Friendslist[i]=lmq.getFriendsList()[i];
		    			}
	    			}
	    			else {
	    				Friendslist= new String[lmq.getFriendsList().length];
	    				
	    				for(int i =0; i<lmq.getFriendsList().length;i++) {
		    				Friendslist[i]=lmq.getFriendsList()[i];
		    			}
	    			}
	    			

	    			String[] options = { "Close","Add "+lmq+" as friend", "View "+lmq+ "'s Friends list", "get color of person" };

	    			int x = JOptionPane.showOptionDialog(null, "View "+lmq.getName()+"'s friends list or add "+lmq+" as a friend?", " ", JOptionPane.DEFAULT_OPTION,
	    					JOptionPane.INFORMATION_MESSAGE, lmq.getIcon(), options, options[0]);

	    			if (x == 1) {	//add friend option, searches the hashtable for a person, then adds that person, then adds the user to that person's friendlist
	    		
	    				lmq.addFriend(fp.User);
	    				fp.AddFriend(lmq);
	    				
	    				
	    			}
	    			if (x == 2) {	//simple way to display a friendslist by using a joptionshowinput dialog
	    			
	    				JOptionPane.showInputDialog( null, lmq.getName()+"'s friendlist", " ", JOptionPane.PLAIN_MESSAGE, null, Friendslist, Friendslist[0]);
	    				
	    				//views friends list
	    			}
	    			if (x==3) {
	    				JOptionPane.showMessageDialog(null, lmq.getColor());
	    			}
	    			if (x <= 0) { //exit 
	    					//exit
	    			}
	    		}
	            }
	        }
	      };
	      
	      list.addMouseListener(mouseListener);
	    
	    

	    
			
       JScrollPane jp = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
               JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
       
       
       
       JScrollBar bar = jp.getVerticalScrollBar();
       bar.setSize(10, 10);
      
       Center.add(jp, BorderLayout.CENTER);
       
   		JPanel WhiteSpace = new JPanel();
   		WhiteSpace.setPreferredSize(new Dimension(25,height));
   		Center.add(WhiteSpace, BorderLayout.EAST);
   		WhiteSpace.setForeground(Color.BLUE);
   		setVisible(true);       
		
		Search.setFont(new Font("Test", Font.PLAIN, 20));
		
		Search.setPreferredSize(new Dimension(700,50));
		Center.add(Top,BorderLayout.NORTH);
		Top.add(Search,BorderLayout.CENTER);
		
		Top.setBackground(DarkBlue);		
		
		JPanel Bottom = new JPanel();
		Bottom.setBackground(DarkBlue);
		 Bottom.setPreferredSize(new Dimension(200,30));
		 Bottom.setLayout(new GridLayout(1,5));
		 
		//check if two people are friends
		JButton AreFriends = new JButton("Check if Friends");
		Bottom.add(AreFriends);
		AreFriends.setPreferredSize(new Dimension(200,30));
		AreFriends.addActionListener(new ActionListener() {
            

			@Override
			public void actionPerformed(ActionEvent e) {
				CheckFriends();
				}
	
			
        });
		
		
		
		//check the hash code values in the hashtable
		JButton hashVal = new JButton("HashValues");
		Bottom.add(hashVal);
		hashVal.setPreferredSize(new Dimension(50,30));
		
		//*******************************************************************
		// Button HashVal
		//
		// Tells the peoplerenderer to display the hash values
		//*******************************************************************
		hashVal.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent e) {
				if(displayHash) {
					displayHash=false;
				}
				else if(!displayHash) {
					displayHash=true;
				}
				

				Render.setViewHash(displayHash);
				list.updateUI();
			}
        });
		
		JButton MultDiv = new JButton("Div/Mult Change");
		Bottom.add(MultDiv);
		MultDiv.setPreferredSize(new Dimension(50,30));
		//*******************************************************************
		// Button MultDiv
		//
		// Tells the hashtable to change their method of adding people to the array to multiplication or division
		// Then tells the people renderer to change their method of display to multiplication or division
		//*******************************************************************
		MultDiv.addActionListener(new ActionListener() {
            

			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList <Person> container = new ArrayList<Person>();
				
				for(int i =0;i<names.length;i++) {
					container.add( Globalht.chainedHashSearch(names[i]));
					
				}
				Boolean b = Globalht.getSwitchMethod();
				
				Globalht= new HashTable();
				Globalht.setSwitchMethod(!b);
				Render.setViewMethod(!b);

				for(int i =0;i<container.size();i++) {

					Globalht.chainedHashInsert(container.get(i));

				}
				names=Globalht.HashTabletoStringArr();
				
				listModel.removeAllElements();
				
				for(int i =0;i<names.length;i++) {
					Person nil = Globalht.chainedHashSearch(names[i]);  
					 if(nil!=null) {
				   			listModel.addElement(nil);
				        }
					
				}
				
				
			}
        });
		
		JPanel botFill = new JPanel();
		botFill.setOpaque(false);
		Bottom.add(botFill);
//		
		JButton B = new JButton("Friends List");
		Bottom.add(B);
		this.add(Bottom,BorderLayout.SOUTH);
		B.setPreferredSize(new Dimension(200,30));
		//*******************************************************************
		// Button FriendsList
		//
		// Simple set visible/set notvisisble friendlist button
		//*******************************************************************
		B.addActionListener(new ActionListener() {
            

			@Override
			public void actionPerformed(ActionEvent e) {
				if(fp.isVisible()) {
					fp.setVisible(false);
				}
				else if(!fp.isVisible()) {
					fp.setVisible(true);
				}
			}
        });
		
		
		this.add(fp,BorderLayout.EAST);


		
	}
	//*******************************************************************
	// Method CheckFriends
	//
	// Checks if the user entered valid people.
	// Then checks if the friends list of those people are friends with each other, in the hasharray.
	// Then checks if the user is one of those people
	// Has a fail safe for if the user is the same name as one of those people.
	//*******************************************************************
	private void CheckFriends() {

		JTextField sField = new JTextField(10);
		JTextField vField = new JTextField(10);


		JPanel myPanel = new JPanel();
		
		myPanel.add(new JLabel("Person 1:"));	
		myPanel.add(sField);
		myPanel.add(Box.createHorizontalStrut(10));	
		myPanel.add(new JLabel("Person 2:"));
		myPanel.add(vField);
		myPanel.add(Box.createHorizontalStrut(10));	


		int result = JOptionPane.showConfirmDialog(null, myPanel, "Please enter the two people you would like to check",
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
		
		String per1= setNameCapital(sField.getText());  //set name capital is a custom method
		String per2= setNameCapital(vField.getText());
		
		Person f1=Globalht.chainedHashSearch(per1);
		Person f2=Globalht.chainedHashSearch(per2); 
		
		

			if(f1 == null && !fp.getUser().getName().equals(per1)) {
				JOptionPane.showMessageDialog( null, "Person 1's name does not exist", "", JOptionPane.WARNING_MESSAGE);
			
	
				}
			
			else if(f2 == null && !fp.getUser().getName().equals(per2)) {
				JOptionPane.showMessageDialog( null, "Person 2's name does not exist", "", JOptionPane.WARNING_MESSAGE);
			
				}
			else if(per1.equalsIgnoreCase(fp.getUser().getName())&&per2.equalsIgnoreCase(fp.getUser().getName())) {
				//case where user is friends with user?
				JOptionPane.showMessageDialog( null, "You are friends with yourself", "", JOptionPane.DEFAULT_OPTION);
				
				
				
			}
			

			//person 2 is friends with user, but not with other person of same name
			else {
			JFrame  f = new JFrame(""); 
			
			f.setSize(460, 160); 
			
	        
			JDialog d = new JDialog(f, ""); 
			d.setSize(f.getWidth(), f.getHeight());
			d.setLayout(new GridLayout(3,1,15,15));
			
			d.setLocationRelativeTo(null);
			
			Box box = Box.createHorizontalBox();
			JLabel personName1;
			Image image1;
			if(f1!=null) {
				personName1= new JLabel(f1.getName());
				image1 = f1.image.getScaledInstance(30, 30, f1.image.SCALE_SMOOTH);
			}
			else{
				personName1= new JLabel("You");
				image1 = fp.getUser().image.getScaledInstance(30, 30, fp.getUser().image.SCALE_SMOOTH);
			}			
			personName1.setFont(new Font("Test", Font.PLAIN, 20));
			box.add(personName1);		
			JLabel PersonLable1 = new JLabel();
			
			PersonLable1.setIcon(new ImageIcon(image1));
			box.add(PersonLable1);
			
			
			JLabel personName2;
			Image image2;	
			if(f2!=null) {
				personName2=  new JLabel(" and "+ f2.getName());
				image2 = f2.image.getScaledInstance(30, 30, f2.image.SCALE_SMOOTH);	
			}
			else{
				personName2= new JLabel(" and You");
				image2 = fp.getUser().image.getScaledInstance(30, 30, fp.getUser().image.SCALE_SMOOTH);	
			}
		
			personName2.setFont(new Font("Test", Font.PLAIN, 20));
			box.add(personName2);
			JLabel PersonLable2 = new JLabel();
					
			PersonLable2.setIcon(new ImageIcon(image2));
			
			box.add(PersonLable2);
			
			JLabel yourname = new JLabel();
			
			JLabel arefriends = null; 

			if(f1==null) {
				
				if(fp.getUser().isFriend(f2)){
					arefriends = new JLabel(" are friends");
				}
				else {
					arefriends = new JLabel(" are not friends");
				}
			}
			else if(f2==null){
				if(fp.getUser().isFriend(f1)){
					arefriends = new JLabel(" are friends");
				}
				else {
					arefriends = new JLabel(" are not friends");
				}
			}
			else if(f1.equals(f2)){
				arefriends = new JLabel(" are the same person!");
			}
			else if(f1.isFriend(f2)&&f2.isFriend(f1)) {
				arefriends = new JLabel(" are friends");
				
			}
			else  {
				arefriends = new JLabel(" are not friends");
				
				if(fp.getUser().isFriend(f1)) {
					yourname = new JLabel("You are friends with "+f2+" however*");
				}
				else if(fp.getUser().isFriend(f2)) {
					yourname = new JLabel(f2+" is friends with You however*");
				}
			}
			
			arefriends.setFont(new Font("Test", Font.PLAIN, 20));
			box.add(arefriends);
			
			JPanel closepanel = new JPanel();
			closepanel.setLayout(new BorderLayout());
			JButton closepane = new JButton("Close");
			closepane.addActionListener(new ActionListener() {
            
			@Override
			public void actionPerformed(ActionEvent e) {
				
				d.dispose();
			}
        });
			

			JPanel whitespace = new JPanel();
			whitespace.setVisible(true);
			
			d.add(whitespace);
			
			JPanel whitespace2 = new JPanel();
			
			whitespace2.add(box);
			d.add(whitespace2);
			
			closepanel.add(closepane,BorderLayout.EAST);
			closepanel.add(yourname,BorderLayout.WEST);
			d.add(closepanel);			
			d.setVisible(true);
			
			
			}
		}
		
	}
	//*******************************************************************
	// Method setNameCaptital
	//
	// Forcefully set a name to captitalize the first letter, and lowercase the rest of the letters.
	//*******************************************************************
	
	private String setNameCapital(String s) {
		String n =s.substring(0,1).toUpperCase();
		String n2 =s.substring(1).toLowerCase();
		 n= n+n2;
		return n;
		
	}
	//*******************************************************************
	// Method randomWithRange
	//
	// standard methods to return a number between two ranges
	//*******************************************************************
	static int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}

}
