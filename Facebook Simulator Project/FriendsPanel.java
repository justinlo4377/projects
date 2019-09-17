import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FriendsPanel extends JPanel{
	int w;
	int h;
	int index =0;
	Person User; 
	DefaultListModel<Person> listModel = new DefaultListModel<Person>();
		
	public FriendsPanel(int width, int height, HashTable globalht) {
		String m = JOptionPane.showInputDialog("Enter Your First Name");
		m= setNameCapital(m);
		User= new Person(m);

		
		for(String s :User.getFriendsList()) {	//adds people to the user's friendlist 
			Person p = globalht.chainedHashSearch(s);
		listModel.addElement(p);	
		}
		
		width = w;
		height = h;

		this.setLayout(new BorderLayout());				
		this.setPreferredSize(new Dimension(120,650));	//heights and widths and colors.

		JPanel FriendModify = new JPanel();
		FriendModify.setLayout(new BorderLayout());
		
		JPanel FriendModifyup = new JPanel();
		JPanel FriendModifydown = new JPanel();
		
		FriendModify.setPreferredSize(new Dimension(120,160));
		
		FriendModifyup.setPreferredSize(new Dimension(120,70));
		FriendModifydown.setPreferredSize(new Dimension(120,90));
		
		
		
		Color DarkBlue = new Color(66, 103, 178);
		
		JPanel FriendListBorder = new JPanel();
		JLabel tf = new JLabel("Friends List");
		FriendListBorder.setBackground(DarkBlue);
		FriendListBorder.add(tf);
		
		tf.setPreferredSize(new Dimension(100,25));
		tf.setBackground(Color.WHITE);
		tf.setForeground(Color.WHITE);
		
		FriendModifyup.setBackground(DarkBlue);
		
		FriendModifyup.setLayout(new BorderLayout());
		
		JLabel iconpic = new JLabel(User.getIcon());
		FriendModifyup.add(iconpic,BorderLayout.WEST);
		
		JLabel Username = new JLabel(User.getName(), SwingConstants.CENTER);
		Username.setForeground(Color.WHITE);
		
		Username.setFont(new Font("Test", Font.PLAIN, 25));
		FriendModifyup.add(Username,BorderLayout.CENTER);		
		FriendModifydown.setBackground(this.getBackground());
		this.add(FriendModify,BorderLayout.NORTH);
			
		//takes an image from the resources folder, and displays it as the ? button
		Image image = null;
				
	         try {
	           image = ImageIO.read(new File("resources/help.png"));
	         } catch (IOException e) {
	             e.printStackTrace();
	         }

			  image =image.getScaledInstance(30, 30,image.SCALE_SMOOTH);
			
			  

		ImageIcon icon = new ImageIcon(image);
		
		
		
		
		JButton add = new JButton();
		add.setIcon(icon); 

		add.setPreferredSize(new Dimension(50,50));
		
		//*******************************************************************
		// add/help button
		//
		// Displays a message to inform the user on how to use this UI
		//*******************************************************************
		add.addActionListener(new ActionListener() {
            

			@Override
			public void actionPerformed(ActionEvent e) {	
				JOptionPane.showMessageDialog(null, "Double click a person for more actions\nTo Remove, select a person in the friends list and then click the remove button"
						+ "	\nHashvalues displays the method used to calculate the person's position in the HashTable\nDiv Mult Change changes the method used to calculate the person's "
						+ "position in the HashTable");
			
			}
        });
		
		//*******************************************************************
		// remove button
		//
		// Removes a selected person from the list. 
		// Then searches the global hashtable for that person and removes you
		// from that persons list.
		//*******************************************************************
		JButton remove = new JButton();
		  
		  remove.setLayout(new GridLayout(2,1));
		   JLabel remove1 = new JLabel("Remove");
		   remove1.setHorizontalAlignment(JLabel.CENTER);
		   JLabel remove2 = new JLabel("Friend");
		   remove2.setHorizontalAlignment(JLabel.CENTER);
		   remove.add(remove1);
		   remove.add(remove2);
		   remove.setPreferredSize(new Dimension(130,50));
		remove.addActionListener(new ActionListener() {
            

			@Override
			public void actionPerformed(ActionEvent e) {
				if(User.l.getSize()>0){
					Person p= listModel.getElementAt(index);
					User.removeFriend(p);
					listModel.remove(index);
					 globalht.chainedHashSearch(p.getName()).removeFriend(User);	//finds the person selected and removes user from friends list
				}
				
				
			}
        });
		
		
		FriendModifydown.add(remove,BorderLayout.WEST);
		FriendModifydown.add(add,BorderLayout.EAST);
		
		FriendModify.add(FriendModifyup,BorderLayout.NORTH);
		FriendModify.add(FriendModifydown,BorderLayout.SOUTH);
		
		tf.setPreferredSize(new Dimension(190,20));
		
		FriendListBorder.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		FriendListBorder.setVisible(true);
		FriendModifydown.add(FriendListBorder,BorderLayout.SOUTH);
	
		FriendModifydown.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		 
		JList list = new JList(listModel);
		 PeopleRenderer Render = new PeopleRenderer();	 
		list.setCellRenderer(Render);
		list.setFixedCellHeight(41);
	    list.setFixedCellWidth(100);
		//*******************************************************************
		// Mouselistener
		//
		// Identical selector method to the main class.
	    // Selects a person in the collum closest to where the mouse is click
	    // Only detects clicks if the mouse in within the list frame.
	    // Takes two clicks to do more actions.
		//*******************************************************************
	    MouseListener mouseListener = new MouseAdapter() {
	        public void mouseClicked(MouseEvent mouseEvent) {
	      	
	          JList theList = (JList) mouseEvent.getSource();
	           int index = theList.locationToIndex(mouseEvent.getPoint());
	            

	            if (mouseEvent.getClickCount()>=2) {
	            	if(!listModel.isEmpty()&&listModel.getElementAt(0)!=null) {
	            	
	            		
	            		Person p= listModel.getElementAt(index);
	            		p= listModel.getElementAt(index);
	            		String Friendslist[];


	            		if(p.getFriendsList().length<20 ) {
	            			Friendslist = new String[20];
	            			for(int i =0; i<p.getFriendsList().length;i++) {
	            				Friendslist[i]=p.getFriendsList()[i];
	            			}
	            		}
	            		else {
	            			Friendslist= new String[p.getFriendsList().length];

	            			for(int i =0; i<p.getFriendsList().length;i++) {
	            				Friendslist[i]=p.getFriendsList()[i];
	            			}
	            		}



	            		String[] options = { "Close", "View "+p+ "'s Friends list" };

	            		int x = JOptionPane.showOptionDialog(null, "View "+p.getName()+"'s friends list?", " ", JOptionPane.DEFAULT_OPTION,
	            				JOptionPane.INFORMATION_MESSAGE, p.getIcon(), options, options[0]);


	            		if (x == 1) {
	            			JOptionPane.showInputDialog( null, p.getName()+"'s friendlist", " ", JOptionPane.PLAIN_MESSAGE, null, Friendslist, Friendslist[0]);

	            			//views friends list
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
       
        this.add(jp, BorderLayout.CENTER);
       
        setVisible(true);
		
	}
	//*******************************************************************
	// Add Friend
	//
	// Method called from main class to add a person to your friendslist
	//*******************************************************************
	public void AddFriend(Person j) {
	
		listModel.clear();
		User.addFriend(j);
		
		
		for(int i =0; i<User.getFriendsList().length;i++) {
			
			listModel.addElement(User.l.find(User.getFriendsList()[i]).getData()); //traverses the friends list and returns a person node's data container the person

		}

	}
	//*******************************************************************
	// getUser
	//
	// returns the user
	// returns the person object representing you.
	//*******************************************************************
	public Person getUser() {
		
		return this.User;
	}

	static int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	private void SetBorder(Border b) {
		this.setBorder(b);
	}
	
	private String setNameCapital(String s) {
		String n =s.substring(0,1).toUpperCase();
		String n2 =s.substring(1).toLowerCase();
		 n= n+n2;
		return n;
		
	}
	

}
