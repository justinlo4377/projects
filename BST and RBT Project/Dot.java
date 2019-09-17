import java.awt.Color;



//*******************************************************************
// Class Dot
//
// Basic class which holds all the information needed to paint on a panel/frame 
// Takes in a person in the constructor as the basis for the dot's information
//*******************************************************************	
public class Dot 
{

	private int x = 0;
	private int y = 0;
	private int radius=0;
	public int number=0;
	public String name;
	public Color c;
	public Dot head;
	
	public Dot(int x, int y, int radius, Person person ) {

		c = person.getColor();
		this.x = x;
		this.y = y;
		this.radius = radius;
		
		number= person.getValue();
		name = person.getName();

	}
	//*******************************************************************
	// getDisplayString
	//
	// returns the String displayed in the panels
	//*******************************************************************
	public String getDisplayString() {
		String s =String.format("%09d", number);
		s=s.substring(0,3)+"-\n"+s.substring(3,5)+"-\n"+s.substring(5,9);

		return s;
	}
	//*******************************************************************
	// getListString
	//
	// returns the String displayed in the In Order List
	//*******************************************************************
	public String getListString() {
		String s =String.format("%09d", number);
		s=s.substring(0,3)+"\n-"+s.substring(3,5)+"\n-"+s.substring(5,9);

		return s;
	}
	public int getNumber() {
		return number;
	}
	public String getName() {
		return name;
	}
	public Color getColor() {
		return c;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getRadius() {
		return radius;
	}
	public String toString() {
		
		return c.toString()+" "+this.getX()+" "+this.getY()+" "+this.radius;
	}

	//needed if the Dot Is at the root of the Tree Node
	
	public void setHead(Dot save) {
		head = save;
	}
	public Dot getHead() {
		return head;
	}
	

}
