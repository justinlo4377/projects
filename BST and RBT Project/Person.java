import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
//*******************************************************************
// Person class
//
// Creates a person with a random color by entering a string name.
//*******************************************************************
public class Person {
	

	String name; 
	Image image;
	int value;

	JPanel hf= new JPanel();
	Color c;
	int RandomProfile;
	
	public Person (String Name,int Value) { //constructor
		name = Name;
		value = Value;
		
		c = Color.BLACK;

		image = setProfile(c);
	}
	 
	//*******************************************************************
	// get Color
	//
	// returns the profile color of this person
	//*******************************************************************
	 Color getColor() {
		 return c;
	 }
	 void setColor(Color b) {
		 c=b;
	 }
	 
	//*******************************************************************
	// getProfile
	//
	// gets the profile from the resources folder, then creates an icon based on that profile
	//*******************************************************************
	 
	 Image setProfile(Color c) {
		 BufferedImage Bimage = new BufferedImage (100, 100,BufferedImage.TYPE_INT_ARGB);
		 Graphics2D g =  (Graphics2D) Bimage.getGraphics();
		 g.setColor(c);
		 g.setStroke(new BasicStroke(5));
		 g.drawOval(25, 25, 50, 50);
		    
         
        image = Bimage;
        
		image =image.getScaledInstance(100, 100,image.SCALE_SMOOTH);
		  
		 return image;
	 }
	Image getImage() {
		
		return setProfile(c);
	}
	int randomWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	//*******************************************************************
	// getIcon
	//
	// returns the icon of this person
	//*******************************************************************	
	ImageIcon getIcon()
	{
	
	  return new ImageIcon(image);
	}
	//*******************************************************************
	// getDisplayString
	//
	// returns the String displayed in the panels
	//*******************************************************************
	public String getDisplayString() {
		String s =String.format("%09d", value);
		s=s.substring(0,3)+"-\n"+s.substring(3,5)+"-\n"+s.substring(5,9);
		
		return s;
	}
	//*******************************************************************
	// getListString
	//
	// returns the String displayed in the In Order List
	//*******************************************************************
	public String getListString() {
		String s =String.format("%09d", value);
		s=s.substring(0,3)+"-"+s.substring(3,5)+"-"+s.substring(5,9);

		return s;
	}
	public String getName() {
		return name;
	}
	public int getValue() {
		return value;
	}

	
	public String toString(){ 
		  return name+" "+value;  
		 }  
	//*******************************************************************
	// equals
	//
	// Overides equals method. Compares if the person has the same name of the person
	// it is being compared too
	//*******************************************************************	
	public boolean equals(Person p) {
		if(this.toString().compareTo(p.toString())==0){
			if(this.getValue()==p.getValue()) {
				return true;
			}
		
		}

		return false;
	}
	

	
	
	
}
