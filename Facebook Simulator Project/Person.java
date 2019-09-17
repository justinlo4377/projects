import java.awt.Color;
import java.awt.Graphics;
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
// Future: Will adapt to read from a text file and jpg using Buffered readers
//*******************************************************************
public class Person {
	
	LinkedList  l = new LinkedList();
	String Name; 
	Image image;


	JPanel hf= new JPanel();
	Color c;
	int RandomProfile;
	
	public Person (String name) { 
		Name = name;
		
		c = new Color(randomWithRange(1, 255),randomWithRange(1, 255),randomWithRange(1, 255));
		while (c==Color.WHITE||c==Color.BLACK) {
			c = new Color(randomWithRange(0, 255),randomWithRange(0, 255),randomWithRange(0, 255));
		}

		image = getProfile();
	}
	 
	//*******************************************************************
	// get Color
	//
	// returns the profile color of this person
	//*******************************************************************
	 Color getColor() {
		 return c;
	 }
	 
	//*******************************************************************
	// getProfile
	//
	// gets the profile from the resources folder, then creates an icon based on that profile
	//*******************************************************************
	 
	 Image getProfile() {
		 BufferedImage Bimage = null;
		 
		
		
         try {
           Bimage = ImageIO.read(new File("resources/Profile icon.png"));

         } catch (IOException e) {
             e.printStackTrace();
         }
         
         for (int col = 0; col < Bimage.getWidth(); col++) {
        	  for (int row = 0; row < Bimage.getHeight(); row++) {

        		  int border = 5;
        		  
        		  if(row<=border|col<=border||col>=Bimage.getWidth()-border||row>=Bimage.getHeight()-border) {
        			  
        			  Bimage.setRGB(col, row,Color.BLACK.getRGB());
        		  }
        		  if(Bimage.getRGB(col, row)==Color.BLACK.getRGB()) {
        			  
        			  Bimage.setRGB(col, row,Color.BLACK.getRGB());
        		  }
        		 
        		  else {
        			  Bimage.setRGB(col, row,c.getRGB());
        		  }
             	
              } 
         }
         
        image = Bimage;
        
		image =image.getScaledInstance(70, 70,image.SCALE_SMOOTH);
		  
		 return image;
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
	// removeFriend
	//
	// if the inputed person exists in this person's friendslist, removes it
	//*******************************************************************
	public void removeFriend(Person  f) {
		if (l.find(f.getName())!=null){
			l.remove(f);

		}
	}
	
	//*******************************************************************
	// addFriend
	//
	// if the inputed person does not exist in this person's friendslist, adds the person as a friend
	//*******************************************************************
	public void addFriend(Person j) {
		if(l.find(j.getName())==null) {
		l.append(j);

		}
	}
	
	//*******************************************************************
	// isFriend
	//
	// Checks the friends list if this person is friends with the given person
	//*******************************************************************	
	public boolean isFriend(Person f) {
		if(l.find(f.getName())==null) {
			return false;
		}
		else {
			return true;
		}
	}
	//*******************************************************************
	// getFriendsList
	//
	// returns a string of all people who are friends with this person
	//*******************************************************************	
	public String[] getFriendsList() {
		return FriendlisttoString();
	}
	public String getName() {
		return Name;
	}

	
	public String toString(){ 
		  return Name;  
		 }  
	//*******************************************************************
	// equals
	//
	// overides equals method. Compares if the person has the same name and color id of the person
	// it is being compared too
	//*******************************************************************	
	public boolean equals(Person p) {
		if(this.toString().compareTo(p.toString())==0){
			return true;
		}
		if(this.getColor().equals(p.getColor())){
			return true;
		}
		return false;
	}
	
	private String[] FriendlisttoString() {
		
		String fl = l.toString();
		
		return fl.split(",");
	}
	
	
	
}
