import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.ImageIcon;

public class PeopleRenderer extends JLabel implements ListCellRenderer<Person> {
	Boolean b = false;
	Boolean c = true;

	//*******************************************************************
	// PeopleRenderer
	//
	// A customized listcellrenderer which only works on a person 
	// Will display the person's name and their icon 
	// Has the choice to display their hashing method (Multiplication/Division) or to show it or not at all.
	//*******************************************************************

	@Override
	public Component getListCellRendererComponent(JList<? extends Person> list, Person person, int index,
			boolean isSelected, boolean cellHasFocus) {

		if(person!=null) {
			
			ImageIcon i = new ImageIcon(person.image);
			
			setIcon(i);
			 setIconTextGap(20);

			 if(b==true) {
				 int j;
				 if(c==true) {
					 j = person.hashCode()%13;
					 this.setText(person.toString()+"\t	\t	 Division Method: Hashcode%13 = Position: "+j);
				 }
				 else {
					 double A = (Math.sqrt(5)-1)/2;
					 j = (int) (13*((person.hashCode()*A)%1));
					 this.setText(person.toString()+"\t	\tMultiplication Method: 13*((Hashcode*A)%1)) = Position: "+j);
				 }
				
		
			 }
			 else if(b==false) {
				 this.setText(person.toString());
			 }
			 
			 
			
			setOpaque(false);
			
			if (isSelected)

		      {
		        setBackground(list.getSelectionBackground());
		      }
		      else
		      {
		         setBackground(list.getBackground());
		      }

		      setFont(list.getFont());
		      
		      if (isSelected && cellHasFocus)
		         setBorder(BorderFactory.createEtchedBorder(1));
		      else
		    	  this.setBorder(BorderFactory.createEtchedBorder(0));
		      
		 
			}
			return this;
	}
	//*******************************************************************
	// SetViewHash
	//
	// displays the hashvalues or not
	//*******************************************************************

	public void setViewHash(Boolean displayHash) {

		b = displayHash;
	}
	//*******************************************************************
	// SetViewMethod
	//
	// displays either division or multiplication method
	//*******************************************************************
	public void setViewMethod(Boolean displayMethod) {
		c = displayMethod;
	}
}
