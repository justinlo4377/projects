import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class Display extends JFrame implements ActionListener
{
	private JButton[] buttons;
	private JLabel Prompt;
	public String s;

	//*******************************************************************
	// Display
	//
	// Note* Im not sure if this is a proper way of creating an interface. This class was created just for fun.
	//
	// Creates a series of radio buttons and a single label to display onto a panel.
	// Adds radio buttons to a group, to determine which one is selected. 
	// Adds radio buttons to panel
	// When a Radio button is pressed, the action listener changes the global string S. to the action called.
	// I.E. Pressing the JRadio button "New Random SSN" will change String s to "New Random SSN". 
	//*******************************************************************

	public Display(){

		Prompt = new JLabel("Each sort uses the list displayed below. To change the list, use: New Random SSN ");
		Prompt.setHorizontalAlignment((int) CENTER_ALIGNMENT);		//makes the jLable text appear in the middle
		Prompt.setPreferredSize(new Dimension(50, 50));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		buttonPanel.setAlignmentY(CENTER_ALIGNMENT);

		buttonPanel.setLayout( new GridLayout(0, 4) );

		buttons = new JButton[5];

		ButtonGroup group = new ButtonGroup();						//Creates buttons and then adds them to the group

		JRadioButton GRssn = new JRadioButton("New Random SSN");
		group.add(GRssn);
		buttonPanel.add(GRssn);
		GRssn.addActionListener( this );

		JRadioButton Quick = new JRadioButton("QuickSort");
		group.add(Quick);
		buttonPanel.add(Quick);
		Quick.addActionListener( this );

		JRadioButton Bucket = new JRadioButton("BucketSort");

		group.add(Bucket);
		buttonPanel.add(Bucket);
		Bucket.addActionListener( this );

		JRadioButton Radix = new JRadioButton("RadixSort");
		group.add(Radix);
		buttonPanel.add(Radix);
		Radix.addActionListener( this );

		buttons[1]  = new JButton("New Random SSN");
		buttons[2]  = new JButton("QuickSort");
		buttons[3]  = new JButton("BucketSort");
		buttons[4]  = new JButton("RadixSort");



		getContentPane().add(Prompt, BorderLayout.NORTH);			//Chooses where to display buttons
		getContentPane().add(buttonPanel, BorderLayout.WEST);
		setResizable( false );

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {					//Changes global string s to the action command

		//System.out.println(e.getActionCommand().toString());
		s = e.getActionCommand().toString();
		//System.out.println(s);
	}
	
//	public static void main(String[] args)
//	{
//		//UIManager.put("Button.margin", new Insets(10, 10, 10, 10) );
//		Display frame = new Display();
//		//frame.getContentPane().setPreferredSize(new Dimension(200, 200));
//		
//		frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
//		frame.pack();
//		frame.setLocationRelativeTo( null );
//		frame.setVisible(true);
//	}

}
